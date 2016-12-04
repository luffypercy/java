package cn.springmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoIterable;

import cn.dota.util.MongoDBUtil;

@Controller
@Scope("prototype")
public class MainController {

	private HttpServletRequest request;
	
	
	@RequestMapping("/tt/index")
	public String index(){
		System.out.println(111);
		request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		/*List<DotaTeam> team=DotaUtil.getTeamInfo(0, 20);
		List<String> idList=new ArrayList<String>();
		for(DotaTeam t:team){
			idList.addAll(t.getPlayper_account_ids());
			idList.add(t.getAdmin_account_id());
		}*/
		MongoIterable<String> dblist=MongoDBUtil.instance.getAllDBNames();
		MongoCursor<String> iterator = dblist.iterator();
		List<String> dblista=new ArrayList<String>();
		Map<String,List<String>> colist=new HashMap<String,List<String>>();
		  while (iterator.hasNext())
		  {
			  String key=iterator.next().toString();
			  dblista.add(key);
			  colist.put(key, MongoDBUtil.instance.getAllCollections("admin"));
			  List<String> ss=new ArrayList<String>();
			  for(String coname:MongoDBUtil.instance.getAllCollections("admin")){
				  MongoCollection<Document> ts =MongoDBUtil.instance.getDB(key).getCollection(coname);
				  
			         System.out.println("集合 test 选择成功");
			         
			         //检索所有文档  
			         /** 
			         * 1. 获取迭代器FindIterable<Document> 
			         * 2. 获取游标MongoCursor<Document> 
			         * 3. 通过游标遍历检索出的文档集合 
			         * */  
			         FindIterable<Document> findIterable = ts.find();  
			         MongoCursor<Document> mongoCursor = findIterable.iterator();  
			         while(mongoCursor.hasNext()){  
			        	 ss.add( mongoCursor.next().toJson());
			         }  
				  colist.put(key, ss);
			  }

		  }
			request.setAttribute("dblist", dblista);
		request.setAttribute("colist", colist);
		
		/*System.out.println(JSON.toJSONString(team));
		this.request.setAttribute("players", DotaUtil.getPlayerInfo(idList));
		this.request.setAttribute("teams", team);*/
		return "index";
	}
	
	
	
}
