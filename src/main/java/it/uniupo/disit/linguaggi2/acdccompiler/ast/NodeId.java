package it.uniupo.disit.linguaggi2.acdccompiler.ast;

import it.uniupo.disit.linguaggi2.acdccompiler.symboltable.Attributes;
import it.uniupo.disit.linguaggi2.acdccompiler.visitor.IVisitor;

import static java.util.Objects.requireNonNull;

public class NodeId extends NodeAST {

    private final String name;
    private Attributes definition;

    public NodeId(String name) {
        this.name = requireNonNull(name);
    }

    public String getName() {
        return name;
    }

    public void setDefinition(Attributes definition) {
        this.definition = definition;
    }

    public Attributes getDefinition() {
        return definition;
    }

    @Override
    public String toString() {
        return "NodeId{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

}
