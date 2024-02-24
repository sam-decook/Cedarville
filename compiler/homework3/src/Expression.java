import java.io.IOException;
import java.io.Reader;

public class Expression {
    Expression lhs;
    Expression rhs;
    Type op;
    long number;
    
    public enum Type {ADD, MUL, NUMBER, GROUP}
    
    public Expression(long number) {
        this.number = number;
        this.lhs = null;
        this.rhs = null;
        this.op = Type.NUMBER;
    }
    
    public Expression(Type op, Expression lhs, Expression rhs) {
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        this.number = 0;
    }
    
    /* Grammar productions:
     *   expr -> term { op term }
     *   term -> ( expr ) | NUM
     *   op -> + | *
     */
    static Expression parse(Reader input) throws IOException {
        Expression lhs = Term.parse(input);

        char token = (char) input.read();

        while (token == '+' || token == '*') {            
            Expression rhs = Term.parse(input);
            
            switch (token) {
                case '+':
                    lhs = new Expression(Type.ADD, lhs, rhs);
                    break;
                case '*':
                    lhs = new Expression(Type.MUL, lhs, rhs);
                    break;
                default:
                    throw new IOException("Operand not + or *");
            }

            token = (char) input.read();
        }

        return lhs;
    }

    static long calculate(Expression expr) {
        switch (expr.op) {
            case ADD:
                return calculate(expr.lhs) + calculate(expr.rhs);
            case MUL:
                return calculate(expr.lhs) * calculate(expr.rhs);
            case NUMBER:
                return expr.number;
            default: //unreachable
                return 0;
        }
    }
}
