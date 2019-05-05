package it.uniupo.disit.linguaggi2.acdccompiler.ast;

import it.uniupo.disit.linguaggi2.acdccompiler.symboltable.Attributes;

import static java.util.Objects.requireNonNull;

public class NodeId extends NodeAST {

    private final String name;
    private Attributes definition;

    public NodeId(String name) {
        this.name = requireNonNull(name);
    }

    public String getName() {
        return name;
    }

    public void setDefinition(Attributes definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return "NodeId{" +
                "name='" + name + '\'' +
                '}';
    }
}
