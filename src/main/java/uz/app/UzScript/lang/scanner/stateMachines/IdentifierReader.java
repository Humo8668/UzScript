package uz.app.UzScript.lang.scanner.stateMachines;

public class IdentifierReader extends StateMachine {

    StringBuffer str = new StringBuffer();
    private static char[] alphabet = new char[] {
        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
        '0','1','2','3','4','5','6','7','8','9', '_'
    };

    public IdentifierReader() {
        super(
            alphabet, 
            new char[]{
                's',  // start state
                'e',  // end state
                'c'   // reading sequence of letters of identifier
            }, 's', 'e');
    }

    @Override
    public boolean Read(char symbol) {
        if(this.currentState == 'e') {
            return true;
        }
        if(!this.isInAlphabet(symbol)) {
            this.setState('e');
            return true;
        }
        if(this.currentState == 's') {
            if(!Character.isLetter(symbol)) {
                throw new RuntimeException("This is not Identifier!");
            } else {
                this.setState('c');
                str.append(symbol);
                return false;
            }
        }
        if(this.currentState == 'c') {
            str.append(symbol);
            return false;
        }
        return true;
    }
    
    public String getReadIdentifier() {
        if(this.currentState != 'e') {
            throw new RuntimeException("Reading the identifier is not ended yet!");
        }
        return str.toString();
    }

    public static boolean isAlphabetic(char symbol) {
        for (char c : alphabet) {
            if(c == symbol) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLegitAsFirstSymbol(char symbol) {
        return isAlphabetic(symbol) && !Character.isDigit(symbol);
    }
}
