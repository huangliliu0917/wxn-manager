package wxn.store.dal.mapper;

import org.apache.ibatis.annotations.Param;
import wxn.store.dal.model.TbkItemDO;

public interface TbkItemMapper {

    void insert(TbkItemDO tbkItemDO);

    void updateTbkItemByItemId(@Param("itemId")String itemId);
}