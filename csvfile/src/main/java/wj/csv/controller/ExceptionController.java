package wj.csv.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import wj.csv.Exception.CustomException;

@Controller
public class ExceptionController {
      
	  @RequestMapping(value = "/{type}", method = RequestMethod.GET)
      public ModelAndView getPages(@PathVariable(value = "type") String type) throws Exception{
          if ("error".equals(type)) {
              // 由handleCustomException处理
              throw new CustomException("E888", "This is custom message");
         } else if ("io-error".equals(type)) {
             // 由handleAllException处理
             throw new IOException();
         } else {
             return new ModelAndView("index").addObject("msg", type);
         }
     }

}
