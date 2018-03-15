package com.github.kahlkn.demo.mockito;

import com.apyhs.artoria.util.RandomUtils;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 *
 */
public class QuickStart {

    @Test
    public void test1() {
        // 创建 mock 对象
        List list = mock(List.class);
        // 填充数据
        when(list.get(0)).thenReturn("hello");
        // 断言预期
        assertEquals("will \"hello\"", "hello", list.get(0));
        // 打印数据
        System.out.println(list);
        System.out.println(list.get(0));
    }

    @Test
    public void test2() {
        // mock request
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("foo")).thenReturn("boo");
        System.out.println(request);
        System.out.println(request.getParameter("foo"));

        // mock Iterator
        Iterator i = mock(Iterator.class);
        System.out.println(i.getClass());
        // 第一种方式
        when(i.next()).thenReturn("way 1 - 1").thenReturn("way 1 -2");
        System.out.println(i.next());
        System.out.println(i.next());
        // 第二种方式
        when(i.next()).thenReturn("way 2 - 1", "way 2 - 2");
        System.out.println(i.next());
        System.out.println(i.next());
        // 第三种方式，都是等价的
        when(i.next()).thenReturn("way 3 - 1");
        when(i.next()).thenReturn("way 3 - 2");
        System.out.println(i.next());
        System.out.println(i.next());
    }

    @Test
    public void test3() {
        // 没有返回值的方法
        // 可能是 不能 用 test 吧
        QuickStart qs = mock(QuickStart.class);
        doNothing().when(qs).notify();
        qs.test1();
        // 或
        when(qs).notify();
        qs.test2();
    }

    @Test
    public void test4() {
        // mock Iterator
        Iterator i = mock(Iterator.class);
        System.out.println(i.getClass());
        when(i.next()).thenReturn("way 1 - 1").thenReturn("way 1 -2");
        System.out.println(i.next());

        when(i.next()).thenThrow(new RuntimeException());
        System.out.println(i.next());
        // void 方法的
        // doThrow(new RuntimeException()).when(i).remove();
        // 迭代风格
        // 第一次调用 remove 方法什么都不做，第二次调用抛出 RuntimeException 异常
        // doNothing().doThrow(new RuntimeException()).when(i).remove();
    }

    @Test
    public void test5() {
        List list = mock(List.class);
        when(list.get(anyInt())).thenReturn("hello");
        // 此时打印是 hello
        System.out.println(list.get(RandomUtils.nextInt(10000)));
        System.out.println(list.get(RandomUtils.nextInt(10000)));
        System.out.println(list.get(RandomUtils.nextInt(10000)));
        System.out.println(list.get(RandomUtils.nextInt(10000)));
    }

    @Test
    public void test6() {
        final Map<String, Object> hash = new HashMap<>();
        Answer<Object> aswser = new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                System.out.println(Arrays.toString(args));
                return hash.get(args[0].toString());
            }
        };
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("isRawOutput")).thenReturn(true);
        when(request.getAttribute("errMsg")).thenAnswer(aswser);
        when(request.getAttribute("msg")).thenAnswer(aswser);
        System.out.println(request.getAttribute("isRawOutput"));
        System.out.println(request.getAttribute("errMsg"));
        System.out.println(request.getAttribute("msg"));

    }

    @Test
    public void test7() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        final Map<String, Object> hash = new HashMap<>();
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                // Object mock = invocation.getMock();
                System.out.println(args[1]);
                hash.put(args[0].toString(), args[1]);
                return "called with arguments: " + args;
            }
        }).when(request).setAttribute(anyString(), anyString());
        request.setAttribute("hello", "world");
        request.setAttribute("hello1", "world1");
        System.out.println(hash);
    }

}
