package com.cheyipai.ui.bean;

import java.util.List;

/**
 * @author shaoshuai.
 * @PackageName com.cheyipai.ui.servicehall.activitys.countcar.bean
 * @date 2016-08-22 11:15
 * @description 城市区域实体Bean
 */
public class CityResionBean {

    private int PositionID;
    private String First;
    private String FirstByte;//首字母
    private int ProvinceId;//省份ID
    private String ProvinceName;// 省份名称
    private String CityId;// 城市ID
    private String CityName;// 城市名称
    private List<Integer> ProvinceIdList;
    private String CantCode;//城市编码/省份编码
    private String CantName;//城市名称/省份名称

    public int getPositionID() {
        return PositionID;
    }

    public void setPositionID(int positionID) {
        PositionID = positionID;
    }

    public String getFirst() {
        return First;
    }

    public void setFirst(String first) {
        First = first;
    }

    public String getFirstByte() {
        return FirstByte;
    }

    public void setFirstByte(String firstByte) {
        FirstByte = firstByte;
    }

    public int getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(int provinceId) {
        ProvinceId = provinceId;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public List<Integer> getProvinceIdList() {
        return ProvinceIdList;
    }

    public void setProvinceIdList(List<Integer> provinceIdList) {
        ProvinceIdList = provinceIdList;
    }

    public String getCantCode() {
        return CantCode;
    }

    public void setCantCode(String cantCode) {
        CantCode = cantCode;
    }

    public String getCantName() {
        return CantName;
    }

    public void setCantName(String cantName) {
        CantName = cantName;
    }
}
