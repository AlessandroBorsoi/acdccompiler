package it.uniupo.disit.linguaggi2.acdccompiler.symboltable;

import it.uniupo.disit.linguaggi2.acdccompiler.ast.LangType;

import static java.util.Objects.requireNonNull;

public class Attributes {

    private final LangType type;
    private final char register;

    public Attributes(LangType type, char register) {
        this.type = requireNonNull(type);
        this.register = register;
    }

    public LangType getType() {
        return type;
    }

    public String toString() {
        return type.toString();
    }

    public char getRegister() {
        return register;
    }
}
