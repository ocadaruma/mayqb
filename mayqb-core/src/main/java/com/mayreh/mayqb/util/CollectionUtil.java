package com.mayreh.mayqb.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionUtil {

    public static <T> List<T> prepend(T element, List<T> tail) {
        return Stream.concat(Stream.of(element), tail.stream()).collect(Collectors.toList());
    }

    public static <T> List<T> append(List<T> init, T element) {
        return Stream.concat(init.stream(), Stream.of(element)).collect(Collectors.toList());
    }
}
