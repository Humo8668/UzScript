package uz.app.UzScript.lang.exceptions;

public class UndefinedTerminalSymbol extends Exception {
    String undefinedTerminal;
    public UndefinedTerminalSymbol(String symbol) {
        super();
        this.undefinedTerminal = symbol;
    }

    public String getUndefinedTerminal() {
        return undefinedTerminal;
    }
}
