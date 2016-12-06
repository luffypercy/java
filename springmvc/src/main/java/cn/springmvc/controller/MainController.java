package cn.springmvc.controller;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.springmvc.enums.DbWREnums;
import cn.springmvc.util.FrameUtil;
import xyxc.wx.model.XcWxUsersModel;
import xyxc.wx.service.IXcWxUsersService;

import javax.annotation.Resource;


@Controller
@Scope("prototype")
@RequestMapping("/tt/")
public class MainController extends BaseControl{

	@Resource
	private IXcWxUsersService xcWxUsersService;
	
	
	@RequestMapping("index")
	public String index() throws Exception{
		System.out.println(111);
		List<XcWxUsersModel> users=this.xcWxUsersService.getModelList(FrameUtil.newHashMap(), DbWREnums.WRITE);
		request.setAttribute("userList", users);
		
		return "index";
	}
	
	
	
}