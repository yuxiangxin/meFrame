package me.android.frame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.android.baseframe.utils.IoUtils;

public class CopyProject {

    private static final ArrayList<String> ignoreName = new ArrayList<String>() {{
        add("build");
    }};
    private static final ArrayList<String> ignoreExt = new ArrayList<String>() {{
        add("apk");
    }};

    public static void main (String[] args) {

        HashMap<String, String> jobData = new HashMap<>();
        jobData.put("G:\\yuDev\\Project\\Launcher", "G:\\yuDev\\Project\\backup\\Launcher");
        jobData.put("G:\\yuDev\\Project\\VLCMusicPlayer", "G:\\yuDev\\Project\\backup\\VLCMusicPlayer");
        jobData.put("G:\\yuDev\\Project\\RockKeystone", "G:\\yuDev\\Project\\backup\\RockKeystone");


        CopyProject util = new CopyProject();
        for (Map.Entry<String, String> sse : jobData.entrySet()) {
            util.copy(sse.getKey(), sse.getValue());
        }
    }

    private void copy (String from, String to) {
        File fromFile = new File(from);

        ArrayList<File> fromFiles = new ArrayList<>();
        IoUtils.filterFileWithDir(fromFile, pathname -> {
            if (pathname.isHidden() || pathname.getName().startsWith(".")) {
                return false;
            }
            if (ignoreName.contains(pathname.getName())) {
                return false;
            }
            String extension = IoUtils.getExtension(pathname);
            if (ignoreExt.contains(extension)) {
                return false;
            }
            return true;
        }, fromFiles);

        File toFile = new File(to);
        if (toFile.exists()) {
            IoUtils.deleteFileOrDir(toFile);
        }
        toFile.mkdirs();

        System.out.println("fromFiles:" + fromFiles.size());
        System.out.println(toFile.exists());
        for (File file : fromFiles) {
            String newPath = file.getPath().replace(from, to);
            File newFile = new File(newPath);
            File parentFile = newFile.getParentFile();
            if (parentFile == null) {
                System.out.println("copy stop," + parentFile + " is NULL");
                return;
            }
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            try {
                IoUtils.copyFile(file, newFile);
                //System.out.println("copy from:" + file.getPath());
                //System.out.println("copy  to:" + newFile.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("copy done");

    }


}
