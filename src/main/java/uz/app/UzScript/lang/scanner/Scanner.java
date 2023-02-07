package uz.app.UzScript.lang.scanner;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import uz.app.UzScript.lang.exceptions.UndefinedIdentifier;
import uz.app.UzScript.lang.exceptions.UndefinedTerminalSymbol;
import uz.app.UzScript.lang.exceptions.UnexpectedLiteral;
import uz.app.UzScript.lang.interpreter.Interpreter;
import uz.app.UzScript.lang.scanner.stateMachines.IdentifierReader;
import uz.app.UzScript.lang.scanner.stateMachines.MultiLineCommentReader;
import uz.app.UzScript.lang.scanner.stateMachines.NumberReader;
import uz.app.UzScript.lang.scanner.stateMachines.SingleLineCommentReader;
import uz.app.UzScript.lang.scanner.stateMachines.StringReader;
import uz.app.UzScript.lang.scanner.units.Identifier;
import uz.app.UzScript.lang.scanner.units.Lexeme;
import uz.app.UzScript.lang.scanner.units.NonTerminal;
import uz.app.UzScript.lang.scanner.units.NumberValue;
import uz.app.UzScript.lang.scanner.units.StringValue;
import uz.app.UzScript.lang.scanner.units.Terminal;
import uz.app.UzScript.lang.scanner.units.Lexeme.Type;

public class Scanner {
    private enum State {
        IDLE,
        READING_NUMBER,
        READING_STRING,
        READING_IDENTIFIER,
        COMMENT_FIRST_SLASH,
        COMMENT_SINGLE_LINE,
        COMMENT_MULTI_LINE
    }

    State state;
    LexemesTable lexemesTable;
    Interpreter interpreter;
    //Collection<Character> wordDelimiter;
    Queue<Integer> lexemesSequence;
    //**************************
    StringBuilder str = new StringBuilder();
    StringReader stringReaderMachine;
    NumberReader numberReaderMachine;
    IdentifierReader identifierReaderMachine;
    SingleLineCommentReader singleLineCommentReaderMachine;
    MultiLineCommentReader multiLineCommentReaderMachine;

    public Scanner() {
        state = State.IDLE;
        lexemesTable = new LexemesTable();
        interpreter = new Interpreter(lexemesTable);
        lexemesSequence = new ConcurrentLinkedQueue<>();
    }

    public boolean isLexemeSeparator(char c) {
        return c == '\n' || c == ' ';
    }

    public char getLexemeSeparator() {
        return ' ';
    }

