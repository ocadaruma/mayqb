package com.mayreh.mayqb.util;

import java.sql.SQLException;

@FunctionalInterface
public interface ThrowablePredicate<T> {
    boolean apply(T t) throws SQLException;
}
