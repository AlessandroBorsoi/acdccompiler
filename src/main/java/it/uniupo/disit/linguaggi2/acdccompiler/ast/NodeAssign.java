package it.uniupo.disit.linguaggi2.acdccompiler.ast;

import it.uniupo.disit.linguaggi2.acdccompiler.visitor.IVisitor;

import static java.util.Objects.requireNonNull;

public class NodeAssign extends NodeStm {

    private final NodeId id;
    private NodeExpr expr;

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

    public void setExpr(NodeExpr expr) {
        this.expr = requireNonNull(expr);
    }

    @Override
    public String toString() {
        return "NodeAssign{" +
                "id=" + id +
                ", expr=" + expr +
                '}';
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
