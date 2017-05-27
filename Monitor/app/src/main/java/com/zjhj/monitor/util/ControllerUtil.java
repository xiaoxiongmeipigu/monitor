package com.zjhj.monitor.util;

import android.content.Intent;

import com.live.LiveActivity;
import com.zjhj.commom.application.AppContext;
import com.zjhj.monitor.activity.CodeActivity;
import com.zjhj.monitor.activity.MainActivity;
import com.zjhj.monitor.activity.link.LinkActivity;
import com.zjhj.monitor.activity.news.NewsActivity;
import com.zjhj.monitor.activity.shops.FeedbackActivity;
import com.zjhj.monitor.activity.shops.ShopDescActivity;
import com.zjhj.monitor.activity.shops.ShopDetailActivity;
import com.zjhj.monitor.activity.shops.ShopsActivity;
import com.zjhj.monitor.activity.webview.WebviewControlActivity;


/**
 * Created by brain on 2016/6/22.
 */
public class ControllerUtil {


    /**
     * 首页
     */
    public static void go2Main() {
        Intent intent = new Intent(AppContext.getInstance(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }


    /**
     * h5页面
     */
    public static void go2WebView(String url, String title, String shareTitle, String shareContext, String shareLOGO, boolean isShare) {
        Intent intent = new Intent(AppContext.getInstance(), WebviewControlActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("isShare", isShare);
        intent.putExtra("shareTitle", shareTitle);

        intent.putExtra("shareContext", shareContext);
        intent.putExtra("shareLOGO", shareLOGO);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 市监动态
     */
    public static void go2News() {
        Intent intent = new Intent(AppContext.getInstance(), NewsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 常用链接
     */
    public static void go2Link() {
        Intent intent = new Intent(AppContext.getInstance(), LinkActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 商家列表
     */
    public static void go2Shops() {
        Intent intent = new Intent(AppContext.getInstance(), ShopsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 商家详情
     */
    public static void go2ShopDetail() {
        Intent intent = new Intent(AppContext.getInstance(), ShopDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 商家服务详情
     */
    public static void go2ShopDesc() {
        Intent intent = new Intent(AppContext.getInstance(), ShopDescActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 反馈
     */
    public static void go2Feedback() {
        Intent intent = new Intent(AppContext.getInstance(), FeedbackActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 二维码
     */
    public static void go2Code() {
        Intent intent = new Intent(AppContext.getInstance(), CodeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }

    /**
     * 监控
     */
    public static void go2Live() {
        Intent intent = new Intent(AppContext.getInstance(), LiveActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContext.getInstance().startActivity(intent);
    }


}