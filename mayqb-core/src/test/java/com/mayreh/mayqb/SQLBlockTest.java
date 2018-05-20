package com.mayreh.mayqb;

import com.mayreh.mayqb.Parameters.*;
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
        SQLBlock block = SQLBlock.of("${@} + ${@}", IntParameter.of(1), IntParameter.of(1));
        assertThat(block).isEqualTo(new SQLBlock("? + ?", Arrays.asList(IntParameter.of(1), IntParameter.of(1))));
    }

    @Test
    public void embeddedParameters() {
        SQLBlock block = SQLBlock.of("${@} AS cnt", SQLBlock.of("COUNT(1)"));
        assertThat(block).isEqualTo(new SQLBlock("COUNT(1) AS cnt", Collections.emptyList()));
    }

    @Test
    public void mixed() {
        SQLBlock block = SQLBlock.of("SELECT ${@} FROM foo WHERE id = ${@}", SQLBlock.of("name"), IntParameter.of(1));
        assertThat(block).isEqualTo(new SQLBlock("SELECT name FROM foo WHERE id = ?", Collections.singletonList(IntParameter.of(1))));
    }

    @Test
    public void join() {
        assertThat(SQLBlock.join(SQLBlock.of(", "), SQLBlock.of("1")))
                .isEqualTo(SQLBlock.of("1"));

        assertThat(SQLBlock.join(SQLBlock.of(", "), SQLBlock.of("name"), SQLBlock.of("COUNT(*)")))
                .isEqualTo(SQLBlock.of("name, COUNT(*)"));

        assertThat(SQLBlock.join(SQLBlock.of(", ")))
                .isEqualTo(SQLBlock.EMPTY);
    }
}
