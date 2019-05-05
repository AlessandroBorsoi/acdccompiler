package it.uniupo.disit.linguaggi2.acdccompiler.ast;

import it.uniupo.disit.linguaggi2.acdccompiler.visitor.IVisitor;

public class NodeConvert extends NodeExpr {

    private final NodeExpr expr;

    public NodeConvert(NodeExpr expr, TypeDescriptor resType) {
        this.expr = expr;
        this.setResType(resType);
    }

    @Override
    public String toString() {
        return "NodeConvert{" +
                "expr=" + expr +
                '}';
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public NodeExpr getExpr() {
        return expr;
    }
}
