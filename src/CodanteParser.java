import java.io.IOException;

public class CodanteParser {
    private CodanteLexer lexer;
    private Token currentToken;

    public void parse(CodanteLexer lexer) throws IOException, ParseException {
        this.lexer = lexer;
        currentToken = lexer.getNextToken();

        parseCode();

        lexer.close();
    }

    private void parseCode() throws IOException, ParseException {
        while (currentToken.getType() != TokenType.EOF) {
            parseStatement();
        }
    }

    private void parseStatement() throws IOException, ParseException {
        if (match(TokenType.IDENTIFIER, "output")) {
            parseOutputStatement();
        } else if (match(TokenType.KEYWORD, "if")) {
            parseIfStatement();
        } else {
            throw new ParseException("Unexpected token: " + currentToken, currentToken.getLine(), currentToken.getCharIndex());
        }
    }

    private void parseOutputStatement() throws IOException, ParseException {
        match(TokenType.STRING, "O resultado da soma é: ");

        if (currentToken.getType() == TokenType.IDENTIFIER) {
            System.out.print(currentToken.getLexeme());
            currentToken = lexer.getNextToken();
        } else {
            throw new ParseException("Unexpected token: " + currentToken, currentToken.getLine(), currentToken.getCharIndex());
        }

        match(TokenType.STRING, "\n");
    }

    private void parseIfStatement() throws IOException, ParseException {
        // Parsing condition
        match(TokenType.SYMBOL, "(");

        if (currentToken.getType() == TokenType.IDENTIFIER) {
            System.out.print(currentToken.getLexeme());
            currentToken = lexer.getNextToken();
        } else {
            throw new ParseException("Unexpected token: " + currentToken, currentToken.getLine(), currentToken.getCharIndex());
        }

        match(TokenType.SYMBOL, ">");

        if (currentToken.getType() == TokenType.NUMBER) {
            System.out.print(currentToken.getLexeme());
            currentToken = lexer.getNextToken();
        } else {
            throw new ParseException("Unexpected token: " + currentToken, currentToken.getLine(), currentToken.getCharIndex());
        }

        match(TokenType.SYMBOL, ")");

        // Parsing if-block
        if (currentToken.getType() == TokenType.KEYWORD && currentToken.getLexeme().equals("then")) {
            currentToken = lexer.getNextToken();
            parseIfBlock();
        } else {
            throw new ParseException("Expected 'then' keyword, found: " + currentToken, currentToken.getLine(), currentToken.getCharIndex());
        }

        // Parsing else-block
        if (currentToken.getType() == TokenType.KEYWORD && currentToken.getLexeme().equals("else")) {
            currentToken = lexer.getNextToken();
            parseElseBlock();
        } else {
            throw new ParseException("Expected 'else' keyword, found: " + currentToken, currentToken.getLine(), currentToken.getCharIndex());
        }

        // Parsing end keyword
        if (currentToken.getType() == TokenType.KEYWORD && currentToken.getLexeme().equals("end")) {
            currentToken = lexer.getNextToken();
        } else {
            throw new ParseException("Expected 'end' keyword, found: " + currentToken, currentToken.getLine(), currentToken.getCharIndex());
        }
    }

    private void parseIfBlock() throws IOException, ParseException {
        match(TokenType.STRING, "O resultado é maior que 10\n");
    }

    private void parseElseBlock() throws IOException, ParseException {
        match(TokenType.STRING, "O resultado não é maior que 10\n");
    }

    private boolean match(TokenType expectedType, String expectedLexeme) throws IOException, ParseException {
        if (currentToken.getType() == expectedType && currentToken.getLexeme().equals(expectedLexeme)) {
            currentToken = lexer.getNextToken();
            return true;
        } else {
            throw new ParseException("Expected '" + expectedLexeme + "', found: " + currentToken, currentToken.getLine(), currentToken.getCharIndex());
        }
    }
}
