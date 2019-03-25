package it.uniupo.disit.linguaggi2.acdccompiler.scanner;

import it.uniupo.disit.linguaggi2.acdccompiler.token.Token;
import it.uniupo.disit.linguaggi2.acdccompiler.token.TokenType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import static it.uniupo.disit.linguaggi2.acdccompiler.token.TokenType.*;
import static java.lang.Character.*;

public class Scanner {

    private static final char EOF = 0xFFFF;
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

    public Scanner(String fileName) throws FileNotFoundException {
        this.buffer = new PushbackReader(new FileReader(fileName));
        this.nextToken = null;
        this.row = 1;
    }

    public Token nextToken() throws IOException, LexicalException {
        Token currentToken;
        if (nextToken == null) {
            currentToken = scanNextToken();
        } else {
            currentToken = nextToken;
            nextToken = null;
        }
        return checkInvalid(currentToken);
    }

    public Token peekToken() throws IOException, LexicalException {
        if (nextToken == null)
            nextToken = scanNextToken();
        return checkInvalid(nextToken);
    }

    private Token scanNextToken() throws IOException {
        char nextChar = readChar();
        while (isBlank(nextChar)) {
            if (nextChar == '\n') row++;
            nextChar = readChar();
        }
        if (isDigit(nextChar))
            return scanNumber(nextChar);
        if (isLetter(nextChar) && isLowerCase(nextChar))
            return scanId(nextChar);
        if (isOperator(nextChar))
            return Token.of(row, OPERATORS.get(nextChar));
        if (isEOF(nextChar))
            return Token.of(row, TokenType.EOF);
        return invalidToken(nextChar);
    }

    private boolean isBlank(char nextChar) {
        return SKIP_CHARS.contains(nextChar);
    }

    private boolean isOperator(char nextChar) {
        return OPERATORS.containsKey(nextChar);
    }

    private Token scanNumber(char nextChar) throws IOException {
        StringBuilder builder = new StringBuilder().append(nextChar);
        appendWhile(Character::isDigit, builder);
        nextChar = readChar();
        if (nextChar != '.') {
            buffer.unread(nextChar);
            return Token.of(row, INUM, builder.toString());
        } else {
            builder.append(nextChar);
            appendWhile(Character::isDigit, builder);
            return Token.of(row, FNUM, builder.toString());
        }
    }

    private Token scanId(char nextChar) throws IOException {
        StringBuilder builder = new StringBuilder().append(nextChar);
        appendWhile(c -> isLetter(c) && isLowerCase(c), builder);
        String id = builder.toString();
        if (KEYWORDS.containsKey(id))
            return Token.of(row, KEYWORDS.get(id));
        return Token.of(row, ID, id);
    }

    private boolean isEOF(char nextChar) {
        return nextChar == EOF;
    }

    private Token invalidToken(char nextChar) throws IOException {
        StringBuilder builder = new StringBuilder().append(nextChar);
        appendWhile(Character::isLetter, builder);
        return Token.of(row, INVALID, builder.toString());
    }

    private Token checkInvalid(Token token) throws LexicalException {
        if (token.getType() == INVALID)
            throw new LexicalException(token.toString());
        return token;
    }

    private void appendWhile(Predicate<Character> condition, StringBuilder builder) throws IOException {
        char nextChar = readChar();
        while (condition.test(nextChar)) {
            builder.append(nextChar);
            nextChar = readChar();
        }
        buffer.unread(nextChar);
    }

    private char readChar() throws IOException {
        return (char) buffer.read();
    }
}
