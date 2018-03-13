package wxn.store.convert;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import wxn.store.utils.HttpClientResponse;
import wxn.store.utils.HttpClientUtils;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * 通过获取cookie实现口令转换
 */
public class ConvertCodeToCookie {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\wangkecheng\\Desktop\\A\\c\\chromedriver.exe");
        //通过绝对路径来指定浏览器驱动地址
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        ChromeOptions option = new ChromeOptions();
        option.addArguments("disable-infobars");
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

        String token = null;
        Set<Cookie> cookies = driver.manage().getCookies();
        Iterator<Cookie> it = cookies.iterator();
        while (it.hasNext()) {
            Cookie str = it.next();
            System.out.println("key:" + str.getName() + " value:" + str.getValue());
            if ("_tb_token_".equals(str.getName())) {
                token = str.getValue();
            }
        }

        if (null != token) {
            System.out.println(token);
            generateCode(token, driver);
        }
        Thread.sleep(12 * 60 * 60 * 1000);
        Scanner scanner1 = new Scanner(System.in);
        driver.quit();
    }

    private static void generateCode(String token, WebDriver driver) {
        String url = "http://pub.alimama.com/urltrans/urltrans.json?";
        String siteid = "42970707";
        String adzoneid = "272988181";

        String itemUrl = "http://item.taobao.com/item.htm?id=561921276560";
        String t = String.valueOf(System.currentTimeMillis());

        String reqUrl = url + "siteid=" + siteid + "&adzoneid=" + adzoneid + "&promotionURL=" + itemUrl + "&t=" + t + "&pvid=50_101.244.222.124_8002_1520667151192&_tb_token_=" + token + "&_input_charset=utf-8";
        System.out.println(reqUrl);

        driver.get(reqUrl);
        //获取url
        String currentUrl = driver.getCurrentUrl();
        //获取页面的全部源代码
        String pageSource = driver.getPageSource();

        System.out.println("CurrentURL:" + currentUrl);
        System.out.println("PageSource:" + pageSource);

    }
}
