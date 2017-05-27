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
