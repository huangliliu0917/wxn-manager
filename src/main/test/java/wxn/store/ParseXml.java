package wxn.store;


import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;

public class ParseXml {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String xml = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head></head><body><pre style=\"word-wrap: break-word; white-space: pre-wrap;\">{\"data\":{\"sclick\":\"https://s.click.taobao.com/t?e=m%3D2%26s%3DCKGAGaPXEmscQipKwQzePOeEDrYVVa64Qih%2F7PxfOKS5VBFTL4hn2ehOOjv2ZZYvc4zWPc6e822s%2BorpGHkbq5im20%2FJIkbFTUllBSXAXWtAKZTXKEm9umIZ5UlvKIFqUUjCPjr6QWTy6vo%2B3b3GPgyW7mQwqx7e\",\"taoToken\":\"￥dfkT0LyNWD6￥\",\"qrCodeUrl\":\"//gqrcode.alicdn.com/img?type=hv&amp;text=https%3A%2F%2Fs.click.taobao.com%2FhrlbFTw%3Faf%3D3&amp;h=300&amp;w=300\",\"shortLinkUrl\":\"https://s.click.taobao.com/hrlbFTw\"},\"info\":{\"message\":null,\"ok\":true},\"ok\":true,\"invalidKey\":null}</pre></body></html>";
        Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML

        Element rootElt = doc.getRootElement(); // 获取根节点
        System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
        Iterator iterss = rootElt.elementIterator("body"); ///获取根节点下的子节点body
        // 遍历body节点
        while (iterss.hasNext()) {
            Element recordEless = (Element) iterss.next();
            String result = recordEless.elementTextTrim("pre"); // 拿到body节点下的子节点result值
            System.out.println(result);

            JSONObject jsonObject = JSONObject.fromObject(result);
            String data = jsonObject.get("data").toString();
            JSONObject jsonObject1 = JSONObject.fromObject(data);

            System.out.println(jsonObject1.get("taoToken"));
            System.out.println(jsonObject1.get("sclick"));
            System.out.println(jsonObject1.get("shortLinkUrl"));

        }
    }
}
