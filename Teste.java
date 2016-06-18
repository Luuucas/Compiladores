package com.br.compilador;

public class Teste {
    private static int numLog = 1;
    private static int numSymLog = 1;

    public static void main(String[] args) throws SymbolExistsException {
        SymbolTable sym = new SymbolTable();

        log(sym);
        sym.initScope();
        log(sym);
        sym.insert(new Symbol("x", "tipo", 1));
        sym.insert(new Symbol("y", "tipo", 2));
        sym.insert(new Symbol("z", "tipo", 3));
        sym.initScope();
        sym.insert(new Symbol("x", "tipo", 4));
        log(sym.search("yy"));
        log(sym);
        sym.finishScope();
        log(sym);
        sym.finishScope();
        log(sym);
    }

    public static void log(Symbol sym) {
        System.out.println(String.format("{%d}: %s", numSymLog++, sym != null ? sym.getId()+":"+sym.getLine() : "NOT FOUND"));
    }

    public static void log(SymbolTable sym) {
        System.out.println(String.format("[%d]: %s", numLog++, sym));
    }

}
