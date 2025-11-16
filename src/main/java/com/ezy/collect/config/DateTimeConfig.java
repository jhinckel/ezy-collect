package com.ezy.collect.config;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeConfig {

    public static final String DEFAULT_DATE_FORMAT                = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_SERIALIZE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private static final String ISO_DATE_TIME_DESERIALIZE_FORMAT          = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String HYPHEN_SPACE_DATE_TIME_DESERIALIZE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String SLASH_DATE_TIME_DESERIALIZE_FORMAT        = "yyyy/MM/dd'T'HH:mm:ss";
    private static final String SLASH_SPACE_DATE_TIME_DESERIALIZE_FORMAT  = "yyyy/MM/dd HH:mm:ss";

	public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
    public static final DateTimeFormatter DEFAULT_DATE_TIME_SERIALIZE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_SERIALIZE_FORMAT);
    public static final DateTimeFormatter HYPHEN_SPACE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(HYPHEN_SPACE_DATE_TIME_DESERIALIZE_FORMAT);

	public static final DateTimeFormatter CUSTOM_DATE_TIME_DESERIALIZE_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern(ISO_DATE_TIME_DESERIALIZE_FORMAT + ".SSSSSSSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(ISO_DATE_TIME_DESERIALIZE_FORMAT + ".SSSSSSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(ISO_DATE_TIME_DESERIALIZE_FORMAT + ".SSSSSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(ISO_DATE_TIME_DESERIALIZE_FORMAT + ".SSSSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(ISO_DATE_TIME_DESERIALIZE_FORMAT + ".SSSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(ISO_DATE_TIME_DESERIALIZE_FORMAT + ".SSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(ISO_DATE_TIME_DESERIALIZE_FORMAT + ".SSS"))
            .appendOptional(DateTimeFormatter.ofPattern(ISO_DATE_TIME_DESERIALIZE_FORMAT + ".SS"))
            .appendOptional(DateTimeFormatter.ofPattern(ISO_DATE_TIME_DESERIALIZE_FORMAT + ".S"))
            .appendOptional(DateTimeFormatter.ofPattern(ISO_DATE_TIME_DESERIALIZE_FORMAT))
            .appendOptional(DateTimeFormatter.ofPattern(SLASH_DATE_TIME_DESERIALIZE_FORMAT + ".SSSSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(SLASH_DATE_TIME_DESERIALIZE_FORMAT + ".SSSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(SLASH_DATE_TIME_DESERIALIZE_FORMAT + ".SSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(SLASH_DATE_TIME_DESERIALIZE_FORMAT + ".SSS"))
            .appendOptional(DateTimeFormatter.ofPattern(SLASH_DATE_TIME_DESERIALIZE_FORMAT + ".SS"))
            .appendOptional(DateTimeFormatter.ofPattern(SLASH_DATE_TIME_DESERIALIZE_FORMAT + ".S"))
            .appendOptional(DateTimeFormatter.ofPattern(SLASH_DATE_TIME_DESERIALIZE_FORMAT))
            .appendOptional(DateTimeFormatter.ofPattern(HYPHEN_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".SSSSSSSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(HYPHEN_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".SSSSSSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(HYPHEN_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".SSSSSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(HYPHEN_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".SSSSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(HYPHEN_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".SSSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(HYPHEN_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".SSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(HYPHEN_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".SSS"))
            .appendOptional(DateTimeFormatter.ofPattern(HYPHEN_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".SS"))
            .appendOptional(DateTimeFormatter.ofPattern(HYPHEN_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".S"))
            .appendOptional(DateTimeFormatter.ofPattern(HYPHEN_SPACE_DATE_TIME_DESERIALIZE_FORMAT))
            .appendOptional(DateTimeFormatter.ofPattern(SLASH_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".SSSSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(SLASH_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".SSSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(SLASH_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".SSSS"))
            .appendOptional(DateTimeFormatter.ofPattern(SLASH_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".SSS"))
            .appendOptional(DateTimeFormatter.ofPattern(SLASH_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".SS"))
            .appendOptional(DateTimeFormatter.ofPattern(SLASH_SPACE_DATE_TIME_DESERIALIZE_FORMAT + ".S"))
            .appendOptional(DateTimeFormatter.ofPattern(SLASH_SPACE_DATE_TIME_DESERIALIZE_FORMAT))
	        .toFormatter();
}
