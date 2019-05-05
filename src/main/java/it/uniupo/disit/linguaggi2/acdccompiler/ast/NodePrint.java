package it.uniupo.disit.linguaggi2.acdccompiler.ast;

import it.uniupo.disit.linguaggi2.acdccompiler.visitor.IVisitor;

import static java.util.Objects.requireNonNull;

public class NodePrint extends NodeStm {

    private final NodeId id;

    public NodePrint(NodeId id) {
        this.id = requireNonNull(id);
    }

    public NodeId getId() {
        return id;
    }

    @Override
    public String toString() {
        return "NodePrint{" +
                "id=" + id +
                '}';
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

}