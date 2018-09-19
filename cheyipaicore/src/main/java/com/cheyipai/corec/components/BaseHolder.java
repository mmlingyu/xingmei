package com.cheyipai.corec.components;

/**
 * Created by 景涛 on 2015/12/8.
 */
public class BaseHolder {
    public BaseHolder(int resourceId, String info) {
        this.resourceId = resourceId;
        this.info = info;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private int resourceId;
   private String info;

}
