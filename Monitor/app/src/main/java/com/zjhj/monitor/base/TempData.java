package com.zjhj.monitor.base;

import com.hikvision.vmsnetsdk.ServInfo;
import com.zjhj.commom.result.MapiUserResult;

/**
 * Created by brain on 2017/6/1.
 */
public final class TempData {

    private static TempData ins = new TempData();
    /**
     * 监控登录的临时数据
     */
    private MapiUserResult loginData;

    /**
     * 登录返回的数据
     */
    private ServInfo userData;

    public static void setIns(TempData ins) {
        TempData.ins = ins;
    }

    public ServInfo getUserData() {
        return userData;
    }

    public void setUserData(ServInfo userData) {
        this.userData = userData;
    }

    public static TempData getIns() {
        return ins;
    }

    public MapiUserResult getLoginData() {
        return loginData;
    }

    public void setLoginData(MapiUserResult loginData) {
        this.loginData = loginData;
    }
}
