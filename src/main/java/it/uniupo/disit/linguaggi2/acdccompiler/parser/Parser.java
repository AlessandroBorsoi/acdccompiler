package it.uniupo.disit.linguaggi2.acdccompiler.parser;

import it.uniupo.disit.linguaggi2.acdccompiler.ast.*;
import it.uniupo.disit.linguaggi2.acdccompiler.scanner.LexicalException;
import it.uniupo.disit.linguaggi2.acdccompiler.scanner.Scanner;
import it.uniupo.disit.linguaggi2.acdccompiler.token.Token;
import it.uniupo.disit.linguaggi2.acdccompiler.token.TokenType;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static it.uniupo.disit.linguaggi2.acdccompiler.ast.LangOper.*;
import static it.uniupo.disit.linguaggi2.acdccompiler.ast.LangType.FLOAT_TYPE;
import static it.uniupo.disit.linguaggi2.acdccompiler.ast.LangType.INT_TYPE;
import static it.uniupo.disit.linguaggi2.acdccompiler.parser.NonTerminal.*;
import static it.uniupo.disit.linguaggi2.acdccompiler.token.TokenType.*;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

public class Parser {

    private final Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = requireNonNull(scanner);
    }

    public NodeAST parse() throws IOException, LexicalException, SyntacticException {
        return parsePrg();
    }

    private NodeProgram parsePrg() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case FLOAT:
            case INT:
            case ID:
            case PRINT:
            case EOF:
                NodeProgram nodeProgram = new NodeProgram(parseDSs());
                match(EOF);
                return nodeProgram;
        }
        throw new SyntacticException(token, PRG);
    }

    private List<NodeDecSt> parseDSs() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case FLOAT:
            case INT:
                return concat(Stream.of(parseDcl()), parseDSs().stream()).collect(toList());
            case ID:
            case PRINT:
                return concat(Stream.of(parseStm()), parseDSs().stream()).collect(toList());
            case EOF:
                return emptyList();
        }
        throw new SyntacticException(token, DSS);
    }

    private NodeDecl parseDcl() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case FLOAT:
                match(FLOAT);
                return new NodeDecl(new NodeId(match(ID).getValue()), FLOAT_TYPE);
            case INT:
                match(INT);
                return new NodeDecl(new NodeId(match(ID).getValue()), INT_TYPE);
        }
        throw new SyntacticException(token, DCL);
    }

    private NodeStm parseStm() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case ID:
                Token id = match(ID);
                match(ASSIGN);
                return new NodeAssign(new NodeId(id.getValue()), parseExp());
            case PRINT:
                match(PRINT);
                return new NodePrint(new NodeId(match(ID).getValue()));
        }
        throw new SyntacticException(token, STM);
    }

    private NodeExpr parseExp() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case INUM:
            case FNUM:
            case ID:
                return parseExpP(parseTr());
        }
        throw new SyntacticException(token, EXP);
    }

    private NodeExpr parseExpP(NodeExpr left) throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case PLUS:
                match(PLUS);
                return parseExpP(new NodeBinOp(PLUS_OP, left, parseTr()));
            case MINUS:
                match(MINUS);
                return parseExpP(new NodeBinOp(MINUS_OP, left, parseTr()));
            case FLOAT:
            case INT:
            case ID:
            case PRINT:
            case EOF:
                return left;
        }
        throw new SyntacticException(token, EXPP);
    }

    private NodeExpr parseTr() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case INUM:
            case FNUM:
            case ID:
                return parseTrP(parseVal());
        }
        throw new SyntacticException(token, TR);
    }

    private NodeExpr parseTrP(NodeExpr left) throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case TIMES:
                match(TIMES);
                return parseTrP(new NodeBinOp(TIMES_OP, left, parseVal()));
            case DIV:
                match(DIV);
                return parseTrP(new NodeBinOp(DIV_OP, left, parseVal()));
            case PLUS:
            case MINUS:
            case FLOAT:
            case INT:
            case ID:
            case PRINT:
            case EOF:
                return left;
        }
        throw new SyntacticException(token, TRP);
    }

    private NodeExpr parseVal() throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        switch (token.getType()) {
            case INUM:
                return new NodeCost(match(INUM).getValue(), INT_TYPE);
            case FNUM:
                return new NodeCost(match(FNUM).getValue(), FLOAT_TYPE);
            case ID:
                return new NodeDeref(new NodeId(match(ID).getValue()));
        }
        throw new SyntacticException(token, VAL);
    }

    private Token match(TokenType tokenType) throws IOException, LexicalException, SyntacticException {
        Token token = scanner.peekToken();
        if (tokenType != token.getType())
            throw new SyntacticException(token, tokenType);
        return scanner.nextToken();
    }

}
