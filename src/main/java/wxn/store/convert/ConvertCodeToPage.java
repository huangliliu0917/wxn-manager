package wxn.store.convert;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * 通过操控浏览器页面来实现口令转换
 */
public class ConvertCodeToPage {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\wangkecheng\\Desktop\\A\\c\\chromedriver.exe");
        //通过绝对路径来指定浏览器驱动地址
        System.setProperty("webdriver.chrome.driver",file.getAbsolutePath());

        ChromeOptions option=new ChromeOptions();
        option.addArguments("disable-infobars");//关闭浏览器检测提示语
        option.addArguments("user-data-dir=C:/Users/wangkecheng/AppData/Local/Google/Chrome/User Data");

        WebDriver driver = new ChromeDriver(option);
        //访问网站
        driver.get("http://pub.alimama.com/myunion.htm");
        //窗口最大化
        driver.manage().window().maximize();

        //扫码登陆成功后，往下运行
        Scanner scanner = new Scanner(System.in);
        String text = scanner.next();
        System.out.println("登陆成功");

        try {
            //关闭联盟弹框
            WebElement divElement = driver.findElement(By.xpath("//*[@id=\"brix_78\"]/div[3]/div/span[1]"));
            divElement.click();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("未获取到联盟弹框元素");
        }
        WebElement ulElement = null;

        try {
            //进入联盟产品
            ulElement = driver.findElement(By.xpath("//*[@id=\"sidebar\"]/ul/li[2]/a"));
            ulElement.click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("进入联盟产品失败");
            e.printStackTrace();
        }

        try {
            //进入链接转换页面
            WebElement zlElement = driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div/ul[2]/li[1]/ul/li[4]/a"));
            zlElement.click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("进入链接转换页面失败");
            e.printStackTrace();
        }

        try {
            //获取文本编辑框
            WebElement textElement = driver.findElement(By.xpath("//*[@id=\"J_originUrl\"]"));
            textElement.click();
            textElement.sendKeys("http://item.taobao.com/item.htm?id=564046892377");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("获取文本编辑框失败");
            e.printStackTrace();
        }

        try {
            //开始转链
            //WebElement zhElement = driver.findElement(By.xpath("//*[@id=\"brix_680\"]/div[1]/div[1]/div/button"));
            WebElement zhElement =  driver.findElement(By.className("promo")).findElement(By.tagName("button"));
            zhElement.click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            //配置推广位
            //WebElement tgElement = driver.findElement(By.xpath("//*[@id=\"brix_2014\"]/div/button"));
            WebElement tgElement =  driver.findElement(By.xpath("//*[@id=\"magix_vf_zone\"]/following-sibling::div[1]")).findElement(By.tagName("button"));

            tgElement.click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            //获取转换后的链接
            WebElement generateCodeElement = driver.findElement(By.xpath("//*[@id=\"clip1\"]"));
            String kouling = generateCodeElement.getText();
            System.out.println(kouling);
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Set<Cookie> cookies = driver.manage().getCookies();
        Iterator<Cookie> it = cookies.iterator();
        while (it.hasNext()) {
            Cookie str = it.next();
            System.out.println("name:"+str.getName()+" value:"+str.getValue());
        }

        driver.quit();
    }
}
