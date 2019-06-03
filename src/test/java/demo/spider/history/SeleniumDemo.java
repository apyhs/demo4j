package demo.spider.history;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.os.WindowsUtils;

import java.io.File;

public class SeleniumDemo {

    private WebDriver driver;

    @Before
    public void init() {
        // 设置驱动路径
        System.setProperty("webdriver.chrome.driver", "D:\\Kit\\WebDriver\\chromedriver.exe");
        driver = new ChromeDriver();
//        System.setProperty("phantomjs.binary.path", "D:\\Kit\\WebDriver\\phantomjs.exe");
//        driver = new PhantomJSDriver();
    }

    @After
    public void after() {
        // 关闭
        // driver.close();
        // 关闭 并且 结束进程
        driver.quit();
    }

    @Test // 浏览器最大化 前进，后退， 刷新
    public void test1() throws Exception {
        driver.get("http://uux.me");

        // 等待浏览器引擎加载完成
        Thread.sleep(3000);

        // 浏览器最大化 - 无效
        // driver.manage().window().maximize();
        // driver.manage().window().setSize(new Dimension(0, 0));

        driver.navigate().to("http://www.baidu.com");
        Thread.sleep(3000);

        // 刷新浏览器
        driver.navigate().refresh();
        Thread.sleep(1000);

        // 浏览器后退
        driver.navigate().back();
        Thread.sleep(1000);

        // 浏览器前进
        driver.navigate().forward();
        Thread.sleep(1000);
    }

    @Test // 截图操作
    public void test2() throws Exception {
        // 无效？
        driver.get("http://www.baidu.com");
        // 等待浏览器引擎加载完成
        Thread.sleep(3000);

        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File("C:\\Users\\Kahle\\Desktop\\1.png"));
    }

    @Test // 模拟鼠标操作
    public void test3() throws Exception {
        driver.get("http://www.baidu.com");
        // 等待浏览器引擎加载完成
        Thread.sleep(3000);

        // 无效？
        Actions action = new Actions(driver);
        action.contextClick(driver.findElement(By.name("tj_trnews"))).perform();
    }

    @Test // 杀掉Windows浏览器进程
    public void test4() throws Exception {
        // kill firefox
        WindowsUtils.killByName("firefox.exe");
        // kill IE
        WindowsUtils.killByName("iexplore.exe");
        // kill chrome
        WindowsUtils.killByName("chrome.exe");
    }

}
