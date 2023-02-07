package uz.app.UzScript.lang.scanner.units;

public class StringValue extends NonTerminal {
    private String value;

    public StringValue(Integer id, String val) {
        super(id);
        this.value = val;
    }
    public String getValue() {
        return value;
    }
}
