package cn.springmvc.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxinmy.weixin4j.mp.WeixinProxy;

import cn.springmvc.enums.DbWREnums;
import cn.springmvc.util.FrameUtil;
import xyxc.wx.model.XcWxUsersModel;
import xyxc.wx.service.IXcWxUsersService;


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
	
	public void test(){
		WeixinProxy weixinProxy = new WeixinProxy();
	}
	
}