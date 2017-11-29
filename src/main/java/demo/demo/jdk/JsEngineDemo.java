package demo.demo.jdk;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class JsEngineDemo {

    @Test
    public void test1() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        engine.eval("var a=3; var b=4; print (a+b);");
        // 不能调用浏览器中定义的js函数 // 错误，会抛出alert引用不存在的异常
        // engine.eval("alert(\"js alert\");");
    }

}
