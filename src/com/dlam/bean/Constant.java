
package com.dlam.bean;

import com.dlam.entity.ViewUserCallBack;

/**
 * 
 * @ClassName:  Constant   
 * @Description:IP地址与接口名   
 * @author: chunsoft 
 * @date:   2015-8-6 下午5:16:48
 */
public class Constant {
	//public static String IP = "http://192.168.155.7:8080/dragon_webapp/json/";
	//public static String IP = "http://10.9.71.189:8080/dragon_webapp/json/";
	//宿舍
	public static String ImageUri = "http://dlamlpz.duapp.com/";
	public static String IP = "http://dlamlpz.duapp.com/json/";
	
	/**
	 * 首页所有资讯列表
	 */
	public static String GetInfoList = "getInfoList.json";
	/**
	 * 获取想要参加的活动信息
	 */
	public static String getMyInfoWant = "getMyInfoWantList.json";
	/**
	 * 存放登录用的手机号
	 */
	public static String phonenum;
	/**
	 * 修改用户密码接口
	 */
	public static String ChangePasswd = "changePasswd.json";
	/**
	 * 搜索用户接口
	 */
	public static String SearchUser = "searchUserResourceList.json";
	/**
	 * 存放登录的用户信息
	 */
	public static ViewUserCallBack thisUser = new ViewUserCallBack();
	public static D_ViewUserBean userData = new D_ViewUserBean(); 
	/**
	 * 手机获取验证码接口
	 */
	public static String RequestPIN = "requestPIN.json";
	/**
	 * 手机获取验证码密码
	 */
	public static String RequestPWD = "requestPasswd.json";
	/**
	 * 手机密码设置
	 */
	public static String SetPassword = "setPasswd.json";
	/**
	 * 用户id
	 */
	public static String userid;
	/**
	 * 用户令牌
	 */
	public static String token = "";
	
	/**
	 * 注册用户接口
	 */
	public static String CreateUser = "createUser.json";
	/**
	 * 为信息点赞接口
	 */
	public static String getPraiseInfo = "praiseInfo.json";
	/**
	 * 对信息取消点赞接口
	 */
	public static String getCancelPraiseInfo = "cancelPraiseInfo.json";
	/**
	 * 想参加活动信息接口
	 */
	public static String getwantInfo = "wantInfo.json";
	/**
	 * 想参加活动信息接口
	 */
	public static String getCancelWantInfo = "cancelWantInfo.json";
	/**
	 * 取得我收藏的信息列表接口
	 */
	public static String getMyFavoriteList = "getMyFavoriteList.json";
	/**
	 * 修改用户接口
	 */
	public static String ModifyUser = "modifyUser.json";
	/**
	 * 读取用户接口
	 */
	public static String ViewUser = "viewUser.json";
	/**
	 * 验证用户登录接口
	 */
	public static String VerifyUser = "verifyUser.json";
	/**
	 * 找回用户密码接口
	 */
	public static String RequestPasswd = "requestPasswd.json";
	
	/**
	 * 查看详细信息接口
	 */
	public static String ViewInfo = "viewInfo.json";
	/**
	 * 取得信息评论接口
	 */
	public static String CommentList = "getInfoCommentList.json";
	/**
	 * 取得想参加活动列表接口
	 */
	public static String WantList = "getInfoWantList.json";
	/**
	 * 取得人脉列表接口
	 */
	public static String getUserResourceList = "getUserResourceList.json";
	/**
	 * 意见反馈接口
	 */
	public static String feedback = "feedback.json";
	/**
	 * 取得最近来访列表接口
	 */
	public static String getUserVisitorList = "getUserVisitorList.json";
	/**
	 * 取得提醒接口
	 */
	public static String getRemindInfo = "remindInfo.json";
	/**
	 * 取得取消提醒接口
	 */
	public static String getCancelRemindInfo = "cancelRemindInfo.json";
	/**
	 * 取得收藏信息接口
	 */
	public static String getFavorInfo = "favorInfo.json";
	/**
	 * 取得取消收藏信息接口
	 */
	public static String getCancelFavorInfo = "cancelFavorInfo.json";
	
