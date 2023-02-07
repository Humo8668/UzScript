package uz.app.UzScript.lang.scanner.units;

public class NumberValue extends NonTerminal {
    private Number value;

    public NumberValue(Integer id, Number val) {
        super(id);
        this.value = val;
    }
    public Number getValue() {
        return value;
    }
}
