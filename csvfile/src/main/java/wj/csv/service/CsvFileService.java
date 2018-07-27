package wj.csv.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wj.csv.mapper.AccountMapper;
import wj.csv.mapper.ItemsMapper;
import wj.csv.mapper.UserMapper;
import wj.csv.pojo.Account;
import wj.csv.pojo.Items;
import wj.csv.pojo.TableList;
import wj.csv.pojo.User;
import wj.csv.utils.AutoFillBean;

@Service
public class CsvFileService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private ItemsMapper itemsMapper;
	
	public <T> void insertData(InputStream file, String fileName) throws ClassNotFoundException {
		List<T> beanList = AutoFillBean.fillBean(file, fileName);
		//判断获得的beanList是属于哪个类的，然后用相应的mapper进行数据插入操作
		Class<?> clazz;
		try {
			clazz = AutoFillBean.getClassFromFileName(fileName);
/**
 * 这里调用相应的mapper之前用的是if判断，然后将bean强转为对应的class类再用mapper的方法把数据传进去，这里要怎么优化？
 */
			if (clazz.getName().endsWith("User")) {
				for (T bean : beanList) {
					User user = (User) bean;
					Integer uid = user.getUid();
					if (userMapper.findByPrimaryKey(uid) != null) { //如果数据库中已经有这个数据，就删掉，再插入
						userMapper.deleteByPrimaryKey(uid);
					}
					userMapper.insert(user);
				}
			}
			
			if (clazz.getName().endsWith("Account")) {
				for (T bean : beanList) {
					Account account = (Account) bean;
					String primaryKey = account.getAccount();
					if (accountMapper.findByPrimaryKey(primaryKey) != null) { //如果数据库中已经有这个数据，就删掉，再插入
						accountMapper.deleteByPrimaryKey(primaryKey);
					}
					accountMapper.insert(account);
				}
			}
			
			if (clazz.getName().endsWith("Items")) {
//				List<Items> itemsList = (List<Items>) beanList;
				for (T bean : beanList) {
					Items items = (Items) bean;
					String primaryKey = items.getItem();
					if (itemsMapper.findByPrimaryKey(primaryKey) != null) { //如果数据库中已经有这个数据，就删掉，再插入
						itemsMapper.deleteByPrimaryKey(primaryKey);
					}
					itemsMapper.insert(bean);
				}
			}
		} catch (ClassNotFoundException e) {
			throw e;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> clazz){ //适用于所有表格的查询所有数据的功能，返回beanList
		List<T> beanList = new ArrayList<T>();
		if (clazz == User.class) {
			beanList = (List<T>) userMapper.selectByExample(null);
		} else if (clazz == Account.class) {
			beanList = (List<T>) accountMapper.selectByExample(null);
		} else if (clazz == Items.class) {
			beanList = (List<T>) itemsMapper.selectByExample(null);
		} else {
			return null;
		}
		return beanList;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T findByPrimaryKey(Class<T> clazz, String primaryKey) {
		T bean = null;
		if (clazz == User.class) {
			Integer uid = Integer.parseInt(primaryKey);
			bean = (T) userMapper.findByPrimaryKey(uid);
		} else if (clazz == Account.class) {
			bean = (T) accountMapper.findByPrimaryKey(primaryKey);
		} else if (clazz == Items.class) {
			bean = (T) itemsMapper.findByPrimaryKey(primaryKey);
		} else {
			return null;
		}
		return bean;
	}


	public void deleteTable(String tableName) {
		if(tableName.endsWith("u")) {//说明这个要删的表是user表
			//获得所有user表里面的user对象
			List<User> userList = userMapper.selectByExample(null);
			//获取每个user的主键，然后调用mapper的方法将其在数据库中删掉
			for (User user: userList) {
				Integer pk = user.getUid();
				userMapper.deleteByPrimaryKey(pk);
			}
		}
		if(tableName.endsWith("i")) {//说明这个要删的表是items表
			//获得所有items表里面的item对象
			List<Items> itemsList = itemsMapper.selectByExample(null);
			//获取每个items的主键，然后调用mapper的方法将其在数据库中删掉
			for (Items item: itemsList) {
				String pk = item.getItem();
				itemsMapper.deleteByPrimaryKey(pk);
			}
		}
		if(tableName.endsWith("a")) {//说明这个要删的表是account表
			//获得所有account表里面的account对象
			List<Account> accountList = accountMapper.selectByExample(null);
			//获取每个account的主键，然后调用mapper的方法将其在数据库中删掉
			for (Account account: accountList) {
				String pk = account.getAccount();
				accountMapper.deleteByPrimaryKey(pk);
			}
		}
	}

/*	public List<TableList> showTables(){
		List<String> tables = userMapper.showTables();
		List<TableList> tableList = new ArrayList<TableList>();
		for (String tableName : tables) {
			TableList table = new TableList();
			table.setTableName(tableName);
			tableList.add(table);
		}
		return tableList;
	}*/
	
	public void downloadTable() {
		
	}

	public void editItemByPrimaryKey() {
		
	}

	public <T> Integer deleteByPrimaryKey(Class<T> clazz, String primaryKey) {
		if (clazz == User.class) {
			Integer uid = Integer.parseInt(primaryKey);
			return (userMapper.deleteByPrimaryKey(uid));
		} else if (clazz == Account.class) {
			return (accountMapper.deleteByPrimaryKey(primaryKey));
		} else if (clazz == Items.class) {
			return (itemsMapper.deleteByPrimaryKey(primaryKey));
		} else {
			return null;
		}
	}


	
}
