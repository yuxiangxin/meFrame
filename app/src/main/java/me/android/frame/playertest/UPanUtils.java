package me.android.frame.playertest;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuxiangxin on 2020/09/28
 * 描述: U盘读取
 */
public class UPanUtils {

    /**
     * 获取u盘/tf卡路径
     */
    public static List<UPan> loadUPan (Context context) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                ArrayList<UPan> uPans = new ArrayList<>();
                StorageManager mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
                List<StorageVolume> storageVolumes = null;
                storageVolumes = mStorageManager.getStorageVolumes();
                Class volumeInfoClazz = null;
                volumeInfoClazz = Class.forName("android.os.storage.StorageVolume");

                Field nameField = volumeInfoClazz.getDeclaredField("mDescription");
                Field fileField = volumeInfoClazz.getDeclaredField("mPath");

                for (StorageVolume storageVolume : storageVolumes) {
                    if (Environment.MEDIA_MOUNTED.equals(storageVolume.getState())) {
                        nameField.setAccessible(true);
                        fileField.setAccessible(true);
                        String fsLabelString = (String) nameField.get(storageVolume);//U盘名称
                        File file = (File) fileField.get(storageVolume);//U盘路径
                        if (file != null && file.canRead() && !file.equals(Environment.getExternalStorageDirectory())) {
                            uPans.add(new UPan(file.getPath(), getNotNullValue(fsLabelString, "U盘")));
                        }
                    }
                }
                return uPans;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class UPan {
        private String name;
        private String path;

        private UPan (String path, String name) {
            this.path = path;
            this.name = name;
        }

        public String getName () {
            return name;
        }

        public String getPath () {
            return path;
        }
    }

    private static <T> T getNotNullValue (T... value) {
        if (value == null || value.length == 0)
            return null;
        for (T t : value) {
            if (t != null) {
                return t;
            }
        }
        return null;
    }
}
