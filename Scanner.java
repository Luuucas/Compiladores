package com.br.compilador;
import java.io.*;

public class Scanner {
    private static final char EOF = 0;
    private static final int STATE_ID = 666;

    private BufferedReader input;
    private String line = null;
    private int lineIdx = 0;
    private int columnIdx = 0;

    private Token rollback = null;

    public Scanner(File input) throws FileNotFoundException {
        this.input = new BufferedReader(new FileReader(input));
    }

    public int getLine() {
        return lineIdx;
    }

    public int getColumn() {
        return columnIdx;
    }

    public Token nextToken() throws IOException, LexException {
        ScannerSt st = new ScannerSt();

        if (rollback != null) {
            st.token = rollback;
            rollback = null;
        } else {

            while (st.token == null && !st.exit) {
                st.ch = nextChar();

                switch (st.state) {
                    case 0: {
                        if (st.ch == '+') {
                        	st.lexema += st.ch;
                            st.token = new Token(TipoToken.OP_SOMA, st.lexema);                            
                        } else if (st.ch == '(') {
                            st.token = new Token(TipoToken.ABRE_PAR);
                        } else if (st.ch == ')') {
                            st.token = new Token(TipoToken.FECHA_PAR);
                        }else if (st.ch == '{') {
                            st.token = new Token(TipoToken.ABRE_BLOCO);
                        } else if (st.ch == '}') {
                            st.token = new Token(TipoToken.FECHA_BLOCO);
                        } else if (st.ch == '&') {
                            st.token = new Token(TipoToken.ENDERECO);
                        } else if (st.ch == ';') {
                            st.token = new Token(TipoToken.FIM_CMD);
                        } else if (st.ch == '-') {
                        	st.lexema += st.ch;
                            st.token = new Token(TipoToken.OP_SUB, st.lexema);
                        }else if (st.ch == '*') {
                        	st.lexema += st.ch;
                            st.token = new Token(TipoToken.OP_MULT, st.lexema);
                        }else if (st.ch == '/') {
                        	st.lexema += st.ch;
                            st.token = new Token(TipoToken.OP_DIV, st.lexema);
                        } else if (st.ch == '=') {
                            st.lexema += st.ch;
                            st.state = 55;
                        } else if (st.ch == '"') {
                            st.lexema += st.ch;
                            st.state = 20;
                        } else if (st.ch == '.') {
                            st.lexema += st.ch;
                            st.state = 18;
                        } else if (st.ch == ',') { //state 2
                            st.token = new Token(TipoToken.VIRGULA);
                        } else if (st.ch == 'i') {
                            st.lexema += st.ch;
                            st.state = 3;
                        } else if (st.ch == 'd') {
                            st.lexema += st.ch;
                            st.state = 6;
                        } else if (st.ch == 'f') {
                            st.lexema += st.ch;
                            st.state = 12;
                        } else if (st.ch == 'c') {
                            st.lexema += st.ch;
                            st.state = 25;
                        } else if (st.ch == 'p') {
                            st.lexema += st.ch;
                            st.state = 29;
                        }else if (st.ch == 'm') {
                            st.lexema += st.ch;
                            st.state = 35;
                        }else if(st.ch== 's'){
                        	st.lexema += st.ch;
                        	st.state = 41;
                        }else if(st.ch == 'w'){
                        	st.lexema += st.ch;
                        	st.state = 46;
                        }else if(st.ch == 'e'){
                        	st.lexema += st.ch;
                        	st.state = 51;
                        }else if (isLetterOrUnderline(st.ch)) {
                            st.lexema += st.ch;
                            st.state = STATE_ID;
                        } else if (Character.isDigit(st.ch)) {
                            st.lexema += st.ch;
                            st.state = 16;
                        } else if (st.ch == EOF) {
                            st.exit = true;
                        } else if (st.ch != ' ' && st.ch != '\t' && st.ch != '\n') {
                            throw new LexException(st.ch, lineIdx, columnIdx);
                        }
                        break;
                    }
                    case 3:
                    	if(st.ch == 'f'){
                    		st.token = new Token(TipoToken.IF, st.lexema);
                            rollback(st.ch);
                    	}
                        treatTipoVar(st, 'n', 4);
                        break;
                    case 4:
                        treatTipoVar(st, 't', 5);
                        break;
                    case 5: {
                        if (isLetterOrNumberOrUnderline(st.ch)) {
                            st.state = STATE_ID;
                            st.lexema += st.ch;
                        } else {
                            st.token = new Token(TipoToken.TIPO_VAR, st.lexema);
                            rollback(st.ch);
                        }
                        break;
                    }
                    case 6:
                        treatTipoVar(st, 'o', 7);
                        break;
                    case 7:
                        treatTipoVar(st, 'u', 8);
                        break;
                    case 8:
                        treatTipoVar(st, 'b', 9);
                        break;
                    case 9:
                        treatTipoVar(st, 'l', 10);
                        break;
                    case 10:
                        treatTipoVar(st, 'e', 5);
                        break;
                    case 12:
                        treatTipoVar(st, 'l', 13);
                        break;
                    case 13:
                        treatTipoVar(st, 'o', 14);
                        break;
                    case 14:
                        treatTipoVar(st, 'a', 15);
                        break;
                    case 15:
                        treatTipoVar(st, 't', 5);
                        break;
                    case 16:
                        if (Character.isDigit(st.ch)) {
                            st.lexema += st.ch;
                        } else if (st.ch == '.') {
                            st.lexema += st.ch;
                            st.state = 19;
                        } else {
                            st.token = new Token(TipoToken.NUMERO_INT, st.lexema);
                            rollback(st.ch);
                        }
                        break;
                    case 18:
                        if (Character.isDigit(st.ch)) {
                            st.lexema += st.ch;
                            st.state = 19;
                        } else {
                            throw new LexException(st.ch, lineIdx, columnIdx);
                        }
                        break;
                    case 19:
                        if (Character.isDigit(st.ch)) {
                            st.lexema += st.ch;
                        } else {
                            st.token = new Token(TipoToken.NUMERO_REAL, st.lexema);
                            rollback(st.ch);
                        }
                        break;
                    case 20:
                        if (st.ch == '"') {
                            st.lexema += st.ch;
                            st.token = new Token(TipoToken.STRING, st.lexema);
                        } else if (st.ch == '%') {
                            st.lexema += st.ch;
                            st.state = 21;
                        } else if (st.ch != '\n') {
                            st.lexema += st.ch;
                            st.state = 22;
                        } else {
                            throw new LexException(st.ch, lineIdx, columnIdx);
                        }
                        break;
                    case 21:
                        if (st.ch == 'd') {
                            st.lexema += st.ch;
                            st.state = 23;
                        } else if (st.ch == 'f') {
                            st.lexema += st.ch;
                            st.state = 24;
                        } else if (st.ch != '\n') {
                            st.lexema += st.ch;
                            st.state = 22;
                        } else {
                            throw new LexException(st.ch, lineIdx, columnIdx);
                        }
                        break;
                    case 22:
                        if (st.ch == '"') {
                            st.lexema += st.ch;
                            st.token = new Token(TipoToken.STRING, st.lexema);
                        } else if (st.ch != '\n') {
                            st.lexema += st.ch;
                        } else {
                            throw new LexException(st.ch, lineIdx, columnIdx);
                        }
                        break;
                    case 23:
                        if (st.ch == '"') {
                            st.token = new Token(TipoToken.FMT_INT);
                        } else if (st.ch != '\n') {
                            st.lexema += st.ch;
                            st.state = 22;
                        } else {
                            throw new LexException(st.ch, lineIdx, columnIdx);
                        }
                        break;
                    case 24:
                        if (st.ch == '"') {
                            st.token = new Token(TipoToken.FMT_FLOAT);
                        } else if (st.ch != '\n') {
                            st.lexema += st.ch;
                            st.state = 22;
                        } else {
                            throw new LexException(st.ch, lineIdx, columnIdx);
                        }
                        break;
                    case 25:
                    	treatTipoVar(st, 'h', 26);
                    	break;
                    	
                    case 26:
                    	treatTipoVar(st, 'a', 27);
                    	break;
                    case 27:
                    	treatTipoVar(st, 'r', 28);
                    	break;
                    
                    case 28:
                    	if (isLetterOrNumberOrUnderline(st.ch)) {
                            st.state = STATE_ID;
                            st.lexema += st.ch;
                        } else {
                            st.token = new Token(TipoToken.TIPO_VAR, st.lexema);
                            rollback(st.ch);
                        }
                        break;
                        
                    case 29:
                    	treatTipoVar(st, 'r', 30);
                    	break;
                    case 30:
                    	treatTipoVar(st, 'i', 31);
                    	break;
                    case 31:
                    	treatTipoVar(st, 'n', 32);
                    	break;
                    case 32:
                    	treatTipoVar(st, 't', 33);
                    	break;
                    case 33:
                    	treatTipoVar(st, 'f', 34);
                    	break;
                    case 34:
                    	if (isLetterOrNumberOrUnderline(st.ch)) {
                            st.state = STATE_ID;
                            st.lexema += st.ch;
                        } else {
                            st.token = new Token(TipoToken.PRINT, st.lexema);
                            rollback(st.ch);
                        }
                    	break;
                    	
                    case 35:
                    	treatTipoVar(st, 'a', 36);
                    	break;
                    
                    case 36:
                    	treatTipoVar(st, 'i', 37);
                    	break;
                    
                    case 37:
                    	treatTipoVar(st, 'n', 38);
                    	break;
                    case 38:
                    	treatTipoVar(st, '(', 39);
                    	break;

                    case 39:
                    	treatTipoVar(st, ')', 40);
                    	break;
                    
                    case 40:
                    	if (isLetterOrNumberOrUnderline(st.ch)) {
                            st.state = STATE_ID;
                            st.lexema += st.ch;
                        } else {
                            st.token = new Token(TipoToken.MAIN);
                            rollback(st.ch);
                        }
                    	break;
                    	
                    case 41: treatTipoVar(st, 'c', 42);
                    		 break;
                    case 42: treatTipoVar(st, 'a', 43);
           		 			 break;
                    case 43: treatTipoVar(st, 'n', 44);
           		 			 break;
                    case 44: treatTipoVar(st, 'f', 45);
           		 			 break;
           		 			 
                    case 45:
                    	if (isLetterOrNumberOrUnderline(st.ch)) {
                            st.state = STATE_ID;
                            st.lexema += st.ch;
                        } else {
                            st.token = new Token(TipoToken.SCAN);
                            rollback(st.ch);
                        }
                    	break;
                    	
                    case 46: treatTipoVar(st, 'h', 47);
                    		 break;
                    case 47: treatTipoVar(st, 'i', 48);
           		 			 break;
                    case 48: treatTipoVar(st, 'l', 49);
           		 			 break;
                    case 49: treatTipoVar(st, 'e', 50);
           		 			 break;
           		 			 
                    case 50:
                    	if (isLetterOrNumberOrUnderline(st.ch)) {
                            st.state = STATE_ID;
                            st.lexema += st.ch;
                        } else {
                            st.token = new Token(TipoToken.WHILE);
                            rollback(st.ch);
                        }
                    	break;
                    
                    case 51: treatTipoVar(st, 'l', 52);
  		 			 	 	 break;
                    case 52: treatTipoVar(st, 's', 53);
	 			 	 	 	 break;
                    case 53: treatTipoVar(st, 'e', 54);
	 			 	 	 	 break;
	 			 	 	 	 
                    case 54:
                    	if (isLetterOrNumberOrUnderline(st.ch)) {
                            st.state = STATE_ID;
                            st.lexema += st.ch;
                        } else {
                            st.token = new Token(TipoToken.ELSE);
                            rollback(st.ch);
                        }
                    	break;
                    	
                    case 55: treatTipoIgual(st, '=');
                    	
                    	
                    case STATE_ID: {
                        if (isLetterOrNumberOrUnderline(st.ch)) {
                            st.lexema += st.ch;
                        } else {
                            st.token = new Token(TipoToken.ID, st.lexema);
                            rollback(st.ch);
                        }
                        break;
                    }
                }
            }
        }

        /*System.out.println("LOG: " + (st.token != null ?
            st.token.getTipo().name() : "EOF"));*/

        return st.token;
    }

