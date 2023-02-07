package uz.app.UzScript.lang.scanner.stateMachines;

import java.util.regex.Pattern;

public abstract class StateMachine {
    char[] alphabet;
    char[] states;
    char startState;
    char exitState;
    char currentState;

    public StateMachine(char[] alphabet, char[] states, char startState, char exitState) {
        this.alphabet = alphabet.clone();
        this.states = states.clone();
        this.exitState = exitState;

        this.startState = 0;
        for (char c : states) {
            if(c == startState) {
                this.startState = startState;
                break;
            }
        }
        if(this.startState == 0) {
            throw new RuntimeException("The start state is illegal or must be in states list.");
        }
        this.currentState = startState;

        this.exitState = 0;
        for (char c : states) {
            if(c == exitState) {
                this.exitState = exitState;
                break;
            }
        }
        if(this.exitState == 0) {
            throw new RuntimeException("The exit state is illegal or must be in states list.");
        }
    }

    public StateMachine(String alphabetRegex, char[] states, char startState, char exitState) {
        Pattern pattern = Pattern.compile(alphabetRegex);
    }

    protected boolean isInStatesList(char state) {
        for (char c : states) {
            if(c == state) {
                return true;
            }
        }
        return false;
    }

    protected boolean isInAlphabet(char symbol) {
        for (char c : alphabet) {
            if(c == symbol) {
                return true;
            }
        }
        return false;
    }

    protected void setState(char newState) {
        if(!isInStatesList(newState))
            throw new RuntimeException("Illegal state to set");
        this.currentState = newState;
    }
    protected char getState() {
        return this.currentState;
    }

    /**
     *
     * @param symbol The symbol to 
     * @return Returns {@code}true{@code} if exit state has reached or got the symbol out of the alphabet
     */
    public abstract boolean Read(char symbol);
}
