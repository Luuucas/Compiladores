package com.br.compilador;

public class Token {
    private String lexema;
    private TipoToken tipo;

    public Token(TipoToken tipo) {
        setTipo(tipo);
    }

    public Token(TipoToken tipo, String lexema) {
        setTipo(tipo);
        setLexema(lexema);
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public void setTipo(TipoToken tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        String str;

        if (lexema != null) {
            str = String.format("<%s,'%s'>", tipo.name(), lexema);
        } else {
            str = String.format("<%s>", tipo.name());
        }

        return str;
    }
}
