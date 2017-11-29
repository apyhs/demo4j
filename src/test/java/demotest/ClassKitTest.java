package demotest;

import demo.ClassKit;
import demo.ObjKit;
import demo.StreamKit;
import org.junit.Test;

import java.io.Serializable;

public class ClassKitTest implements Cloneable, Serializable {

    private String name;
    private String pass;
    private TimeKitTest tKitTt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public TimeKitTest gettKitTt() {
        return tKitTt;
    }

    public void settKitTt(TimeKitTest tKitTt) {
        this.tKitTt = tKitTt;
    }

    public void show() {
        System.out.println("show!");
    }

    @Test
    public void test1() {
        ClassKit.invoke(new ClassKitTest(), "show", new Object[]{});
        ClassKit.invoke(new ClassKitTest(), "show");
    }

    @Test
    public void simpleClone() {
        ClassKitTest jKitTest = new ClassKitTest();
        jKitTest.setName("zhangsan"); jKitTest.setPass("123");
        jKitTest.settKitTt(new TimeKitTest());
        ClassKitTest jKitTest1 = ObjKit.simpleClone(jKitTest);
        System.out.println(jKitTest);
        System.out.println(jKitTest1);
        System.out.println(jKitTest == jKitTest1);
        System.out.println(ObjKit.simpleClone(jKitTest).gettKitTt());
        System.out.println(ObjKit.simpleClone(jKitTest).gettKitTt());
        System.out.println(ObjKit.simpleClone(jKitTest).gettKitTt());
    }

    @Test
    public void deepClone() {
        ClassKitTest jKitTest = new ClassKitTest();
        jKitTest.setName("zhangsan"); jKitTest.setPass("123");
        jKitTest.settKitTt(new TimeKitTest());

        byte[] data = StreamKit.serializeObject(jKitTest);
        ClassKitTest jKitTest1 = StreamKit.deserializeObject(data);
        System.out.println(jKitTest);
        System.out.println(jKitTest1);
        System.out.println(jKitTest == jKitTest1);

        System.out.println(((ClassKitTest)StreamKit.deserializeObject(data)).gettKitTt());
        System.out.println(((ClassKitTest)StreamKit.deserializeObject(data)).gettKitTt());
        System.out.println(((ClassKitTest)StreamKit.deserializeObject(data)).gettKitTt());

        System.out.println(ObjKit.deepClone(jKitTest1).gettKitTt());
        System.out.println(ObjKit.deepClone(jKitTest1).gettKitTt());
        System.out.println(ObjKit.deepClone(jKitTest1).gettKitTt());
        System.out.println(ObjKit.deepClone(jKitTest1).gettKitTt());
    }

//    @Test
//    public void exhaustion() {
//        JKit.exhaustion(StrKit.toList(
//                StrKit.toList("a", "b", "c"),
//                StrKit.toList("1", "2", "3"),
//                StrKit.toList("d", "u", "v"),
//                StrKit.toList("p", "d", "y")
//        ), new FuncKit<Integer, String>() {
//            @Override
//            public Integer callback(String... args) {
//                System.out.println(args[0]);
//                return null;
//            }
//        });
//    }

}
