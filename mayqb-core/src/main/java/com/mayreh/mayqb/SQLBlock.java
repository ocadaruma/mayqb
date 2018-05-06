package com.mayreh.mayqb;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a SQL building block
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
public class SQLBlock {

    /**
     * Literal part of this block
     */
    private final String value;

    /**
     * Parameters of this block
     */
    private final List<Object> parameters;

    /**
     * Instantiate SQLBlock
     */
    public static SQLBlock of(String value, Object... parameters) {

        SQLBlockParser.Result parseResult = SQLBlockParser.parse(value);

        List<Object> expandedParams = new ArrayList<>();
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
                } else {
                    // should be replaced with placeholder
                    queryPart.append("?");
                    expandedParams.add(param);
                }
            }
        }

        return new SQLBlock(queryPart.toString(), expandedParams);
    }
}
