package it.uniupo.disit.linguaggi2.acdccompiler.ast;

import it.uniupo.disit.linguaggi2.acdccompiler.visitor.IVisitor;

public abstract class NodeAST {

    private TypeDescriptor resType;

    public abstract void accept(IVisitor visitor);

    public TypeDescriptor getResType() {
        return resType;
    }

    public void setResType(TypeDescriptor resType) {
        this.resType = resType;
    }
}
