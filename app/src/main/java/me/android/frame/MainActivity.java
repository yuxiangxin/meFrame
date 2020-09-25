package me.android.frame;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.android.baseframe.debug.DebugUtils;
import me.android.baseframe.utils.LogUtils;
import me.android.baseframe.utils.Utils;
import me.android.baseframe.widget.focus.FocusBorder;
import me.android.baseframe.widget.focus.MFocusBolder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FocusBorder.OnFocusCallback {


    private static final String TAG = "MainActivity";
    private View mMe_progress;
    private FocusBorder mBuild;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.to_dev).setOnClickListener(this);

        mBuild = MFocusBolder.create().build(this);
        mBuild.boundGlobalFocusListener(this);


        mMe_progress = findViewById(R.id.progress);


    }

    private void TestUtils () {
        boolean in = Utils.isIn(View.FOCUS_DOWN, 1, 2, View.FOCUS_DOWN, 2, 3);
        LogUtils.v(TAG, "1 :" + in);
    }


    @Override
    public void onClick (View v) {
        DebugUtils.startDevelopment(this);
    }

    @Nullable
    @Override
    public FocusBorder.Options onFocus (View oldFocus, View newFocus) {
        if (newFocus == mMe_progress) {
            mBuild.setVisible(false, false);
            return null;
        } else {
            return FocusBorder.OptionsFactory.get();
        }
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
