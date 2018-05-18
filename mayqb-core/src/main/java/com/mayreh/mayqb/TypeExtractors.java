package com.mayreh.mayqb;

import com.mayreh.mayqb.util.Option;
import com.mayreh.mayqb.util.ThrowableSupplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

/**
 * Provides extractors for standard types
 */
public class TypeExtractors {

    private interface IndexBasedExtractor<T> {
        T get(ResultSet rs, int idx) throws SQLException;
    }

    private interface ColumnLabelBasedExtractor<T> {
        T get(ResultSet rs, String columnLabel) throws SQLException;
    }

    private static <T> TypeExtractor<T> extractor(
            IndexBasedExtractor<T> byIdx,
            ColumnLabelBasedExtractor<T> byColumnLabel) {
        return new TypeExtractor<T>() {
            @Override
            public T get(ResultSet rs, int idx) throws SQLException {
                return byIdx.get(rs, idx);
            }

            @Override
            public T get(ResultSet rs, String columnLabel) throws SQLException {
                return byColumnLabel.get(rs, columnLabel);
            }
        };
    }

    public static <T> TypeExtractor<Option<T>> option(TypeExtractor<T> extractor) {
        return new TypeExtractor<Option<T>>() {
            @Override
            public Option<T> get(ResultSet rs, int idx) throws SQLException {
                return wrapNull(() -> extractor.get(rs, idx));
            }

            @Override
            public Option<T> get(ResultSet rs, String columnLabel) throws SQLException {
                return wrapNull(() -> extractor.get(rs, columnLabel));
            }

            private Option<T> wrapNull(ThrowableSupplier<T> a) throws SQLException {
                Option<T> result;
                try {
                    result = Option.apply(a.get());
                } catch (NullPointerException e) {
                    result = Option.none();
                }
                return result;
            }
        };
    }

    public static final TypeExtractor<Integer> INTEGER = extractor(ResultSet::getInt, ResultSet::getInt);

    public static final TypeExtractor<Long> LONG = extractor(ResultSet::getLong, ResultSet::getLong);

    public static final TypeExtractor<String> STRING = extractor(ResultSet::getString, ResultSet::getString);
}
