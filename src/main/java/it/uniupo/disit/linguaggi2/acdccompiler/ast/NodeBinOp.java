package it.uniupo.disit.linguaggi2.acdccompiler.ast;

import it.uniupo.disit.linguaggi2.acdccompiler.visitor.IVisitor;

import static java.util.Objects.requireNonNull;

public class NodeBinOp extends NodeExpr {

    private final LangOper op;
    private NodeExpr left;
    private NodeExpr right;

    public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right) {
        this.op = requireNonNull(op);
        this.left = requireNonNull(left);
        this.right = requireNonNull(right);
    }

    public LangOper getOp() {
        return op;
    }

    public NodeExpr getLeft() {
        return left;
    }

    public NodeExpr getRight() {
        return right;
    }

    public void setLeft(NodeExpr left) {
        this.left = requireNonNull(left);
    }

    public void setRight(NodeExpr right) {
        this.right = requireNonNull(right);
    }

    @Override
    public String toString() {
        return "NodeBinOp{" +
                "op=" + op +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

}