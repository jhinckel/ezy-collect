package com.ezy.collect.utils;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegexUtils {

    private static final Pattern TEXT_INSIDE_BRACES = Pattern.compile("^\\{\\s*\\S*\\}$");

    public static boolean isTextInsideBraces(String text) {
        if (Objects.nonNull(text)) {
            return TEXT_INSIDE_BRACES.matcher(text).find();
        }
        return false;
    }

}
