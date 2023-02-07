package uz.app.UzScript.lang.scanner.units;

public class Identifier extends NonTerminal {

    /**
     * Name of identifier (variable, function names, class names)
     */
    private String name;

    public String getName() {
        return name;
    }

    public Identifier(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
