package it.uniupo.disit.linguaggi2.acdccompiler.ast;

public abstract class NodeAST {

    private TypeDescriptor resType;

    public TypeDescriptor getResType() {
        return resType;
    }

    public void setResType(TypeDescriptor resType) {
        this.resType = resType;
    }
}
