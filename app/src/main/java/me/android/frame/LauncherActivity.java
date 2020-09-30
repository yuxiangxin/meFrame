package me.android.frame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.Nullable;
import me.android.frame.playertest.OpenAudioPlayerHelp;
import me.android.frame.playertest.UPanUtils;

public class LauncherActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView () {
        findViewById(R.id.upan1).setOnClickListener(this);
        findViewById(R.id.upan2).setOnClickListener(this);
        findViewById(R.id.history).setOnClickListener(this);
        findViewById(R.id.player).setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.upan1:
                openUpan(0);
                break;
            case R.id.upan2:
                openUpan(1);
                break;
            case R.id.history:
                OpenAudioPlayerHelp.openHistory(this);
                break;
            case R.id.player:
                launcher();
                break;
        }
    }

    private void launcher () {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setPackage("com.ashd.vlcmusicplayer");
        intent.setClassName("com.ashd.vlcmusicplayer", "com.ashd.vlcmusicplayer.view.HomeActivity");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void openUpan (int index) {
        List<UPanUtils.UPan> uPans = UPanUtils.loadUPan(this);
        if (uPans != null && uPans.size() >= index + 1) {
            OpenAudioPlayerHelp.openPath(this, uPans.get(index));
        } else {
            Toast.makeText(this, "未检测到U盘", Toast.LENGTH_LONG).show();
        }
    }
}
