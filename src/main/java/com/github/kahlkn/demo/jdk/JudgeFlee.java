package com.github.kahlkn.demo.jdk;

import org.junit.Test;

/**
 * 有 ABCD 四个点形成电子围栏，既在地图上有个四边形（假设是）。
 * 有个 E 表示汽车，在调用接口
 */
public class JudgeFlee {

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
        Point e = new Point(10d, 11d);
        System.out.println("isInside: " + isInside(a, b, c, d, e));
    }

    public boolean isInside(Point a, Point b, Point c, Point d, Point e) {
        // 数据的非空效验，不做了
        Double abe = calcTriangleArea(a, b, e);
        Double bce = calcTriangleArea(b, c, e);
        Double cde = calcTriangleArea(c, d, e);
        Double dae = calcTriangleArea(d, a, e);
        Double abcd = calcTetragon(a, b, c, d);
        return (abe + bce + cde + dae) <= abcd;
    }

    private Double calcTetragon(Point a, Point b, Point c, Point d) {
        return calcTriangleArea(b, d, a) + calcTriangleArea(b, d, c);
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
