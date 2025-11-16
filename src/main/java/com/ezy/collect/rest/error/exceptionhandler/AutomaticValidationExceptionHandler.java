package com.ezy.collect.rest.error.exceptionhandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ezy.collect.domain.model.ErrorMessage;
import com.ezy.collect.rest.error.ErrorResponse;
import com.ezy.collect.rest.error.FieldErrors;
import com.ezy.collect.rest.error.FieldErrors.ErrorMessageHandler;
import com.ezy.collect.utils.RegexUtils;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AutomaticValidationExceptionHandler {

    /**This method deal with the automatic validation made by jackson*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("methodArgumentNotValidException {}", ex.getMessage());
        List<DetailedError> detailedErrors = ex.getFieldErrors().stream()
                .map(fe -> buildInvalidArgumentDetailedError(fe, ex))
                .flatMap(Collection::stream)
                .toList();

        return new ErrorResponse(ErrorMessage.INVALID_FIELD, detailedErrors);
    }


    /** This method deal with values in an improper format, like a date wrong date*/
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse invalidFormatException(InvalidFormatException ex) {
        log.warn("invalidFormatException {} {}", ex.getValue(), ex.getMessage());
        String field = ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .findFirst().orElse(null);

        String message = FieldErrors.formatMessage(FieldErrors.INVALID_VALUE, field, ex.getValue());
        return new ErrorResponse(ErrorMessage.INVALID_FIELD, new DetailedError(field, message));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse illegalArgumentException(IllegalArgumentException ex) {
        log.warn("illegalArgumentException {}", ex.getMessage());
        return new ErrorResponse(
                ErrorMessage.INVALID_PARAMETER.getCode(),
                String.format(ErrorMessage.INVALID_PARAMETER.getError(), ex.getMessage()),
                ErrorMessage.INVALID_PARAMETER.getError(),
                ex.getMessage()
        );
    }

    private List<DetailedError> buildInvalidArgumentDetailedError(FieldError fe, MethodArgumentNotValidException ex) {
        List<DetailedError> detailedErrors = new ArrayList<>();
        Object customException = ex.getBindingResult().getTarget();

        try {
            Field declaredField = customException.getClass().getDeclaredField(fe.getField());
            Annotation[] annotations = declaredField.getAnnotations();

            if (annotations != null && annotations.length != 0) {
                for (Annotation annotation : declaredField.getAnnotations()) {
                    Optional<String> messageTemplate = getMessageTemplate(fe, annotation);

                    messageTemplate.ifPresent(template -> {
                        detailedErrors.add(new DetailedError(fe.getField(), FieldErrors.formatMessage(template, fe.getField(), fe.getRejectedValue())));
                    });
                }
            } else {
                detailedErrors.add(new DetailedError(fe.getField(), fe.getDefaultMessage()));
            }
        } catch (Exception e) {
           log.warn("Error getting validation error message.", e);

           detailedErrors.add(new DetailedError(fe.getField(), FieldErrors.formatMessage(fe.getDefaultMessage(), fe.getField(), fe.getRejectedValue())));
        }
        return detailedErrors;
    }

    /*
     * If some day we need another attribute besides "message", we can cast the annotation to correct type to get the fields.
     *
     * Ex.:
     *      if (annotation instanceof Pattern) {
     *          Pattern pattern = (Pattern) annotation;
     *          String attributeMessage = (String) type.getMethod("regexp").invoke(annotation)
     *
     *          messageTemplate += " " + <BUILD ADDITIONAL MESSAGE INFO HEHE>;
     *      }
     */
    private Optional<String> getMessageTemplate(FieldError fe, Annotation annotation) throws Exception {
        Class<? extends Annotation> type = annotation.annotationType();
        Optional<String> messageTemplate = Optional.empty();

        if (type.getSimpleName().equals(fe.getCode())) {
            String classMessage = (String) type.getMethod(FieldErrors.DEFAULT_MESSAGE_ANNOTATION_ATTRIBUTE).invoke(annotation);

            if (RegexUtils.isTextInsideBraces(classMessage)) {
                messageTemplate = ErrorMessageHandler.fromClass(type);
            } else {
                messageTemplate = Optional.ofNullable(classMessage);
            }
        }
        return messageTemplate;
    }

}
