package com.mayreh.mayqb;

import com.mayreh.mayqb.util.Option;
import com.mayreh.mayqb.util.ThrowableBiFunction;
import com.mayreh.mayqb.util.ThrowableFunction;
import lombok.RequiredArgsConstructor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class ResultTransformer {

    private final SQLBlock sql;

    public ResultTransformer(SQLBlock sql) {
        this.sql = sql;
    }

    /**
     *
     * @param f
     * @param <T>
     * @return
     */
    public <T> ResultExtractor<T> transform(ThrowableFunction<WrappedResultSet, T> f) {
        throw new NotImplementedException();
    }

    /**
     *
     * @param f
     * @param <TOne>
     * @return
     */
    public <TOne> One<TOne> one(ThrowableFunction<WrappedResultSet, TOne> f) {
        return new One<>(f);
    }

    @RequiredArgsConstructor
    public static class One<TOne> {
        private final ThrowableFunction<WrappedResultSet, TOne> f;

        @RequiredArgsConstructor
        public class ToMany<TMany> {
            private final ThrowableFunction<WrappedResultSet, Option<TMany>> g;

            public <TResult> ResultExtractor<TResult> transform(
                    ThrowableBiFunction<TOne, List<TMany>, TResult> h) {
                throw new NotImplementedException();
            }
        }
    }
}
