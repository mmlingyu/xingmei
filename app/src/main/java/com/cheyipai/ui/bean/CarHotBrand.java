package com.cheyipai.ui.bean;

import com.cheyipai.corec.base.api.ResponseData;

import java.util.List;

/**
 * Created by Administrator on 2016/8/19.
 */
public class CarHotBrand extends ResponseData {
    private List<String> Data;

    public List<String> getData() {
        return Data;
    }

    public void setData(List<String> data) {
        Data = data;
    }
}
