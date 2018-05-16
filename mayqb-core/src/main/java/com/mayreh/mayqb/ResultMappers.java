package com.mayreh.mayqb;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultMappers {

    public static final ResultMapper<Integer> INTEGER = new ResultMapper<Integer>() {
        @Override
        public Integer get(ResultSet rs, int idx) throws SQLException {
            return rs.getInt(idx);
        }

        @Override
        public Integer get(ResultSet rs, String columnName) throws SQLException {
            return rs.getInt(columnName);
        }
    };
}
