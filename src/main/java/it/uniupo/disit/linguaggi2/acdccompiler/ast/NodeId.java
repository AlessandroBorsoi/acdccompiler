package it.uniupo.disit.linguaggi2.acdccompiler.ast;

import static java.util.Objects.requireNonNull;

public class NodeId extends NodeAST {

    private final String name;

    public NodeId(String name) {
        this.name = requireNonNull(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "NodeId{" +
                "name='" + name + '\'' +
                '}';
    }
}
