package com.mayreh.mayqb;

import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class WrappedResultSet {

    private final ResultSet underlying;

    public int getInt(int idx) throws SQLException {
        return get(idx, TypeExtractors.INTEGER);
    }

    public int getInt(String columnLabel) throws SQLException {
        return get(columnLabel, TypeExtractors.INTEGER);
    }

    public long getLong(int idx) throws SQLException {
        return get(idx, TypeExtractors.LONG);
    }

    public long getLong(String columnLabel) throws SQLException {
        return get(columnLabel, TypeExtractors.LONG);
    }

    public String getString(int idx) throws SQLException {
        return get(idx, TypeExtractors.STRING);
    }

    public String getString(String columnLabel) throws SQLException {
        return get(columnLabel, TypeExtractors.STRING);
    }

    public <T> T get(int idx, TypeExtractor<T> extractor) throws SQLException {
        return extractor.get(underlying, idx);
    }

    public <T> T get(String columnLabel, TypeExtractor<T> extractor) throws SQLException {
        return extractor.get(underlying, columnLabel);
    }
}
