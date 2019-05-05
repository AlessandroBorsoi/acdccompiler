package it.uniupo.disit.linguaggi2.acdccompiler.visitor;

import it.uniupo.disit.linguaggi2.acdccompiler.ast.*;
import it.uniupo.disit.linguaggi2.acdccompiler.symboltable.Attributes;
import it.uniupo.disit.linguaggi2.acdccompiler.symboltable.SymTable;

import static it.uniupo.disit.linguaggi2.acdccompiler.ast.TypeDescriptor.*;
import static it.uniupo.disit.linguaggi2.acdccompiler.symboltable.SymTable.enter;

public class TypeCheckingVisitor implements IVisitor {

    private final StringBuilder log;

    TypeCheckingVisitor() {
        this.log = new StringBuilder();
    }

    @Override
    public void visit(NodeProgram node) {

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
        Attributes attr = new Attributes(td);
        if (!enter(id.getName(), attr)) {
            node.setResType(ERROR);
            logError("id: '" + id.getName() + "' of type: '" + node.getType() + "' is duplicated");
        }
        id.setResType(getResultType(td));
        id.setDefinition(attr);
    }

    @Override
    public void visit(NodePrint node) {

    }

    @Override
    public void visit(NodeAssign node) {

    }

    @Override
    public void visit(NodeCost node) {

    }

    @Override
    public void visit(NodeDeref node) {

    }

    @Override
    public void visit(NodeBinOp node) {

    }

    @Override
    public void visit(NodeConv node) {

    }

    private void logError(String message) {
        if (log.length() > 0)
            log.append("\n");
        log.append(message);
    }

    private boolean compatibile(TypeDescriptor t1, TypeDescriptor t2) {
        return t1 != ERROR && (t1 == t2 || (t1 == FLOAT && t2 == INT));
    }

    private NodeExpr converti(NodeExpr node) {
        return null;
    }
}
