package com.br.compilador;

public class SymbolExistsException extends Exception {
    private Symbol symbol;
    private Symbol lastSymbol;

    public SymbolExistsException(Symbol symbol, Symbol lastSymbol) {
        this.symbol = symbol;
        this.lastSymbol = lastSymbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getLastSymbol() {
        return lastSymbol;
    }

    public void setLastSymbol(Symbol lastSymbol) {
        this.lastSymbol = lastSymbol;
    }

    @Override
    public String toString() {
        return "Erro ao analiasar a linha "+ symbol.getLine()
                + ". O símbolo '" + symbol.getId() + "' já foi declarado na linha " + lastSymbol.getLine() + ".";
    }
}
