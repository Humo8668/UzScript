package uz.app.UzScript.lang.scanner;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uz.app.UzScript.lang.exceptions.UnexpectedLiteral;


public class ScannerTest {
    @Test
    public void testRead() {
        Scanner scanner;
        String testStr;
        String codeRead;
        String testStrZipped;
        int CYCLE;

        String[] testStrings = new String[] {
            "g = 9.81 ;",
            "str = \"asd\" ;",
            "doimiy g = 9.810001 ;",
            "agar a > 0 bosh a = 0 oxir ;",
            "S = 0 ; har ( i = 0 ; i < 10 ; i = i + 1 ) bosh S = S + i ; oxir ;",
            "b = 1 ; i = 1 ; j = 1 ; "+
                "toki ( b < 100 ) "+
                "bosh "+
                "j = b ; b = i + b ; i = j ; "+
                "oxir ; konsol . yoz ( b ) ;",
            "agar a == 0 bosh a = a + 1 ; oxir ;"
        };

        for(CYCLE = 0; CYCLE < testStrings.length; CYCLE++) {
            testStr = testStrings[CYCLE];
            scanner = new Scanner();
            try {
                for(int i = 0; i < testStr.length(); i++) {
                    scanner.Read(testStr.charAt(i));
                }
            } catch(UnexpectedLiteral ex) {
                throw new RuntimeException(ex);
            }
        
            codeRead = scanner.getCodeRead();
            assertTrue("CYCLE: "+CYCLE+"; The code read by scanner is not the same as test code", 
                codeRead.trim().equals(testStr.trim())
            );
        }
        
        /*/ ******************************************************
        CYCLE = 1;
        testStr = "g = 9.81;";
        scanner = new Scanner();
        try {
            for(int i = 0; i < testStr.length(); i++) {
                scanner.Read(testStr.charAt(i));
            }
        } catch(UnexpectedLiteral ex) {
            throw new RuntimeException(ex);
        }
        
        codeRead = scanner.getCodeRead();
        //testStrZipped = testStr.replaceAll(" ", "");
        assertTrue("CYCLE: "+CYCLE+"; The code read by scanner is not the same as test code", codeRead.replaceAll(" ", "").equals(testStr.replaceAll(" ", "")));


        // ******************************************************
        CYCLE = 2;
        testStr = "str = \"asd\";";
        scanner = new Scanner();
        try {
            for(int i = 0; i < testStr.length(); i++) {
                scanner.Read(testStr.charAt(i));
            }
        } catch(UnexpectedLiteral ex) {
            throw new RuntimeException(ex);
        }
        
        codeRead = scanner.getCodeRead();
        assertTrue("CYCLE: "+CYCLE+"; The code read by scanner is not the same as test code", codeRead.replaceAll(" ", "").equals(testStr.replaceAll(" ", "")));

        // ******************************************************
        CYCLE = 3;
        testStr = "doimiy g = 9.810001;";
        scanner = new Scanner();
        try {
            for(int i = 0; i < testStr.length(); i++) {
                scanner.Read(testStr.charAt(i));
            }
        } catch(UnexpectedLiteral ex) {
            throw new RuntimeException(ex);
        }
        
        codeRead = scanner.getCodeRead();
        assertTrue("CYCLE: "+CYCLE+"; The code read by scanner is not the same as test code", codeRead.replaceAll(" ", "").equals(testStr.replaceAll(" ", "")));


        // ******************************************************
        CYCLE = 4;
        testStr = "agar a > 0 bosh a = 0 oxir;";
        scanner = new Scanner();
        try {
            for(int i = 0; i < testStr.length(); i++) {
                scanner.Read(testStr.charAt(i));
            }
        } catch(UnexpectedLiteral ex) {
            throw new RuntimeException(ex);
        }
        
        codeRead = scanner.getCodeRead();
        assertTrue("CYCLE: "+CYCLE+"; The code read by scanner is not the same as test code", codeRead.replaceAll(" ", "").equals(testStr.replaceAll(" ", "")));

        // ******************************************************
        CYCLE = 5;
        testStr = "S = 0; har(i = 0; i < 10; i = i+1) bosh S = S + i; oxir;";
        scanner = new Scanner();
        try {
            for(int i = 0; i < testStr.length(); i++) {
                scanner.Read(testStr.charAt(i));
            }
        } catch(UnexpectedLiteral ex) {
            throw new RuntimeException(ex);
        }
        
        codeRead = scanner.getCodeRead();
        assertTrue("CYCLE: "+CYCLE+"; The code read by scanner is not the same as test code", codeRead.replaceAll(" ", "").equals(testStr.replaceAll(" ", "")));

        */
    }
}
