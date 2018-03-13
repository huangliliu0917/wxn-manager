package wxn.store;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.NTbkItem;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.request.TbkItemInfoGetRequest;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkItemInfoGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import wxn.store.dal.mapper.TbkItemDetailMapper;
import wxn.store.dal.mapper.TbkItemMapper;
import wxn.store.dal.model.TbkItemDO;
import wxn.store.dal.model.TbkItemDetailDO;

import java.util.List;

@Slf4j
public class TaoBaoApiTest extends BaseSpringTest{

    public final static String url = "https://eco.taobao.com/router/rest";
    public final static String appkey = "24810297";
    public final static String secret = "76250cfab3f567fdb5e90e7d03c1b10c";

    @Autowired
    private TbkItemMapper tbkItemMapper;
    @Autowired
    private TbkItemDetailMapper tbkItemDetailMapper;

    @Test
    @Rollback
    public void getPorducts() throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        TbkItemGetRequest req = new TbkItemGetRequest();
        req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        req.setQ("首饰品");
        //req.setCat("16,18");
        //req.setItemloc("杭州");
        req.setSort("total_sales");
        req.setPageNo(1L);//第几页
        req.setPageSize(20L);//每页多少产品
        TbkItemGetResponse rsp = client.execute(req);
        List<NTbkItem> list = rsp.getResults();
      for(NTbkItem nTbkItem : list){
            String smallImages = parseImageList(nTbkItem.getSmallImages());
            TbkItemDO itemDO = new TbkItemDO();
            itemDO.setItemId(String.valueOf(nTbkItem.getNumIid()));
            itemDO.setTitle(nTbkItem.getTitle());
            itemDO.setPictUrl(nTbkItem.getPictUrl());
            itemDO.setSmallImages(smallImages);
            itemDO.setReservePrice(nTbkItem.getReservePrice());
            itemDO.setZkFinalPrice(nTbkItem.getZkFinalPrice());
            itemDO.setUserType(nTbkItem.getUserType());
            itemDO.setProvcity(nTbkItem.getProvcity());
            itemDO.setItemUrl(nTbkItem.getItemUrl());
            itemDO.setNick(nTbkItem.getNick());
            itemDO.setSellerId(String.valueOf(nTbkItem.getSellerId()));
            itemDO.setVolume(nTbkItem.getVolume());
            tbkItemMapper.insert(itemDO);
            itemDetail(String.valueOf(nTbkItem.getNumIid()));

            System.out.println(String.valueOf(nTbkItem.getSellerId()));
            System.out.println(smallImages);
        }
    }


    public void itemDetail(String itemId)throws ApiException{
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        TbkItemInfoGetRequest req = new TbkItemInfoGetRequest();
        req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,nick,seller_id,volume,cat_leaf_name,cat_name");
        req.setPlatform(2L);
        req.setNumIids(itemId);
        TbkItemInfoGetResponse rsp = client.execute(req);
        List<NTbkItem> list = rsp.getResults();
        for(NTbkItem nTbkItem : list){
            String smallImages = parseImageList(nTbkItem.getSmallImages());
            TbkItemDetailDO tbkItemDetailDO = new TbkItemDetailDO();
            tbkItemDetailDO.setItemId(String.valueOf(nTbkItem.getNumIid()));
            tbkItemDetailDO.setTitle(nTbkItem.getTitle());
            tbkItemDetailDO.setPictUrl(nTbkItem.getPictUrl());
            tbkItemDetailDO.setSmallImages(smallImages);
            tbkItemDetailDO.setReservePrice(nTbkItem.getReservePrice());
            tbkItemDetailDO.setZkFinalPrice(nTbkItem.getZkFinalPrice());
            tbkItemDetailDO.setUserType(nTbkItem.getUserType());
            tbkItemDetailDO.setProvcity(nTbkItem.getProvcity());
            tbkItemDetailDO.setItemUrl(nTbkItem.getItemUrl());
            tbkItemDetailDO.setNick(nTbkItem.getNick());
            tbkItemDetailDO.setSellerId(String.valueOf(nTbkItem.getSellerId()));
            tbkItemDetailDO.setVolume(nTbkItem.getVolume());
            tbkItemDetailDO.setCatLeftName(nTbkItem.getCatLeafName());
            tbkItemDetailDO.setCatName(nTbkItem.getCatName());
            tbkItemDetailMapper.insert(tbkItemDetailDO);
        }
        System.out.println(rsp.getBody());
    }

    private String parseImageList(List<String> smallImages) {
        String delPrefix = "";
        String delPostfix = "";
        if(null != smallImages && smallImages.size()>0){
            delPrefix = smallImages.toString().replace("[","");
            delPostfix = delPrefix.replace("]","");
        }
        return delPostfix;
    }
}
