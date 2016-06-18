package com.br.compilador;

public class SymbolNotFoundException extends Exception {

    private Symbol symbol;

    public SymbolNotFoundException(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Erro ao analiasar a linha "+ symbol.getLine()
                + ". O símbolo '" + symbol.getId() + "' não foi declarado.";
    }
}
