package it.uniupo.disit.linguaggi2.acdccompiler.visitor;

import it.uniupo.disit.linguaggi2.acdccompiler.ast.*;
import it.uniupo.disit.linguaggi2.acdccompiler.symboltable.Attributes;
import it.uniupo.disit.linguaggi2.acdccompiler.symboltable.SymTable;

import static it.uniupo.disit.linguaggi2.acdccompiler.ast.TypeDescriptor.*;
import static it.uniupo.disit.linguaggi2.acdccompiler.symboltable.SymTable.enter;
import static it.uniupo.disit.linguaggi2.acdccompiler.visitor.Register.newRegister;

public class TypeCheckingVisitor implements IVisitor {

    private final StringBuilder log;

    TypeCheckingVisitor() {
        this.log = new StringBuilder();
    }

    @Override
    public void visit(NodeProgram node) {
        node.getDecSts().forEach(nodeDecSt -> nodeDecSt.accept(this));
    }

    @Override
    public void visit(NodeId node) {
        Attributes attr = SymTable.lookup(node.getName());
        if (attr == null) {
            node.setResType(ERROR);
            logError("id: '" + node.getName() + "' never declared");
        } else {
            node.setResType(getResultType(attr.getType()));
            node.setDefinition(attr);
        }
    }

    @Override
    public void visit(NodeDecl node) {
        NodeId id = node.getId();
        LangType td = node.getType();
        Attributes attr = new Attributes(td, newRegister());
        if (!enter(id.getName(), attr)) {
            node.setResType(ERROR);
            logError("id: '" + id.getName() + "' of type: '" + node.getType() + "' is duplicated");
        }
        id.setResType(getResultType(td));
        id.setDefinition(attr);
    }

    @Override
    public void visit(NodePrint node) {
        NodeId id = node.getId();
        id.accept(this);
        if (id.getResType() == ERROR) {
            node.setResType(ERROR);
        }
    }

    @Override
    public void visit(NodeAssign node) {
        NodeId id = node.getId();
        id.accept(this);
        NodeExpr expr = node.getExpr();
        expr.accept(this);
        TypeDescriptor idType = id.getResType();
        TypeDescriptor exprType = expr.getResType();
        if (!compatible(idType, exprType)) {
            node.setResType(ERROR);
            logError("type " + idType + " not compatible with " + exprType);
        } else if (idType != exprType) {
            node.setExpr(convert(expr));
        }
    }

    @Override
    public void visit(NodeCost node) {
        node.setResType(getResultType(node.getType()));
    }

    @Override
    public void visit(NodeDeref node) {
        NodeId id = node.getId();
        id.accept(this);
        node.setResType(id.getResType());
    }

    @Override
    public void visit(NodeBinOp node) {
        NodeExpr exprLeft = node.getLeft();
        exprLeft.accept(this);
        NodeExpr exprRight = node.getRight();
        exprRight.accept(this);
        TypeDescriptor resTypeLeft = exprLeft.getResType();
        TypeDescriptor resTypeRight = exprRight.getResType();
        if (resTypeLeft == ERROR || resTypeRight == ERROR) {
            node.setResType(ERROR);
        } else if (resTypeLeft == resTypeRight) {
            node.setResType(resTypeLeft);
        } else {
            node.setLeft(convert(exprLeft));
            node.setRight(convert(exprRight));
            node.setResType(FLOAT);
        }
    }

    @Override
    public void visit(NodeConvert node) {
        NodeExpr expr = node.getExpr();
        expr.accept(this);
        if (expr.getResType() != INT) {
            node.setResType(ERROR);
            logError("Cannot convert type " + expr.getResType());
        } else {
            node.setResType(FLOAT);
        }
    }

    @Override
    public String output() {
        return log.toString();
    }

    private void logError(String message) {
        if (log.length() > 0)
            log.append("\n");
        log.append(message);
    }

    private boolean compatible(TypeDescriptor t1, TypeDescriptor t2) {
        return t1 != ERROR && (t1 == t2 || (t1 == FLOAT && t2 == INT));
    }

    private NodeExpr convert(NodeExpr node) {
        if (node.getResType() == FLOAT) return node;
        else return new NodeConvert(node, FLOAT);
    }
}
