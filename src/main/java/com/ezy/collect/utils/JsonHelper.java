package com.ezy.collect.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ezy.collect.config.DateTimeConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class JsonHelper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        JavaTimeModule timeModule = new JavaTimeModule();

        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeConfig.DEFAULT_DATE_FORMATTER));
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeConfig.DEFAULT_DATE_TIME_SERIALIZE_FORMATTER));

        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeConfig.DEFAULT_DATE_FORMATTER));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeConfig.CUSTOM_DATE_TIME_DESERIALIZE_FORMATTER));

        OBJECT_MAPPER.registerModule(timeModule);
        OBJECT_MAPPER.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        OBJECT_MAPPER.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(com.fasterxml.jackson.core.JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        
        OBJECT_MAPPER.findAndRegisterModules();
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static String toJsonString(final Map<String, Object> objectMap) {
        try {
            return OBJECT_MAPPER.writeValueAsString(objectMap);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    public static <T> T getObjectFromString(String json, Class<T> clazz) throws JsonMappingException, JsonProcessingException {
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    public static Map<String, Object> readValue(String content) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(content, new TypeReference<>() { });
    }

    public static String writeValueAsString(Object value) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(value);
    }

}
