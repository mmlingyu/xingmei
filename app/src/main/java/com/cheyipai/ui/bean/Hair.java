package com.cheyipai.ui.bean;

/**
 * Created by gjt on 2016/7/18.
 */
public class Hair {

    private String name;
    private String tags;//  |分割的数组
    private int sex;
    private int zan;
    private String faceTypes;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getFaceTypes() {
        return faceTypes;
    }

    public void setFaceTypes(String faceTypes) {
        this.faceTypes = faceTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

}
