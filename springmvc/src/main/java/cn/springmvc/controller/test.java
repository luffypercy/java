package cn.springmvc.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.generatecode.CodeUtil;
import cn.springmvc.util.FrameUtil;
import cn.springmvc.util.HttpsUtil;

public class test {
	private static Map loginMap;
	
	
	public static void main(String[] args)
			throws Exception {
		login();
		Long t=0l;
		while(true){
			if(t<FrameUtil.getTime()){
				t=FrameUtil.getTime();
				for(String bid:getTbList()){
					tb(bid);
				}
				t+=60;
				System.out.println(t);
			}
		}
		//split();
	}
	
	public static void split(){
		String template="仅限定期投资使用\n满{v1}元可用\n不可叠加使用";
		String result="";
		String[] templates= template.split("\n");
		for(String temp:templates){
			System.out.println(temp);
			if(temp.contains("{v1}")){
					//temp=temp.replace("{v1}", NumberUtil.formatNumber(23, NumberUtil.FORMAT_1));
				continue;
			}
			result+=(temp+"\n");
		}
		System.out.println(result.substring(0, result.length()-1)); 
	}
	
	public static List<String> getTbList() throws Exception{
		String json=HttpsUtil.requestPostForm("http://api.yijiedai.com/V_2_0/invest/invest", loginMap);
		Map<String,Object> m=JSON.parseObject(json, Map.class);
		Map<String,Object> data=(Map<String, Object>) m.get("data");
		Map<String,Object> page=(Map<String, Object>) data.get("page");
		List<Map<String,Object>> dataList=(List<Map<String, Object>>) page.get("dataList");
		 List<String> ids=new ArrayList<String>();
		for(Map<String,Object> borr:dataList){
			if((Integer)borr.get("status")==40){
				if(((Integer)borr.get("starttime"))<FrameUtil.getTime()){
					if(checkbuy(borr.get("borrow_id").toString())){
					ids.add( borr.get("borrow_id").toString());
					}
				}
			}
		}
		return ids;
	}
	
	public static boolean checkbuy(String borrow_id) throws Exception{
		String json=HttpsUtil.requestPostForm("http://api.yijiedai.com/V_2_0/creditor", loginMap);
		Map<String,Object> m=JSON.parseObject(json, Map.class);
		Map<String,Object> data=(Map<String, Object>) m.get("data");
		Map<String,Object> page=(Map<String, Object>) data.get("page");
		List<Map<String,Object>> dataList=(List<Map<String, Object>>) page.get("dataList");
		for(Map<String,Object> borr:dataList){
			if(borr.get("borrow_id").toString().equals(borrow_id)){
				return true;
			}
		}
		return false;
	}
	
	
	public static String tb(String id) throws Exception{
		loginMap.put("borrow_id", id);
		loginMap.put("tbprice", 100);
		String json=HttpsUtil.requestPostForm("http://api.yijiedai.com/V_2_0/invest/tb", loginMap);
		Map<String,Object> m=JSON.parseObject(json, Map.class);
		System.out.println(json);
		return (String) m.get("state");
	}
	
	public static void login() throws Exception{
		Map request = new HashMap<String, String>();
		request.put("username", "luffypercy");
		request.put("password", "2a0aedf66dad109d40717261c4d4f71b");
		request.put("device_no", "yjd_c5218323-1ddb-45c7-a212-caa7c887a846_android");
		request.put("mobile_type", "android");
		String json=HttpsUtil.requestPostForm("http://api.yijiedai.com/login", request);
		Map<String,Object> m=JSON.parseObject(json, Map.class);
		loginMap=(Map) m.get("data");
		System.out.println(loginMap.toString());
	}
	
	public static void login2() throws Exception{
		Map request = new HashMap<String, String>();
		request.put("username", "兔斯基_囧");
		request.put("password", "a8b1c0142f3d370eb25c31ea4e277f84");
		request.put("device_no", "yjd_c5218323-1ddb-45c7-a212-caa7c887a846_android");
		request.put("mobile_type", "android");
		String json=HttpsUtil.requestPostForm("http://api.yijiedai.com/login", request);
		Map<String,Object> m=JSON.parseObject(json, Map.class);
		loginMap=(Map) m.get("data");
		System.out.println(loginMap.toString());
	}

}
