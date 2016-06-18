package com.br.compilador;

import java.io.StringWriter;
import java.util.Stack;

public class SymbolTable {

    private Stack<Symbol> stack;

    public SymbolTable() {
        stack = new Stack<>();
    }

    public void insert(Symbol newSymbol) throws SymbolExistsException {
        Stack<Symbol> aux = new Stack<>();

        if (!stack.isEmpty()) {
            Symbol symbol;
            do {
                symbol = stack.pop();
                aux.push(symbol);

                if (newSymbol.getId().equals(symbol.getId())) {
                    pushAll(aux, stack);
                    throw new SymbolExistsException(newSymbol, symbol);
                }
            } while (symbol.getId() != null);

            pushAll(aux, stack);
            stack.push(newSymbol);
        }
    }

    private void pushAll(Stack from, Stack to) {
        while (!from.isEmpty()) {
            to.push(from.pop());
        }
    }

    public Symbol search(String id) {
        Stack<Symbol> aux = new Stack<>();
        Symbol symbolFound = null;

        while (!stack.isEmpty()) {
            Symbol symbol = stack.pop();
            aux.push(symbol);

            if (id.equals(symbol.getId())) {
                symbolFound = symbol;
                break;
            }
        }

        pushAll(aux, stack);

        return symbolFound;
    }

    public void initScope() {
        stack.push(new Symbol());
    }

    public void finishScope() {
        if (!stack.isEmpty()) {
            Symbol symbol;
            do {
                symbol = stack.pop();
            } while (symbol.getId() != null);
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");

        str.append("[");
        if (!stack.isEmpty()) {
            for (Symbol sym : stack) {
                str.append(sym.getId() != null ? sym.getId() : "$");
                str.append(",");
            }
            str.deleteCharAt(str.length()-1);
        }
        str.append("]");
        return str.toString();
    }
}
