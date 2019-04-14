package it.uniupo.disit.linguaggi2.acdccompiler.ast;

import static java.util.Objects.requireNonNull;

public class NodeAssign extends NodeStm {

    private final NodeId id;
    private final NodeExpr expr;

    public NodeAssign(NodeId id, NodeExpr expr) {
        this.id = requireNonNull(id);
        this.expr = requireNonNull(expr);
    }

    public NodeId getId() {
        return id;
    }

    public NodeExpr getExpr() {
        return expr;
    }

    @Override
    public String toString() {
        return "NodeAssign{" +
                "id=" + id +
                ", expr=" + expr +
                '}';
    }

}
