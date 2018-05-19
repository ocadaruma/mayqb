package com.mayreh.mayqb.util;

import java.sql.SQLException;

@FunctionalInterface
public interface ThrowableBiFunction<T, U, R> {
    R apply(T t, U u) throws SQLException;
}
