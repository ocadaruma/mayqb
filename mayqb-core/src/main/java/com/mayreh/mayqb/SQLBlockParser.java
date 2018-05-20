package com.mayreh.mayqb;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SQLBlockParser {

    private static final Pattern PARAMETER_PATTERN =
            Pattern.compile(Pattern.quote("${@}"));

    @Value
    static class Result {

        List<String> parts;
    }

    static Result parse(String str) {
        List<String> parts = new ArrayList<>();
        Matcher matcher = PARAMETER_PATTERN.matcher(str);

        int start = 0;
        while(matcher.find()) {
            parts.add(str.substring(start, matcher.start()));
            start = matcher.end();
        }
        parts.add(str.substring(start));

        return new Result(parts);
    }
}
