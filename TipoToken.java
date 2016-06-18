package com.br.compilador;

public enum TipoToken {
	MAIN("main()"),
    VIRGULA(","),
    FIM_CMD(";"),
    ID("identificador"),
    TIPO_VAR("tipo de variável"),
    NUMERO_INT("inteiro"),
    NUMERO_REAL("real"),
    ATRIBUICAO("atribuição"),
    PRINT("printf"),
    ABRE_PAR("("),
    FECHA_PAR(")"),
    ABRE_BLOCO("{"),
    FECHA_BLOCO("}"),
    STRING("string"),
    FMT_INT("%d"),
    FMT_FLOAT("%f"),
    ENDERECO("&"),
    SCAN("scanf"),
    OP_SOMA("+"),
    OP_SUB("-"),
    OP_MULT("*"),
    OP_DIV("/"),
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    OP_MAIORQ(">"),
    OP_MENORQ("<"),
    OP_MAIOROUEG(">="),
    OP_MENOROUEG("<="),
    OP_IGUAL("=="),
    CHAR("char");

    String nick;
    
    TipoToken(String nick) {
        this.nick = nick;
    }
    

    @Override
    public String toString() {
        return nick;
    }
}
