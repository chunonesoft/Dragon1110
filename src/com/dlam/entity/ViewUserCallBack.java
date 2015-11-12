package com.dlam.entity;

public class ViewUserCallBack
{
	/**
	 * 1  retcode  返回代码  1:成功. 0:失败
2  retmsg  返回描述  0： 加载用户信息失败,
请重新加载.等等 ；1：
加载成功
3  userid  用户编号 
4  username  用户昵称 
5  phonenum  手机号 
6  locationid  地 址 编 号 (FK
t_location)
7  locationname  地址名称 
8  age_range  年龄范围,1 <25,2
25-30  ,3  31-35,4
36-40,5 > 40
9  age_rangename  年龄范围,1 <25,2
25-30  ,3  31-35,4
36-40,5 > 40
10  characterid  角色定位编号(FK
t_character)
11  charactername  角色定位名称 
12  status  当前状态编号(FK
t_status)
蓝胖子项目接口设计
常州多啦艾萌网络科技有限公司 - 12 -
13  statusname  当前状态名称 
14  demand  需求描述 
15  project  项目描述 
16  email  邮箱地址 
17  email_status  邮箱是否验证,0 未
验证,1 已验证
18  iconpath  图标地址(可以为
空,为空用默认图
标)
19  realname  姓名 
20  career  工作经历 
21  sex  性别， 0 女性， 1 男
性
22   certificated  实名认证，0 未认证，1 已认证
23  tags  标签字符串,空格分隔
	 * 
	 * 
	 * 
	 */
	//1 成功     0失败
	private int retcode;
	
	//retmsg返回描述
	private String retmsg;
	
	//用户编号
	private String userid;
	
	private String username;
	
	private String phonenum;
	//地址编号
	private String locationid;
	//地址名称
	private String locationname;
	//年龄范围   参数1   2       3      4    5
	private String age_range;
	//年龄范围  值<25 25-30  31-35  36-40  >40
	private String age_rangename;
	//角色定位编号  (FKt_character)
	private String characterid;
	//角色定位名称
	private String charactername;
	//当前状态编号(FKt_status)
	private String status;
	//当前状态名称
	private String statusname;
	//需求描述
	private String demand;
	//项目描述
	private String project;
	//邮箱地址
	private String email;
	//邮箱是否验证  0未验证     1已验证
	private String email_status;
	//图片地址
	private String iconpath;
	//姓名
	private String realname;
	//工作经历
	private String career;
	//性别
	private String sex;
	//实名认证，0 未认证，1 已认证
	private String certificated;
	//标签字符串,空格分隔
	private String tags;
	//关注领域,空格分隔
	private String focusarea;
	//是否是好友:0 不是好友;1 是好友
	private int is_friend;
	public ViewUserCallBack(int retcode, String retmsg, String userid,
			String username, String phonenum, String locationid,
			String locationname, String age_range, String age_rangename,
			String characterid, String charactername, String status,
			String statusname, String demand, String project, String email,
			String email_status, String iconpath, String realname,
			String career, String sex, String certificated, String tags,
			String focusarea, int is_friend)
	{
		super();
		this.retcode = retcode;
		this.retmsg = retmsg;
		this.userid = userid;
		this.username = username;
		this.phonenum = phonenum;
		this.locationid = locationid;
		this.locationname = locationname;
		this.age_range = age_range;
		this.age_rangename = age_rangename;
		this.characterid = characterid;
		this.charactername = charactername;
		this.status = status;
		this.statusname = statusname;
		this.demand = demand;
		this.project = project;
		this.email = email;
		this.email_status = email_status;
		this.iconpath = iconpath;
		this.realname = realname;
		this.career = career;
		this.sex = sex;
		this.certificated = certificated;
		this.tags = tags;
		this.focusarea = focusarea;
		this.is_friend = is_friend;
	}
	public ViewUserCallBack()
	{
		super();
	}
	public int getRetcode()
	{
		return retcode;
	}
	public void setRetcode(int retcode)
	{
		this.retcode = retcode;
	}
	public String getRetmsg()
	{
		return retmsg;
	}
	public void setRetmsg(String retmsg)
	{
		this.retmsg = retmsg;
	}
	public String getUserid()
	{
		return userid;
	}
	public void setUserid(String userid)
	{
		this.userid = userid;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPhonenum()
	{
		return phonenum;
	}
	public void setPhonenum(String phonenum)
	{
		this.phonenum = phonenum;
	}
	public String getLocationid()
	{
		return locationid;
	}
	public void setLocationid(String locationid)
	{
		this.locationid = locationid;
	}
	public String getLocationname()
	{
		return locationname;
	}
	public void setLocationname(String locationname)
	{
		this.locationname = locationname;
	}
	public String getAge_range()
	{
		return age_range;
	}
	public void setAge_range(String age_range)
	{
		this.age_range = age_range;
	}
	public String getAge_rangename()
	{
		return age_rangename;
	}
	public void setAge_rangename(String age_rangename)
	{
		this.age_rangename = age_rangename;
	}
	public String getCharacterid()
	{
		return characterid;
	}
	public void setCharacterid(String characterid)
	{
		this.characterid = characterid;
	}
	public String getCharactername()
	{
		return charactername;
	}
	public void setCharactername(String charactername)
	{
		this.charactername = charactername;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getStatusname()
	{
		return statusname;
	}
	public void setStatusname(String statusname)
	{
		this.statusname = statusname;
	}
	public String getDemand()
	{
		return demand;
	}
	public void setDemand(String demand)
	{
		this.demand = demand;
	}
	public String getProject()
	{
		return project;
	}
	public void setProject(String project)
	{
		this.project = project;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getEmail_status()
	{
		return email_status;
	}
	public void setEmail_status(String email_status)
	{
		this.email_status = email_status;
	}
	public String getIconpath()
	{
		return iconpath;
	}
	public void setIconpath(String iconpath)
	{
		this.iconpath = iconpath;
	}
	public String getRealname()
	{
		return realname;
	}
	public void setRealname(String realname)
	{
		this.realname = realname;
	}
	public String getCareer()
	{
		return career;
	}
	public void setCareer(String career)
	{
		this.career = career;
	}
	public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex = sex;
	}
	public String getCertificated()
	{
		return certificated;
	}
	public void setCertificated(String certificated)
	{
		this.certificated = certificated;
	}
	public String getTags()
	{
		return tags;
	}
	public void setTags(String tags)
	{
		this.tags = tags;
	}
	public String getFocusarea()
	{
		return focusarea;
	}
	public void setFocusarea(String focusarea)
	{
		this.focusarea = focusarea;
	}
	public int getIs_friend()
	{
		return is_friend;
	}
	public void setIs_friend(int is_friend)
	{
		this.is_friend = is_friend;
	}
	@Override
	public String toString()
	{
		return "ViewUserCallBack [retcode=" + retcode + ", retmsg=" + retmsg
				+ ", userid=" + userid + ", username=" + username
				+ ", phonenum=" + phonenum + ", locationid=" + locationid
				+ ", locationname=" + locationname + ", age_range=" + age_range
				+ ", age_rangename=" + age_rangename + ", characterid="
				+ characterid + ", charactername=" + charactername
				+ ", status=" + status + ", statusname=" + statusname
				+ ", demand=" + demand + ", project=" + project + ", email="
				+ email + ", email_status=" + email_status + ", iconpath="
				+ iconpath + ", realname=" + realname + ", career=" + career
				+ ", sex=" + sex + ", certificated=" + certificated + ", tags="
				+ tags + ", focusarea=" + focusarea + ", is_friend="
				+ is_friend + "]";
	}
	
}
