package uz.app.UzScript.lang.scanner.stateMachines;

import static org.junit.Assert.assertEquals;

import java.text.NumberFormat;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import uz.app.UzScript.lang.scanner.stateMachines.NumberReader;

public class NumberReaderTest {
    private NumberReader numberReader;
    @Before
    public void init() {
        numberReader = new NumberReader();
    }

    @Test
    public void testRead() throws ParseException {
        String numStr = "123.55";
        Number num = NumberFormat.getInstance().parse(numStr);
        
        for (char symbol : (numStr + ";").toCharArray()) {
            if(this.numberReader.Read(symbol)) {
                break;
            }
        }
        Number result = this.numberReader.getNumber();
        assertEquals(num, result);
        //********************************
        
        numStr = "11";
        num = NumberFormat.getInstance().parse(numStr);
        this.numberReader = new NumberReader();
        for (char symbol : (numStr + ";").toCharArray()) {
            if(this.numberReader.Read(symbol)) {
                break;
            }
        }
        result = this.numberReader.getNumber();
        assertEquals(num, result);
        //********************************
        
        numStr = "11a1";
        num = NumberFormat.getInstance().parse(numStr);
        this.numberReader = new NumberReader();
        for (char symbol : (numStr + ";").toCharArray()) {
            if(this.numberReader.Read(symbol)) {
                break;
            }
        }
        result = this.numberReader.getNumber();
        assertEquals(num, result);
    }

    @Test(expected = RuntimeException.class)
    public void testGetNumber() {
        this.numberReader.getNumber();
    }
}