	/**
	 * 所有的action的监听的必须要以"ACTION_"开头
	 * 
	 */

	/**
	 * 花名册有删除的ACTION和KEY
	 */
	public static final String ROSTER_DELETED = "roster.deleted";
	public static final String ROSTER_DELETED_KEY = "roster.deleted.key";

	/**
	 * 花名册有更新的ACTION和KEY
	 */
	public static final String ROSTER_UPDATED = "roster.updated";
	public static final String ROSTER_UPDATED_KEY = "roster.updated.key";

	/**
	 * 花名册有增加的ACTION和KEY
	 */
	public static final String ROSTER_ADDED = "roster.added";
	public static final String ROSTER_ADDED_KEY = "roster.added.key";

	/**
	 * 花名册中成员状态有改变的ACTION和KEY
	 */
	public static final String ROSTER_PRESENCE_CHANGED = "roster.presence.changed";
	public static final String ROSTER_PRESENCE_CHANGED_KEY = "roster.presence.changed.key";

	/**
	 * 收到好友邀请请求
	 */
	public static final String ROSTER_SUBSCRIPTION = "roster.subscribe";
	public static final String ROSTER_SUB_FROM = "roster.subscribe.from";
	public static final String NOTICE_ID = "notice.id";

	public static final String NEW_MESSAGE_ACTION = "roster.newmessage";

	/**
	 * 我的消息
	 */
	public static final String MY_NEWS = "my.news";
	public static final String MY_NEWS_DATE = "my.news.date";

	/**
	 * 服务器的配置
	 */
	public static final String LOGIN_SET = "eim_login_set";// 登录设置
	public static final String USERNAME = "username";// 账户
	public static final String PASSWORD = "password";// 密码
	public static final String XMPP_SEIVICE_NAME = "xmpp_service_name";// 服务名
	public static final String IS_AUTOLOGIN = "isAutoLogin";// 是否自动登录
	public static final String IS_NOVISIBLE = "isNovisible";// 是否隐身
	public static final String IS_REMEMBER = "isRemember";// 是否记住账户密码
	public static final String IS_FIRSTSTART = "isFirstStart";// 是否首次启动
	/**
	 * 登录提示
	 */
	public static final int LOGIN_SECCESS = 0;// 成功
	public static final int HAS_NEW_VERSION = 1;// 发现新版本
	public static final int IS_NEW_VERSION = 2;// 当前版本为最新
	public static final int LOGIN_ERROR_ACCOUNT_PASS = 3;// 账号或者密码错误
	public static final int SERVER_UNAVAILABLE = 4;// 无法连接到服务器
	public static final int LOGIN_ERROR = 5;// 连接失败

	public static final String XMPP_CONNECTION_CLOSED = "xmpp_connection_closed";// 连接中断

	public static final String LOGIN = "login"; // 登录
	public static final String RELOGIN = "relogin"; // 重新登录

	/**
	 * 好友列表 组名
	 */
	public static final String ALL_FRIEND = "所有好友";// 所有好友
	public static final String NO_GROUP_FRIEND = "未分组好友";// 所有好友
	/**
	 * 系统消息
	 */
	public static final String ACTION_SYS_MSG = "action_sys_msg";// 消息类型关键字
	public static final String MSG_TYPE = "broadcast";// 消息类型关键字
	public static final String SYS_MSG = "sysMsg";// 系统消息关键字
	public static final String SYS_MSG_DIS = "系统消息";// 系统消息
	public static final String ADD_FRIEND_QEQUEST = "好友请求";// 系统消息关键字
	/**
	 * 请求某个操作返回的状态值
	 */
	public static final int SUCCESS = 0;// 存在
	public static final int FAIL = 1;// 不存在
	public static final int UNKNOWERROR = 2;// 出现莫名的错误.
	public static final int NETWORKERROR = 3;// 网络错误
	/***
	 * 企业通讯录根据用户ｉｄ和用户名去查找人员中的请求ｘｍｌ是否包含自组织
	 */
	public static final int containsZz = 0;
	/***
	 * 创建请求分组联系人列表xml分页参数
	 */
	public static final String currentpage = "1";// 当前第几页
	public static final String pagesize = "1000";// 当前页的条数

