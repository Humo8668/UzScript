package uz.app.UzScript.lang.scanner.units;

public class Terminal extends Lexeme {
    private String symbol;
    public String getSymbol() {
        return symbol;
    }

    public Terminal(Integer id, String symbol) {
        super(Type.TERM, id);
        this.symbol = symbol;
    }
}
