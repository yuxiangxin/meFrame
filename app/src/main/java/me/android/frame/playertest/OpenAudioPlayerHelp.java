package me.android.frame.playertest;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by yuxiangxin on 2020/09/28
 * 描述:打开音频播放器工具
 */
public class OpenAudioPlayerHelp {


    private static final String PLAYER_PKG = "com.ashd.vlcmusicplayer";

    /**
     * 在播放器中打开U盘
     */
    public static void openPath (Activity activity, UPanUtils.UPan uPan) {
        String openUPanScheme = String.format("ashdaudioplayer://?action=0&path=%s&name=%s", uPan.getPath(), uPan.getName());
        start(activity, openUPanScheme);
    }

    /**
     * 在播放器中打开历史记录
     */
    public static void openHistory (Activity activity) {
        start(activity, "ashdaudioplayer://?action=1");
    }

    private static void start (Activity activity, String scheme) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(scheme));
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(activity, "请先安装音乐播放器", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "打开失败!", Toast.LENGTH_SHORT).show();
        }
    }


}
