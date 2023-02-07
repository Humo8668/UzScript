package uz.app.UzScript.lang.scanner.units;

public abstract class Lexeme {
    public enum Type {
        TERM,
        NON_TERM
    }

    /**
     * The id of lexem (fixed by language's initial parameters)
     */
    protected Integer id;
    
    public Integer getId() {
        return id;
    }

    /**
     * Type of lexem (Terminal/NonTerminal). Used for differentiation on abstract level.
     */
    protected Type type;

    public Type getType() {
        return type;
    }

    public Lexeme(Type type, Integer id) {
        this.type = type;
        this.id = id;
    }
}