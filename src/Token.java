public class Token {
    public int kind;
    public String image;
    public int intValue;

    public Token(int kind) {
        this(kind, null, 0);
    }

    public Token(int kind, String image) {
        this(kind, image, 0);
    }

    public Token(int kind, int intValue) {
        this(kind, null, intValue);
    }

    public Token(int kind, String image, int intValue) {
        this.kind = kind;
        this.image = image;
        this.intValue = intValue;
    }
}
