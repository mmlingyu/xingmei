package com.cheyipai.ui.bean;



import com.cheyipai.corec.base.api.ResponseData;

import java.util.List;

/**
 * Created by Administrator on 2016/8/19.
 */
public class CarHotWordBrand extends ResponseData {
    private List<HotWord> Data;

    public List<HotWord> getData() {
        return Data;
    }

    public void setData(List<HotWord> data) {
        Data = data;
    }

    public class HotWord{
        private String Brand;
        private String SeriesName;

        public String getBrand() {
            return Brand;
        }

        public void setBrand(String brand) {
            Brand = brand;
        }

        public String getSeriesName() {
            return SeriesName;
        }

        public void setSeriesName(String seriesName) {
            SeriesName = seriesName;
        }
    }
}
