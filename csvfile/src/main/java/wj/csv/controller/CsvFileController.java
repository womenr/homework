package wj.csv.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import wj.base.controller.BaseController;
import wj.csv.pojo.Account;
import wj.csv.pojo.Items;
import wj.csv.pojo.Msg;
import wj.csv.pojo.TableInfo;
import wj.csv.pojo.User;
import wj.csv.service.CsvFileService;


@Controller
public class CsvFileController extends BaseController{
	
	@Autowired
	private CsvFileService csvFileService;
	
	@RequestMapping("/editItem")
	public String eidtItem(String pk){
	        return null;
	}
	
	@RequestMapping("/deleteItem")
	public String deleteItem(String pk){
	        return null;
	}
	
	@RequestMapping("/downloadTable")
	public String downloadTable(String tableName){
		return null;
	}
	
	@RequestMapping("/deleteTable")
	public String deleteTable(String tableName, Model model){
	    System.out.println(tableName);
	    if(tableName != null) {
	    	csvFileService.deleteTable(tableName);
	    	model.addAttribute("tabelMsg", "成功清空表格数据");
	        return "files/upload";
	    }
	    	model.addAttribute("tabelMsg", "清空表格数据失败");
	        return "files/upload";
	}
	
	@RequestMapping("/home")
	public String home(Model model) {
		List<User> userList = csvFileService.findAll(User.class);
		List<Items> itemsList = csvFileService.findAll(Items.class);
		List<Account> accountList = csvFileService.findAll(Account.class);
		model.addAttribute("userList", userList);
		model.addAttribute("itemsList", itemsList);
		model.addAttribute("accountList", accountList);
		List<TableInfo> tableList = csvFileService.getTableNames();
		model.addAttribute("tableList", tableList);
		return "files/upload";
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.POST) 
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file, Model model) {

		if(file.isEmpty()) {
        	model.addAttribute("fileMsg", "上传文件内容为空");
        	return "forward:/home";
        }
        //判断文件是否是csv文件
		String fileName = file.getOriginalFilename();
		if (!(fileName.endsWith(".csv"))) {
			model.addAttribute("fileMsg", "所上传的文件必须是csv文件");
			return "forward:/home";
		}
		
		if (file.getSize() > 20971420) {
			model.addAttribute("fileMsg", "所上传的文件大小超过了20M");
			return "forward:/home";
		}
		
		//将上传上来的文件直接转化成流
		InputStream fileInput;
		try {
			fileInput = file.getInputStream();
			//调用service方法，从文件名判断应该存放到哪个数据库的表然后进行数据存放
			csvFileService.insertData(fileInput, fileName);
		} catch (IOException e) {
			model.addAttribute("fileMsg", "文件上传失败");
			return "forward:/home";
		} catch (ClassNotFoundException e) {
			model.addAttribute("fileMsg", "文件名不符合规定");
			return "forward:/home";
		}
		//如果上述步骤都通过了，那就说明文件上传成功，并且数据也保存到了数据库，则跳转到成功页面。
    	return "files/success";
	}
	

	
	
}
