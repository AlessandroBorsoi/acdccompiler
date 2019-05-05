package it.uniupo.disit.linguaggi2.acdccompiler.visitor;

import it.uniupo.disit.linguaggi2.acdccompiler.ast.*;
import it.uniupo.disit.linguaggi2.acdccompiler.symboltable.Attributes;

import static it.uniupo.disit.linguaggi2.acdccompiler.ast.TypeDescriptor.FLOAT;
import static it.uniupo.disit.linguaggi2.acdccompiler.ast.TypeDescriptor.INT;

public interface IVisitor {

    void visit(NodeProgram node);

    void visit(NodeId node);

    void visit(NodeDecl node);

    void visit(NodePrint node);

    void visit(NodeAssign node);

    void visit(NodeCost node);

    void visit(NodeDeref node);

    void visit(NodeBinOp node);

    void visit(NodeConv node);

    default TypeDescriptor getResultType(LangType type) {
        return type == LangType.INT_TYPE ? INT : FLOAT;
    }
}
