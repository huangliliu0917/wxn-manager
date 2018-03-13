package wxn.store.dal.model;


import lombok.Data;
import lombok.ToString;

/**
 * 商品列表
 */
@Data
@ToString
public class TbkItemDetailDO {
    private String itemId;//商品ID
    private String title;//商品名称
    private String pictUrl;//商品主图
    private String smallImages;//商品小图列表
    private String reservePrice;//商品一口价
    private String zkFinalPrice;//商品折扣价
    private long userType;//卖家类型，0表示集市，1表示商城
    private String provcity;//宝贝所在地
    private String itemUrl;//商品地址
    private String nick;//卖家昵称
    private String sellerId;//卖家Id
    private long volume;//30天销量
    private String catLeftName;//叶子类目名称
    private String catName;//一级类目名称
    private String sclick;//淘口令长链接
    private String codeDate;//口令生成时间
    private String taoToken;//淘口令
    private String shortLinkUrl;//淘口令短链接
    private String isCode;//1:有口令链接 2:无口令链接
}
