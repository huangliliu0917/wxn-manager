package wxn.store.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.http.client.config.RequestConfig.DEFAULT;


/**
 * apache httpclient 工具类
 * <p>
 * 1.GET 请求
 * 2.POST 请求
 * 3.参数map
 * </p>
 */
public class HttpClientUtils {


    private HttpClientUtils() {
    }

    private static  final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 通过get方式发送http请求
     * @param url
     * @return
     */
    public static HttpClientResponse get(String url) {
        //响应信息实体类
        HttpClientResponse response = new HttpClientResponse();
        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpGet httpGet = new HttpGet(url);
        try {
            //执行get请求
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            //获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            //响应状态
            response.setStatus(httpResponse.getStatusLine().toString());
            //判断响应实体是否为空
            if (entity != null) {
                if(entity.getContentEncoding()!=null){
                    response.setContentEncoding(entity.getContentEncoding().toString());
                }
                response.setResponseContent(EntityUtils.toString(entity,DEFAULT_CHARSET));
            }
        } catch (IOException e) {
        } finally {
            try {
                //关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
            }
        }
        return response;
    }

    /**
     * 通过POST方式发送http请求
     * @param url
     */
    public static HttpClientResponse post(String url, Map<String,String> paramMap){
        //响应信息实体类
        HttpClientResponse response = new HttpClientResponse();
        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(DEFAULT);
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for(Map.Entry<String,String> entry:paramMap.entrySet()){
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(formparams, DEFAULT_CHARSET);
            httpPost.setEntity(entity);

            HttpResponse httpResponse;
            //post请求
            httpResponse = closeableHttpClient.execute(httpPost);

            //getEntity()
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                if(httpEntity.getContentEncoding()!=null)
                    response.setContentEncoding(entity.getContentEncoding().toString());
                response.setResponseContent(EntityUtils.toString(httpEntity, DEFAULT_CHARSET));
            }
            //释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
        }
        return response;
    }

}
