package com.mayreh.mayqb;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

public class ExecutableSQL {

    public static class Single<T> {
        public Optional<T> executeSync(DBContext ctx) {
            throw new NotImplementedException();
        }
    }

    public static class List<T> {
        public java.util.List<T> executeSync(DBContext ctx) {
            throw new NotImplementedException();
        }
    }
}
