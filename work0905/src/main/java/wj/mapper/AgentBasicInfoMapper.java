package wj.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import wj.pojo.AgentBasicInfo;

public interface AgentBasicInfoMapper {
/*    long countByExample(AgentBasicInfoExample example);

    int deleteByExample(AgentBasicInfoExample example);

    int deleteByPrimaryKey(AgentBasicInfoKey key);

    int insert(AgentBasicInfo record);

    int insertSelective(AgentBasicInfo record);

    List<AgentBasicInfo> selectByExample(AgentBasicInfoExample example);

    AgentBasicInfo selectByPrimaryKey(AgentBasicInfoKey key);

    int updateByExampleSelective(@Param("record") AgentBasicInfo record, @Param("example") AgentBasicInfoExample example);

    int updateByExample(@Param("record") AgentBasicInfo record, @Param("example") AgentBasicInfoExample example);

    int updateByPrimaryKeySelective(AgentBasicInfo record);

    int updateByPrimaryKey(AgentBasicInfo record);*/
    
    List<AgentBasicInfo> orderByShopNumberAsc(); 
}