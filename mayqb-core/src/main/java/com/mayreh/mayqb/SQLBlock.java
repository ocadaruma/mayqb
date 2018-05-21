package com.mayreh.mayqb;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a SQL building block
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
@ToString
public class SQLBlock {

    public static final SQLBlock EMPTY = SQLBlock.of("");

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
        return this.append(SQLBlock.of("ASC"));
    }

    public SQLBlock desc() {
        return this.append(SQLBlock.of("DESC"));
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

    public static SQLBlock join(SQLBlock delimiter, SQLBlock... blocks) {
        return join(delimiter, Arrays.asList(blocks));
    }

    public static SQLBlock join(SQLBlock delimiter, List<SQLBlock> blocks) {
        SQLBlock result = EMPTY;

        for (int i = 0; i < blocks.size(); i++) {
            if (i > 0) {
                result = SQLBlock.of("${@}${@}", result, delimiter);
            }
            result = SQLBlock.of("${@}${@}", result, blocks.get(i));
        }

        return result;
    }
}
