package it.uniupo.disit.linguaggi2.acdccompiler.ast;

import it.uniupo.disit.linguaggi2.acdccompiler.visitor.IVisitor;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class NodeProgram extends NodeAST {

    private final List<NodeDecSt> decSts;

    public NodeProgram(List<NodeDecSt> decSts) {
        this.decSts = requireNonNull(decSts);
    }

    public List<NodeDecSt> getDecSts() {
        return decSts;
    }

    @Override
    public String toString() {
        return "NodeProgram{" +
                "decSts=" + decSts +
                '}';
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

}
