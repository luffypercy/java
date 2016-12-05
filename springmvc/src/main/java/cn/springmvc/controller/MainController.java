package cn.springmvc.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.springmvc.enums.DbWREnums;
import cn.springmvc.util.FrameUtil;
import xyxc.wx.model.XcWxUsersModel;
import xyxc.wx.service.IXcWxUsersService;


@Controller
@Scope("prototype")
public class MainController {

	private HttpServletRequest request;
	@Resource
	private IXcWxUsersService xcWxUsersService;
	
	
	@RequestMapping("/tt/index")
	public String index() throws Exception{
		System.out.println(111);
		request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		List<XcWxUsersModel> users=this.xcWxUsersService.getModelList(FrameUtil.newHashMap(), DbWREnums.WRITE);
		request.setAttribute("userList", users);
		
		return "index";
	}
	
	
	
}