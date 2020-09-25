package me.android.baseframe.net;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by yuxiangxin on 2020/08/25
 * 描述:Api返回值
 */
public class RespBean<T> {
    private String code;
    private String msg;
    private T data;

    @JSONField(serialize = false)
    public boolean isOk () {
        return "0".equals(code);
    }

    public String getCode () {
        return code;
    }

    public void setCode (String code) {
        this.code = code;
    }

    public String getMsg () {
        return msg;
    }

    public void setMsg (String msg) {
        this.msg = msg;
    }

    public T getData () {
        return data;
    }

    public void setData (T data) {
        this.data = data;
    }
}
