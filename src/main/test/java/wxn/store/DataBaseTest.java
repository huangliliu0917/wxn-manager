package wxn.store;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wxn.store.dal.mapper.ItemCouponMapper;
import wxn.store.dal.mapper.TbkItemDetailMapper;
import wxn.store.dal.mapper.TbkItemMapper;
import wxn.store.dal.model.ItemCouponDO;
import wxn.store.dal.model.TbkItemDetailDO;

import java.util.List;

@Slf4j
public class DataBaseTest extends BaseSpringTest {

    @Autowired
    private TbkItemMapper tbkItemMapper;
    @Autowired
    private TbkItemDetailMapper tbkItemDetailMapper;

    @Test
    public void test() {
        //获取商品详情列表
        List<TbkItemDetailDO> tbkItemDetails = tbkItemDetailMapper.getTbkItemDetailList();
        System.out.println(tbkItemDetails.size());
    }

    @Test
    public void updateItemState(){
        String itemId = "561921276560";
        tbkItemMapper.updateTbkItemByItemId(itemId);
    }

    @Test
    public void updateItemDetail(){
        TbkItemDetailDO tbkItemDetailDO = new TbkItemDetailDO();
        tbkItemDetailDO.setSclick("https://s.click.taobao.com/t?e=m%3D2%26s%3DIvIlsclgdjEcQipKwQzePOeEDrYVVa64Qih%2F7PxfOKS5VBFTL4hn2dxgF%2F35UEMRDOz%2BQ0Bmwbys%2BorpGHkbq5im20%2FJIkbFTUllBSXAXWtAKZTXKEm9uicsKaI45UJxaMwip%2FMQxD8JcfKzhlWrPlEsGBpbm51r");
        tbkItemDetailDO.setTaoToken("￥8Ue60LBnTSD￥");
        tbkItemDetailDO.setShortLinkUrl("https://s.click.taobao.com/u4nNFTw");
        tbkItemDetailDO.setItemId("561921276560");
        tbkItemDetailMapper.updateTbkItemByItemId(tbkItemDetailDO);
    }
}
