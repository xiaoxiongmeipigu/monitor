package com.zjhj.commom.api;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjhj.commom.result.MapiFeedResult;
import com.zjhj.commom.result.MapiItemResult;
import com.zjhj.commom.result.MapiResourceResult;
import com.zjhj.commom.util.DebugLog;
import com.zjhj.commom.util.MapiUtil;
import com.zjhj.commom.util.RequestCallback;
import com.zjhj.commom.util.RequestExceptionCallback;
import com.zjhj.commom.util.RequestPageCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brain on 2017/5/31.
 */
public class ItemApi extends BasicApi{

    /**
     * 首页接口
     * @param activity
     * @param callback
     * @param exceptionCallback
     */
    public static void defaultindex(Activity activity,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        MapiUtil.getInstance().call(activity,defaultindex,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }


    /**
     * 常用链接列表
     * @param activity
     * @param page
     * @param limit
     * @param callback
     * @param exceptionCallback
     */
    public static void linklist(Activity activity, String page, String limit,
                                final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){

        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("limit",limit);
        MapiUtil.getInstance().call(activity,linklist,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiResourceResult> result = JSONArray.parseArray(json.getJSONObject("data").getJSONArray("list").toJSONString(),MapiResourceResult.class);
                Integer count = json.getJSONObject("data").getInteger("page_count");
                if(null!=count){
                    callback.success(count,result);
                }

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }


    /**
     * 商户列表
     * @param activity
     * @param page
     * @param limit
     * @param keyword
     * @param street_id
     * @param callback
     * @param exceptionCallback
     */
    public static void merchantlist(Activity activity, String page, String limit,String keyword,String street_id,
                                final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("limit",limit);
        if(!TextUtils.isEmpty(keyword))
            params.put("keyword",keyword);
        if(!TextUtils.isEmpty(street_id))
            params.put("street_id",street_id);
        MapiUtil.getInstance().call(activity,merchantlist,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiItemResult> result = JSONArray.parseArray(json.getJSONObject("data").getJSONArray("list").toJSONString(),MapiItemResult.class);
                Integer count = json.getJSONObject("data").getInteger("page_count");
                if(null!=count){
                    callback.success(count,result);
                }

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 商户详情
     * @param activity
     * @param merchant_id
     * @param callback
     * @param exceptionCallback
     */
    public static void merchantdetail(Activity activity,String merchant_id,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("merchant_id",merchant_id);
        MapiUtil.getInstance().call(activity,merchantdetail,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 摄像头列表
     * @param activity
     * @param merchant_id
     * @param callback
     * @param exceptionCallback
     */
    public static void merchantcamera(Activity activity,String merchant_id,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("merchant_id",merchant_id);
        MapiUtil.getInstance().call(activity,merchantcamera,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiItemResult> result = JSONArray.parseArray(json.getJSONArray("data").toJSONString(),MapiItemResult.class);
                callback.success(result);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }


    /**
     * 新闻列表
     * @param activity
     * @param page
     * @param limit
     * @param cat_id
     * @param callback
     * @param exceptionCallback
     */
    public static void postlist(Activity activity, String page, String limit,String cat_id,
                                    final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("limit",limit);
        params.put("cat_id",cat_id);

        MapiUtil.getInstance().call(activity,postlist,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiItemResult> result = JSONArray.parseArray(json.getJSONObject("data").getJSONArray("list").toJSONString(),MapiItemResult.class);
                Integer count = json.getJSONObject("data").getInteger("page_count");
                if(null!=count){
                    callback.success(count,result);
                }

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });

    }

    /**
     * 提交反馈信息
     * @param activity
     * @param merchant_id
     * @param title
     * @param content
     * @param name
     * @param tel
     * @param callback
     * @param exceptionCallback
     */
    public static void merchantfeedback(Activity activity,String merchant_id,String title,String content,String name,
                                        String tel,final RequestCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("merchant_id",merchant_id);
        params.put("title",title);
        params.put("content",content);
        params.put("name",name);
        params.put("tel",tel);
        MapiUtil.getInstance().call(activity,merchantfeedback,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                callback.success(json);
            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

    /**
     * 信息反馈
     * @param activity
     * @param merchant_id
     * @param page
     * @param limit
     * @param callback
     * @param exceptionCallback
     */
    public static void merchantfeedbacklist(Activity activity,String merchant_id,String page,String limit,final RequestPageCallback callback, final RequestExceptionCallback exceptionCallback){
        Map<String,String> params = new HashMap<>();
        params.put("merchant_id",merchant_id);
        params.put("page",page);
        params.put("limit",limit);
        MapiUtil.getInstance().call(activity,merchantfeedbacklist,params,new MapiUtil.MapiSuccessResponse(){
            @Override
            public void success(JSONObject json) {
                DebugLog.i("json="+json);
                List<MapiFeedResult> result = JSONArray.parseArray(json.getJSONObject("data").getJSONArray("list").toJSONString(),MapiFeedResult.class);
                Integer count = json.getJSONObject("data").getInteger("page_count");
                if(null!=count){
                    callback.success(count,result);
                }

            }
        },new MapiUtil.MapiFailResponse(){
            @Override
            public void fail(Integer code, String failMessage) {
                exceptionCallback.error(code,failMessage);
            }
        });
    }

}
