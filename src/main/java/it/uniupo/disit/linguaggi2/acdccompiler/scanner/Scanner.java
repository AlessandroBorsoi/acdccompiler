package it.uniupo.disit.linguaggi2.acdccompiler.scanner;

import it.uniupo.disit.linguaggi2.acdccompiler.token.Token;
import it.uniupo.disit.linguaggi2.acdccompiler.token.TokenType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.Map;
import java.util.Set;

import static it.uniupo.disit.linguaggi2.acdccompiler.token.TokenType.*;
import static java.lang.Character.*;

class Scanner {

    private static final Set<Character> SKIP_CHARS = Set.of(' ', '\n', '\t', '\r');
    private static final Map<String, TokenType> KEYWORDS = Map.of(
            "float", FLOAT,
            "int", INT,
            "print", PRINT);
    private static final Map<Character, TokenType> OPERATORS = Map.of(
            '=', ASSIGN,
            '+', PLUS,
            '-', MINUS,
            '*', TIMES,
            '/', DIV);

    private final PushbackReader buffer;
    private Token nextToken;
    private int row;

    Scanner(String fileName) throws FileNotFoundException {
        this.buffer = new PushbackReader(new FileReader(fileName));
        this.nextToken = null;
        this.row = 1;
    }

    Token nextToken() throws IOException, LexicalException {
        Token currentToken;
        if (nextToken == null) {
            currentToken = scanNextToken();
        } else {
            currentToken = nextToken;
            nextToken = null;
        }
        return checkInvalid(currentToken);
    }

    Token peekToken() throws IOException, LexicalException {
        if (nextToken == null)
            nextToken = scanNextToken();
        return checkInvalid(nextToken);
    }

    private Token scanNextToken() throws IOException {
        char nextChar = readChar();
        while (isBlank(nextChar))
            nextChar = readChar();
        if (isDigit(nextChar))
            return scanNumber(nextChar);
        if (isLetter(nextChar) && isLowerCase(nextChar))
            return scanId(nextChar);
        if (isOperator(nextChar))
            return Token.of(row, OPERATORS.get(nextChar));
        if (isEOF(nextChar))
            return Token.of(row, EOF);
        return getInvalidToken(nextChar);
    }

    private Token checkInvalid(Token token) throws LexicalException {
        if (token.getType() == INVALID)
            throw new LexicalException(token.toString());
        return token;
    }

    private boolean isBlank(char nextChar) {
        if (nextChar == '\n') row++;
        return SKIP_CHARS.contains(nextChar);
    }

    private boolean isOperator(char nextChar) throws IOException {
        return OPERATORS.containsKey(nextChar) && isValid(nextChar);
    }

    private boolean isValid(char nextChar) throws IOException {
        if (isLetter(nextChar))
            return !isLetter(peekChar());
        return true;
    }

    private Token scanNumber(char nextChar) throws IOException {
        StringBuilder builder = new StringBuilder().append(nextChar);
        nextChar = appendDigits(builder);
        if ((nextChar != '.')) {
            buffer.unread(nextChar);
            return Token.of(row, INUM, builder.toString());
        }
        builder.append(nextChar);
        buffer.unread(appendDigits(builder));
        return Token.of(row, FNUM, builder.toString());
    }

    private char appendDigits(StringBuilder builder) throws IOException {
        char nextChar = readChar();
        while (isDigit(nextChar)) {
            builder.append(nextChar);
            nextChar = readChar();
        }
        return nextChar;
    }

    private Token scanId(char nextChar) throws IOException {
        StringBuilder builder = new StringBuilder();
        do {
            builder.append(nextChar);
            nextChar = readChar();
        } while (isLetter(nextChar) && isLowerCase(nextChar));
        buffer.unread(nextChar);
        String id = builder.toString();
        if (KEYWORDS.containsKey(id))
            return Token.of(row, KEYWORDS.get(id));
        return Token.of(row, ID, id);
    }

    private boolean isEOF(char nextChar) {
        return nextChar == 0xFFFF;
    }

    private Token getInvalidToken(int nextChar) throws IOException {
        StringBuilder builder = new StringBuilder().append(((char) nextChar));
        int invalidChar = readChar();
        while (isLetter(invalidChar)) {
            builder.append(((char) invalidChar));
            invalidChar = readChar();
        }
        buffer.unread(invalidChar);
        return Token.of(row, INVALID, builder.toString());
    }

    private int peekChar() throws IOException {
        int currentChar = readChar();
        buffer.unread(currentChar);
        return currentChar;
    }

    private char readChar() throws IOException {
        return (char) buffer.read();
    }
}
