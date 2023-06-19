import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            CodanteLexer lexer = new CodanteLexer("Codante.txt");
            CodanteParser parser = new CodanteParser();
            parser.parse(lexer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Parse error at line " + e.getLine() + ", char " + e.getCharIndex() + ": " + e.getMessage());
        }
    }
}
