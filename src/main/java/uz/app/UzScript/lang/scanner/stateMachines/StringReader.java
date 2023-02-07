package uz.app.UzScript.lang.scanner.stateMachines;

public class StringReader extends StateMachine {
    private StringBuffer stringBuffer = new StringBuffer();

    public StringReader() {
        super(new char[] {},
            new char[] {
                'S',  // start
                'E',  // exit
                'C',  // character
                '\\',  // next symbol is special symbol (like ",\n or something)
            },
            'S', 'E');
    }

    @Override
    protected boolean isInAlphabet(char symbol) {
        return true;
    }

    @Override
    public boolean Read(char symbol) {
        if(this.currentState == 'S') {
            if(symbol != '"') 
                throw new RuntimeException("String must begin with <\"> sign.");
            this.setState('C');
        } else if(this.currentState == 'E') {
            throw new RuntimeException("String reading have ended yet!");
        } else if(this.currentState == 'C') {
            if(symbol == '\\') {
                this.setState('\\');
            } else if(symbol == '"') {
                this.setState('E');
                return true;
            } else {
                stringBuffer.append(symbol);
            }
        } else if(this.currentState == '\\') {
            if(symbol == 'n')
                stringBuffer.append('\n');
            else
                stringBuffer.append(symbol);
            this.setState('C');
        }
        return false;
    }

    public String getString() {
        if(this.currentState != 'E') {
            throw new RuntimeException("String reading have not ended yet!");
        }
        return stringBuffer.toString();
    }
}
