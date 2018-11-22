package com.cheyipai.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


/**
 * Created by gjt on 2016/7/18.
 */
public class Hair implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    private int id;
    private String name;
    private String tags;//  |分割的数组
    private int sex;
    private int zan;
    private String type;//lianxing
    private String faceTypes;
    private String modelPath;
    private String faceFile;
    private String face;//face ++
    private int beauty;

    public Hair(){

    }

    public int getBeauty() {
        return beauty;
    }

    public void setBeauty(int beauty) {
        this.beauty = beauty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

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

  /*  @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(tags);

        parcel.writeInt(sex);
        parcel.writeInt(zan);
        parcel.writeInt(type);
        parcel.writeString(faceTypes);
        parcel.writeString(modelPath);
        parcel.writeString(faceFile);
        parcel.writeString(face);
    }*/
}
