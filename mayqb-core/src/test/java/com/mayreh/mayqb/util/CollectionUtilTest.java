package com.mayreh.mayqb.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

public class CollectionUtilTest {

    @Test
    public void prepend() {
        assertThat(CollectionUtil.prepend("a", Collections.emptyList()))
                .isEqualTo(Collections.singletonList("a"));

        assertThat(CollectionUtil.prepend("a", Arrays.asList("b", "c", "d")))
                .isEqualTo(Arrays.asList("a", "b", "c", "d"));
    }

    @Test
    public void append() {
        assertThat(CollectionUtil.append(Collections.emptyList(), "a"))
                .isEqualTo(Collections.singletonList("a"));

        assertThat(CollectionUtil.append(Arrays.asList("a", "b", "c"), "d"))
                .isEqualTo(Arrays.asList("a", "b", "c", "d"));
    }
}
