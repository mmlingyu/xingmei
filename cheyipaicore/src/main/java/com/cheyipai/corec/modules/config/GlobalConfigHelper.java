package com.cheyipai.corec.modules.config;
/**
 * 全局信息配置助手
 * Created by yangqinghai on 2014/11/12.
 */
public class GlobalConfigHelper {

    private static final String TEST = "test";
    private static final String UID = "uid";
    private static final String LOGINTOKEN = "loginToken";
    private static final String EXPIREDDATE = "expiredDate";
    /**
     * 账户昵称
     */
    private static final String NICKNAME = "nickName";
    private static final String USERNAME = "tel";
    private static final String VERSION = "version_time";
    private static final String LOCATION_TIME = "location_time";
    private static final String YEAR = "year";
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_filename";
    /**
     * 选择的城市
     */
    private static final String CITY = "city";
    /**
     * 油价查询省份
     */
    private static final String PROVINCE = "province";


    /**
     * 城市所在省份简称
     */
    private static final String CITY_SHORT = "cityshort";

    /**
     * app 渠道号
     */
    private static final String CHANNEL = "channel";

    private static GlobalConfig mGlobalConfig = new GlobalConfig();

    private static GlobalConfigHelper mInstance;

    public static GlobalConfigHelper getInstance() {
        if (mInstance == null) {
            mInstance = new GlobalConfigHelper();
        }
        return mInstance;
    }

    /**
     * 保存测试配置
     *
     * @return
     */
    public String getTEST() {
        return mGlobalConfig.getString(TEST);
    }

    /**
     * 获取测试配置
     *
     * @return
     */
    public void putTEST() {
        mGlobalConfig.putString(TEST, "1223");
        mGlobalConfig.commit();
    }

    public void putYear(String year) {
        mGlobalConfig.putString(YEAR, year);
        mGlobalConfig.commit();
    }

    public String getYear() {
        return mGlobalConfig.getString(YEAR);
    }


    public void putCurrentAppVersionTime(String version) {
        mGlobalConfig.putString(VERSION, version);
        mGlobalConfig.commit();
    }

    public String getCurrentAppVersionTime() {
        return mGlobalConfig.getString(VERSION);
    }


    public void putLocationTime(String version) {
        mGlobalConfig.putString(LOCATION_TIME, version);
        mGlobalConfig.commit();
    }

    public String getLocationTime() {
        return mGlobalConfig.getString(LOCATION_TIME);
    }





    /**
     * 移除数据
     */
    public void removeUserInfo() {
        mGlobalConfig.remove(UID);
        mGlobalConfig.remove(LOGINTOKEN);
        mGlobalConfig.remove(USERNAME);
        mGlobalConfig.remove(EXPIREDDATE);
        mGlobalConfig.remove(NICKNAME);
        mGlobalConfig.commit();
    }

    /**
     * 保存城市
     *
     * @return
     */
    public String getCity(String defaultCity) {
        return mGlobalConfig.getString(CITY, defaultCity);
    }

    /**
     * 保存城市
     *
     * @return
     */
    public String getCity() {
        return mGlobalConfig.getString(CITY);
    }

    /**
     * 获取城市
     *
     * @return
     */
    public void putCity(String city) {
        mGlobalConfig.putString(CITY, city);
        mGlobalConfig.commit();
    }

    /**
     * 油价查询
     * 保存省份
     * @return
     */
    public String getOilProvince() {
        return mGlobalConfig.getString(PROVINCE);
    }

    /**
     * 油价查询
     * 获取省份
     * @return
     */
    public void putOilProvince(String province) {
        mGlobalConfig.putString(PROVINCE, province);
        mGlobalConfig.commit();
    }


    /**
     * 保存城市所在省份简称
     *
     * @return
     */
    public String getCityShort() {
        return mGlobalConfig.getString(CITY_SHORT);
    }

    /**
     * 保存城市所在省份简称
     *
     * @return
     */
    public void putCityShort(String cityShort) {
        mGlobalConfig.putString(CITY_SHORT, cityShort);
        mGlobalConfig.commit();
    }

    /**
     * 获取昵称
     *
     * @return
     */
    public String getNickName() {
        return mGlobalConfig.getString(NICKNAME);
    }

    /**
     * 保存昵称
     *
     * @return
     */
    public void setNickName(String nickName) {
        mGlobalConfig.putString(NICKNAME, nickName);
        mGlobalConfig.commit();
    }

    /**
     * 保存渠道号
     * @param channel 渠道号
     */
    public void setChannel(String channel) {
        mGlobalConfig.putString(CHANNEL, channel);
        mGlobalConfig.commit();
    }

    /**
     * 获取渠道号
     * @return 渠道号
     */
    public String getChannel() {
        return mGlobalConfig.getString(CHANNEL);
    }
}
