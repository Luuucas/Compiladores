package com.br.compilador;


public class LexException extends Throwable {
    private Character ch;
    private int line;
    private int column;

    public LexException(Character ch, int line, int column) {
        setCh(ch);
        setLine(line);
        setColumn(column);
    }

    public Character getCh() {
        return ch;
    }

    public void setCh(Character ch) {
        this.ch = ch;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return String.format("Erro ao analisar a linha %d na " +
                "coluna %d. Caractere encontrado: %s",
                getLine(), getColumn(), String.valueOf(getCh() != null ? getCh() : "EOF"));
    }
}
