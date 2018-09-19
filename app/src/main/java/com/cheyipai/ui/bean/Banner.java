package com.cheyipai.ui.bean;


import com.cheyipai.corec.base.api.ResponseData;

import java.util.List;

/**
 * Created by 景涛 on 2015/9/18.
 */
public class Banner extends ResponseData {
    private List<BannerInfo> data;

    public List<BannerInfo> getData() {
        return data;
    }

    public void setData(List<BannerInfo> data) {
        this.data = data;
    }
}
