package it.uniupo.disit.linguaggi2.acdccompiler.visitor;

import it.uniupo.disit.linguaggi2.acdccompiler.ast.NodeAST;
import it.uniupo.disit.linguaggi2.acdccompiler.parser.Parser;
import it.uniupo.disit.linguaggi2.acdccompiler.scanner.Scanner;
import it.uniupo.disit.linguaggi2.acdccompiler.symboltable.SymTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.uniupo.disit.linguaggi2.acdccompiler.TestUtil.getFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeGeneratorVisitorTest {

    private IVisitor visitor;

    @BeforeEach
    void setUp() {
        visitor = new CodeGeneratorVisitor();
        SymTable.init();
    }

    @Test
    void visitorGeneratesCompleteTargetCode() throws Exception {
        Parser parser = new Parser(new Scanner(getFile("src.txt")));
        NodeAST nodeAST = parser.parse();
        nodeAST.accept(new TypeCheckingVisitor());
        nodeAST.accept(visitor);

        assertEquals("5 sb 0 k lb 5 k 3.2 + sa 0 k la p sz", visitor.output());
    }

    @Test
    void visitorWithAnotherSrcGeneratesCompleteTargetCode() throws Exception {
        Parser parser = new Parser(new Scanner(getFile("anotherSrc.txt")));
        NodeAST nodeAST = parser.parse();
        nodeAST.accept(new TypeCheckingVisitor());
        nodeAST.accept(visitor);

        assertEquals("1.0 6 5 k / sb 0 k lb p sz 1 6 / sa 0 k la p sz", visitor.output());
    }
}