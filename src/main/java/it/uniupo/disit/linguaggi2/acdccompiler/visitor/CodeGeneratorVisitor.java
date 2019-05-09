package it.uniupo.disit.linguaggi2.acdccompiler.visitor;

import it.uniupo.disit.linguaggi2.acdccompiler.ast.*;

import static it.uniupo.disit.linguaggi2.acdccompiler.visitor.Register.newRegister;

public class CodeGeneratorVisitor implements IVisitor {

    private static final String PRINT = "p";
    private static final String REMOVE_FROM_STACK = "sz";
    private static final String RESET_PRECISION = "0 k";
    private static final String SET_PRECISION = "5 k";
    private static final String STACK_TO_REGISTER = "s";
    private static final String REGISTER_TO_STACK = "l";

    private final StringBuilder code;

    CodeGeneratorVisitor() {
        this.code = new StringBuilder();
        Register.init();
    }

    @Override
    public void visit(NodeProgram node) {
        node.getDecSts().forEach(nodeDecSt -> nodeDecSt.accept(this));
    }

    @Override
    public void visit(NodeId node) {

    }

    @Override
    public void visit(NodeDecl node) {
        node.getId().getDefinition().setRegister(newRegister());
    }

    @Override
    public void visit(NodePrint node) {
        emit(REGISTER_TO_STACK + node.getId().getDefinition().getRegister());
        emit(PRINT);
        emit(REMOVE_FROM_STACK);
    }

    @Override
    public void visit(NodeAssign node) {
        node.getExpr().accept(this);
        emit(STACK_TO_REGISTER + node.getId().getDefinition().getRegister());
        emit(RESET_PRECISION);
    }

    @Override
    public void visit(NodeCost node) {
        emit(node.getValue());
    }

    @Override
    public void visit(NodeDeref node) {
        emit(REGISTER_TO_STACK + node.getId().getDefinition().getRegister());
    }

    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        emit(node.getOp().getSymbol());
    }

    @Override
    public void visit(NodeConvert node) {
        node.getExpr().accept(this);
        emit(SET_PRECISION);
    }

    @Override
    public String output() {
        return code.toString();
    }

    private void emit(String command) {
        if (code.length() > 0)
            code.append(" ");
        code.append(command);
    }
}
