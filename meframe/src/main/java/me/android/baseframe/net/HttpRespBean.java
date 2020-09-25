package me.android.baseframe.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

import androidx.annotation.NonNull;


/**
 * Created by yuxiangxin on 2020/09/08
 * 描述:HttpResp
 */
public class HttpRespBean implements Serializable {

    public static final String STATE_OK = "0";
    public static final String STATE_CANCELED = "800";
    public static final String STATE_FAILED = "801";
    public static final String STATE_PARSE_FAILED = "802";
    public static final String STATE_ULR_ILLEGAL = "803";


    String code = STATE_FAILED;
    String msg;
    String respString;

    Exception error;
    Object parseData;

    public String getCode () {
        return code;
    }

    @JSONField(serialize = false)
    public boolean isOk () {
        return STATE_OK.equals(code);
    }

    @JSONField(serialize = false)
    public boolean isCanceled () {
        return STATE_CANCELED.equals(code);
    }

    @JSONField(serialize = false)
    public <T> T getParseData () {
        return (T) parseData;
    }

    public void setParseData (Object parseData) {
        this.parseData = parseData;
    }

    public String getRespString () {
        return respString;
    }

    public String getMsg () {
        return msg;
    }

    public Exception getError () {
        return error;
    }

    public void setParseFailed () {
        code = STATE_PARSE_FAILED;
    }

    public <T> T parseData (Class<T> clz) {
        if (!TextUtils.isEmpty(respString)) {
            T data = JSON.parseObject(respString, clz);
            setParseData(data);
            return data;
        }
        return null;
    }

    public <T extends RespBean> T parseDataFromData (Class<T> clz) {
        if (!TextUtils.isEmpty(respString)) {

            T data = JSON.parseObject(respString, clz);
            code = data.getCode();
            msg = data.getMsg();
            setParseData(data.getData());

            return getParseData();
        }
        return null;
    }


    public void parseArray (Class<?> clz) {
        if (!TextUtils.isEmpty(respString)) {
            setParseData(JSON.parseArray(respString, clz));
        }
    }

    public static HttpRespBean genTestObj (String json, String state, String msg) {
        HttpRespBean resp = new HttpRespBean();
        resp.respString = json;
        resp.code = state;
        resp.msg = msg;
        return resp;
    }

    @NonNull
    @Override
    @JSONField(serialize = false)
    public String toString () {
        return JSON.toJSONString(this);
    }
}
