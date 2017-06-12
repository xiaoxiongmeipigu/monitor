package com.zjhj.commom.result;

/**
 * Created by brain on 2017/6/1.
 */
public class MapiCameraResult extends MapiBaseResult{

    private String index_code;
    private String camera_id;
    private String region_id;
    private String device_id;

    public String getCamera_id() {
        return camera_id;
    }

    public void setCamera_id(String camera_id) {
        this.camera_id = camera_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getIndex_code() {
        return index_code;
    }

    public void setIndex_code(String index_code) {
        this.index_code = index_code;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }
}
