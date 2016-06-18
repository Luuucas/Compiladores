package com.br.compilador;

public class SinException extends Throwable {
    private Token token;
    private TipoToken[] expected;
    private int line;
    private int column;

    public SinException(Token token, TipoToken[] expected, int line, int column) {
        setToken(token);
        setExpected(expected);
        setLine(line);
        setColumn(column);
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public TipoToken[] getExpected() {
        return expected;
    }

    public void setExpected(TipoToken[] expected) {
        this.expected = expected;
    }

    public String getStringExpected() {
        String str = "fim do arquivo";

        if (expected != null && expected.length > 0) {
            str = "";
            for (TipoToken tipo : expected) {
                str += tipo + " ou ";
            }

            str = str.substring(0, str.length()-4);;
        }

        return str;
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
                "coluna %d. Encontrado %s quando o esperado era %s",
                getLine(), getColumn(), String.valueOf(getToken() != null ?
                        getToken().getTipo() : "fim do arquivo"), getStringExpected());
    }
}
