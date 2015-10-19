package cn.dota.util;

import java.util.List;

public class DotaTeam extends BaseModel{
	private String team_id;
	private String name;
	private String tag;
	private String time_created;
	private String rating;
	private String logo;
	private String logo_sponsor;
	private String country_code;
	private String url;
	private String games_played_with_current_roster;
	private List<String> playper_account_ids;
	private String admin_account_id;
	private List<String> league_id;
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTime_created() {
		return time_created;
	}
	public void setTime_created(String timecreated) {
		this.time_created = timecreated;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getLogo_sponsor() {
		return logo_sponsor;
	}
	public void setLogo_sponsor(String logo_sponsor) {
		this.logo_sponsor = logo_sponsor;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getGames_played_with_current_roster() {
		return games_played_with_current_roster;
	}
	public void setGames_played_with_current_roster(String games_played_with_current_roster) {
		this.games_played_with_current_roster = games_played_with_current_roster;
	}
	public List<String> getPlayper_account_ids() {
		return playper_account_ids;
	}
	public void setPlayper_account_ids(List<String> playper_account_ids) {
		this.playper_account_ids = playper_account_ids;
	}
	public String getAdmin_account_id() {
		return admin_account_id;
	}
	public void setAdmin_account_id(String admin_account_id) {
		this.admin_account_id = admin_account_id;
	}
	public List<String> getLeague_id() {
		return league_id;
	}
	public void setLeague_id(List<String> league_id) {
		this.league_id = league_id;
	}
	
	
}
