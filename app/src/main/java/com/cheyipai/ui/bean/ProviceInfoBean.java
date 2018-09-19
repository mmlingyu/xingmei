package com.cheyipai.ui.bean;

import java.io.Serializable;

/**
 * @author shaoshuai.
 * @PackageName com.cheyipai.ui.servicehall.activitys.countcar.bean
 * @date 2016-08-22 14:22
 * @description
 */
public class ProviceInfoBean implements Serializable {

    /**
     * @fieldName: serialVersionUID
     * @fieldType: long
     * @Description: TODO
     */
    private static final long serialVersionUID = 1L;
    private int posID;
    private String name; // 显示的数据
    private String sortLetters; // 显示数据拼音的首字母
    private int mId;

    public int getPosID() {
        return posID;
    }

    public void setPosID(int posID) {
        this.posID = posID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }
}
