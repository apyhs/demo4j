package demo.function;

import artoria.util.Assert;
import org.junit.Test;

/**
 * 有 ABCD 四个点形成电子围栏（按序），既在地图上有个四边形。
 * 有个 E 表示汽车，知道这些点的坐标，判断汽车是否在点子围栏内部。
 */
public class ElectronicFence {

    private static class Point {
        private Double x;
        private Double y;

        public Point(Double x, Double y) {
            this.x = x;
            this.y = y;
        }

        public Double getX() {
            return x;
        }

        public void setX(Double x) {
            this.x = x;
        }

        public Double getY() {
            return y;
        }

        public void setY(Double y) {
            this.y = y;
        }
    }

    @Test
    public void test1() {
        Point a = new Point(0d, 10d);
        Point b = new Point(10d, 10d);
        Point c = new Point(10d, 0d);
        Point d = new Point(0d, 0d);
        Point e = new Point(10d, 10.001d);
        System.out.println("isInside: " + isInside(e, a, b, c, d));
    }

    public boolean isInside(Point e, Point... points) {
        // 数据的非空效验，不做了
        Double sum = 0d;
        for (int i = 0, len = points.length; i < len; i++) {
            Point point1 = points[i];
            Point point2 = points[i == len - 1 ? 0 : i + 1];
            sum += calcTriangleArea(point1, point2, e);
        }
        Double abcd = calcPolygonArea(points);
        return sum <= abcd;
    }

    private Double calcPolygonArea(Point... points) {
        Assert.state(points != null && points.length >= 3, "Points must equal or greater than 3. ");
        Double sum = 0d;
        Point point = points[0];
        for (int i = 0; i < points.length - 2; i++) {
            Point point1 = points[i + 1];
            Point point2 = points[i + 2];
            sum += calcTriangleArea(point1, point2, point);
        }
        return sum;
    }

    private Double calcTriangleArea(Point pa, Point pb, Point pe) {
        double a = calcDistance(pa, pb);
        double b = calcDistance(pa, pe);
        double c = calcDistance(pe, pb);
        double tmp;
        if (b > a) {
            tmp = b;
            b = a;
            a = tmp;
        }
        if (c > a) {
            tmp = c;
            c = a;
            a = tmp;
        }
        if (c > b) {
            tmp = c;
            c = b;
            b = tmp;
        }
        double high = Math.sqrt(
                Math.pow(b, 2) - Math.pow(
                        (Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)) / (2 * a)
                        , 2
                )
        );
        return a * high / 2;
    }

    private Double calcDistance(Point a, Point b) {
        return Math.sqrt(
                Math.pow(Math.abs(a.getX() - b.getX()), 2) +
                        Math.pow(Math.abs(a.getY() - b.getY()), 2)
        );
    }

}
