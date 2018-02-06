package com.apyhs.demo.spider.history;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.Test;

public class HtmlUnitDemo {

    @Test
    public void test1() throws Exception {
        // 得到浏览器对象，直接New一个就能得到，现在就好比说你得到了一个浏览器了
        WebClient webclient = new WebClient();

        // 这里是配置一下不加载css和javaScript,配置起来很简单，是不是
        webclient.getOptions().setCssEnabled(false);
        webclient.getOptions().setJavaScriptEnabled(false);

        // 做的第一件事，去拿到这个网页，只需要调用getPage这个方法即可
        HtmlPage htmlpage = webclient.getPage("http://news.baidu.com/advanced_news.html");

        // 根据名字得到一个表单，查看上面这个网页的源代码可以发现表单的名字叫“f”
        final HtmlForm form = htmlpage.getFormByName("f");
        // 同样道理，获取”百度一下“这个按钮
        final HtmlSubmitInput button = form.getInputByValue("百度一下");
        // 得到搜索框
        final HtmlTextInput textField = form.getInputByName("q1");
        // 设置一下在搜索框内填入”周星驰“
        textField.setValueAttribute("周星驰");
        // 输入好了，我们点一下这个按钮
        final HtmlPage nextPage = button.click();
        // 我把结果转成String
        String result = nextPage.asXml();

        System.out.println(result);
    }

    @Test
    public void test2() throws Exception {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        //参数设置
        // 1 启动JS
        webClient.getOptions().setJavaScriptEnabled(true);
        // 2 禁用Css，可避免自动二次请求CSS进行渲染
        webClient.getOptions().setCssEnabled(false);
        //3 启动客户端重定向
        webClient.getOptions().setRedirectEnabled(true);
        // 4 运行错误时，是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // 5 设置超时
        webClient.getOptions().setTimeout(50000);
        //6 设置忽略证书
        webClient.getOptions().setUseInsecureSSL(true);
        //7 设置Ajax
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        //8 设置cookie
        webClient.getCookieManager().setCookiesEnabled(true);

        // 拿到这个网页
        String url = "https://";
        HtmlPage page = webClient.getPage(url);

        // 等待后台js执行
        webClient.waitForBackgroundJavaScript(10000);

        // 填入用户名和密码
        HtmlInput username = (HtmlInput) page.getElementById("username");
        username.type("admin");
        HtmlInput password = (HtmlInput) page.getElementById("password");
        password.type("admin");

        // 提交
        HtmlButton submit = (HtmlButton) page.getByXPath("/html/body/div[2]/div/div/div/div[2]/button").get(0);
        HtmlPage nextPage = submit.click();
        System.out.println(nextPage.asXml());
    }

    @Test
    public void test3() throws Exception {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        //参数设置
        // 1 启动JS
        webClient.getOptions().setJavaScriptEnabled(true);
        // 2 禁用Css，可避免自动二次请求CSS进行渲染
        webClient.getOptions().setCssEnabled(false);
        //3 启动客户端重定向
        webClient.getOptions().setRedirectEnabled(true);
        // 4 运行错误时，是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // 5 设置超时
        webClient.getOptions().setTimeout(50000);
        //6 设置忽略证书
        webClient.getOptions().setUseInsecureSSL(true);
        //7 设置Ajax
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        //8 设置cookie
        webClient.getCookieManager().setCookiesEnabled(true);

        // 拿到这个网页
        String url = "https://www.zhihu.com/question/46949108";
        HtmlPage page = webClient.getPage(url); // 炸了？

        // 等待后台js执行
        webClient.waitForBackgroundJavaScript(50000);

        HtmlButton more = (HtmlButton) page.getByXPath("//*[@id=\"QuestionAnswers-answers\"]/div[2]/button").get(0);
        page = more.click();

        DomNodeList<DomElement> imgs = page.getElementsByTagName("img");
        for (DomElement img : imgs) {
            System.out.println(img.getAttribute("src"));
        }

    }

}
