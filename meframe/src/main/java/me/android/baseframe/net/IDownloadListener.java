package me.android.baseframe.net;

import java.io.File;

/**
 * Created by yuxiangxin on 2020/08/31
 * 描述:下载监听
 */
public interface IDownloadListener {
    void onDownloadStart (RequestBean req);

    void onDownloadProgress (RequestBean req, long progress, long total);

    void onDownloadSuccess (RequestBean req, File result);

    void onDownloadFailed (RequestBean req, String msg);
}
