package cn.springmvc.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.WeixinProxy;
import com.foxinmy.weixin4j.mp.model.User;

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
	
	@Autowired
	private WeixinProxy weixinProxy ;

	
	@RequestMapping("index")
	public String index() throws Exception{
		System.out.println(111);
		test();
		List<XcWxUsersModel> users=this.xcWxUsersService.getModelList(FrameUtil.newHashMap(), DbWREnums.WRITE);
		request.setAttribute("userList", users);
		
		return "index";
	}
	
	@RequestMapping("index")
	public void test() throws Exception{
		List<User> us=this.weixinProxy.getAllFollowing();
		for(User u:us){
			XcWxUsersModel xu= this.xcWxUsersService.getModelOne(FrameUtil.newHashMap("unionid",u.getUnionId()), DbWREnums.WRITE);
			if(xu==null){
				xu=new XcWxUsersModel();
				xu.copyProperties(u);
				this.xcWxUsersService.insert(xu);
			}else{
				xu.copyProperties(u);
				this.xcWxUsersService.update(xu);
			}
		}
	}
	
}