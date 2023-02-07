package uz.app.UzScript.lang.exceptions;

public class UndefinedIdentifier extends Exception{
    String undefinedNonTerminal;
    public UndefinedIdentifier(String symbol) {
        super();
        this.undefinedNonTerminal = symbol;
    }

    public String getUndefinedNonTerminal() {
        return undefinedNonTerminal;
    }
}
