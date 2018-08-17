package wj.csv.Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MyExceptionHandler {
	
/*	  @ExceptionHandler(CustomException.class)//可以直接写@ExceptionHandler,不指明异常类，会自动映射
      public ModelAndView customExceptionHnadler(CustomException customException){ //还可以声明接收其他任意参数
          ModelAndView modelAndView = new ModelAndView("custom_error");
          modelAndView.addObject("errCode",customException.getErrCode());
          modelAndView.addObject("errMsg",customException.getErrMsg());
          return modelAndView;
      }
	 
      @ExceptionHandler(Exception.class)//可以直接写@EceptionHandler，IOExeption继承于Exception
      public ModelAndView allExceptionHandler(Exception exception){
          ModelAndView modelAndView = new ModelAndView("custom_error");
          modelAndView.addObject("errMsg", "this is Exception.class");
          return modelAndView;
      }*/

  	//处理被springMVC拦截到的上传文件大小过大的异常
  	@ExceptionHandler(MaxUploadSizeExceededException.class) 
  	public String handleException(MaxUploadSizeExceededException ex) throws Exception {        
  		return "files/fileSize"; 
  	}
}
