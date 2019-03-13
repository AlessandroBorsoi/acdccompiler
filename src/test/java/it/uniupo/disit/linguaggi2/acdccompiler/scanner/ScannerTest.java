package it.uniupo.disit.linguaggi2.acdccompiler.scanner;

import it.uniupo.disit.linguaggi2.acdccompiler.token.Token;
import org.junit.jupiter.api.Test;

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
        assertEquals("<FLOAT,r:1>", token.toString());
    }

    @Test
    void intdclFileScannerReturnsFINTDCLToken() throws Exception {
        Scanner scanner = new Scanner(getFile("int.txt"));

        Token token = scanner.nextToken();

        assertEquals(INT, token.getType());
        assertNull(token.getValue());
        assertEquals("<INT,r:1>", token.toString());
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
        assertEquals("<PRINT,r:1>", token.toString());
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
        assertEquals("<ID,r:1,a>", token.toString());
    }

    @Test
    void srcProgramFileScannerReturnsAValidStreamOfTokens() throws Exception {
        Scanner scanner = new Scanner(getFile("src.txt"));

        assertEquals("<FLOAT,r:1>", scanner.nextToken().toString());
        assertEquals("<ID,r:1,b>", scanner.nextToken().toString());
        assertEquals("<INT,r:2>", scanner.nextToken().toString());
        assertEquals("<ID,r:2,a>", scanner.nextToken().toString());
        assertEquals("<ID,r:3,a>", scanner.nextToken().toString());
        assertEquals("<ASSIGN,r:3>", scanner.nextToken().toString());
        assertEquals("<INUM,r:3,5>", scanner.nextToken().toString());
        assertEquals("<ID,r:4,b>", scanner.nextToken().toString());
        assertEquals("<ASSIGN,r:4>", scanner.nextToken().toString());
        assertEquals("<ID,r:4,a>", scanner.nextToken().toString());
        assertEquals("<PLUS,r:4>", scanner.nextToken().toString());
        assertEquals("<FNUM,r:4,3.2>", scanner.nextToken().toString());
        assertEquals("<PRINT,r:5>", scanner.nextToken().toString());
        assertEquals("<ID,r:5,b>", scanner.nextToken().toString());
        assertEquals("<EOF,r:6>", scanner.nextToken().toString());
    }

    @Test
    void srcWithRandomSpacesProgramFileScannerReturnsAValidStreamOfTokens() throws Exception {
        Scanner scanner = new Scanner(getFile("srcWithRandomSpaces.txt"));

        assertEquals("<FLOAT,r:1>", scanner.nextToken().toString());
        assertEquals("<ID,r:3,b>", scanner.nextToken().toString());
        assertEquals("<INT,r:4>", scanner.nextToken().toString());
        assertEquals("<ID,r:5,a>", scanner.nextToken().toString());
        assertEquals("<ID,r:7,a>", scanner.nextToken().toString());
        assertEquals("<ASSIGN,r:7>", scanner.nextToken().toString());
        assertEquals("<INUM,r:9,5>", scanner.nextToken().toString());
        assertEquals("<ID,r:9,b>", scanner.nextToken().toString());
        assertEquals("<ASSIGN,r:9>", scanner.nextToken().toString());
        assertEquals("<ID,r:9,a>", scanner.nextToken().toString());
        assertEquals("<PLUS,r:9>", scanner.nextToken().toString());
        assertEquals("<FNUM,r:9,3.2>", scanner.nextToken().toString());
        assertEquals("<PRINT,r:10>", scanner.nextToken().toString());
        assertEquals("<ID,r:14,b>", scanner.nextToken().toString());
        assertEquals("<EOF,r:29>", scanner.nextToken().toString());
    }

    @Test
    void invalidSymbolThrowsLexicalException() throws Exception {
        Scanner scanner = new Scanner(getFile("invalidSymbol.txt"));

        assertThrows(LexicalException.class, scanner::nextToken);
    }

    @Test
    void srcProgramWithMixedValidAndInvalidTokens() throws Exception {
        LexicalException lexicalException;
        Scanner scanner = new Scanner(getFile("mixed.txt"));

        assertEquals("<ID,r:1,b>", scanner.nextToken().toString());
        lexicalException = assertThrows(LexicalException.class, scanner::peekToken);
        assertEquals("<INVALID,r:1,@>", lexicalException.getMessage());
        lexicalException = assertThrows(LexicalException.class, scanner::nextToken);
        assertEquals("<INVALID,r:1,@>", lexicalException.getMessage());
        assertEquals("<ASSIGN,r:1>", scanner.peekToken().toString());
        assertEquals("<ASSIGN,r:1>", scanner.nextToken().toString());
        lexicalException = assertThrows(LexicalException.class, scanner::peekToken);
        assertEquals("<INVALID,r:1,INVALID>", lexicalException.getMessage());
        lexicalException = assertThrows(LexicalException.class, scanner::nextToken);
        assertEquals("<INVALID,r:1,INVALID>", lexicalException.getMessage());
        assertEquals("<FNUM,r:1,3.2>", scanner.peekToken().toString());
        assertEquals("<FNUM,r:1,3.2>", scanner.nextToken().toString());
        lexicalException = assertThrows(LexicalException.class, scanner::peekToken);
        assertEquals("<INVALID,r:1,PIPPO>", lexicalException.getMessage());
        lexicalException = assertThrows(LexicalException.class, scanner::nextToken);
        assertEquals("<INVALID,r:1,PIPPO>", lexicalException.getMessage());
        lexicalException = assertThrows(LexicalException.class, scanner::peekToken);
        assertEquals("<INVALID,r:1,@>", lexicalException.getMessage());
        lexicalException = assertThrows(LexicalException.class, scanner::nextToken);
        assertEquals("<INVALID,r:1,@>", lexicalException.getMessage());
        lexicalException = assertThrows(LexicalException.class, scanner::peekToken);
        assertEquals("<INVALID,r:1,#>", lexicalException.getMessage());
        lexicalException = assertThrows(LexicalException.class, scanner::nextToken);
        assertEquals("<INVALID,r:1,#>", lexicalException.getMessage());
        lexicalException = assertThrows(LexicalException.class, scanner::peekToken);
        assertEquals("<INVALID,r:1,[>", lexicalException.getMessage());
        lexicalException = assertThrows(LexicalException.class, scanner::nextToken);
        assertEquals("<INVALID,r:1,[>", lexicalException.getMessage());
        lexicalException = assertThrows(LexicalException.class, scanner::peekToken);
        assertEquals("<INVALID,r:1,]>", lexicalException.getMessage());
        lexicalException = assertThrows(LexicalException.class, scanner::nextToken);
        assertEquals("<INVALID,r:1,]>", lexicalException.getMessage());
        assertEquals("<EOF,r:1>", scanner.nextToken().toString());
    }

    @Test
    void srcProgramWithPeekTokenReturnsAValidStreamOfTokens() throws Exception {
        Scanner scanner = new Scanner(getFile("src.txt"));

        assertEquals("<FLOAT,r:1>", scanner.peekToken().toString());
        assertEquals("<FLOAT,r:1>", scanner.nextToken().toString());
        assertEquals("<ID,r:1,b>", scanner.peekToken().toString());
        assertEquals("<ID,r:1,b>", scanner.nextToken().toString());
        assertEquals("<INT,r:2>", scanner.peekToken().toString());
        assertEquals("<INT,r:2>", scanner.nextToken().toString());
        assertEquals("<ID,r:2,a>", scanner.peekToken().toString());
        assertEquals("<ID,r:2,a>", scanner.nextToken().toString());
        assertEquals("<ID,r:3,a>", scanner.peekToken().toString());
        assertEquals("<ID,r:3,a>", scanner.nextToken().toString());
        assertEquals("<ASSIGN,r:3>", scanner.peekToken().toString());
        assertEquals("<ASSIGN,r:3>", scanner.nextToken().toString());
        assertEquals("<INUM,r:3,5>", scanner.peekToken().toString());
        assertEquals("<INUM,r:3,5>", scanner.nextToken().toString());
        assertEquals("<ID,r:4,b>", scanner.peekToken().toString());
        assertEquals("<ID,r:4,b>", scanner.nextToken().toString());
        assertEquals("<ASSIGN,r:4>", scanner.peekToken().toString());
        assertEquals("<ASSIGN,r:4>", scanner.nextToken().toString());
        assertEquals("<ID,r:4,a>", scanner.peekToken().toString());
        assertEquals("<ID,r:4,a>", scanner.nextToken().toString());
        assertEquals("<PLUS,r:4>", scanner.peekToken().toString());
        assertEquals("<PLUS,r:4>", scanner.nextToken().toString());
        assertEquals("<FNUM,r:4,3.2>", scanner.peekToken().toString());
        assertEquals("<FNUM,r:4,3.2>", scanner.nextToken().toString());
        assertEquals("<PRINT,r:5>", scanner.peekToken().toString());
        assertEquals("<PRINT,r:5>", scanner.nextToken().toString());
        assertEquals("<ID,r:5,b>", scanner.peekToken().toString());
        assertEquals("<ID,r:5,b>", scanner.nextToken().toString());
        assertEquals("<EOF,r:6>", scanner.peekToken().toString());
        assertEquals("<EOF,r:6>", scanner.nextToken().toString());
    }
}