public class Token {
    private Type tokenType;
    private Object tokenData;
   
    public enum Type {
        IDENT_TOKEN,
        NUM_TOKEN,
        // Keywords
        IF_TOKEN,
        ELSE_TOKEN,
        INT_TOKEN,
        RETURN_TOKEN,
        VOID_TOKEN,
        WHILE_TOKEN,
        // Symbols
        ADD_TOKEN,      // +
        SUB_TOKEN,      // -
        MUL_TOKEN,      // *
        DIV_TOKEN,      // /
        LT_TOKEN,       // <
        LTE_TOKEN,      // <=
        GT_TOKEN,       // >
        GTE_TOKEN,      // >=
        EQ_TOKEN,       // ==
        NEQ_TOKEN,      // !=
        ASSIGN_TOKEN,   // =
        EOS_TOKEN,      // ; (end-of-statement)
        COMMA_TOKEN,    // ,
        LPAREN_TOKEN,   // (
        RPAREN_TOKEN,   // )
        LBRACKET_TOKEN, // [
        RBRACKET_TOKEN, // ]
        LBRACE_TOKEN,   // {
        RBRACE_TOKEN,   // }
        // Misc
        ERR_TOKEN,
        EOF_TOKEN,
    }

    public enum State {
        START,
        INNUM,
        INID,
        INCOMMENT,
        SLASH,
        STAR,
        LESS,
        GREATER,
        EQUAL,
        EXCLAM,
        DONE
    }
    
    public Token (Token.Type type) {
        this(type, null);
    }

    public Token (Type type, Object data) {
        tokenType = type;
        tokenData = data;
    }
    
    // some access methods
    public Type getType() {
        return tokenType;
    }

    public Object getData() {
        return tokenData;
    }

    // Converts ident in place to a keyword token if it is a keyword
    public void convertToKeyword() {
        if (this.tokenType != Type.IDENT_TOKEN) {
            return;
        }
        switch (String.valueOf(this.tokenData)) {
            case "if":
                this.tokenType = Type.IF_TOKEN;
                break;
            case "else":
                this.tokenType = Type.ELSE_TOKEN;
                break;
            case "int":
                this.tokenType = Type.INT_TOKEN;
                break;
            case "return":
                this.tokenType = Type.RETURN_TOKEN;
                break;
            case "void":    
                this.tokenType = Type.VOID_TOKEN;
                break;
            case "while":
                this.tokenType = Type.WHILE_TOKEN;
                break;
            default:
                return;
        }
    }
}
    