	/***
	 * 创建请求xml操作类型
	 */
	public static final String add = "00";// 增加
	public static final String rename = "01";// 增加
	public static final String remove = "02";// 增加

	/**
	 * 重连接
	 */
	/**
	 * 重连接状态acttion
	 * 
	 */
	public static final String ACTION_RECONNECT_STATE = "action_reconnect_state";
	/**
	 * 描述冲连接状态的关机子，寄放的intent的关键字
	 */
	public static final String RECONNECT_STATE = "reconnect_state";
	/**
	 * 描述冲连接，
	 */
	public static final boolean RECONNECT_STATE_SUCCESS = true;
	public static final boolean RECONNECT_STATE_FAIL = false;
	/**
	 * 是否在线的SharedPreferences名称
	 */
	public static final String PREFENCE_USER_STATE = "prefence_user_state";
	public static final String IS_ONLINE = "is_online";
	/**
	 * 精确到毫秒
	 */
	public static final String MS_FORMART = "yyyy-MM-dd HH:mm:ss SSS";
	
	/*聊天功能*/
	
	public static final int XMPP_PORT = 5222;
	
	/**
	 * 登录状态广播
	 */
	public static final String ACTION_IS_LOGIN_SUCCESS = "com.android.qq.is_login_success";
	/**
	 * 消息记录操作广播
	 */
	public static final String ACTION_MSG_OPER= "com.android.qq.msgoper";
	/**
	 * 添加好友请求广播
	 */
	public static final String ACTION_ADDFRIEND= "com.android.qq.addfriend";
	/**
	 * 新消息广播
	 */
	public static final String ACTION_NEW_MSG= "com.android.qq.newmsg";
	/**
	 *好友在线状态更新广播
	 */
	public static final String ACTION_FRIENDS_ONLINE_STATUS_CHANGE= "com.android.qq.friends_online_status_change";
	
	//静态地图API
	public static  final String LOCATION_URL_S = "http://api.map.baidu.com/staticimage?width=320&height=240&zoom=17&center=";
	public static  final String LOCATION_URL_L = "http://api.map.baidu.com/staticimage?width=480&height=800&zoom=17&center=";
	
	public static final String MSG_TYPE_TEXT="msg_type_text";//文本消息
	public static final String MSG_TYPE_IMG="msg_type_img";//图片
	public static final String MSG_TYPE_VOICE="msg_type_voice";//语音
	public static final String MSG_TYPE_LOCATION="msg_type_location";//位置
	
	public static final String MSG_TYPE_ADD_FRIEND="msg_type_add_friend";//添加好友
	public static final String MSG_TYPE_ADD_FRIEND_SUCCESS="msg_type_add_friend_success";//同意添加好友
	
	public static final String SPLIT="卍";
	
	public static final int NOTIFY_ID=0x90;
	//环信服务器开始
	public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
	public static final String GROUP_USERNAME = "item_groups";
	public static final String CHAT_ROOM = "item_chatroom";
	public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";
	public static final String MESSAGE_ATTR_IS_VIDEO_CALL = "is_video_call";
	public static final String ACCOUNT_REMOVED = "account_removed";
	public static final String CHAT_ROBOT = "item_robots";
	public static final String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";
	
	//环信服务器结束
	/**
	 * 是否开启声音
	 */
	public static final String MSG_IS_VOICE = "msg_is_voice";
	/**
	 * 是否开启振动
	 */
	public static final String MSG_IS_VIBRATE = "msg_is_vibrate";
}
