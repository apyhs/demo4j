package demo._question;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据提示写出 Arguments 类
 */
public class GetRectangleArea {

//    public static void main(String[] args) {
//        Arguments arguments = new Arguments();
//        arguments.parse(args);
//        double width = arguments.getDouble("w");
//        double height = arguments.getDouble("h");
//        System.out.println("矩形的面积为：" + (width * height));
//    }

//    public static void main(String[] args) {
//        Arguments arguments = new Arguments();
//        arguments.addArg("矩形宽度", "w");
//        arguments.addArg("矩形高度", "h");
//        arguments.parse(args);
//        double width = arguments.getDouble("w");
//        double height = arguments.getDouble("h");
//        System.out.println("矩形的面积为：" + (width * height));
//    }

//    $ java GetRectangleArea
//    参数：
//    -w 矩形宽度
//    -h 矩形高度

    // 一个参数可以有简称（如 -w），也可以有全称（如 --width）

    public static void main(String[] args) {
        Arguments arguments = new Arguments();
        arguments.addArg("矩形宽度", "w", "width");
        arguments.addArg("矩形高度", "h", "height");
        arguments.parse(args);
        double width = arguments.getDouble("width");
        double height = arguments.getDouble("h");
        System.out.println("矩形的面积为：" + (width * height));
    }

    public static class Arguments{
        private Map<String, String> simpleCmd2Explain = new HashMap<>();
        private Map<String, String> totalCmd2Simple = new HashMap<>();
        private Map<String, String> simpleCmd2Total = new HashMap<>();
        private Map<String, String> simpleCmd2Value = new HashMap<>();

        public void addArg(String explain, String simpleCmd) {
            // 可以对 simpleCmd 做个判断，是否带 -
            simpleCmd2Explain.put("-" + simpleCmd, explain);
        }

        public void addArg(String explain, String simpleCmd, String totalCmd) {
            // 可以对 totalCmd 做个判断，是否带 --
            simpleCmd = "-" + simpleCmd;
            totalCmd = "--" + totalCmd;
            simpleCmd2Explain.put(simpleCmd, explain);
            totalCmd2Simple.put(totalCmd, simpleCmd);
            simpleCmd2Total.put(simpleCmd, totalCmd);
        }

        public void parse(String[] args) {
            boolean hasTotal = false;
            if (simpleCmd2Explain.size() == totalCmd2Simple.size()) hasTotal = true;
            // 对于 args 的 长度 进行 判断，是否 是 偶数 个，因为 是 一命令 一数据 结构
            if (args != null && args.length != 0) {
                for (int i = 0; i < args.length / 2; i++) {
                    // 严格一点 需要 对 每个 arg 都要 进行效验
                    String explain = simpleCmd2Explain.get(args[i * 2]);
                    if (explain != null && explain.trim().length() != 0) {
                        simpleCmd2Value.put(args[i * 2].substring(1), args[i * 2 + 1]);
                        if (hasTotal) {
                            String total = simpleCmd2Total.get(args[i * 2]);
                            if (total == null || total.trim().length() == 0) continue;
                            simpleCmd2Value.put(total.substring(2), args[i * 2 + 1]);
                        }
                    } else {
                        // 如果这个命令 不存在，那么 它 后面 那个参数 也就 视为无效
                        // 是否 输入 错误命令 就 不 执行 其他 的 了
                    }

                }
            } else {
                System.out.println("$ java GetRectangleArea");
                System.out.println("参数：");
                if (simpleCmd2Explain.size() != 0) {
                    for (Map.Entry<String, String> entry : simpleCmd2Explain.entrySet()) {
                        System.out.println("    " + entry.getKey() + " " + entry.getValue());
                    }
                }
                // 说明未带 任何 参数，退出
                System.exit(1);
            }
        }

        public double getDouble(String cmd) {
            // 可以 对 cmd 做一下 效验
            String s = simpleCmd2Value.get(cmd);
            return s == null || s.trim().length() == 0
                    ? 0d : Double.parseDouble(s);
        }

    }

}
