package com.mayreh.mayqb;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Extract T from ResultSet
 */
public interface TypeExtractor<T> {

    T get(ResultSet rs, int idx) throws SQLException;

    T get(ResultSet rs, String columnLabel) throws SQLException;
}
