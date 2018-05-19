package com.mayreh.mayqb.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class OptionTest {

    @Test
    public void none() {
        assertThat(Option.none().isEmpty()).isTrue();
        assertThat(Option.none() == Option.none()).isTrue();
    }

    @Test
    public void some() {
        assertThat(Option.some(55301).isEmpty()).isFalse();
        assertThat(Option.some("").isEmpty()).isFalse();
    }
}
