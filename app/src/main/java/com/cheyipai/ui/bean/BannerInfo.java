package com.cheyipai.ui.bean;

/**
 * Created by 景涛 on 2015/9/18.
 */
public class BannerInfo {
    private int id;//id
    private String name;//标题
    private String imgUrl;//图片地址
    private String type;//1 : web 2;内部
    private String link;//链接地址
    private int navPosition;//导航位置，首页或其他页面1首页 2 查历史 3 看趋势
    private int orders;//排序
    private String desce;//描述
    private int state;//状态（1可用0停用）

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public void setNavPosition(int navPosition) {
        this.navPosition = navPosition;
    }

    public void setDesce(String desce) {
        this.desce = desce;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getNavPosition() {
        return navPosition;
    }

    public String getDesce() {
        return desce;
    }

    public int getState() {
        return state;
    }

}
