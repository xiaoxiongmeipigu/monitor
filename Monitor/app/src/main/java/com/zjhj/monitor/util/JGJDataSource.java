package com.zjhj.monitor.util;


import com.zjhj.commom.result.MapiResourceResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brain on 2016/7/29.
 */
public class JGJDataSource {


    public static final String TYPE_XIANHUO = 0x01+"";
    public static final String TYPE_XIANDAN = 0x02+"";
    public static final String TYPE_HUODONG = 0x03+"";
    public static final String TYPE_PINTUAN = 0x04+"";
    public static final String TYPE_DINGZHI = 0x05+"";
    public static final String TYPE_TUIHUO = 0x06+"";
    public static final String TYPE_VIP = 0x07+"";
    public static final String TYPE_MORE = 0x08+"";

    public static List<MapiResourceResult> getNews(){
        List<MapiResourceResult> list = new ArrayList<>();
        list.add(new MapiResourceResult("0","积极参与 广泛宣传 长安分局助力文明城市建设"));
        list.add(new MapiResourceResult("0","袁花分局积极主动服务榨油小作坊建设"));
        list.add(new MapiResourceResult("0","海洲所发出辖区首张”三小一摊“食品生产证"));
        list.add(new MapiResourceResult("0","长安分局对辖区内4家食品生产企业进行抽查"));
        list.add(new MapiResourceResult("0","我局开展电线电缆生产企业专项监督检查"));
        list.add(new MapiResourceResult("0","海宁开展食品相关产品质量安全排雷”百日攻坚“"));
        return list;
    }

    public static List<MapiResourceResult> getAddrs(){
        List<MapiResourceResult> list = new ArrayList<>();
        list.add(new MapiResourceResult("0","硖石街道"));
        list.add(new MapiResourceResult("0","海洲街道"));
        list.add(new MapiResourceResult("0","海昌街道"));
        list.add(new MapiResourceResult("0","马桥街道"));
        list.add(new MapiResourceResult("0","袁花街道"));
        list.add(new MapiResourceResult("0","盐官街道"));
        return list;
    }


}
