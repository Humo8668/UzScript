package uz.app.UzScript.lang.scanner.stateMachines;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uz.app.UzScript.lang.scanner.stateMachines.IdentifierReader;

public class IdentifierReaderTest {
    @Test
    public void testRead() {
        char[] testSequence = new char[] {'a', 's', 'd', '1', '_',';'};
        String checkStr = "asd1_";
        IdentifierReader reader = new IdentifierReader();
        for (char c : testSequence) {
            if(reader.Read(c)) {
                break;
            }
        }
        
        assertTrue("Expected: " + checkStr+"; Got: " + reader.getReadIdentifier(), checkStr.equals(reader.getReadIdentifier()));
    }

    @Test
    public void testReadWithWrongData() {
        char[] testSequence = new char[] {'a', '$', 'd', '1', '_','('};
        String checkStr = "a";
        IdentifierReader reader = new IdentifierReader();
        for (char c : testSequence) {
            if(reader.Read(c)) {
                break;
            }
        }
        
        assertTrue("Expected: " + checkStr+"; Got: " + reader.getReadIdentifier(), checkStr.equals(reader.getReadIdentifier()));
    }

    @Test(expected = RuntimeException.class)
    public void testReadWithLeadingDigits() {
        char[] testSequence = new char[] {'1', 'a', 'd', '1', '_','('};
        IdentifierReader reader = new IdentifierReader();
        for (char c : testSequence) {
            if(reader.Read(c)) {
                break;
            }
        }
    }

    @Test(expected = RuntimeException.class)
    public void testGetReadIdentifier() {
        IdentifierReader reader = new IdentifierReader();
        reader.getReadIdentifier();
    }
}
