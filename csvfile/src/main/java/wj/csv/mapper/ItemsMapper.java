package wj.csv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import wj.csv.pojo.Items;
import wj.csv.pojo.ItemsExample;

public interface ItemsMapper extends BaseMapper {
    long countByExample(ItemsExample example);

    int deleteByExample(ItemsExample example);
    
   // Items findByPrimaryKey(String item);

  //  int insert(Items record);

    int insertSelective(Items record);

    List<Items> selectByExample(ItemsExample example);

    int updateByExampleSelective(@Param("record") Items record, @Param("example") ItemsExample example);

    int updateByExample(@Param("record") Items record, @Param("example") ItemsExample example);
    
   // void deleteByPrimaryKey(String pk);
}