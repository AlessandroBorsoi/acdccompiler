package it.uniupo.disit.linguaggi2.acdccompiler.ast;

import static java.util.Objects.requireNonNull;

public class NodeDecl extends NodeDecSt {

    private final NodeId id;
    private final LangType type;

    public NodeDecl(NodeId id, LangType type) {
        this.id = requireNonNull(id);
        this.type = requireNonNull(type);
    }

    public NodeId getId() {
        return id;
    }

    public LangType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "NodeDecl{" +
                "id=" + id.toString() +
                ", type=" + type +
                '}';
    }

}
