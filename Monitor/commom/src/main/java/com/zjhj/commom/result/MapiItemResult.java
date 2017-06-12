package com.zjhj.commom.result;

import java.util.List;

/**
 * Created by brain on 2017/5/25.
 */
public class MapiItemResult extends MapiBaseResult{

    private String date;
    private String title;
    private String desc;
    private String phone;
    private String addr;
    private String cover_pic;
    private String address;
    private String tel;
    private String latitude;
    private String longitude;
    private String post_url;

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    private MapiCameraResult param;

    public MapiCameraResult getParam() {
        return param;
    }

    public void setParam(MapiCameraResult param) {
        this.param = param;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    private List<MapiItemResult> list;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<MapiItemResult> getList() {
        return list;
    }

    public void setList(List<MapiItemResult> list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
