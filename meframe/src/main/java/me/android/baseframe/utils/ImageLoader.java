package me.android.baseframe.utils;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import androidx.annotation.WorkerThread;
import me.android.baseframe.base.BaseFrame;

/**
 * Created by yuxiangxin on 2020/08/26
 * 描述: 图片加载Utils
 */
public class ImageLoader {

    private static final String TAG = "ImageLoader";

    @WorkerThread
    public static File download (String url) throws Exception {
        FutureTarget<File> submit = Glide.with(BaseFrame.getContext())
                .downloadOnly().load(toUri(url)).submit();
        return submit.get();
    }

    private static Uri toUri (String url) {
        if (TextUtils.isEmpty(url))
            return null;
        final Uri uri;
        File file = new File(url);
        if (file.exists()) {
            uri = Uri.fromFile(file);
        } else {
            uri = Uri.parse(url);
        }
        return uri;
    }

    public static void displayImage (ImageView view, String url) {
        if (view == null) {
            LogUtils.e(TAG, "加载图片参数为空");
            return;
        }
        displayImage(Glide.with(view), view, url);
    }

    public static void displayImage (RequestManager requestManager, ImageView view, Integer resId, boolean centerCrop) {
        if (view == null) {
            LogUtils.e(TAG, "加载图片参数为空");
            return;
        }
        RequestBuilder<Drawable> load = requestManager.load(resId);
        RequestOptions options = new RequestOptions();
        if (centerCrop) {
            options = options.centerCrop();
        }
        load.apply(options).into(view);
    }

    public static void displayImage (RequestManager requestManager, ImageView view, String url) {
        displayImage(requestManager, toUri(url), view, -1, -1, false);
    }

    public static void displayImage (RequestManager requestManager, Uri uri, ImageView target, int holder, int errorImgRes, boolean centerCrop) {
        if (requestManager == null || target == null || uri == null) {
            LogUtils.e(TAG, "加载图片参数为空");
            return;
        }
        if (holder != -1) {
            target.setImageResource(holder);
        }
        RequestBuilder<Drawable> load = requestManager.load(uri);
        RequestOptions options = new RequestOptions();
        if (centerCrop) {
            options = options.centerCrop();
        }
        if (errorImgRes != -1) {
            options = options.error(errorImgRes);
        }
        load.apply(options).into(target);
    }

}
