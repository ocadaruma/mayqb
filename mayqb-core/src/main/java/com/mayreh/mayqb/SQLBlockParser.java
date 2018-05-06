package com.mayreh.mayqb;

import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

class SQLBlockParser {

    private static final Pattern PARAMETER_PATTERN =
            Pattern.compile(Pattern.quote("${@}"));

    @Value
    static class Result {

        List<String> parts;
    }

    static Result parse(String str) {

        String[] parts = PARAMETER_PATTERN.split(str);

        return new Result(Arrays.asList(parts));
    }
}
