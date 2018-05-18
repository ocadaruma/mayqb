package com.mayreh.mayqb.util;

import java.sql.SQLException;

@FunctionalInterface
public interface ThrowableSupplier<T> {
    T get() throws SQLException;
}
