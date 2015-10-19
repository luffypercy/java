package cn.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

import cn.dota.util.DotaTeam;
import cn.dota.util.DotaUtil;

@Controller
@RequestMapping("/")
@Scope("prototype")
public class MainController {

	private HttpServletRequest request;
	
	
	@RequestMapping("index")
	public String index(){
		System.out.println(111);
		request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		List<DotaTeam> team=DotaUtil.getTeamInfo(0, 20);
		List<String> idList=new ArrayList<String>();
		for(DotaTeam t:team){
			idList.addAll(t.getPlayper_account_ids());
			idList.add(t.getAdmin_account_id());
		}
		System.out.println(JSON.toJSONString(team));
		this.request.setAttribute("players", DotaUtil.getPlayerInfo(idList));
		this.request.setAttribute("teams", team);
		return "index";
	}
	
	
	
}
