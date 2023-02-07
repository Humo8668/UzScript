package uz.app.UzScript.lang.exceptions;

public class UnexpectedLiteral extends Exception{
    private String literal;
    public UnexpectedLiteral(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }
}
