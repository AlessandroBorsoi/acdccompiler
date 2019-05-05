package it.uniupo.disit.linguaggi2.acdccompiler.symboltable;

import it.uniupo.disit.linguaggi2.acdccompiler.ast.LangType;

import static java.util.Objects.requireNonNull;

public class Attributes {

    private final LangType type;

    public Attributes(LangType type) {
        this.type = requireNonNull(type);
    }

    public LangType getType() {
        return type;
    }

    public String toString() {
        return type.toString();
    }
}
