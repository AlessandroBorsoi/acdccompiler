package it.uniupo.disit.linguaggi2.acdccompiler.ast;

import static java.util.Objects.requireNonNull;

public class NodeDeref extends NodeExpr {

    private final NodeId id;

    public NodeDeref(NodeId id) {
        this.id = requireNonNull(id);
    }

    @Override
    public String toString() {
        return "NodeDeref{" +
                "id=" + id +
                '}';
    }

}
