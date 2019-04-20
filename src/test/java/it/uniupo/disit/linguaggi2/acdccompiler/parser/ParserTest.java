package it.uniupo.disit.linguaggi2.acdccompiler.parser;

import it.uniupo.disit.linguaggi2.acdccompiler.ast.*;
import it.uniupo.disit.linguaggi2.acdccompiler.scanner.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static it.uniupo.disit.linguaggi2.acdccompiler.TestUtil.getFile;
import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parseSrcProgramReturnACompleteNodeAST() throws Exception {
        Parser parser = new Parser(new Scanner(getFile("src.txt")));

        NodeAST nodeAST = parser.parse();

        assertEquals(
                "NodeProgram{" +
                            "decSts=[" +
                                "NodeDecl{" +
                                    "id=NodeId{" +
                                        "name='b'" +
                                    "}, " +
                                    "type=FLOAT_TYPE" +
                                "}, " +
                                "NodeDecl{" +
                                    "id=NodeId{" +
                                        "name='a'" +
                                    "}, " +
                                    "type=INT_TYPE" +
                                "}, " +
                                "NodeAssign{" +
                                    "id=NodeId{" +
                                        "name='a'" +
                                    "}, " +
                                    "expr=NodeCost{" +
                                        "value='5', " +
                                        "type=INT_TYPE" +
                                    "}" +
                                "}, " +
                                "NodeAssign{" +
                                    "id=NodeId{" +
                                        "name='b'" +
                                    "}, " +
                                    "expr=NodeBinOp{" +
                                        "op=PLUS_OP, " +
                                        "left=NodeDeref{" +
                                            "id=NodeId{" +
                                                "name='a'" +
                                            "}" +
                                        "}, " +
                                        "right=NodeCost{" +
                                            "value='3.2', " +
                                            "type=FLOAT_TYPE" +
                                        "}" +
                                    "}" +
                                "}, " +
                                "NodePrint{" +
                                    "id=NodeId{" +
                                        "name='b'" +
                                    "}" +
                                "}" +
                            "]" +
                        "}", nodeAST.toString());
    }

    @Test
    void parseExprReturnACorrectStructure() throws Exception {
        Parser parser = new Parser(new Scanner(getFile("expr.txt")));

        NodeAST nodeAST = parser.parse();

        assertNotNull(nodeAST);
        assertTrue(nodeAST instanceof NodeProgram);
        List<NodeDecSt> decSts = ((NodeProgram) nodeAST).getDecSts();
        assertEquals(4, decSts.size());
        assertTrue(decSts.get(3) instanceof NodeAssign);
        NodeExpr expr = ((NodeAssign) decSts.get(3)).getExpr();
        assertTrue(expr instanceof NodeBinOp);
        assertEquals(
                "NodeBinOp{" +
                            "op=MINUS_OP, " +
                            "left=NodeBinOp{" +
                                "op=PLUS_OP, " +
                                "left=NodeCost{" +
                                    "value='3', " +
                                    "type=INT_TYPE" +
                                "}, " +
                                "right=NodeBinOp{" +
                                    "op=DIV_OP, " +
                                    "left=NodeBinOp{" +
                                        "op=TIMES_OP, " +
                                        "left=NodeCost{" +
                                            "value='5', " +
                                            "type=INT_TYPE" +
                                        "}, " +
                                        "right=NodeCost{" +
                                            "value='7', " +
                                            "type=INT_TYPE" +
                                        "}" +
                                    "}, " +
                                    "right=NodeCost{" +
                                        "value='8.3', " +
                                        "type=FLOAT_TYPE" +
                                    "}" +
                                "}" +
                            "}, " +
                            "right=NodeDeref{" +
                                "id=NodeId{" +
                                    "name='temp'" +
                                "}" +
                            "}" +
                        "}", expr.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "empty.txt",
            "src.txt",
            "srcWithOnlyNecessarySpaces.txt",
            "srcWithRandomSpaces.txt"})
    void parseRightSrcProgramReturnsTrue(String fileName) throws Exception {
        Parser parser = new Parser(new Scanner(getFile(fileName)));

        assertNotNull(parser.parse());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "assign.txt",
            "float.txt",
            "fnumber.txt",
            "id.txt",
            "int.txt",
            "inumber.txt",
            "minus.txt",
            "plus.txt",
            "print.txt",
            "srcWithSyntacticError.txt"})
    void parseWrongSrcProgramReturnsSyntacticException(String fileName) throws Exception {
        Parser parser = new Parser(new Scanner(getFile(fileName)));

        assertThrows(SyntacticException.class, parser::parse);
    }
}