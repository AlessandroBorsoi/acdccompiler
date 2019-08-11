package it.uniupo.disit.linguaggi2.acdccompiler.visitor;

import it.uniupo.disit.linguaggi2.acdccompiler.ast.NodeAST;
import it.uniupo.disit.linguaggi2.acdccompiler.parser.Parser;
import it.uniupo.disit.linguaggi2.acdccompiler.scanner.Scanner;
import it.uniupo.disit.linguaggi2.acdccompiler.symboltable.SymTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.uniupo.disit.linguaggi2.acdccompiler.TestUtil.getFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TypeCheckingVisitorTest {

    private IVisitor visitor;

    @BeforeEach
    void setUp() {
        visitor = new TypeCheckingVisitor();
        SymTable.init();
    }

    @Test
    void visitNodesWithAssignWithoutDecl() throws Exception {
        Parser parser = new Parser(new Scanner(getFile("assignWithoutDecl.txt")));
        NodeAST nodeAST = parser.parse();
        nodeAST.accept(visitor);

        assertEquals("id: 'bar' never declared\n" +
                "type ERROR not compatible with INT", visitor.output());
    }

    @Test
    void visitNodesWithoutErrorsReturnsNoErrors() throws Exception {
        Parser parser = new Parser(new Scanner(getFile("src.txt")));
        NodeAST nodeAST = parser.parse();
        nodeAST.accept(visitor);

        assertEquals("", visitor.output());
    }

    @Test
    void visitNodesWithWrongConversionReturnError() throws Exception {
        Parser parser = new Parser(new Scanner(getFile("invalidTypes.txt")));
        NodeAST nodeAST = parser.parse();
        nodeAST.accept(visitor);

        assertEquals("type INT not compatible with FLOAT\ntype INT not compatible with FLOAT", visitor.output());
    }
}