import java.io.IOException;
import java.io.Reader;

/* Grammar productions:
 *   expr -> term { op term }
 *   term -> ( expr ) | NUM
 *   op -> + | *
 */
public class Term {
    private Term() {}

    static Expression parse(Reader input) throws IOException {
        int token = input.read();
        Expression expr;

        if ((char) token == '(') {
            expr = Expression.parse(input);
        } else {
            int number = token - '0';
            expr = new Expression(number);
        }

        return expr;
    }
}
