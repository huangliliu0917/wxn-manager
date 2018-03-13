package wxn.store;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import wxn.store.dal.mapper.TbkItemDetailMapper;
import wxn.store.dal.mapper.TbkItemMapper;
import wxn.store.dal.model.TbkItemDetailDO;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Slf4j
public class TkbCodeTest extends BaseSpringTest {

    String url = "http://pub.alimama.com/urltrans/urltrans.json?";
    String siteid = "42970707";
    String adzoneid = "272988181";
    String t = String.valueOf(System.currentTimeMillis());

    @Autowired
    private TbkItemMapper tbkItemMapper;
    @Autowired
    private TbkItemDetailMapper tbkItemDetailMapper;

    /**
     * 获取淘口令
     */
    @Test
    public void getTbkCode() throws InterruptedException {
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

     /*   //扫码登陆成功后，往下运行
        Scanner scanner = new Scanner(System.in);
        String text = scanner.next();
        System.out.println("登陆成功");*/
     Thread.sleep(10*1000);

        String token = null;
        Set<Cookie> cookies = driver.manage().getCookies();
        Iterator<Cookie> it = cookies.iterator();
        while (it.hasNext()) {
            Cookie str = it.next();
            if ("_tb_token_".equals(str.getName())) {
                token = str.getValue();
            }
        }

        if (null != token) {
            System.out.println(token);
            generateCode(token, driver);
        }

        driver.quit();
    }

    private void generateCode(String token, WebDriver driver) {
        //获取商品详情列表
        List<TbkItemDetailDO> tbkItemDetails = tbkItemDetailMapper.getTbkItemDetailList();
        for (TbkItemDetailDO tbkItemDetailDO : tbkItemDetails) {
            try {
                Thread.sleep(30*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String reqUrl = url + "siteid=" + siteid + "&adzoneid=" + adzoneid + "&promotionURL=" + tbkItemDetailDO.getItemUrl() + "&t=" + t + "&pvid=50_101.244.222.124_8002_1520667151192&_tb_token_=" + token + "&_input_charset=utf-8";
            driver.get(reqUrl);
            //获取url
            String currentUrl = driver.getCurrentUrl();
            log.info("口令请求链接,currentUrl{}", currentUrl);
            //获取页面的全部源代码
            String pageSource = driver.getPageSource();
            parseXML(pageSource, tbkItemDetailDO.getItemId());
        }
    }

    public void parseXML(String pageSource, String itemId) {
        try {
            Document doc = DocumentHelper.parseText(pageSource); // 将字符串转为XML

            Element rootElt = doc.getRootElement(); // 获取根节点
            System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
            Iterator iterss = rootElt.elementIterator("body"); ///获取根节点下的子节点body
            // 遍历body节点
            while (iterss.hasNext()) {
                Element recordEless = (Element) iterss.next();
                String result = recordEless.elementTextTrim("pre"); // 拿到body节点下的子节点result值
                JSONObject jsonObject = JSONObject.fromObject(result);
                String data = jsonObject.get("data").toString();
                JSONObject jsonObject1 = JSONObject.fromObject(data);

                //修改商品详情信息，将口令写入表中
                TbkItemDetailDO tbkItemDetailDO = new TbkItemDetailDO();
                tbkItemDetailDO.setTaoToken((String) jsonObject1.get("taoToken"));
                tbkItemDetailDO.setSclick((String) jsonObject1.get("sclick"));
                tbkItemDetailDO.setShortLinkUrl((String) jsonObject1.get("shortLinkUrl"));
                tbkItemDetailDO.setItemId(itemId);

                tbkItemDetailMapper.updateTbkItemByItemId(tbkItemDetailDO);
                tbkItemMapper.updateTbkItemByItemId(itemId);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
