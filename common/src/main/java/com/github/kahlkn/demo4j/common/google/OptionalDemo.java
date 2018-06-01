package com.github.kahlkn.demo4j.common.google;

import com.google.common.base.Optional;
import org.junit.Test;

public class OptionalDemo {

    @Test
    public void test1() {
        Optional<String> bbb = Optional.of("bbb");
//        Optional<Object> nul = Optional.of(null);
        Optional<String> aaa = Optional.fromNullable("aaa");
        Optional<Object> nll = Optional.fromNullable(null);
        System.out.println(bbb.orNull());
//        System.out.println(nul.get());
        System.out.println(aaa.orNull());
        System.out.println(nll.orNull());
    }

    @Test
    public void test2() {
        // 如果要求值不能是null，就直接抛空，防止null被后续逻辑吃掉
//        System.out.println(Optional.of(null).get());
        // 允许为null，又忽视，直接扔出null
        System.out.println(Optional.fromNullable(null).orNull());
        // 允许为null，如果null，则走默认值
        System.out.println(Optional.fromNullable(null).or("123"));
    }

    @Test
    public void test3() throws Exception {
        // 如果要求值不能是null，就直接抛空，防止null被后续逻辑吃掉
//        System.out.println(java.util.Optional.of(null).get());
        // 允许为null，又忽视，直接扔出null
        System.out.println(java.util.Optional.ofNullable(null).isPresent());
        System.out.println(java.util.Optional.ofNullable(null).orElse(null));
//        System.out.println( java.util.Optional.ofNullable(null).orElseGet( () -> { return 1000; } ).equals(1000) );
        // 允许为null，如果null，则走默认值
        System.out.println(java.util.Optional.ofNullable(null).orElse("i am null"));
    }

}
