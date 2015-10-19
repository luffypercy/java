package cn.dota.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class DotaUtil {
	private static String KEY="438DC06E955B86CB6F18B953460DC27B";
	
	private static String TEAM_INFO_URL="http://api.steampowered.com/IDOTA2Match_570/GetTeamInfoByTeamID/v1";
	
	private static String PLAYER_SUMMARY="http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002";
	
	private static Long ENCODE_64_BIT=76561197960265728l;
	
	@SuppressWarnings("unchecked")
	public static List<DotaTeam> getTeamInfo(Integer teamid,Integer len){
		List<DotaTeam> team=null;
		try{
			String str=getJsonContent(TEAM_INFO_URL+"?key="+KEY+"&start_at_team_id="+teamid+"&teams_requested="+len, "GET");
			Map<String, Object> result=(Map<String, Object>) JSON.parse(str);
			team=new ArrayList<DotaTeam>();
			for (Map<String, Object> i : (List<Map<String, Object>>) ((Map<String, Object>) result.get("result"))
					.get("teams")) {
				List<String> player_list=new ArrayList<String>();
				List<String> league_list=new ArrayList<String>();
				DotaTeam t = new DotaTeam();
				for (Map.Entry<String, Object> e : i.entrySet()) {
					if (e.getKey().startsWith("player_")) {
						player_list.add((e.getValue() + ""));
					} else if (e.getKey().startsWith("league_id_")) {
						league_list.add((e.getValue() + ""));
					} else {
						Class<? extends DotaTeam> cls = t.getClass();
						Method med = cls.getMethod(getMethodName(e.getKey()), String.class);
						med.invoke(t, e.getValue() + "");
					}
				}
				t.setLeague_id(league_list);
				t.setPlayper_account_ids(player_list);
				team.add(t);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return team;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,PlayerModel> getPlayerInfo(List<String> idList){
		Map<String,PlayerModel> playerInfos=new HashMap<String,PlayerModel>();
		String ids="";
		for(String id:idList){
			if(ids.length()>0){
				ids+=",";
			}
			ids+=(Long.parseLong(id)+ENCODE_64_BIT);
		}
		String str=getJsonContent(PLAYER_SUMMARY+"?key="+KEY+"&steamids="+ids, "GET");
		Map<String, Object> result=(Map<String, Object>) JSON.parse(str);
		String player=JSON.toJSONString((((Map<String, Object>)result.get("response")).get("players")));
		List<PlayerModel> list=(List<PlayerModel>)JSON.parseArray(player, PlayerModel.class);
		for(PlayerModel m:list){
			playerInfos.put((m.getSteamid()-ENCODE_64_BIT)+"", m);
		}
		return playerInfos;
	}
	
	public static String getMethodName(String m){
		String first=m.substring(0, 1);
		return "set"+first.toUpperCase()+m.substring(1);
	};
	
	public static String getJsonContent(String urlStr,String method){
		try{
			URL url=new URL(urlStr);
			HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
			httpConn.setConnectTimeout(3000);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod(method);
			
			int respCode=httpConn.getResponseCode();
			if(respCode==200){
				return ConvertStream2Json(httpConn.getInputStream());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	private static String ConvertStream2Json(InputStream inputStream){
		String jsonStr="";
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		byte[] buffer=new byte[1024];
		int len=0;
		try{
			while((len=inputStream.read(buffer, 0, buffer.length))!=-1){
				out.write(buffer,0,len);
			}
			jsonStr=new String(out.toByteArray());
		}catch(Exception e){e.printStackTrace();}
		return jsonStr;
	}
}