    public void Read(char symbol) throws UnexpectedLiteral {
        switch(this.state) {
            case IDLE: {
                if(isLexemeSeparator(symbol)) {
                    this.state = State.IDLE;
                    break;
                }
                if(symbol == '"') {
                    this.state = State.READING_STRING;
                    stringReaderMachine = new StringReader();
                    stringReaderMachine.Read(symbol);
                } else if(Character.isDigit(symbol)) {
                    this.state = State.READING_NUMBER;
                    numberReaderMachine = new NumberReader();
                    numberReaderMachine.Read(symbol);
                }else if(IdentifierReader.isLegitAsFirstSymbol(symbol)) {
                    this.state = State.READING_IDENTIFIER;
                    identifierReaderMachine = new IdentifierReader();
                    identifierReaderMachine.Read(symbol);
                } else if(symbol == '/') {
                    this.state = State.COMMENT_FIRST_SLASH;
                } else /*If this is not a string|number|identifier(maybe terminal symbol too) 
                        then this is one-symbolic terminal symbol (ex.: +,-,*,(,),[,],=,<,>,;,.... )*/ {
                    if(lexemesTable.isTerminalSymbol("" + symbol)) {
                        try {
                            lexemesSequence.add(lexemesTable.getTerminalId("" + symbol));
                        } catch(UndefinedTerminalSymbol ex) {
                            throw new UnexpectedLiteral("" + symbol);
                        }
                    } else {
                        throw new UnexpectedLiteral("" + symbol);
                    }
                    this.state = State.IDLE;
                }
                break;
            }
            case READING_IDENTIFIER: {
                if(identifierReaderMachine.Read(symbol)) {
                    String identifier = identifierReaderMachine.getReadIdentifier();
                    Integer lexemeId = null;
                    if(lexemesTable.isTerminalSymbol(identifier)) {
                        try {
                            lexemeId = lexemesTable.getTerminalId(identifier);
                        } catch (UndefinedTerminalSymbol e) {
                            throw new RuntimeException(e);
                        }
                    } else if(!lexemesTable.hasIdentifier(identifier)) {
                        lexemeId = lexemesTable.addIdentifier(identifier);
                    } else {
                        try {
                            lexemeId = lexemesTable.getIdentifierId(identifier);
                        } catch (UndefinedIdentifier e) {
                            throw new RuntimeException(e);
                        }
                    }
                    lexemesSequence.add(lexemeId);
                    this.state = State.IDLE;
                    Read(symbol); // this symbol was used as stopper for previous state machine, so we should feed this symbol one more time
                }
                break;
            }
            case READING_NUMBER: {
                if(numberReaderMachine.Read(symbol)) {
                    Number num = numberReaderMachine.getNumber();
                    Integer nonTerminalId = lexemesTable.addNumberValue(num);
                    lexemesSequence.add(nonTerminalId);
                    this.state = State.IDLE;
                    Read(symbol); // this symbol was used as stopper for previous state machine, so we should feed this symbol one more time
                }
                break;
            }
            case READING_STRING: {
                if(stringReaderMachine.Read(symbol)) {
                    String str = stringReaderMachine.getString();
                    Integer nonTerminalId = lexemesTable.addStringValue(str);
                    lexemesSequence.add(nonTerminalId);
                    this.state = State.IDLE;
                }
                break;
            }
            case COMMENT_FIRST_SLASH: {
                if(symbol == '*') { // multi-line comment
                    this.state = State.COMMENT_MULTI_LINE;
                    multiLineCommentReaderMachine = new MultiLineCommentReader();
                } else if (symbol == '/') { // single-line comment
                    this.state = State.COMMENT_SINGLE_LINE;
                    singleLineCommentReaderMachine = new SingleLineCommentReader();
                } else { // if any other symbol, then previous slash was division sign
                    lexemesSequence.add(LexemesTable.T_DIVIDE); // add the division symbol from previous tick to the sequence
                    this.state = State.IDLE; // reset the state so that next symbol will be applied as separated lexeme
                    Read(symbol); // re-read the current symbol to analyze it on new context
                }
                break;
            }
            case COMMENT_MULTI_LINE: {
                if(multiLineCommentReaderMachine.Read(symbol)) {
                    this.state = State.IDLE;
                }
                break;
            }
            case COMMENT_SINGLE_LINE: {
                if(singleLineCommentReaderMachine.Read(symbol)) {
                    this.state = State.IDLE;
                }
                break;
            }
        }
    }

    public Integer getNextLexeme() {
        return this.lexemesSequence.poll();
    }

    public String getCodeRead() {
        StringBuilder codeRead = new StringBuilder();
        Integer lexemeId = getNextLexeme();
        while(lexemeId != null) {
            Lexeme lexeme = lexemesTable.getLexemeById(lexemeId);
            if(lexeme.getType() == Type.TERM) {
                Terminal terminal = (Terminal)lexeme;
                codeRead.append(terminal.getSymbol());
            } else if(lexeme.getType() == Type.NON_TERM) {
                NonTerminal nonTerminal = (NonTerminal)lexeme;
                if(nonTerminal instanceof Identifier) {
                    Identifier identifier = (Identifier)nonTerminal;
                    codeRead.append(identifier.getName());
                } else if(nonTerminal instanceof NumberValue) {
                    NumberValue numberValue = (NumberValue)nonTerminal;
                    codeRead.append(numberValue.getValue().toString());
                } else if(nonTerminal instanceof StringValue) {
                    StringValue stringValue = (StringValue)nonTerminal;
                    codeRead.append("\"");
                    codeRead.append(stringValue.getValue());
                    codeRead.append("\"");
                }
            }
            lexemeId = getNextLexeme();
            codeRead.append(getLexemeSeparator());
        }
        
        return codeRead.toString();
    }
}
