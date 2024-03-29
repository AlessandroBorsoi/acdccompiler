package it.uniupo.disit.linguaggi2.acdccompiler.ast;

public enum LangOper {
    PLUS_OP("+"), MINUS_OP("-"), TIMES_OP("*"), DIV_OP("/");

    private final String symbol;

    LangOper(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
