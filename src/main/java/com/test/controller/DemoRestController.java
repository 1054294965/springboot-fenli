package com.test.controller;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.zhuozhengsoft.pageoffice.*;

/**
 * @author Administrator
 *
 */
@RestController
public class DemoRestController {
	
	@Value("${posyspath}") 
	private String poSysPath;
	
	@Value("${popassword}") 
	private String poPassWord;
	
	@Value("${docpath}") 
	private String docPath;

	@RequestMapping("/hello")
	public String test() {
		System.out.println("hello run");
		return "Hello";
	}
	
	@RequestMapping(value="/word", method=RequestMethod.GET)
	public ModelAndView showWord(HttpServletRequest request, Map<String,Object> map){
		
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		String fileName = request.getParameter("fileName");
		System.out.println(fileName);
		poCtrl.setServerPage("/poserver.zz");//设置服务页面
		poCtrl.addCustomToolButton("保存","Save",1);//添加自定义保存按钮
		poCtrl.addCustomToolButton("盖章","AddSeal",2);//添加自定义盖章按钮
		poCtrl.setSaveFilePage("/save");//设置处理文件保存的请求方法
		poCtrl.setTimeSlice(1); // 并发(只读)控制 超过1分钟不保存
		//打开word
		poCtrl.webOpen("file://"+docPath+fileName,OpenModeType.docAdmin,"张三");
		map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));
		
		ModelAndView mv = new ModelAndView("word");
		return mv;
	}
	@RequestMapping(value="/excel", method=RequestMethod.GET)
	public ModelAndView showExcel(HttpServletRequest request, Map<String,Object> map){

		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		poCtrl.setServerPage("/poserver.zz");//设置服务页面
		poCtrl.addCustomToolButton("保存","Save",1);//添加自定义保存按钮
		poCtrl.addCustomToolButton("盖章","AddSeal",2);//添加自定义盖章按钮
		poCtrl.setSaveFilePage("/save");//设置处理文件保存的请求方法
		poCtrl.setTimeSlice(1); // 并发(只读)控制 超过1分钟不保存
		//打开word
		poCtrl.webOpen("file://"+docPath+"test.xls",OpenModeType.xlsNormalEdit,"张三");
		map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));

		ModelAndView mv = new ModelAndView("excel");
		return mv;
	}
	
	@RequestMapping("/save")
	public void saveFile(HttpServletRequest request, HttpServletResponse response){
		FileSaver fs = new FileSaver(request, response);
		fs.saveToFile(docPath + fs.getFileName());
		fs.close();
	}
	
	
	/**
	 * 添加印章管理程序Servlet（可选）
	 * @return
	 */
	@Bean
    public ServletRegistrationBean servletRegistrationBean2() {
		com.zhuozhengsoft.pageoffice.poserver.AdminSeal adminSeal = new com.zhuozhengsoft.pageoffice.poserver.AdminSeal();
		adminSeal.setAdminPassword(poPassWord);//设置印章管理员admin的登录密码
		adminSeal.setSysPath(poSysPath);//设置印章数据库文件poseal.db存放的目录
		ServletRegistrationBean srb = new ServletRegistrationBean(adminSeal);
		srb.addUrlMappings("/adminseal.zz");
		srb.addUrlMappings("/sealimage.zz");
		srb.addUrlMappings("/loginseal.zz");
        return srb;// 
    }
}
