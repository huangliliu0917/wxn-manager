package wxn.store.dal.mapper;


import wxn.store.dal.model.TbkItemDetailDO;

import java.util.List;

public interface TbkItemDetailMapper {

    void insert(TbkItemDetailDO tbkItemDetailDO);

    /**
     * 获取商品详情列表
     * @return
     */
    List<TbkItemDetailDO> getTbkItemDetailList();

    void updateTbkItemByItemId(TbkItemDetailDO tbkItemDetailDO);
}