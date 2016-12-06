package xyxc.wx.model;

import com.foxinmy.weixin4j.mp.model.User;

import cn.springmvc.model.Model;
import cn.springmvc.util.FrameUtil;

/**
 * <b>description</b>：微信用户信息表模型<br>
 * <b>time</b>：2016-12-05 21:40:52 <br>
 * <b>author</b>：  percy-chen@hotmail.com
 */
public class XcWxUsersModel extends Model{

	/**
	* @Fields serialVersionUID
	*/
	private static final long serialVersionUID = 1L;
	
	 /*
	  * 
	  */
	private java.lang.Long id;
	 /*
	  *只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
 
	  */
	private java.lang.String unionid;
	 /*
	  *用户的标识，对当前公众号唯一 
	  */
	private java.lang.String openid;
	 /*
	  *用户的昵称 
	  */
	private java.lang.String nickname;
	 /*
	  *用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 
	  */
	private java.lang.Integer sex;
	 /*
	  *用户的语言，简体中文为zh_CN 
	  */
	private java.lang.String language;
	 /*
	  *用户所在城市 
	  */
	private java.lang.String city;
	 /*
	  *	用户所在省份 
	  */
	private java.lang.String province;
	 /*
	  *	用户所在国家 
	  */
	private java.lang.String country;
	 /*
	  *用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。 
	  */
	private java.lang.String headimgurl;
	 /*
	  *关注时间用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间 
	  */
	private java.lang.Long subscribe_time;
	 /*
	  *公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注 
	  */
	private java.lang.String remark;
	 /*
	  *用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 
	  */
	private java.lang.Integer subscribe;
	 /*
	  *用户所在的分组ID（兼容旧的用户分组接口） 
	  */
	private java.lang.Integer groupid;
	 /*
	  *用户被打上的标签ID列表 
	  */
	private java.lang.String tagid_list;
	 /*
	  * 
	  */
	private java.lang.Long addtime;
	 /*
	  * 
	  */
	private java.lang.Long uptime;
	
	public void copyProperties(User u){
		Long now=FrameUtil.getTime();
		this.setCity(u.getCity());
		this.setUnionid(u.getUnionId());
		this.setOpenid(u.getOpenId());
		this.setCountry(u.getCountry());
		this.setAddtime(now);
		this.setGroupid(u.getGroupId());
		this.setHeadimgurl(u.getHeadimgurl());
		this.setLanguage(u.getLanguage());
		this.setNickname(u.getNickName());
		this.setProvince(u.getProvince());
		this.setRemark(u.getRemark());
		this.setSubscribe(u.getSubscribeTime()>0l?0:1);
		this.setSubscribe_time(u.getSubscribeTime());
		this.setUptime(now);
	}

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.String getUnionid() {
		return unionid;
	}

	public void setUnionid(java.lang.String unionid) {
		this.unionid = unionid;
	}
	public java.lang.String getOpenid() {
		return openid;
	}

	public void setOpenid(java.lang.String openid) {
		this.openid = openid;
	}
	public java.lang.String getNickname() {
		return nickname;
	}

	public void setNickname(java.lang.String nickname) {
		this.nickname = nickname;
	}
	public java.lang.Integer getSex() {
		return sex;
	}

	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}
	public java.lang.String getLanguage() {
		return language;
	}

	public void setLanguage(java.lang.String language) {
		this.language = language;
	}
	public java.lang.String getCity() {
		return city;
	}

	public void setCity(java.lang.String city) {
		this.city = city;
	}
	public java.lang.String getProvince() {
		return province;
	}

	public void setProvince(java.lang.String province) {
		this.province = province;
	}
	public java.lang.String getCountry() {
		return country;
	}

	public void setCountry(java.lang.String country) {
		this.country = country;
	}
	public java.lang.String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(java.lang.String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public java.lang.Long getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(java.lang.Long subscribe_time) {
		this.subscribe_time = subscribe_time;
	}
	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	public java.lang.Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(java.lang.Integer subscribe) {
		this.subscribe = subscribe;
	}
	public java.lang.Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(java.lang.Integer groupid) {
		this.groupid = groupid;
	}
	public java.lang.String getTagid_list() {
		return tagid_list;
	}

	public void setTagid_list(java.lang.String tagid_list) {
		this.tagid_list = tagid_list;
	}
	public java.lang.Long getAddtime() {
		return addtime;
	}

	public void setAddtime(java.lang.Long addtime) {
		this.addtime = addtime;
	}
	public java.lang.Long getUptime() {
		return uptime;
	}

	public void setUptime(java.lang.Long uptime) {
		this.uptime = uptime;
	}

}
