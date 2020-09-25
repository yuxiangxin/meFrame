package me.android.baseframe.net;


import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import me.android.baseframe.base.BaseFrame;

/**
 * Created by yuxiangxin on 2020/08/23
 * 描述: API请求信息
 */
public class RequestBean implements Serializable {

    public String url;
    public LinkedHashMap<String, Object> params;
    public int action;
    public RequestMethod method = RequestMethod.GET;
    private String callId;
    private String tag;
    public boolean showLoading = false;
    boolean isRespBeanType = true;

    public enum RequestMethod {
        GET, POST, DELETE, PUT
    }

    public RequestBean (String url) {
        this.url = url;
        addBaseParams();
    }

    public RequestBean setUrl (String url) {
        this.url = url;
        return this;
    }

    public String getTag () {
        return tag;
    }

    public RequestBean setTag (String tag) {
        this.tag = tag;
        return this;
    }

    public RequestBean setRespBeanType (boolean respBeanType) {
        isRespBeanType = respBeanType;
        return this;
    }

    public String getCallId () {
        return callId;
    }

    public RequestBean setCallId (String callId) {
        this.callId = callId;
        return this;
    }

    public RequestBean setAction (int action) {
        this.action = action;
        return this;
    }

    public RequestBean setMethod (RequestMethod method) {
        this.method = method;
        return this;
    }

    public RequestBean setShowLoading (boolean showLoading) {
        this.showLoading = showLoading;
        return this;
    }

    public boolean containsParamsKey (String paramsKey) {
        return params != null && params.containsKey(paramsKey);
    }

    public RequestBean addParams (Map<String, Object> addParams) {
        checkInitPasams();
        params.putAll(addParams);
        return this;
    }

    public RequestBean addBaseParams () {
        return addParams("version", BaseFrame.getVersionName()).
                addParams("versionCode", BaseFrame.getVersionCode());
    }

    private void checkInitPasams () {
        if (params == null) {
            params = new LinkedHashMap<>();
        }
    }

    public RequestBean addParams (String key, Object value) {
        checkInitPasams();
        if (value == null) {
            params.remove(key);
        } else {
            params.put(key, value);
        }
        return this;
    }

    public RequestBean removeParams (String key) {
        checkInitPasams();
        params.remove(key);
        return this;
    }

    public <T> T getParams (String key) {
        if (params == null || params.isEmpty())
            return null;
        if (params.containsKey(key)) {
            return (T) params.get(key);
        }
        return null;
    }

    public String toUrl () {
        return HttpUtil.get().appendUrl(url, params);
    }

    public String getMethodString () {
        return method.toString();
    }


}
