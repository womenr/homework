package wj.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import wj.mapper.AgentBasicInfoMapper;
import wj.pojo.AgentBasicInfo;
import wj.utils.MyFileHandler;

@Service
public class AgentService {

	@Autowired
	private AgentBasicInfoMapper agentBasicInfoMapper;
	
	@Scheduled(cron = "0 0 24 ? * WED") //用scheduled注释定时执行该方法，cron语句的意义：秒 分 时 日期 月份 星期 ？表示任意，*表示所有
	public void sortToFiles() {
		//首先指定文件路径和文件名所需匹配的正则表达式
		String directory = "F:\\works";
		String regex = "^agent_\\d+\\.csv"; //匹配开头为agent_结尾为.csv中间为数字的字符串
		//使用方法将匹配的文件找出并删除
		MyFileHandler.pathFilterAndDelete(directory, regex);
		
		//删除后，重新加载数据，保存至指定csv文件中
		//通过店番排序,获取排序后的募集人list
		String order = "shop_number";
		List<AgentBasicInfo> agentBasicInfoList = agentBasicInfoMapper.orderByShopNumberAsc();
		//用作存储单个店番募集人的数据
		List<AgentBasicInfo> dataList = new ArrayList<AgentBasicInfo>(); 
		for (int i = 0; i < agentBasicInfoList.size() -1; i++) { //少遍历一次，因为用上了i+1.
			//遍历list，通过对比前后agent的店番是否一致，一致就输出到同一个文件，文件名为agent_募集人店番.csv
			String number = agentBasicInfoList.get(i).getShopNumber();
			if (number.equals(agentBasicInfoList.get(i+1).getShopNumber())) {
				dataList.add(agentBasicInfoList.get(i));
			} else {
				//如果前后的店番不一致，就说明这是上一个店番的最后一条数据，所以把这条数据写完之后，就要换一个新的店了
				dataList.add(agentBasicInfoList.get(i));
				//加完之后调用方法写入csv文件,清空list，进行下一个店番数据的collect
				MyFileHandler.writeCsvByList(directory, "agent_"+ number + ".csv", dataList);
				dataList.clear();
			}
		}
		//上面的遍历都只遍历到倒二个数据，最后一个数据未处理,经过验证，不管最后一个属于哪个店番，同样如下处理
		AgentBasicInfo lastOne = agentBasicInfoList.get(agentBasicInfoList.size()-1); // 获取最后一行数据
		dataList.add(lastOne);
		MyFileHandler.writeCsvByList(directory, "agent_"+ lastOne.getShopNumber() + ".csv", dataList);
	}
	

}
