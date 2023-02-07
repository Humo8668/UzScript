package uz.app.UzScript.lang.scanner.stateMachines;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uz.app.UzScript.lang.scanner.stateMachines.StringReader;

public class StringReaderTest {
    @Test
    public void testRead() {
        StringReader reader = new StringReader();
        String checkString = "asd123\"\nee";
        char[] charSequence = new char[]{'"','a','s','d','1','2','3','\\','"','\\', 'n','e','e','"'};
        
        for (char c : charSequence) {
            if(reader.Read(c)) {
                break;
            }
        }
        assertTrue("Not equal: " + checkString + " <> " + reader.getString(), checkString.equals(reader.getString()));
    }

    @Test(expected = RuntimeException.class)
    public void testReadWrongSequence() {
        StringReader reader = new StringReader();
        char[] charSequence = new char[]{'"','a','s','d','1','2','3','\\'};
        
        for (char c : charSequence) {
            if(reader.Read(c)) {
                break;
            }
        }
        reader.getString();
        //assertTrue("Not equal: " + checkString + " <> " + reader.getString(), checkString.equals(reader.getString()));
    }

    @Test(expected = RuntimeException.class)
    public void testGetString() {
        StringReader reader = new StringReader();
        reader.getString();
    }
}
