package wj.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import wj.mapper.AgentBasicInfoMapper;
import wj.pojo.AgentBasicInfo;
import wj.utils.MyFileHandler;

@Service
public class AgentService {

	@Autowired
	private AgentBasicInfoMapper agentBasicInfoMapper;
	
	@Value("${directory}")
	private String directory;
	
	@Scheduled(cron = "0 0 24 ? * WED") //用scheduled注释定时执行该方法，cron语句的意义：秒 分 时 日期 月份 星期 ？表示任意，*表示所有
	public void sortToFiles() {

		String regex = "^agent_\\d{5}\\.csv$"; //匹配开头为agent_结尾为.csv中间为数字的字符串
		//使用方法将匹配的文件找出并删除
		MyFileHandler.pathFilterAndDelete(directory, regex);
		
		//删除后，重新加载数据，保存至指定csv文件中
		//通过店番排序,获取排序后的募集人list
		List<AgentBasicInfo> agentBasicInfoList = agentBasicInfoMapper.orderByShopNumberAsc();
		
		if (agentBasicInfoList != null) {
			//用作存储单个店番募集人的数据
			List<String> dataList = new ArrayList<String>(); 
			//指定文件名数字的位数，五位数，不足的左边用0补齐
			DecimalFormat df=new DecimalFormat("00000");
			
			int length = agentBasicInfoList.size();

			for (int i = 0; i < length; i++) {
				//遍历list，通过对比前后agent的店番是否一致，一致就输出到同一个文件，文件名为agent_募集人店番.csv
				String number = agentBasicInfoList.get(i).getShopNumber();
			
				if (i + 1 < length && number.equals(agentBasicInfoList.get(i + 1).getShopNumber())) {
					dataList.add(agentBasicInfoList.get(i).toCsvString());
				} else {
					String formatNumber = df.format(Integer.parseInt(number));
					dataList.add(agentBasicInfoList.get(i).toCsvString());
					MyFileHandler.writeCsvByList(directory, "agent_"+ formatNumber + ".csv", dataList);
					dataList.clear();
				}
			}
		}
	}
}
