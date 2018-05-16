package com.mayreh.mayqb;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a SQL building block
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
public class SQLBlock {

    private static final SQLBlock EMPTY = SQLBlock.of("");

    /**
     * Literal part of this block
     */
    @Getter
    private final String value;

    /**
     * Parameters of this block
     */
    @Getter
    private final List<Parameter> parameters;

    /**
     * Append other block to this
     */
    public SQLBlock append(SQLBlock other) {
        return SQLBlock.of("${@} ${@}", this, other);
    }

    public SQLBlock asc() {
        return SQLBlock.of("${@} asc", this);
    }

    public SQLBlock desc() {
        return SQLBlock.of("${@} desc", this);
    }

    public SQLBlock limit(int n) {
        return SQLBlock.of("${@} limit ${@}", this, SQLBlock.of(String.valueOf(n)));
    }

    public SQLBlock offset(int n) {
        return SQLBlock.of("${@} offset ${@}", this, SQLBlock.of(String.valueOf(n)));
    }

    /**
     * Instantiate SQLBlock
     * parameters must be an instance of SQLBlock or Parameter
     */
    public static SQLBlock of(String value, Object... parameters) {

        SQLBlockParser.Result parseResult = SQLBlockParser.parse(value);

        List<Parameter> expandedParams = new ArrayList<>();
        StringBuilder queryPart = new StringBuilder();
        int numParts = parseResult.getParts().size();

        for (int i = 0; i < numParts; i++) {
            String part = parseResult.getParts().get(i);
            queryPart.append(part);

            if (i < parameters.length) {
                Object param = parameters[i];

                if (param instanceof SQLBlock) {
                    // should be embedded directly
                    SQLBlock block = (SQLBlock) param;
                    queryPart.append(block.value);
                    expandedParams.addAll(block.parameters);
                } else if (param instanceof Parameter) {
                    // should be replaced with placeholder
                    queryPart.append("?");
                    expandedParams.add((Parameter) param);
                } else {
                    throw new IllegalArgumentException("parameter must be an instance of SQLBlock or Parameter : " + param);
                }
            }
        }

        return new SQLBlock(queryPart.toString(), expandedParams);
    }
}
