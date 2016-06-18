package com.br.compilador;

public class Symbol {
    private String id;
    private String type;
    private int line;

    public Symbol() {
    }

    public Symbol(String id, int line) {
        this.id = id;
        this.line = line;
    }

    public Symbol(String id, String type, int line) {
        this.id = id;
        this.type = type;
        this.line = line;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }
}
