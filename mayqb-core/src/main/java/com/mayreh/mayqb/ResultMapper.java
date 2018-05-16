package com.mayreh.mayqb;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultMapper<T> {

    T get(ResultSet rs, int idx) throws SQLException;

    T get(ResultSet rs, String columnName) throws SQLException;
}
