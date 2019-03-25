package it.uniupo.disit.linguaggi2.acdccompiler.parser;

import it.uniupo.disit.linguaggi2.acdccompiler.token.Token;
import it.uniupo.disit.linguaggi2.acdccompiler.token.TokenType;

class SyntacticException extends Exception {

    SyntacticException(Token token, TokenType tokenType) {
        super("Invalid token " + token.toString() + ". Looking for " + tokenType.toString());
    }

    SyntacticException(Token token, NonTerminal nonTerminal) {
        super("Invalid token " + token.toString() + " in non terminal " + nonTerminal.toString());
    }
}
