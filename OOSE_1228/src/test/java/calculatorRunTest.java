package test.java;

import Inteprete.Calculator;
import org.junit.Test;

public class calculatorRunTest {
    Calculator calculator;
    String strExp;
    boolean result;
    boolean expected;

    public void setup(){
        calculator = new Calculator();
    }

    //1
    @Test
    public void strExpIsEmpty(){
        setup();
        strExp = "";
        result = calculator.build(strExp);
        expected = false;
        assert(expected == result);
    }

    //2
    @Test
    public void strExpIsPlus(){
        setup();
        strExp = "+";
        result = calculator.build(strExp);
        expected = false;
        assert(expected == result);
    }

    //3
    @Test
    public void strExpIsMinus(){
        setup();
        strExp = "-";
        result = calculator.build(strExp);
        expected = false;
        assert(expected == result);
    }

    //4
    @Test
    public void strExpIsMultiply(){
        setup();
        strExp = "*";
        result = calculator.build(strExp);
        expected = false;
        assert(expected == result);
    }

    //5
    @Test
    public void strExpIsDevide(){
        setup();
        strExp = "/";
        result = calculator.build(strExp);
        expected = false;
        assert(expected == result);
    }

    //6
    @Test
    public void strExpIsEqual(){
        setup();
        strExp = "=";
        result = calculator.build(strExp);
        expected = false;
        assert(expected == result);
    }

    //7
    @Test
    public void strExpIsDefault(){
        setup();
        strExp = "1";
        result = calculator.build(strExp);
        expected = true;
        assert(expected == result);
    }

    //8
    @Test
    public void twoNumberAdd(){
        setup();
        strExp = "1+1";
        result = calculator.build(strExp);
        expected = true;
        assert(expected == result);
    }

    //9
    @Test
    public void twoNumberMinus(){
        setup();
        strExp = "1-1";
        result = calculator.build(strExp);
        expected = true;
        assert(expected == result);
    }

    //10
    @Test
    public void twoNumberMultiply(){
        setup();
        strExp = "1*1";
        result = calculator.build(strExp);
        expected = true;
        assert(expected == result);
    }

    //11
    @Test
    public void twoNumberDivide(){
        setup();
        strExp = "1/1";
        result = calculator.build(strExp);
        expected = true;
        assert(expected == result);
    }

    //12
    @Test
    public void twoNumberEqual(){
        setup();
        strExp = "1=1";
        result = calculator.build(strExp);
        expected = true;
        assert(expected == result);
    }

}
