package com.br.compilador;


public class InvalidTypeException extends Exception {
    private Symbol symbol;
    private String tipo;

    public InvalidTypeException(Symbol symbol, String tipo) {
        this.symbol = symbol;
        this.tipo = tipo;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Erro ao analiasar a linha "+ symbol.getLine()
                + ". Não foi possível converter " + tipo + " em " + symbol.getType();
    }
}
