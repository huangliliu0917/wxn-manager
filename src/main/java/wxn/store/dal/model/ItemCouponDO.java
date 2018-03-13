package wxn.store.dal.model;

import lombok.Data;

import java.util.Date;

@Data
public class ItemCouponDO {
    private String itemId;//商品ID
    private String title;//商品名称
    private String pictUrl;//商品主图
    private String itemPictUrl;//商品详情页链接地址
    private String cat;//商品一级类目
    private String tbkUrl;//淘宝客链接
    private double itemPrice;//商品价格(单位：元)
    private int volume;//商品月销量
    private String costToIncome;//收入比率(%)
    private double rate;//佣金
    private String sellerWw;//卖家旺旺
    private String sellerId;//卖家id
    private String sellerName;//店铺名称
    private String platformType;//平台类型
    private String couponId;//优惠券id
    private int totalCoupon;//优惠券总量
    private int couponSurplus;//优惠券剩余量
    private int couponPrict;//优惠券面额
    private Date startTime;//优惠券开始时间
    private Date endTime;//优惠券结束时间
    private String couponUrl;//优惠券链接
    private String itemSpreadUrl;//商品优惠券推广链接
    private String is_code;//1:有口令链接 2:无口令链接
    private String isShow;//1:展示 2:不展示
}
