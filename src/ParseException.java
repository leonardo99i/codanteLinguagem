public class ParseException extends Exception {
    private int line;
    private int charIndex;

    public ParseException(String message, int line, int charIndex) {
        super(message);
        this.line = line;
        this.charIndex = charIndex;
    }

    public int getLine() {
        return line;
    }

    public int getCharIndex() {
        return charIndex;
    }
}
