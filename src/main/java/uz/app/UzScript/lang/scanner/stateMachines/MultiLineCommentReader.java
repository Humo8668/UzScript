package uz.app.UzScript.lang.scanner.stateMachines;

/**
 * Note: comment section opening symbols (i.e. "/*") must not be passed to {@code}Read(){@code} method.
 */
public class MultiLineCommentReader extends StateMachine {

    public MultiLineCommentReader() {
        super(
            new char[]{}, 
            new char[]{
                's', // start state
                'e', // end state
                'c', // comment characters
                '*'  // the beginning of the end of comment section >:-D
            }, 
            's', 
            'e');
    }

    @Override
    public boolean Read(char symbol) {
        if(this.currentState == 'e') {
            return true;
        }
        if(this.currentState == 's') {
            this.setState('c');
        }
        if(this.currentState == '*') {
            if(symbol == '/') {
                this.setState('e');
                return true;
            } else {
                this.setState('c');
            }
        }
        if(this.currentState == 'c') {
            if(symbol == '*'){
                this.setState('*');
            }
        }
        
        return false;
    }
    
}
