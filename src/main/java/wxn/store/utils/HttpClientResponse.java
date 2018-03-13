package wxn.store.utils;

/**
 * HttpClient返回信息的封装
 * <p>
 * 方法描述列表
 * </p>
 * User: wangkecheng Date: 2016/12/20
 */
public class HttpClientResponse {

    /**
     * 响应状态
     */
    private String status;

    /**
     * 编码格式
     */
    private String contentEncoding;

    /**
     * 响应体
     */
    private String responseContent;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }
}