    private void treatTipoVar(ScannerSt state, char want, int nextState) {
        if (state.ch == want) {
            state.state = nextState;
            state.lexema += state.ch;
        } else if (isLetterOrNumberOrUnderline(state.ch)) {
            state.state = STATE_ID;
            state.lexema += state.ch;
        } else {
            state.token = new Token(TipoToken.ID, state.lexema);
            rollback(state.ch);
        }
    }
    private void treatTipoIgual(ScannerSt state, char want) {
        if (state.ch == want) {
            state.token= new Token(TipoToken.OP_IGUAL);
        } else {
            state.token = new Token(TipoToken.ATRIBUICAO);
            rollback(state.ch);
        }
    }

    private boolean isLetterOrUnderline(char ch) {
        return Character.isLetter(ch) || ch == '_';
    }

    private boolean isLetterOrNumberOrUnderline(char ch) {
        return Character.isLetterOrDigit(ch) || ch == '_';
    }

    private void rollback(char ch) {
        if (ch != EOF) {
            columnIdx--;
        }
    }

    private char nextChar() throws IOException {
        char next;

        if (line == null || columnIdx >= line.length()) {
            line = input.readLine();
            columnIdx = 0;

            if (line != null) {
                lineIdx++;
                line += '\n';
            }
        }

        if (line == null) { //Fim do arquivo
            next = EOF;
        } else {
            next = line.charAt(columnIdx++);
        }

        return next;
    }

    public void rollbackToken(Token token) {
        rollback = token;
    }

    class ScannerSt {
        Token token = null;
        boolean exit = false;
        String lexema = "";
        int state = 0;
        char ch;
    }

}
