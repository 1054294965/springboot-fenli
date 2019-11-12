package com.test.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

@RestController
public class FileRestController {
	@Value("${posyspath}") 
	private String poSysPath;
	
	@Value("${popassword}") 
	private String poPassWord;
	
	@Value("${docpath}") 
	private String docpath;
	
	@RequestMapping("/fileList")
	public ModelAndView fileList(HttpServletRequest request, Map<String,Object> map){
		File dir = new File(docpath);
		String[] list = dir.list();
		ArrayList<String> fileList = new ArrayList<String>(); 
		for (String string : list) {
			fileList.add(string);
		}
		map.put("fileList",fileList);
		map.put("name","chushiyun");
		ModelAndView mv = new ModelAndView("file_list");
		return mv;
	}
	@RequestMapping(value="/customWord",method=RequestMethod.GET)
	public ModelAndView customWord(HttpServletRequest request, Map<String,Object> map){
		String fileName = request.getParameter("fileName");
		System.out.println(fileName);
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		poCtrl.setServerPage("/poserver.zz");//设置服务页面
		poCtrl.addCustomToolButton("保存","Save",1);//添加自定义保存按钮
		poCtrl.addCustomToolButton("盖章","AddSeal",2);//添加自定义盖章按钮
		poCtrl.setSaveFilePage("save"); //这里可以是save表示是请求， 也可以是save.jsp直接对应到页面
		//打开word
		poCtrl.webOpen("file://"+docpath+fileName,OpenModeType.docAdmin,"张三");
		map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));
		ModelAndView mv = new ModelAndView("word");
		return mv;
	}
	@RequestMapping("/fileDownload")
	 public String fileDownload(HttpServletRequest request,HttpServletResponse response){
		 String fileName = request.getParameter("fileName");
		 System.out.println(docpath+fileName);
		 File file = new File(docpath+fileName);
		 if(file.exists()){
			 response.setContentType("application/force-download");
			 response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
			 byte[] buffer = new byte[1024];
			 FileInputStream fileIn = null;
			 BufferedInputStream byteIn = null;
			 try{
				 fileIn = new FileInputStream(file);
				 byteIn = new BufferedInputStream(fileIn);
				 OutputStream outputStream = response.getOutputStream();
				 int i = byteIn.read(buffer);
				 while(i!=-1){
					 outputStream.write(buffer, 0, i);
					 i = byteIn.read(buffer);
				 }
				 return "下载成功";
			 }catch (Exception e) {
				 e.printStackTrace();
			 }finally {
				 if (byteIn!= null) {
					 try {
						byteIn.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
				 if(fileIn !=null){
					 try {
						fileIn.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
			 }
		 }
		 return "下载失败";
	 }
	
}
