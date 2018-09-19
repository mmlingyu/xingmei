package com.cheyipai.ui.bean;



import com.cheyipai.corec.base.api.ResponseData;

import java.util.List;

/**
 * Created by Administrator on 2016/8/19.
 */
public class CarBasicBrand extends ResponseData {
    private List<Basic> Data;

    public List<Basic> getData() {
        return Data;
    }

    public void setData(List<Basic> data) {
        Data = data;
    }

    public class Basic{
        private String Name;
        private List<BasicItem> Data;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public List<BasicItem> getData() {
            return Data;
        }

        public void setData(List<BasicItem> data) {
            Data = data;
        }
    }

    public class BasicItem{
        private String DispMent;
        private String Gear;

        public String getDispMent() {
            return DispMent;
        }

        public void setDispMent(String dispMent) {
            DispMent = dispMent;
        }

        public String getGear() {
            return Gear;
        }

        public void setGear(String gear) {
            Gear = gear;
        }
    }
}
