package me.android.baseframe.net;

/**
 * Created by yuxiangxin on 2020/08/23
 * 描述: HttpRequest回调
 */
public interface ICallResponse {

    void onSuccess (RequestBean req, HttpRespBean resp);

    void onFailed (RequestBean req, HttpRespBean resp);

}
