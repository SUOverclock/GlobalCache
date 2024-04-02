//import org.junit.*;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    static ByteArrayOutputStream ba;
    static PrintStream pr;
    static Fraction noCache;
    static Fractionable wrapCache;
    static Fractionable proxyCache;

    @BeforeAll
    public static void setUp() {
        ba =new ByteArrayOutputStream();
        pr= new PrintStream(ba);
        System.setOut(pr);
        noCache = new Fraction(3,4);
        wrapCache = new Utils(noCache);
        proxyCache = Utils.get_cache(noCache);

    }

    @Test
    void get_cache() throws NoSuchMethodException {
        double output;  // first test without cache
        double output2;
        System.out.println("invoke double value");
        String expected = ba.toString();
        ba.reset();
        output= noCache.doubleValue();
        String test = ba.toString();
        ba.reset();
        Assertions.assertEquals(expected, test);
        output2= noCache.doubleValue();
        test = ba.toString();
        ba.reset();
        Assertions.assertEquals(expected, test);
        Assertions.assertEquals(output, output2);
        check_work(wrapCache); // test for Wrapper
        check_work(proxyCache); // test for Proxy
    }

        void check_work(Fractionable obj) throws NoSuchMethodException {
        double output;
        double output2;
        System.out.println("invoke double value");
        String expected = ba.toString();
        ba.reset();

        output = obj.doubleValue();
        String test = ba.toString();
        ba.reset();
        Assertions.assertEquals(expected, test);
        output2 = obj.doubleValue();
        test = ba.toString();
        ba.reset();
        Assertions.assertEquals("", test);
        Assertions.assertEquals(output, output2);
        obj.setNum(20);
        output = obj.doubleValue();
        test = ba.toString();
        ba.reset();
        Assertions.assertEquals(expected, test);
        output2 = obj.doubleValue();
        test = ba.toString();
        ba.reset();
        Assertions.assertEquals("", test);
        Assertions.assertEquals(output, output2);
    }
}