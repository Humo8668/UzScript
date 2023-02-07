package uz.app.UzScript.lang.scanner.stateMachines;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Locale;

public class NumberReader extends StateMachine {
    StringBuilder numberStr = new StringBuilder();
    private Hashtable<String, Character> stateMatrix = new Hashtable<>(); // key - concatenation of two chars (symbol,state); value - new state to accept

    public NumberReader() {
        super(new char[]{'0','1','2','3','4','5','6','7','8','9','.'}, 
            new char[] {
                '0', // STATE - leading zeros
                'W', // STATE - whole part
                'F', // STATE - fractional part
                'x'  // STATE - end state - the number have been read. Reading more symbols have been stopped
            },
            '0', 'x'
        );
        for (char c : this.alphabet) {
            stateMatrix.put(c + "" + '0', 'W');
            stateMatrix.put(c + "" + 'W', 'W');
            stateMatrix.put(c + "" + 'F', 'F');
            stateMatrix.put(c + "" + 'x', 'x');
        }
        stateMatrix.put("00", '0');
        stateMatrix.put("0W", 'W');
        stateMatrix.put("0F", 'F');
        stateMatrix.put("0x", 'x');

        stateMatrix.put(".0", 'F');
        stateMatrix.put(".W", 'F');
        stateMatrix.put(".F", 'x');
        stateMatrix.put(".x", 'x');
    }

    @Override
    public boolean Read(char symbol) {
        String matrixKey = symbol + "" + this.currentState;
        if(!stateMatrix.containsKey(matrixKey)) {
            if(!this.isInAlphabet(symbol)) {
                this.currentState = 'x';
                return true;
            } else 
                throw new RuntimeException("Unknown matrix key: " + matrixKey);
        }
        this.currentState = stateMatrix.get(matrixKey);
        if(this.currentState == this.exitState) {
            return true;
        }
        this.numberStr.append(symbol);
        return false;
    }
    
    public Number getNumber() {
        if(this.currentState != this.exitState)
            throw new RuntimeException("The exit state have not reached yet!");
        else
            try {
                return NumberFormat.getInstance(Locale.US).parse(numberStr.toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
    }
}
