package me.android.baseframe.net;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import me.android.baseframe.base.BaseFrame;
import me.android.baseframe.utils.IoUtils;
import me.android.baseframe.utils.LogUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by yuxiangxin on 2020/8/23
 * <p>
 * 描述: okhttp简单封装
 */
public class HttpUtil {

    private static final String TAG = "HttpUtil";
    private static final int MAX_CALL_SIZE = 30;

    private final OkHttpClient mOkHttpClient;
    private final ArrayList<CallHistory> mCallList;
    protected final static boolean DEBUG = BaseFrame.isDebug();

    public static HttpUtil get () {
        return InnerHolder.SINGLE_INSTANCE;
    }

    private static class InnerHolder {
        private static final HttpUtil SINGLE_INSTANCE = new HttpUtil();
    }

    public OkHttpClient getOkHttpClient () {
        return mOkHttpClient;
    }

    private HttpUtil () {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(12, TimeUnit.SECONDS)
                .build();
        mCallList = new ArrayList<>(MAX_CALL_SIZE);
    }

    private void putCall (CallHistory call) {
        mCallList.add(call);
        if (mCallList.size() > MAX_CALL_SIZE) {
            try {
                mCallList.remove(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void removeCall (CallHistory call) {
        try {
            mCallList.remove(call);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Call findCall (String id) {
        if (id == null)
            return null;
        for (int i = 0; i < mCallList.size(); i++) {
            CallHistory item = mCallList.get(i);
            if (id.equals(item.id)) {
                removeCall(item);
                return item.mCall;
            }
        }
        return null;
    }

    /**
     * 取消请求
     *
     * @param tag Call tag
     */
    public void cancelRequestByTag (String tag) {
        if (tag == null)
            return;
        for (int i = 0; i < mCallList.size(); i++) {
            CallHistory item = mCallList.get(i);
            if (tag.equals(item.tag)) {
                Call call = item.mCall;
                if (call != null && call.isExecuted()) {
                    call.cancel();
                }
                removeCall(item);
                i--;
            }
        }
    }

    /**
     * 取消请求
     *
     * @param id Call ID
     */
    public void cancelRequest (String id) {
        Call call = findCall(id);
        if (call != null && call.isExecuted()) {
            call.cancel();
        }
    }

    private void putCallHistory (Call call, String tag) {
        CallHistory callHistory = new CallHistory();
        callHistory.mCall = call;
        callHistory.tag = tag;
        putCall(callHistory);
    }

    private void checkMultiCall (RequestBean req) {
        if (req.getCallId() != null) {
            cancelRequest(req.getCallId());
            req.setCallId(null);
        }
    }

    private void printLog (RequestBean req) {
        if (DEBUG) {
            LogUtils.v(TAG, req.getMethodString() + ":" + req.toUrl());
        }
    }

    private void printLog (Response response, Exception e) {
        if (DEBUG) {
            if (response != null) {
                if (response.isSuccessful() && response.body() != null) {
                    LogUtils.e(TAG, "req :" + response.code());
                } else {
                    LogUtils.e(TAG, "HttpReqFailed :" + response.code());
                }
            } else {
                LogUtils.e(TAG, "HttpReqExcept ");
                if (e != null) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Request buildRequest (RequestBean req) {
        printLog(req);
        Request request = null;
        switch (req.method) {
            case GET: {
                request = new Request.Builder().url(req.toUrl()).get().build();
            }
            break;
            case POST: {
                FormBody.Builder paramsBuilder = new FormBody.Builder();
                for (String key : req.params.keySet()) {
                    paramsBuilder.add(key, String.valueOf(req.params.get(key)));
                }
                request = new Request.Builder().url(req.url).post(paramsBuilder.build()).build();
            }
            break;
            case DELETE: {
                request = new Request.Builder().url(req.toUrl()).delete().build();
            }
            break;
            case PUT: {
                request = new Request.Builder().url(req.toUrl()).put(new FormBody.Builder().build()).build();
            }
            break;
            default:
                throw new UnsupportedOperationException("Unsupported request type:" + req.getMethodString());
        }
        return request;
    }

    /**
     * AsynSend 异步请求
     */
    public void doHttp (RequestBean req, ICallResponse callback) {
        checkMultiCall(req);

        //测试数据
        try {
            Call call = mOkHttpClient.newCall(buildRequest(req));
            putCallHistory(call, req.getTag());
            call.enqueue(new Callback() {
                @Override
                public void onFailure (Call call, IOException e) {
                    printLog(null, e);
                    if (!isCanceled(call)) {
                        callback.onFailed(req, RespDataHandler.toHttpResp(e));
                    }
                }

                @Override
                public void onResponse (Call call, Response response) {
                    printLog(response, null);
                    if (!isCanceled(call)) {
                        callback.onSuccess(req, RespDataHandler.toHttpResp(req.isRespBeanType, response));
                    }
                }

                private boolean isCanceled (Call call) {
                    if (call.isCanceled()) {
                        callback.onFailed(req, handleCancel(req));
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailed(req, RespDataHandler.toHttpResp(e));
        }
    }


    /**
     * SyncSend 同步请求,!UI线程
     */
    public HttpRespBean doHttp (RequestBean req) {
        checkMultiCall(req);
        try {
            Call call = mOkHttpClient.newCall(buildRequest(req));
            putCallHistory(call, req.getTag());
            Response response = call.execute();
            if (call.isCanceled()) {
                return handleCancel(req);
            }
            printLog(response, null);
            return RespDataHandler.toHttpResp(req.isRespBeanType, response);
        } catch (Exception e) {
            e.printStackTrace();
            printLog(null, e);
            return RespDataHandler.toHttpResp(e);
        }
    }

    public void download (RequestBean req, String filePath, IDownloadListener listener) {
        checkMultiCall(req);
        listener.onDownloadStart(req);
        try {
            File checkFile = new File(filePath);
            if (!checkFile.getParentFile().exists()) {
                listener.onDownloadFailed(req, "下载目录未创建");
                return;
            }
            Request request = new Request.Builder()
                    .url(req.toUrl())
                    .addHeader("Connection", "close")
                    .build();
            Call call = mOkHttpClient.newCall(request);
            putCallHistory(call, req.getTag());
            call.enqueue(new Callback() {
                @Override
                public void onFailure (Call call, IOException e) {
                    e.printStackTrace();
                    LogUtils.i(TAG, "download failed");
                    listener.onDownloadFailed(req, RespDataHandler.toHttpResp(e).getMsg());
                }

                @Override
                public void onResponse (Call call, Response response) {
                    if (call.isCanceled()) {
                        listener.onDownloadFailed(req, handleCancel(req).getMsg());
                        return;
                    }
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    // 储存下载文件的目录
                    try {
                        is = response.body().byteStream();
                        long total = response.body().contentLength();
                        File tempFile = new File(filePath + ".temp");
                        if (tempFile.exists() && tempFile.length() > 0) {
                            tempFile.delete();
                        }
                        fos = new FileOutputStream(tempFile);
                        long sum = 0;
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            sum += len;
                            //int progress = (int) (sum * 1.0f / total * 100);
                            listener.onDownloadProgress(req, sum, total);
                            if (call.isCanceled()) {
                                listener.onDownloadFailed(req, handleCancel(req).getMsg());
                                return;
                            }
                        }
                        fos.flush();
                        File resultFile = new File(filePath);
                        tempFile.renameTo(resultFile);
                        if (resultFile.exists() && resultFile.length() > 0) {
                            listener.onDownloadSuccess(req, resultFile);
                        }
                        LogUtils.e(TAG, "download success");
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onDownloadFailed(req, RespDataHandler.toHttpResp(e).getMsg());
                    } finally {
                        IoUtils.ioClose(is, fos);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            listener.onDownloadFailed(req, RespDataHandler.toHttpResp(e).getMsg());
        }
    }


    private HttpRespBean handleCancel (RequestBean req) {
        return RespDataHandler.genCanceledResp();
    }

    public String appendUrl (String baseUrl, Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return baseUrl;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl).append("?");
        for (String key : map.keySet()) {
            sb.append(key).append("=").append(map.get(key)).append("&");
        }
        String reqUrl = sb.toString();
        if (reqUrl.endsWith("&")) {
            reqUrl = reqUrl.substring(0, reqUrl.length() - 1);
        }
        return reqUrl;
    }

    private class CallHistory {
        private Call mCall;
        private String id;
        private String tag;

        private CallHistory () {
            id = UUID.randomUUID().toString();
        }

        @Override
        public boolean equals (Object o) {
            if (this == o)
                return true;
            if (!(o instanceof CallHistory))
                return false;
            CallHistory that = (CallHistory) o;
            if (id != null ? !id.equals(that.id) : that.id != null)
                return false;
            return tag != null ? tag.equals(that.tag) : that.tag == null;
        }

        @Override
        public int hashCode () {
            return id != null ? id.hashCode() : 0;
        }

        @Override
        public String toString () {
            return " " + tag + ":" + id.substring(0, 4) + " ";
        }
    }
}
