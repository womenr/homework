package wj.csv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import wj.csv.pojo.Account;
import wj.csv.pojo.AccountExample;

public interface AccountMapper extends BaseMapper {
    long countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

   // Account findByPrimaryKey(String account);
    
   // int insert(Account record);

    int insertSelective(Account record);

    List<Account> selectByExample(AccountExample example);

    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);
    
  //  void deleteByPrimaryKey(String pk);
}