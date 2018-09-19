/**
 *
 */
package com.cheyipai.ui.bean;

/**
 * @ClassName: FlagBase
 * @Description: 公共常量
 * @author: SHAOS
 * @date: 2016-2-26 上午9:47:34
 */
public class FlagBase {

	public static final int NEED_CONFIRM_CARS_FLAG = 10010;// 待确认车辆
	public static final int ORDERS_ALL_CARS_FLAG = 10020;// 全部
	public static final int ORDERS_WAIT_PAY_FLAG = 10021;// 待付款
	public static final int ORDERS_PAID_FLAG = 10022;// 已付款
	public static final int ORDERS_COMPLETE_CARS_FLAG = 10023;// 已验车
	public static final int ORDERS_FTRAD_SUCCESS_LAG = 10024;// 交易成功
	public static final int ORDERS_TRAD_DOWN_FLAG = 10025;// 交易关闭
	public static final int HISTORY_BIDDING_CARS_FLAG = 10030;// 历史竞价车辆

	public static final int SIGN_LOG_CURR = 10050;// 近三个月签到日志
	public static final int SIGN_LOG_LAST = 10051;// 切换上一个月
	public static final int SIGN_LOG_NEXT = 10052;// 切换下一个月

	public static final int MEDIA_PHOTO = 10061;// 拍照标识
	public static final int MEDIA_SPHOTO = 10062;// 选照标识

	public static final int MEDIA_IMG_FRONT = 10071;// 正面照片
	public static final int MEDIA_IMG_OBVERSE = 10072;// 反面照片
	public static final int MEDIA_IMG_HAND = 10073;// 手持照片
	public static final int MEDIA_IMG_SERVICE = 10074;// 增值服务照片
	public static final int SCAN_CODE_FLAG = 10075;// 扫描返回标志
	public static final int MEDIA_IMG_CLIENTINFO_FRONT = 10076;// 客户资料正面照片
	public static final int MEDIA_IMG_CLIENTINFO_OBVERSE = 10077;// 客户资料反面照片

	public static final int SIGN_LOCATION = 10080;// 经营所在地
	public static final int SIGN_CAR_POINT = 10081;// 提车点
	public static final int SERVICE_NETWORK = 10082;// 服务网点

	public static final int HALL_SEARCH_CONDITION = 10091;// 交易大厅搜索

	public static final int SIGN_ATURE_REQUEST_CODE = 100; // 电子签名
	public static final int SIGN_ATURE_RESULT_CODE = 101;
	public static final int SALE_SLIP_REQUEST_CODE = 102; // 签购单
	public static final int SALE_SLIP_RESULT_CODE = 103;

	public static final int TRADING_HALL_FILTER_FLAG = 10091;
	public static final int TRADING_HALL_BRAND_FLAG = 10092;

	public static final int GATHER_ADD_CUSTOM_FLAG = 20001;// 添加定制
	public static final int GATHER_EDIT_CUSTOM_FLAG = 20002;// 编辑定制

	public static final int LOGIN_GATHER_ADD_FLAG = 20010;// 登录跳转聚添加页面
	public static final int LOGIN_GATHER_EDIT_FLAG = 20011;// 登录跳转聚编辑页面
	public static final int LOGIN_GATHER_DELE_FLAG = 20012;// 登录跳转聚删除页面
	public static final int LOGIN_GATHER_DETAL_FLAG = 20013;// 登录跳转聚详情页面
	public static final int LOGIN_GATHER_CARDETAIL_FLAG = 20014;// 登录状态下跳转车辆详情
	public static final int LOGIN_GATHER_MIAOSHA_FLAG = 20015;// 登录状态下跳转活动车辆列表
	public static final int LOGIN_GATHER_ZHAOXIANGSHI_FLAG = 20016;// 登录状态下跳找相似
	public static final int LOGIN_YIYANBAO_CAR_INPUT_FLAG = 20018;// 登录状态下跳车辆录入
	public static final int LOGIN_YIYANBAO_CAR_MANAGER = 20019;// 登录状态下跳车辆管理
	public static final int TAB_GROUP_GATHER_FLAG = 20020;// 主页跳转聚页面
	public static final int LOGIN_YIYIANBAO_BACK_FLAG = 20021;// 套餐页面返回到服务
	public static final int LOGIN_YIYIANBAO_INTO_CAR_INPUT_FLAG = 20022;// 套餐页面点击进入车辆录入页面
	public static final int TAB_HOME_POSITION_FLAG = 20023;// 跳转登录前首页TAB位置
	public static final int GATHER_JUMP_CAR_DETAIL = 20028;//从聚类跳转到车辆详情

	public static final int GETUI_lOGINING_TO_CAR_DETAILS = 104; // 个推push已登录时的从某页面到车辆详情
	public static final int GETUI_INAPP_TO_CAR_DETAILS = 105; // 个推push在app内未登录状态到车辆详情
	public static final int SIGN_STATE_ERRORS = -100; // 非签约状态之外的值

