package it.uniupo.disit.linguaggi2.acdccompiler.parser;

import it.uniupo.disit.linguaggi2.acdccompiler.scanner.Scanner;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static it.uniupo.disit.linguaggi2.acdccompiler.TestUtil.getFile;
import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "empty.txt",
            "src.txt",
            "srcWithOnlyNecessarySpaces.txt",
            "srcWithRandomSpaces.txt"})
    void parseRightSrcProgramReturnsTrue(String fileName) throws Exception {
        Parser parser = new Parser(new Scanner(getFile(fileName)));

        assertTrue(parser.parse());
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