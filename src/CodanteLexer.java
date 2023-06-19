import java.io.*;
import java.util.*;

public class CodanteLexer {
    private BufferedReader reader;
    private String currentLine;
    private int currentLineIndex;
    private int currentCharIndex;
    private Token currentToken;

    public CodanteLexer(String filename) throws IOException {
        reader = new BufferedReader(new FileReader(filename));
        currentLine = reader.readLine();
        currentLineIndex = 1;
        currentCharIndex = 0;
        currentToken = null;
    }

    public Token getNextToken() throws IOException, ParseException {
        if (currentToken != null && currentToken.getType() == TokenType.EOF) {
            return currentToken;
        }

        skipWhitespace();

        if (currentCharIndex >= currentLine.length()) {
            if (currentLine == null) {
                return new Token(TokenType.EOF, "", currentLineIndex, currentCharIndex);
            }
            currentLine = reader.readLine();
            currentLineIndex++;
            currentCharIndex = 0;
            return getNextToken();
        }

        char currentChar = currentLine.charAt(currentCharIndex);

        if (Character.isDigit(currentChar)) {
            return parseNumber();
        } else if (Character.isLetter(currentChar)) {
            return parseIdentifier();
        } else {
            throw new ParseException("Unexpected character: " + currentChar, currentLineIndex, currentCharIndex);
        }
    }

    private void skipWhitespace() {
        while (currentCharIndex < currentLine.length() && Character.isWhitespace(currentLine.charAt(currentCharIndex))) {
            currentCharIndex++;
        }
    }

    private Token parseNumber() {
        int startIndex = currentCharIndex;
        while (currentCharIndex < currentLine.length() && Character.isDigit(currentLine.charAt(currentCharIndex))) {
            currentCharIndex++;
        }
        String lexeme = currentLine.substring(startIndex, currentCharIndex);
        return new Token(TokenType.NUMBER, lexeme, currentLineIndex, startIndex);
    }

    private Token parseIdentifier() {
        int startIndex = currentCharIndex;
        while (currentCharIndex < currentLine.length() && Character.isLetterOrDigit(currentLine.charAt(currentCharIndex))) {
            currentCharIndex++;
        }
        String lexeme = currentLine.substring(startIndex, currentCharIndex);
        return new Token(TokenType.IDENTIFIER, lexeme, currentLineIndex, startIndex);
    }

    public void close() throws IOException {
        reader.close();
    }
}
