package uz.app.UzScript.lang.scanner.stateMachines;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uz.app.UzScript.lang.scanner.stateMachines.MultiLineCommentReader;

public class MultiLineCommentReaderTest {
    @Test
    public void testRead() {
        MultiLineCommentReader reader;
        String testStr;
        int pos;
        
        // Cycle 1
        reader = new MultiLineCommentReader();
        testStr = "blabla comment */";
        pos = 0;
        for(pos = 0; pos < testStr.length(); pos++) {
            if(reader.Read(testStr.charAt(pos))) {
                break;
            }
        }
        assertTrue("Stopped before reached the end.", pos == testStr.indexOf("*/") + "*/".length() - 1);
        assertTrue("Not stopped after reached the end.", reader.Read('a'));

        // Cycle 2
        reader = new MultiLineCommentReader();
        testStr = "tricky * comment / \n \n \n /*  */";
        pos = 0;
        for(pos = 0; pos < testStr.length(); pos++) {
            if(reader.Read(testStr.charAt(pos))) {
                break;
            }
        }
        assertTrue("Stopped before reached the end.", pos == testStr.indexOf("*/") + "*/".length() - 1);
        assertTrue("Not stopped after reached the end.", reader.Read('a'));
    }
}
