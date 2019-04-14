package it.uniupo.disit.linguaggi2.acdccompiler.ast;

import static java.util.Objects.requireNonNull;

public class NodeBinOp extends NodeExpr {

    private final LangOper op;
    private final NodeExpr left;
    private final NodeExpr right;

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

    @Override
    public String toString() {
        return "NodeBinOp{" +
                "op=" + op +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

}