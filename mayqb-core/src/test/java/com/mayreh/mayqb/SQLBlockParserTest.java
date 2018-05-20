package com.mayreh.mayqb;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

public class SQLBlockParserTest {

    @Test
    public void parseEmpty() {
        assertThat(SQLBlockParser.parse("").getParts()).isEqualTo(Collections.singletonList(""));
    }

    @Test
    public void parseSingle() {
        assertThat(SQLBlockParser.parse("${@}").getParts()).isEqualTo(Arrays.asList("", ""));
    }

    @Test
    public void parseAdjacent() {
        assertThat(SQLBlockParser.parse("${@}${@}").getParts()).isEqualTo(Arrays.asList("", "", ""));
    }
}
