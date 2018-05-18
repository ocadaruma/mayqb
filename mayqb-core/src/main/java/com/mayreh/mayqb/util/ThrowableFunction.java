package com.mayreh.mayqb.util;

import java.sql.SQLException;

@FunctionalInterface
public interface ThrowableFunction<T, U> {
    U apply(T t) throws SQLException;
}
