package uz.app.UzScript.lang.scanner.stateMachines;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uz.app.UzScript.lang.scanner.stateMachines.SingleLineCommentReader;

public class SingleLineCommentReaderTest {
    @Test
    public void testRead() {
        SingleLineCommentReader reader;
        String testStr;

        // Cycle 1
        reader = new SingleLineCommentReader();
        testStr = "// 11123123 \n";
        for(int i = 0; i < testStr.length(); i++) {
            if(reader.Read(testStr.charAt(i))) {
                break;
            }
        }
        assertTrue("Reader is not in \"end\"-state after new line", reader.getState() == reader.exitState);

        // Cycle 2
        reader = new SingleLineCommentReader();
        testStr = "11123123 //";
        for(int i = 0; i < testStr.length(); i++) {
            if(reader.Read(testStr.charAt(i))) {
                break;
            }
        }
        assertTrue("Reader is in \"end\"-state while no new line reached", reader.getState() != reader.exitState);
    }
}
