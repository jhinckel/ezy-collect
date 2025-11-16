package com.ezy.collect.rest.error;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class FieldErrors {

    public static final String DEFAULT_MESSAGE_ANNOTATION_ATTRIBUTE = "message";
    public static final String INVALID_FIELD = "Invalid field.";

    public static final String VALUE_TOO_BIG_MESSAGE = "Value too big for field '${field}'.";
    public static final String REQUIRED_VALUE = "Field '${field}' is required.";
    public static final String VALUE_MUST_BE_GREATER_THAN_ZERO = "The value for '${field}' must be greater than 0.";
    public static final String INVALID_VALUE = "Value '${value}' is invalid for field '${field}'.";

    private static final String FIELD = "${field}";
    private static final String VALUE = "${value}";

    public static String formatMessage(String message, String field) {
        return formatMessage(message, field, null);
    }

    public static String formatMessage(String message, String field, Object value) {
        Map<String, Object> map = new HashMap<>();
        String formattedMessage;

        map.put(FIELD, field);

        if (Objects.nonNull(value)) {
            map.put(VALUE, value);
            formattedMessage = message;
        } else {
            formattedMessage = REQUIRED_VALUE;
        }

        for (Map.Entry<String, Object> entry : map.entrySet()) {
                formattedMessage = formattedMessage.replace(entry.getKey(), entry.getValue().toString());
        }
        return formattedMessage;
    }

    @Getter
    public enum ErrorMessageHandler {

        NOT_NULL(NotNull.class, REQUIRED_VALUE),
        NOT_EMPTY(NotEmpty.class, INVALID_VALUE),
        NOT_BLANK(NotBlank.class, INVALID_VALUE),
        POSITIVE(Positive.class, VALUE_MUST_BE_GREATER_THAN_ZERO),
        PATTERN(Pattern.class, INVALID_VALUE),
        EMAIL(Email.class, INVALID_VALUE),
        SIZE(Size.class, INVALID_VALUE);

        private static final Map<String, Optional<String>> ENUM_MAP = new HashMap<>();

        static {
            for (ErrorMessageHandler error : ErrorMessageHandler.values()) {
                ENUM_MAP.put(error.getValidationClass().getSimpleName(), Optional.ofNullable(error.getMessage()));
            }
        }

        private Class<?> validationClass;
        private String message;

        private ErrorMessageHandler(Class<?> validationClass, String message) {
            this.validationClass = validationClass;
            this.message = message;
        }

        public static Optional<String> fromClass(Class<?> validationClass) {
            if (!Objects.isNull(validationClass)) {
                return ENUM_MAP.getOrDefault(validationClass.getSimpleName(), Optional.empty());
            }
            return Optional.empty();
        }
    }
}
