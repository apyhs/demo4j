package demo.base;

import java.util.HashMap;
import java.util.Map;

public class JMap extends HashMap<Object, Object> {

    private JMap() { }

    public static JMap create() {
        return new JMap();
    }

    public static JMap create(Map map) {
        return new JMap().set(map);
    }

    public static JMap create(Object key, Object value) {
        return new JMap().set(key, value);
    }

    public JMap set(Map map) {
        super.putAll(map);
        return this;
    }

    public JMap set(Object key, Object value) {
        super.put(key, value);
        return this;
    }

    public JMap setCode(Object code) {
        super.put(STRING_CODE, code);
        return this;
    }

    public JMap setData(Object data) {
        super.put(STRING_DATA, data);
        return this;
    }

    public JMap setMsg(Object msg) {
        super.put(STRING_MSG, msg);
        return this;
    }

    public JMap setError(Object error) {
        super.put(STRING_ERROR, error);
        return this;
    }

    private static final String STRING_CODE = "code";
    private static final String STRING_DATA = "data";
    private static final String STRING_MSG = "msg";
    private static final String STRING_ERROR = "error";

}
