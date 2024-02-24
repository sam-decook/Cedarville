import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/* Parse an arithmetic expression. Only parenthesis give higher precedence.
 * Operands are to be left-associative. E.g. 3+2*4 produces 20, not 11.
 * 
 * Input consists of single-digit numbers, parenthesis, +, *, and no spaces.
 *
 * Grammar productions:
 *   expr -> term { op term }
 *   term -> ( expr ) | NUM
 *   op -> + | *
 */

public class Main {
    public static void main(String[] args) throws IOException {
        Reader input = new InputStreamReader(System.in);

        Expression expr = Expression.parse(input);
        System.out.println(Expression.calculate(expr));

        input.close();
    }

    static boolean isDigit(byte token) {
        return token >= '0' && token <= '9';
    }
}
