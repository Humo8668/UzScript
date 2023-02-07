package uz.app.UzScript.lang.scanner.stateMachines;

public class SingleLineCommentReader extends StateMachine {

    public SingleLineCommentReader() {
        super(
            new char[]{}, 
            new char[]{
                's',
                'e',
                'c'
            },
            's', 
            'e');
    }

    @Override
    public boolean Read(char symbol) {
        if(this.currentState == 'e') {
            throw new RuntimeException("Reading the single-line comment has ended.");
        }
        if(this.currentState == 's') {
            setState('c');
        }
        if(this.currentState == 'c') {
            if(symbol == '\n') {
                setState('e');
                return true;
            }
        }
        
        return false;
    }
    
}
