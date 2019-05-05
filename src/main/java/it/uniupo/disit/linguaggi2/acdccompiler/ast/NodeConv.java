package it.uniupo.disit.linguaggi2.acdccompiler.ast;

public class NodeConv extends NodeExpr {

    private final NodeExpr expr;

    public NodeConv(NodeExpr expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "NodeConv{" +
                "expr=" + expr +
                '}';
    }

    public NodeExpr getExpr() {
        return expr;
    }
}
