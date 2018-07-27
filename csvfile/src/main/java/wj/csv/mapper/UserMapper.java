package wj.csv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import wj.csv.pojo.User;
import wj.csv.pojo.UserExample;

public interface UserMapper extends BaseMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);
    
  //  User findByPrimaryKey(Integer uid);
    
   // int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

//	void deleteByPrimaryKey(Integer pk);
	
}