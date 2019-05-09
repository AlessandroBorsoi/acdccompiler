package it.uniupo.disit.linguaggi2.acdccompiler.symboltable;

import it.uniupo.disit.linguaggi2.acdccompiler.ast.LangType;

import static java.util.Objects.requireNonNull;

public class Attributes {

    private final LangType type;
    private char register;

    public Attributes(LangType type) {
        this.type = requireNonNull(type);
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

    public void setRegister(char register) {
        this.register = register;
    }
}
