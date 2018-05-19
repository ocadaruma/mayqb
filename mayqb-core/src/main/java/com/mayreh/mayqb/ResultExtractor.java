package com.mayreh.mayqb;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ResultExtractor<T> {

    public ExecutableSQL.List<T> list() {
        throw new NotImplementedException();
    }

    public ExecutableSQL.Single<T> single() {
        throw new NotImplementedException();
    }
}
