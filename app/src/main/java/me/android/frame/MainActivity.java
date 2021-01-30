package me.android.frame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.android.baseframe.debug.DebugUtils;
import me.android.baseframe.utils.LogUtils;
import me.android.baseframe.utils.Utils;
import me.android.baseframe.widget.focus.FocusBorder;
import me.android.baseframe.widget.focus.MFocusBolder;
import me.android.frame.playertest.OpenAudioPlayerHelp;
import me.android.frame.playertest.UPanUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FocusBorder.OnFocusCallback {


    private static final String TAG = "MainActivity";

    private FocusBorder mBuild;
    private List<UPanUtils.UPan> mUPans;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mUPans = UPanUtils.loadUPan(this);
        findViewById(R.id.to_dev).setOnClickListener(this);

        mBuild = MFocusBolder.create().build(this);
        mBuild.boundGlobalFocusListener(this);

        findViewById(R.id.to_history).setOnClickListener(this);
        findViewById(R.id.to_upan).setOnClickListener(this);




    }


    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.to_dev:
                DebugUtils.startDevelopment(this);
                break;
            case R.id.to_history:
                //OpenAudioPlayerHelp.openHistory(this);
                startActivity(new Intent(this, LauncherActivity.class));
                break;
            case R.id.to_upan:
                if (!Utils.isEmpty(mUPans)) {
                    OpenAudioPlayerHelp.openPath(this, mUPans.get(0));
                }
                break;
        }

    }

    @Nullable
    @Override
    public FocusBorder.Options onFocus (View oldFocus, View newFocus) {
        return FocusBorder.OptionsFactory.get();
    }

    public static void main (String[] a) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = -12; i <= 12; i++) {
            integers.add(i);
        }
        System.out.println("" + integers.size());
        System.out.println("" + integers);
    }
}
