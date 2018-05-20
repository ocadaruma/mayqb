package com.mayreh.mayqb;

import lombok.Value;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class contains various parameter implementation for standard types
 */
public class Parameters {

    @Value(staticConstructor = "of")
    public static class IntParameter implements Parameter {
        int value;

        @Override
        public void bind(PreparedStatement stmt, int idx) throws SQLException {
            stmt.setInt(idx, value);
        }
    }

    @Value(staticConstructor = "of")
    public static class LongParameter implements Parameter {
        long value;

        @Override
        public void bind(PreparedStatement stmt, int idx) throws SQLException {
            stmt.setLong(idx, value);
        }
    }

    @Value(staticConstructor = "of")
    public static class StringParameter implements Parameter {
        String value;

        @Override
        public void bind(PreparedStatement stmt, int idx) throws SQLException {
            stmt.setString(idx, value);
        }
    }
}
