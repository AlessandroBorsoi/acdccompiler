package it.uniupo.disit.linguaggi2.acdccompiler.ast;

import it.uniupo.disit.linguaggi2.acdccompiler.visitor.IVisitor;

import static java.util.Objects.requireNonNull;

public class NodeCost extends NodeExpr {

    private final String value;
    private final LangType type;

    public NodeCost(String value, LangType type) {
        this.value = requireNonNull(value);
        this.type = requireNonNull(type);
    }

    public String getValue() {
        return value;
    }

    public LangType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "NodeCost{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

}
