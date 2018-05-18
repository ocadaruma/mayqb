package com.mayreh.mayqb;

import com.mayreh.mayqb.util.Option;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mayreh.mayqb.TypeExtractors.option;

@RequiredArgsConstructor
public class WrappedResultSet {

    private final ResultSet underlying;

    public int getInt(int idx) throws SQLException {
        return get(idx, TypeExtractors.INTEGER);
    }

    public int getInt(String columnLabel) throws SQLException {
        return get(columnLabel, TypeExtractors.INTEGER);
    }

    public Option<Integer> getIntOpt(int idx) throws SQLException {
        return get(idx, option(TypeExtractors.INTEGER));
    }

    public Option<Integer> getIntOpt(String columnLabel) throws SQLException {
        return get(columnLabel, option(TypeExtractors.INTEGER));
    }

    public long getLong(int idx) throws SQLException {
        return get(idx, TypeExtractors.LONG);
    }

    public long getLong(String columnLabel) throws SQLException {
        return get(columnLabel, TypeExtractors.LONG);
    }

    public Option<Long> getLongOpt(int idx) throws SQLException {
        return get(idx, option(TypeExtractors.LONG));
    }

    public Option<Long> getLongOpt(String columnLabel) throws SQLException {
        return get(columnLabel, option(TypeExtractors.LONG));
    }

    public String getString(int idx) throws SQLException {
        return get(idx, TypeExtractors.STRING);
    }

    public String getString(String columnLabel) throws SQLException {
        return get(columnLabel, TypeExtractors.STRING);
    }

    public Option<String> getStringOpt(int idx) throws SQLException {
        return get(idx, option(TypeExtractors.STRING));
    }

    public Option<String> getStringOpt(String columnLabel) throws SQLException {
        return get(columnLabel, option(TypeExtractors.STRING));
    }

    public <T> T get(int idx, TypeExtractor<T> extractor) throws SQLException {
        return extractor.get(underlying, idx);
    }

    public <T> T get(String columnLabel, TypeExtractor<T> extractor) throws SQLException {
        return extractor.get(underlying, columnLabel);
    }
}
