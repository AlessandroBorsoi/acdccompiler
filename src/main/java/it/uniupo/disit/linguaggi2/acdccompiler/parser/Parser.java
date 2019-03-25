package it.uniupo.disit.linguaggi2.acdccompiler.parser;

import it.uniupo.disit.linguaggi2.acdccompiler.scanner.LexicalException;
import it.uniupo.disit.linguaggi2.acdccompiler.scanner.Scanner;
import it.uniupo.disit.linguaggi2.acdccompiler.token.Token;
import it.uniupo.disit.linguaggi2.acdccompiler.token.TokenType;

import java.io.IOException;

import static it.uniupo.disit.linguaggi2.acdccompiler.parser.NonTerminal.*;
import static it.uniupo.disit.linguaggi2.acdccompiler.token.TokenType.*;
import static java.util.Objects.requireNonNull;

public class Parser {

    private final Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = requireNonNull(scanner);
    }

    public boolean parse() throws IOException, LexicalException, SyntacticException {
        parsePrg();
        return true;
    }

    private void parsePrg() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case FLOAT:
            case INT:
            case ID:
            case PRINT:
            case EOF:
                parseDSs();
                match(EOF);
                return;
        }
        throw new SyntacticException(token, PRG);
    }

    private void parseDSs() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case FLOAT:
            case INT:
                parseDcl();
                parseDSs();
                return;
            case ID:
            case PRINT:
                parseStm();
                parseDSs();
                return;
            case EOF:
                return;
        }
        throw new SyntacticException(token, DSS);
    }

    private void parseDcl() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case FLOAT:
                match(FLOAT);
                match(ID);
                return;
            case INT:
                match(INT);
                match(ID);
                return;
        }
        throw new SyntacticException(token, DCL);
    }

    private void parseStm() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case ID:
                match(ID);
                match(ASSIGN);
                parseExp();
                return;
            case PRINT:
                match(PRINT);
                match(ID);
                return;
        }
        throw new SyntacticException(token, STM);
    }

    private void parseExp() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case INUM:
            case FNUM:
            case ID:
                parseTr();
                parseExpP();
                return;
        }
        throw new SyntacticException(token, EXP);
    }

    private void parseExpP() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case PLUS:
                match(PLUS);
                parseTr();
                parseExpP();
                return;
            case MINUS:
                match(MINUS);
                parseTr();
                parseExpP();
                return;
            case FLOAT:
            case INT:
            case ID:
            case PRINT:
            case EOF:
                return;
        }
        throw new SyntacticException(token, EXPP);
    }

    private void parseTr() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case INUM:
            case FNUM:
            case ID:
                parseVal();
                parseTrP();
                return;
        }
        throw new SyntacticException(token, TR);
    }

    private void parseTrP() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case TIMES:
                match(TIMES);
                parseVal();
                parseTrP();
                return;
            case DIV:
                match(DIV);
                parseVal();
                parseTrP();
                return;
            case PLUS:
            case MINUS:
            case FLOAT:
            case INT:
            case ID:
            case PRINT:
            case EOF:
                return;
        }
        throw new SyntacticException(token, TRP);
    }

    private void parseVal() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case INUM:
                match(INUM);
                return;
            case FNUM:
                match(FNUM);
                return;
            case ID:
                match(ID);
                return;
        }
        throw new SyntacticException(token, VAL);
    }

    private void match(TokenType tokenType) throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        if (tokenType != token.getType())
            throw new SyntacticException(token, tokenType);
        scanner.nextToken();
    }
}
