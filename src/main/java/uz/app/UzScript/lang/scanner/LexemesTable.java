package uz.app.UzScript.lang.scanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import uz.app.UzScript.lang.exceptions.UndefinedIdentifier;
import uz.app.UzScript.lang.exceptions.UndefinedTerminalSymbol;
import uz.app.UzScript.lang.scanner.units.Identifier;
import uz.app.UzScript.lang.scanner.units.Lexeme;
import uz.app.UzScript.lang.scanner.units.NonTerminal;
import uz.app.UzScript.lang.scanner.units.NumberValue;
import uz.app.UzScript.lang.scanner.units.StringValue;
import uz.app.UzScript.lang.scanner.units.Terminal;

public class LexemesTable {
    private class Sequence {
       private int counter = 0;

       public int getNext() {
           return ++counter;
       }

       public int getCurrent() {
           return counter;
       }
    }

    Collection<Terminal> terminals = new ArrayList<Terminal>();
    Collection<NonTerminal> nonTerminals = new ArrayList<NonTerminal>();

    Map<Integer, Lexeme> mapIdToLexeme = new HashMap<Integer, Lexeme>();
    Map<String, Terminal> mapSymbolToTerminal = new HashMap<String, Terminal>();
    Map<String, Identifier> mapSymbolToIdentifier = new HashMap<String, Identifier>();
    //Map<Terminal, String> mapTerminalToSymbol = new HashMap<Terminal, String>();

    
    Sequence nonTerminalIdSeq = new Sequence();

    public static final Integer T_CONST = 0;
    public static final Integer T_BEGIN = -1;
    public static final Integer T_END = -2;
    public static final Integer T_IF = -3;
    public static final Integer T_ELSE = -4;
    public static final Integer T_AND = -5;
    public static final Integer T_OR = -6;
    public static final Integer T_FOR = -7;
    public static final Integer T_WHILE = -8;
    public static final Integer T_ASSIGN = -9;
    public static final Integer T_PLUS = -10;
    public static final Integer T_MINUS = -11;
    public static final Integer T_MULTIPLY = -12;
    public static final Integer T_DIVIDE = -13;
    public static final Integer T_BRACKET_LEFT = -14;
    public static final Integer T_BRACKET_RIGHT = -15;
    public static final Integer T_EQUALS = -16;
    public static final Integer T_LESS_THAN = -17;
    public static final Integer T_GREATER_THAN = -18;
    public static final Integer T_CODE_LINE_SEPARATOR = -19;
    public static final Integer T_SQUARE_BRACKET_LEFT = -20;
    public static final Integer T_SQUARE_BRACKET_RIGHT = -21;
    public static final Integer T_CURRY_BRACKET_LEFT = -22;
    public static final Integer T_CURRY_BRACKET_RIGHT = -23;
    public static final Integer T_DOT = -25;

    public LexemesTable() {
        this.terminals.add(new Terminal(T_CONST, "doimiy"));
        this.terminals.add(new Terminal(T_BEGIN, "bosh"));
        this.terminals.add(new Terminal(T_END, "oxir"));
        this.terminals.add(new Terminal(T_IF, "agar"));
        this.terminals.add(new Terminal(T_ELSE, "bo'lmasa"));
        this.terminals.add(new Terminal(T_AND, "va"));
        this.terminals.add(new Terminal(T_OR, "yo'ki"));
        this.terminals.add(new Terminal(T_FOR, "har"));
        this.terminals.add(new Terminal(T_WHILE, "toki"));
        this.terminals.add(new Terminal(T_ASSIGN, "="));
        this.terminals.add(new Terminal(T_PLUS, "+"));
        this.terminals.add(new Terminal(T_MINUS, "-"));
        this.terminals.add(new Terminal(T_MULTIPLY, "*"));
        this.terminals.add(new Terminal(T_DIVIDE, "/"));
        this.terminals.add(new Terminal(T_BRACKET_LEFT, "("));
        this.terminals.add(new Terminal(T_BRACKET_RIGHT, ")"));
        this.terminals.add(new Terminal(T_EQUALS, "=="));
        this.terminals.add(new Terminal(T_LESS_THAN, ">"));
        this.terminals.add(new Terminal(T_GREATER_THAN, "<"));
        this.terminals.add(new Terminal(T_CODE_LINE_SEPARATOR, ";"));
        this.terminals.add(new Terminal(T_SQUARE_BRACKET_LEFT, "["));
        this.terminals.add(new Terminal(T_SQUARE_BRACKET_RIGHT, "]"));
        this.terminals.add(new Terminal(T_CURRY_BRACKET_LEFT, "{"));
        this.terminals.add(new Terminal(T_CURRY_BRACKET_RIGHT, "}"));
        this.terminals.add(new Terminal(T_DOT, "."));

        for (Terminal terminal : terminals) {
            mapSymbolToTerminal.put(terminal.getSymbol(), terminal);
            //mapTerminalToSymbol.put(terminal, terminal.getSymbol());
            mapIdToLexeme.put(terminal.getId(), terminal);
        }
    }

    public Lexeme getLexemeById(Integer lexemeId) {
        return mapIdToLexeme.get(lexemeId);
    }

    public Integer getTerminalId(String symbol) throws UndefinedTerminalSymbol {
        if(!this.mapSymbolToTerminal.containsKey(symbol)) {
            throw new UndefinedTerminalSymbol(symbol);
        }
        return mapSymbolToTerminal.get(symbol).getId();
    }

    /**
     * Returns the Id of Identifier (variable, constant, function name, etc..)
     * @return
     */
    public Integer getIdentifierId(String symbol) throws UndefinedIdentifier {
        if(!mapSymbolToIdentifier.containsKey(symbol)) {
            throw new UndefinedIdentifier(symbol);
        }
        return mapSymbolToIdentifier.get(symbol).getId();
    }

    public Integer addStringValue(String value) {
        int id = nonTerminalIdSeq.getNext();
        StringValue stringValue = new StringValue(id, value);
        nonTerminals.add(stringValue);
        mapIdToLexeme.put(id, stringValue);
        return id;
    }

    public Integer addNumberValue(Number value) {
        int id = nonTerminalIdSeq.getNext();
        NumberValue numberValue = new NumberValue(id, value);
        nonTerminals.add(numberValue);
        mapIdToLexeme.put(id, numberValue);
        return id;
    }

    public Integer addIdentifier(String name) {
        int id = nonTerminalIdSeq.getNext();
        Identifier identifier = new Identifier(id, name);
        nonTerminals.add(identifier);
        mapSymbolToIdentifier.put(name, identifier);
        mapIdToLexeme.put(id, identifier);
        return id;
    }

    public boolean isTerminalSymbol(String symbol) {
        return mapSymbolToTerminal.containsKey(symbol);
    }

    public boolean hasIdentifier(String symbol) {
        return this.mapSymbolToIdentifier.containsKey(symbol);
    }
}
