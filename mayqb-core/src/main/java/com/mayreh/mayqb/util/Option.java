package com.mayreh.mayqb.util;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Another implementation of Maybe
 */
public final class Option<T> {

    private static final Option<?> NONE = new Option<>();

    private final T value;

    private Option() {
        this.value = null;
    }

    private Option(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public static <T> Option<T> none() {
        @SuppressWarnings("unchecked")
        Option<T> none = (Option<T>) NONE;
        return none;
    }

    public static <T> Option<T> some(T value) {
        return new Option<>(value);
    }

    public static <T> Option<T> apply(T value) {
        return value == null ? none() : some(value);
    }

    public boolean isEmpty() {
        return this.value == null;
    }

    public boolean nonEmpty() {
        return !isEmpty();
    }

    public boolean isDefined() {
        return nonEmpty();
    }

    public Option<T> filter(ThrowablePredicate<? super T> f) throws SQLException {
        if (isEmpty()) {
            return this;
        } else {
            return f.apply(this.value) ? this : none();
        }
    }

    public <U> Option<U> map(ThrowableFunction<? super T, ? extends U> f) throws SQLException {
        if (isEmpty()) {
            return none();
        } else {
            return Option.apply(f.apply(this.value));
        }
    }

    public <U> Option<U> flatMap(ThrowableFunction<? super T, Option<U>> f) throws SQLException {
        if (isEmpty()) {
            return none();
        } else {
            return f.apply(this.value);
        }
    }

    public Optional<T> toOptional() {
        return isEmpty() ? Optional.empty() : Optional.of(this.value);
    }

    public List<T> toList() {
        return isEmpty() ? Collections.emptyList() : Collections.singletonList(this.value);
    }
}