	public static final int GETUI_LOGINING_TO_4S_REPORT = 104;
	public static final int GETUI_INAPP_TO_4S_REPORT = 105;


	public static final int LOGIN_HOME_DETAL_FLAG = 20030;// 首页登录跳转检测报告页面
	public static final int LOGIN_HOME_HALL_FLAG = 20031;// 首页登录跳转交易大厅页面
	public static final int LOGIN_SERVICE_SEARCH_FLAG = 20032;// 服务登录跳转价格查询页面
	public static final int LOGIN_SERVICE_M_Q_FLAG = 20033;// 服务登录跳转维修记录查询页面
	public static final int LOGIN_SERVICE_M_Q_HISTORY_FLAG = 20038;// 服务登录跳转维修记录查询历史纪录页面
	public static final int SIGNED_STATED_DETAL_FLAG = 999;// 签约状态成功签了一种合同

	public static final int POLICY_TYPE_BEN = 1;// 出价方式本市
	public static final int POLICY_TYPE_WAI = 2;// 出价方式外迁
	public static final int POLICY_TYPE_WU = 3;// 无出价方式

	public static final int BID_TYPE_SECOND_KILL = 1;// 一口价出价
	public static final int BID_TYPE_AUCTION = 2;// 竞价模式出价

	public static final int FLAG_NAMEAUTHEN_PHOTO = 20032;// 实名认证标志
	public static final int FLAG_CREATE_VALUE_PHOTO = 20033;// 增值服务标志
	public static final int FLAG_CLIENT_INFO_PHOTO = 20034;// 客户资料录入图片标志

	public static final String ISINAPP = "isInApp"; // 是否启动了home页
	public static final String ISLOGIN = "loginlogin"; // 是否已登录
	public static final String CLIENTID = "clientid"; // 设备ID
	public static final int LOGIN_SPECIAL_ACTIVITIES_CARDETAIL_FLAG = 20017; // 从专场活动跳转车辆详情
	public static final int SHOU_HOU_INFO_STYTLE = 2;// waytag为3和4的售后信息类型
	public static final int WAY_TAG1 = 1;// waytag1
	public static final int WAY_TAG3 = 3;// waytag3
	public static final int WAY_TAG4 = 4;// waytag4
	public static final int LOGIN_SERVICE_ORDER_FLAG = 400;// 增值服务标志
	public static final int LOGIN_SERVICE_EPGD_FLAG = 401;// 增值服务标志
	public static final String HAS_NET = "501"; // 有网
	public static final String NO_NET = "502"; // 没有网络
	public static final String LOAD_ERRPR = "503"; // 没有网络
	public static final int BANKCAR_FLAG = 1000;// 绑定银行卡返回的用户所选的银行名字
	public static final int BIDSTATE_POLICY_NULL = -1;   //未承诺
	public static final int BIDSTATE_POLICY_LOCAL = 0;   //承诺了本市
	public static final int BIDSTATE_POLICY_NONLOCAL = 1;  //承诺了外迁
	public static final int NONLOCALTAG_BEN = 1;
	public static final int NONLOCALTAG_WAI = 2;
	public static final int NONLOCALTAG_BUXIANWU = 3;
	public static final int NONLOCALTAG_BUXIAN = 4;
	public static final int BIDTYPE_BID = 0;   //普通出价
	public static final int BIDTYPE_QUOTE = 1;   //智能报价
	public static final String CYP_CODE_SUCCESS = "10000";
	public static final int FOCUS_FOCUS = 0;  //请求关注车辆
	public static final int FOCUS_NOFOCUS = 1;  //请求取消关注车辆

	public static final int VIEW_TAG_CHENGNUO = 1;
	public static final int VIEW_TAG_CHUJIA = 2;
	public static final int VIEW_TAG_CHUJIAQUREN = 3;
	public static final int VIEW_TAG_ZHI = 4;
	public static final int VIEW_TAG_ZHIQUREN = 5;

	public static final int ORDER_PAY_SETTLEMENT_PAGE = 301; //订单已结算的收银台页面
	public static final int ORDER_PAY_NO_SETTLEMENT_PAGE = 302; //订单未结算的收银台页面
	public static final int HALL_ATTENTION_FLAG = 20040; //交易大厅查看关注车辆列表
	public static final int USE_BIND_CARD_PAY = 1;  //收银台页面选择使用银行卡支付
	public static final int USE_VOUCHER_PAY = 2;  //收银台页面选择使用代金币支付
	public static final int USE_POS_PAY = 11;   // 来源0：虚拟保证金 1：银行转账 2：POS刷卡 3：现金 4：支付宝 5：提现
	// 6:信息费7:保证金 8：自动支付 9:银盛 11:POS刷头
	public static final int USE_UNION_POS_PAY = 2;  //使用银联Pos机支付
	public static final int USE_QUICK_POS_PAY = 1;  //使用快钱Pos机支付




}
