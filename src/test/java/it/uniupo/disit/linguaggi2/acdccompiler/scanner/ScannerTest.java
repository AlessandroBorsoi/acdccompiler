package it.uniupo.disit.linguaggi2.acdccompiler.scanner;

import it.uniupo.disit.linguaggi2.acdccompiler.token.Token;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static it.uniupo.disit.linguaggi2.acdccompiler.TestUtil.getFile;
import static it.uniupo.disit.linguaggi2.acdccompiler.token.TokenType.*;
import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {

    @Test
    void emptyFileScannerReturnsEOFToken() throws Exception {
        Scanner scanner = new Scanner(getFile("empty.txt"));

        Token token = scanner.nextToken();

        assertEquals(EOF, token.getType());
        assertNull(token.getValue());
        assertEquals("<EOF,r:1>", token.toString());
    }

    @Test
    void inumberFileScannerReturnsINUMToken() throws Exception {
        Scanner scanner = new Scanner(getFile("inumber.txt"));

        Token token = scanner.nextToken();

        assertEquals(INUM, token.getType());
        assertEquals("42", token.getValue());
        assertEquals("<INUM,r:1,42>", token.toString());
    }

    @Test
    void fnumberFileScannerReturnsFNUMToken() throws Exception {
        Scanner scanner = new Scanner(getFile("fnumber.txt"));

        Token token = scanner.nextToken();

        assertEquals(FNUM, token.getType());
        assertEquals("123.9876", token.getValue());
        assertEquals("<FNUM,r:1,123.9876>", token.toString());
    }

    @Test
    void floatdclFileScannerReturnsFLOATDCLToken() throws Exception {
        Scanner scanner = new Scanner(getFile("float.txt"));

        Token token = scanner.nextToken();

        assertEquals(FLOAT, token.getType());
        assertNull(token.getValue());
        assertEquals("<FLOAT>", token.toString());
    }

    @Test
    void intdclFileScannerReturnsFINTDCLToken() throws Exception {
        Scanner scanner = new Scanner(getFile("int.txt"));

        Token token = scanner.nextToken();

        assertEquals(INT, token.getType());
        assertNull(token.getValue());
        assertEquals("<INT>", token.toString());
    }

    @Test
    void assignFileScannerReturnsASSINGToken() throws Exception {
        Scanner scanner = new Scanner(getFile("assign.txt"));

        Token token = scanner.nextToken();

        assertEquals(ASSIGN, token.getType());
        assertNull(token.getValue());
        assertEquals("<ASSIGN,r:1>", token.toString());
    }

    @Test
    void printFileScannerReturnsPRINTToken() throws Exception {
        Scanner scanner = new Scanner(getFile("print.txt"));

        Token token = scanner.nextToken();

        assertEquals(PRINT, token.getType());
        assertNull(token.getValue());
        assertEquals("<PRINT>", token.toString());
    }

    @Test
    void plusFileScannerReturnsPLUSToken() throws Exception {
        Scanner scanner = new Scanner(getFile("plus.txt"));

        Token token = scanner.nextToken();

        assertEquals(PLUS, token.getType());
        assertNull(token.getValue());
        assertEquals("<PLUS,r:1>", token.toString());
    }

    @Test
    void minusFileScannerReturnsMINUSToken() throws Exception {
        Scanner scanner = new Scanner(getFile("minus.txt"));

        Token token = scanner.nextToken();

        assertEquals(MINUS, token.getType());
        assertNull(token.getValue());
        assertEquals("<MINUS,r:1>", token.toString());
    }

    @Test
    void timesFileScannerReturnsTIMESToken() throws Exception {
        Scanner scanner = new Scanner(getFile("times.txt"));

        Token token = scanner.nextToken();

        assertEquals(TIMES, token.getType());
        assertNull(token.getValue());
        assertEquals("<TIMES,r:1>", token.toString());
    }

    @Test
    void divFileScannerReturnsTIMESToken() throws Exception {
        Scanner scanner = new Scanner(getFile("div.txt"));

        Token token = scanner.nextToken();

        assertEquals(DIV, token.getType());
        assertNull(token.getValue());
        assertEquals("<DIV,r:1>", token.toString());
    }

    @Test
    void idFileScannerReturnsIDToken() throws Exception {
        Scanner scanner = new Scanner(getFile("id.txt"));

        Token token = scanner.nextToken();

        assertEquals(ID, token.getType());
        assertEquals("a", token.getValue());
        assertEquals("<ID,a>", token.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"src.txt", "srcWithOnlyNecessarySpaces.txt", "srcWithRandomSpaces.txt"})
    void srcProgramFileScannerReturnsAValidStreamOfTokens(String fileName) throws Exception {
        Scanner scanner = new Scanner(getFile(fileName));

        assertEquals("<FLOAT>", scanner.nextToken().toString());
        assertEquals("<ID,b>", scanner.nextToken().toString());
        assertEquals("<INT>", scanner.nextToken().toString());
        assertEquals("<ID,a>", scanner.nextToken().toString());
        assertEquals("<ID,a>", scanner.nextToken().toString());
        assertEquals("<ASSIGN>", scanner.nextToken().toString());
        assertEquals("<INUM,5>", scanner.nextToken().toString());
        assertEquals("<ID,b>", scanner.nextToken().toString());
        assertEquals("<ASSIGN>", scanner.nextToken().toString());
        assertEquals("<ID,a>", scanner.nextToken().toString());
        assertEquals("<PLUS>", scanner.nextToken().toString());
        assertEquals("<FNUM,3.2>", scanner.nextToken().toString());
        assertEquals("<PRINT>", scanner.nextToken().toString());
        assertEquals("<ID,b>", scanner.nextToken().toString());
        assertEquals("<EOF>", scanner.nextToken().toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalidId.txt",
            "invalidSymbol.txt",
            "invalidFloatdcl.txt",
            "invalidIntdcl.txt",
            "invalidPrint.txt"})
    void invalidIdThrowsLexicalException(String fileName) throws Exception {
        Scanner scanner = new Scanner(getFile(fileName));

        assertThrows(LexicalException.class, scanner::nextToken);
    }

    @Test
    void srcProgramWithMixedValidAndInvalidTokens() throws Exception {
        Scanner scanner = new Scanner(getFile("mixed.txt"));

        assertEquals("<ID,b>", scanner.nextToken().toString());
        assertThrows(LexicalException.class, scanner::peekToken, "<INVALID,@>");
        assertThrows(LexicalException.class, scanner::nextToken, "<INVALID,@>");
        assertEquals("<ASSIGN>", scanner.peekToken().toString());
        assertEquals("<ASSIGN>", scanner.nextToken().toString());
        assertThrows(LexicalException.class, scanner::peekToken, "<INVALID,invalid>");
        assertThrows(LexicalException.class, scanner::nextToken, "<INVALID,invalid>");
        assertEquals("<FNUM,3.2>", scanner.nextToken().toString());
        assertThrows(LexicalException.class, scanner::peekToken, "<INVALID,pippo>");
        assertThrows(LexicalException.class, scanner::nextToken, "<INVALID,pippo>");
        assertThrows(LexicalException.class, scanner::nextToken, "<INVALID,@>");
        assertThrows(LexicalException.class, scanner::nextToken, "<INVALID,#>");
        assertThrows(LexicalException.class, scanner::peekToken, "<INVALID,[>");
        assertThrows(LexicalException.class, scanner::nextToken, "<INVALID,[>");
        assertThrows(LexicalException.class, scanner::nextToken, "<INVALID,]>");
        assertEquals("<EOF>", scanner.nextToken().toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"src.txt", "srcWithOnlyNecessarySpaces.txt", "srcWithRandomSpaces.txt"})
    void srcProgramWithPeekTokenReturnsAValidStreamOfTokens(String fileName) throws Exception {
        Scanner scanner = new Scanner(getFile(fileName));

        assertEquals("<FLOATDCL>", scanner.peekToken().toString());
        assertEquals("<FLOATDCL>", scanner.nextToken().toString());
        assertEquals("<ID,b>", scanner.peekToken().toString());
        assertEquals("<ID,b>", scanner.nextToken().toString());
        assertEquals("<INTDCL>", scanner.peekToken().toString());
        assertEquals("<INTDCL>", scanner.nextToken().toString());
        assertEquals("<ID,a>", scanner.peekToken().toString());
        assertEquals("<ID,a>", scanner.nextToken().toString());
        assertEquals("<ID,a>", scanner.peekToken().toString());
        assertEquals("<ID,a>", scanner.nextToken().toString());
        assertEquals("<ASSIGN>", scanner.peekToken().toString());
        assertEquals("<ASSIGN>", scanner.nextToken().toString());
        assertEquals("<INUM,5>", scanner.peekToken().toString());
        assertEquals("<INUM,5>", scanner.nextToken().toString());
        assertEquals("<ID,b>", scanner.peekToken().toString());
        assertEquals("<ID,b>", scanner.nextToken().toString());
        assertEquals("<ASSIGN>", scanner.peekToken().toString());
        assertEquals("<ASSIGN>", scanner.nextToken().toString());
        assertEquals("<ID,a>", scanner.peekToken().toString());
        assertEquals("<ID,a>", scanner.nextToken().toString());
        assertEquals("<PLUS>", scanner.peekToken().toString());
        assertEquals("<PLUS>", scanner.nextToken().toString());
        assertEquals("<FNUM,3.2>", scanner.peekToken().toString());
        assertEquals("<FNUM,3.2>", scanner.nextToken().toString());
        assertEquals("<PRINT>", scanner.peekToken().toString());
        assertEquals("<PRINT>", scanner.nextToken().toString());
        assertEquals("<ID,b>", scanner.peekToken().toString());
        assertEquals("<ID,b>", scanner.nextToken().toString());
        assertEquals("<EOF>", scanner.peekToken().toString());
        assertEquals("<EOF>", scanner.peekToken().toString());
        assertEquals("<EOF>", scanner.nextToken().toString());
    }
}