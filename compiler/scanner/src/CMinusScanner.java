import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class CMinusScanner implements Scanner {
    private BufferedReader inFile;
    private Token nextToken;

    public CMinusScanner (String filename) throws IOException {
        inFile = new BufferedReader(new FileReader(filename));
        nextToken = getToken();
    }

    public Token getNextToken () throws IOException {
        Token returnToken = nextToken;
        if (nextToken.getType() != Token.Type.EOF_TOKEN) {
            nextToken = getToken();
        }
        return returnToken;
    }
    
    public Token viewNextToken () {
        return nextToken;
    }

    public Token getToken() throws IOException {
        Token.Type currentToken = null;
        StringBuilder data = new StringBuilder();
        Token.State state = Token.State.START;
        inFile.mark(0);
        char c = (char)inFile.read();
        while(state != Token.State.DONE){ 
            switch(state){
                case START:
                    if(Character.isDigit(c)) {
                        state = Token.State.INNUM;
                    } 
                    else if (Character.isAlphabetic(c)) {
                        state = Token.State.INID;
                    } 
                    else if ((c == ' ') || (c == '\t') || (c == '\n')) {
                        inFile.mark(0);
                        c = (char)inFile.read();
                    } 
                    else if (c == '/'){
                        state = Token.State.SLASH;
                    }
                    else if (c == '<'){
                        state = Token.State.LESS;
                    }
                    else if (c == '>'){
                        state = Token.State.GREATER;
                    }
                    else if (c == '='){
                        state = Token.State.EQUAL;
                    }
                    else if (c == '!'){
                        state = Token.State.EXCLAM;
                    }
                    else{
                        state = Token.State.DONE;
                        data.append(c);
                        switch(c) {
                            case (char) 0xFFFFFFFF: 
                                currentToken = Token.Type.EOF_TOKEN;
                                break;
                            case '+':
                                currentToken = Token.Type.ADD_TOKEN;
                                break;
                            case '-':
                                currentToken = Token.Type.SUB_TOKEN;
                                break;
                            case '*':
                                currentToken = Token.Type.MUL_TOKEN;
                                break;
                            case '(':
                                currentToken = Token.Type.LPAREN_TOKEN;
                                break;
                            case ')':
                                currentToken = Token.Type.RPAREN_TOKEN;
                                break;
                            case '{':
                                currentToken = Token.Type.LBRACE_TOKEN;
                                break;
                            case '}':
                                currentToken = Token.Type.RBRACE_TOKEN;
                                break;
                            case '[':
                                currentToken = Token.Type.LBRACKET_TOKEN;
                                break;
                            case ']':
                                currentToken = Token.Type.RBRACKET_TOKEN;
                                break;
                            case ';':
                                currentToken = Token.Type.EOS_TOKEN;
                                break;
                            case ',':
                                currentToken = Token.Type.COMMA_TOKEN;
                                break;
                            default:
                                currentToken = Token.Type.ERR_TOKEN;
                                break;
                        }
                    }
                    break;
                case SLASH: 
                    data.append(c);
                    inFile.mark(0);
                    c = (char)inFile.read();
                    if (c == '*') {
                        state = Token.State.INCOMMENT; 
                    }
                    else {
                        if (c != (char) 0xFFFFFFFF){
                            inFile.reset();
                        }
                        currentToken = Token.Type.DIV_TOKEN;
                        state = Token.State.DONE;    
                    }
                    break;
                case INCOMMENT: 
                    data.append(c);
                    c = (char) inFile.read();
                    inFile.mark(0);
                    if (c == '*') {
                        state = Token.State.STAR;
                    } 
                    else if (c == (char) 0xFFFFFFFF) {
                        state = Token.State.DONE;
                        currentToken = Token.Type.ERR_TOKEN;
                    }
                    else {
                        if (c != (char) 0xFFFFFFFF) {
                            inFile.reset();
                        }
                    }
                    break;
                case STAR:
                    c = (char) inFile.read();
                    if (c == '/') {
                        state = Token.State.START;
                        data = new StringBuilder();
                        c = (char) inFile.read();
                        inFile.mark(0);
                    } else if (c != '*') {
                        state = Token.State.INCOMMENT;
                    } // remain in STAR if c == '*'
                    break;
                case INNUM:
                    data.append(c);
                    inFile.mark(0);
                    c = (char)inFile.read();
                    if(Character.isAlphabetic(c)){
                        data.append(c);
                        state = Token.State.DONE;
                        currentToken = Token.Type.ERR_TOKEN;                      
                    }
                    else if (!Character.isDigit(c)){
                        if(c != (char)0xFFFFFFFF){
                            inFile.reset();
                        }
                        state = Token.State.DONE;
                        currentToken = Token.Type.NUM_TOKEN;
                    }

                    break;
                case INID:
                    data.append(c);
                    inFile.mark(0);
                    c = (char)inFile.read();
                    if(Character.isDigit(c)){
                        data.append(c);
                        state = Token.State.DONE;
                        currentToken = Token.Type.ERR_TOKEN;
                    }
                    else if (!Character.isAlphabetic(c)) {
                        if(c != (char)0xFFFFFFFF){
                            inFile.reset();
                        }
                        state = Token.State.DONE;
                        currentToken = Token.Type.IDENT_TOKEN;
                    } 
                    break;
                case LESS:
                    data.append(c);
                    inFile.mark(0);
                    c = (char)inFile.read();
                    if(c == '='){
                        data.append(c);
                        currentToken = Token.Type.LTE_TOKEN;
                    }
                    else{
                        if(c != (char)0xFFFFFFFF){
                            inFile.reset();
                        }
                        currentToken = Token.Type.LT_TOKEN;
                    }
                    state = Token.State.DONE;
                    break;
                case GREATER:
                    data.append(c);
                    inFile.mark(0);
                    c = (char)inFile.read();
                    if (c == '=') {
                        data.append(c);
                        currentToken = Token.Type.GTE_TOKEN;
                    }
                    else {
                        if(c != (char)0xFFFFFFFF){
                            inFile.reset();
                        }
                        currentToken = Token.Type.GT_TOKEN;
                    }
                    state = Token.State.DONE;
                    break;
                case EQUAL:
                    data.append(c);
                    inFile.mark(0);
                    c = (char)inFile.read();
                    if (c == '=') {
                        data.append(c);
                        currentToken = Token.Type.EQ_TOKEN;
                    }
                    else {
                        if(c != (char)0xFFFFFFFF){
                            inFile.reset();
                        }
                        currentToken = Token.Type.ASSIGN_TOKEN;
                    }
                    state = Token.State.DONE;
                    break;
                case EXCLAM:
                    data.append(c);
                    inFile.mark(0);
                    c = (char)inFile.read();
                    if (c == '=') {
                        data.append(c);
                        currentToken = Token.Type.NEQ_TOKEN;
                    }
                    else {
                        if(c != (char)0xFFFFFFFF){
                            inFile.reset();
                        }
                        currentToken = Token.Type.ERR_TOKEN;
                    }
                    state = Token.State.DONE;
                    break;
                default:
                    state = Token.State.DONE;
                    currentToken = Token.Type.ERR_TOKEN;
                    break;

                
            }
        }
        Token token = new Token(currentToken, data.toString());
        token.convertToKeyword();
        return token;
    }
    
    public static void main (String[] args) throws IOException{
        File outputFile = new File("scanner/src/output.txt");
        FileWriter writeOutput = new FileWriter(outputFile);
        CMinusScanner scan = new CMinusScanner("scanner/src/bad.txt");   
        while(scan.nextToken.getType() != Token.Type.EOF_TOKEN){
            Token tok = scan.nextToken;
            String toPrint = tok.getType() + " \t" + tok.getData() + "\n";
            System.out.print(toPrint);
            writeOutput.write(toPrint);
            scan.getNextToken();
        }

        writeOutput.close();
    }
}

    