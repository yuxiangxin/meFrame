package me.android.baseframe.base;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by yuxiangxin on 2020/09/08
 * 描述: ViewPager基本的Adapter
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragments = null;
    private List<String> mFragNames = null;
    private boolean keepItem = true;

    public ViewPagerAdapter (FragmentManager fm, Fragment[] frags, String[] mFragNames) {
        this(fm, new ArrayList<>(Arrays.asList(frags)), new ArrayList<>(Arrays.asList(mFragNames)));
    }

    public ViewPagerAdapter (FragmentManager fm, List<? extends Fragment> frags, List<String> mFragNames) {
        super(fm);
        this.mFragments = new ArrayList<>(frags.size());
        this.mFragments.addAll(frags);
        this.mFragNames = mFragNames;
    }

    public ArrayList<Fragment> getFragments () {
        return mFragments;
    }

    public List<String> getFragTags () {
        return mFragNames;
    }

    @Override
    public Fragment getItem (int position) {
        return mFragments != null ? mFragments.get(position) : null;
    }

    @Override
    public int getCount () {
        return mFragments != null ? mFragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle (int position) {
        return mFragNames != null ? mFragNames.get(position) : null;
    }

    @Override
    public void destroyItem (ViewGroup container, int position, Object object) {
        if (!keepItem()) {
            super.destroyItem(container, position, object);
        }
    }

    public void remove (int index) {
        if (index >= 0 && index < mFragments.size()) {
            mFragments.remove(index);
        }
    }

    protected boolean keepItem () {
        return keepItem;
    }

    public boolean isKeepItem () {
        return keepItem;
    }

    public void setKeepItem (boolean keepItem) {
        this.keepItem = keepItem;
    }
}