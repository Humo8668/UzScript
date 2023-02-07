package uz.app.UzScript.lang.interpreter;

import uz.app.UzScript.lang.scanner.LexemesTable;

public class Interpreter {
    LexemesTable lexemesTable;

    public Interpreter(LexemesTable lexemesTable) {
        this.lexemesTable = lexemesTable;
    }

    public LexemesTable getLexemesTable() {
        return lexemesTable;
    }
}
