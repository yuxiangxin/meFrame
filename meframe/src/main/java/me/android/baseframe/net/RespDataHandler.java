package me.android.baseframe.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import me.android.baseframe.utils.LogUtils;
import okhttp3.Response;

/**
 * Created by yuxiangxin on 2020/08/26
 * 描述: Http响应数据解析处理
 */
class RespDataHandler {

    /**
     * 连接正常响应数据解析
     */
    static HttpRespBean toHttpResp (boolean isRespBeanType, Response resp) {
        HttpRespBean httpRespBean = new HttpRespBean();

        if (resp != null && resp.isSuccessful() && resp.body() != null) {
            //连接正常
            try {
                //响应正常
                httpRespBean.respString = resp.body().string();
                if (HttpUtil.DEBUG) {
                    LogUtils.v("HttpUtil", "Resp: " + httpRespBean.respString);
                }
                if (isRespBeanType) {
                    //解析DATA数据
                    StringRespBean respBean = JSON.parseObject(httpRespBean.respString, StringRespBean.class);
                    if (respBean != null) {
                        //解析成功
                        if (respBean.isOk()) {
                            //OK
                            httpRespBean.code = HttpRespBean.STATE_OK;
                        } else {
                            //业务错误
                            httpRespBean.code = respBean.getCode();
                        }
                        httpRespBean.msg = respBean.getMsg();
                        httpRespBean.respString = respBean.getData();
                    } else {
                        //JSON解析出错
                        httpRespBean.code = HttpRespBean.STATE_PARSE_FAILED;
                        httpRespBean.msg = getErrorMsg(httpRespBean.code);
                    }
                } else {
                    //不自动解析DATA数据
                    httpRespBean.code = HttpRespBean.STATE_OK;
                    httpRespBean.msg = getErrorMsg(httpRespBean.code);
                }
            } catch (Exception e) {
                //数据处理异常
                e.printStackTrace();
                return toHttpResp(e);
            }
        } else {
            //连接异常
            httpRespBean.code = HttpRespBean.STATE_FAILED;
            httpRespBean.msg = getErrorMsg(httpRespBean.code);
        }
        return httpRespBean;
    }

    private static String getErrorMsg (String code) {
        if (code == null) {
            code = HttpRespBean.STATE_FAILED;
        }
        switch (code) {
            case HttpRespBean.STATE_OK:
                return "OK";
            case HttpRespBean.STATE_CANCELED:
                return "请求被重置!";
            case HttpRespBean.STATE_PARSE_FAILED:
                return "数据解析失败!";
            case HttpRespBean.STATE_ULR_ILLEGAL:
                return "请求链接无效!";
            case HttpRespBean.STATE_FAILED:
            default:
                return "网络连接不可用,请检查网络是否可用!";
        }
    }

    /**
     * 异常解析处理
     */
    static HttpRespBean toHttpResp (Exception ex) {
        //一般网络问题
        HttpRespBean httpResp = new HttpRespBean();
        httpResp.code = HttpRespBean.STATE_FAILED;
        httpResp.error = ex;
        if (ex instanceof JSONException) {
            //JSON解析一场
            httpResp.code = HttpRespBean.STATE_PARSE_FAILED;
            httpResp.msg = getErrorMsg(httpResp.code);
        } else if (ex instanceof UnknownHostException || ex instanceof SocketTimeoutException) {
            //网络问题
            httpResp.code = HttpRespBean.STATE_FAILED;
            httpResp.msg = getErrorMsg(httpResp.code);
        } else if (ex instanceof IllegalArgumentException) {
            String message = ex.getMessage();
            if (message != null && message.startsWith("unexpected url:")) {
                httpResp.code = HttpRespBean.STATE_ULR_ILLEGAL;
                httpResp.msg = getErrorMsg(httpResp.code);
            } else {
                httpResp.msg = getErrorMsg(httpResp.code);
            }
        } else {
            httpResp.msg = getErrorMsg(httpResp.code);
        }
        return httpResp;
    }

    /**
     * 请求取消
     */
    static HttpRespBean genCanceledResp () {
        HttpRespBean httpResp = new HttpRespBean();
        httpResp.code = HttpRespBean.STATE_CANCELED;
        httpResp.msg = getErrorMsg(httpResp.code);
        return httpResp;
    }
}
