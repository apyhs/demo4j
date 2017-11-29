package demotest.base;

import org.junit.Test;
import demo.base.Exhaustion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExhaustionTest {

    @Test
    public void test1() {
        List<String> d1 = new ArrayList<>();
        Collections.addAll(d1, "1", "2", "3");
        List<String> d2 = new ArrayList<>();
        Collections.addAll(d2, "4", "5", "6");
        List<String> d3 = new ArrayList<>();
        Collections.addAll(d3, "7", "8", "9");
        List<String> d4 = new ArrayList<>();
        Collections.addAll(d4, "a", "b", "c");
        List<List<String>> dn = new ArrayList<>();
        Collections.addAll(dn, d1, d2, d3, d4);
        new Exhaustion(dn) {
            @Override
            public void handle(String nowString) {
                System.out.println(nowString);
            }
        }.run();

    }

}
