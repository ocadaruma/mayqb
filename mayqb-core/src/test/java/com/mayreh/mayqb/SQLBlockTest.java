package com.mayreh.mayqb;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

public class SQLBlockTest {

    @Test
    public void noParameters() {
        SQLBlock block = SQLBlock.of("SELECT");
        assertThat(block).isEqualTo(new SQLBlock("SELECT", Collections.emptyList()));
    }

    @Test
    public void placeholders() {
        SQLBlock block = SQLBlock.of("${@} + ${@}", 1, 1);
        assertThat(block).isEqualTo(new SQLBlock("? + ?", Arrays.asList(1, 1)));
    }

    @Test
    public void embeddedParameters() {
        SQLBlock block = SQLBlock.of("${@} AS cnt", SQLBlock.of("COUNT(1)"));
        assertThat(block).isEqualTo(new SQLBlock("COUNT(1) AS cnt", Collections.emptyList()));
    }

    @Test
    public void mixed() {
        SQLBlock block = SQLBlock.of("SELECT ${@} FROM foo WHERE id = ${@}", SQLBlock.of("name"), 1);
        assertThat(block).isEqualTo(new SQLBlock("SELECT name FROM foo WHERE id = ?", Collections.singletonList(1)));
    }
}
