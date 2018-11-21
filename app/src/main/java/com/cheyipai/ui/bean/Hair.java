package com.cheyipai.ui.bean;

import java.io.Serializable;

/**
 * Created by gjt on 2016/7/18.
 */
public class Hair implements Serializable {
    private int id;
    private String name;
    private String tags;//  |分割的数组
    private int sex;
    private int zan;
    private String faceTypes;
    private String modelPath;
    private String faceFile;

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public String getFaceFile() {
        return faceFile;
    }

    public void setFaceFile(String faceFile) {
        this.faceFile = faceFile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
