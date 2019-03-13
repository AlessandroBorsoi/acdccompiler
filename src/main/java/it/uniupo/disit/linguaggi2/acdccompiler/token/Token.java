package it.uniupo.disit.linguaggi2.acdccompiler.token;

import static java.util.Objects.requireNonNull;

public class Token {

    private final int row;
    private final TokenType type;
    private final String value;

    private Token(int row, TokenType type, String value) {
        this.row = row;
        this.type = requireNonNull(type);
        this.value = value;
    }

    public static Token of(int row, TokenType type, String value) {
        return new Token(row, type, value);
    }

    public static Token of(int row, TokenType type) {
        return new Token(row, type, null);
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<").append(type.toString())
                .append(",r:").append(row);
        if (value != null)
            builder.append(",").append(value);
        return builder.append(">").toString();
    }

}
