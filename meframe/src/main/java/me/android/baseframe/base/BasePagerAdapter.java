package me.android.baseframe.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import me.android.baseframe.utils.Utils;

/**
 * Created by yuxiangxin on 2020/09/08
 * 描述:  base viewpager Pager adapter
 */
public abstract class BasePagerAdapter<E> extends PagerAdapter {
    protected String TAG = getTAG();
    protected Context mContext;
    protected List<E> mDatas;


    public BasePagerAdapter (Context context, List<E> datas) {
        mContext = context;
        mDatas = new ArrayList<E>();
        setDatas(datas);
    }


    public void setDatas (List<E> datas) {
        mDatas.clear();
        if (!Utils.isEmpty(datas)) {
            mDatas.addAll(datas);
        }
    }

    public String getTAG () {
        return getClass().getSimpleName();
    }

    @Override
    public int getCount () {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public Object instantiateItem (ViewGroup container, int position) {
        BaseHolder<E> holder = onCreateViewHolder(container, position);
        E item = getItem(position);
        holder.bindView(item);
        container.addView(holder.itemView);
        return holder.itemView;
    }

    protected abstract BaseHolder<E> onCreateViewHolder (ViewGroup container, int position);

    @Override
    public void destroyItem (ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject (View view, Object object) {
        return view != null && view.equals(object);
    }

    //ext

    protected RequestManager mGlideManger;

    public RequestManager getGlideManger () {
        return mGlideManger;
    }

    public void setGlideManger (FragmentActivity activity) {
        mGlideManger = Glide.with(activity);
    }

    public void setGlideManger (Activity activity) {
        mGlideManger = Glide.with(activity);
    }


    public void setGlideManger (Context context) {
        mGlideManger = Glide.with(context);
    }

    public void pauseLoadImg () {
        mGlideManger.pauseRequests();
    }

    public void resumeLoadImg () {
        mGlideManger.resumeRequests();
    }

    public void setData (List<E> datas) {
        this.mDatas.clear();
        if (datas != null) {
            this.mDatas.addAll(datas);
        }
    }

    public void setData (E[] datas) {
        this.mDatas.clear();
        if (datas != null) {
            for (E t : datas) {
                this.mDatas.add(t);
            }
        }
    }

    public void addData (List<E> datas) {
        if (datas != null && datas.size() > 0) {
            this.mDatas.addAll(datas);
        }
    }

    public void addData (E[] datas) {
        if (datas != null && datas.length > 0) {
            for (E data : datas) {
                this.mDatas.add(data);
            }
        }
    }

    public void addData (E data) {
        if (data != null) {
            this.mDatas.add(data);
        }
    }

    public void addDataToFirst (E data) {
        if (data != null) {
            this.mDatas.add(0, data);
        }
    }

    public void addDataToFirst (List<E> list) {
        if (list != null) {
            this.mDatas.addAll(0, list);
        }
    }

    public void addData (int index, E data) {
        if (index >= 0 && index <= mDatas.size()) {
            if (data != null) {
                this.mDatas.add(index, data);
            }
        }
    }

    public void addData (int index, List<E> datas) {
        if (index >= 0 && index <= mDatas.size()) {
            if (datas != null && datas.size() > 0) {
                this.mDatas.addAll(index, datas);
            }
        }
    }

    public void removeData (E data) {
        if (data != null) {
            this.mDatas.remove(data);
        }
    }

    public E removeData (int index) {
        if (index >= 0 && index < mDatas.size()) {
            return this.mDatas.remove(index);
        }
        return null;
    }

    public void clearData () {
        this.mDatas.clear();
    }

    public List<E> getDatas () {
        return this.mDatas;
    }

    public int getDatasSize () {
        return this.mDatas.size();
    }

    public void modifyData (E data) {
        if (this.mDatas.contains(data)) {
            int index = this.mDatas.indexOf(data);
            if (index != -1) {
                this.mDatas.remove(index);
                this.mDatas.add(index, data);
            }
        }
    }

    public void modifyData (int position, E data) {
        if (position >= 0 && position < mDatas.size() && data != null) {
            //LogUtils.v(TAG, "修改数据:position--->" + position);
            this.mDatas.remove(position);
            this.mDatas.add(position, data);
        }
    }


    public E getLastItem () {
        return getItem(mDatas.size() - 1);
    }

    public E getItem (int position) {
        if (position >= 0 && position < this.mDatas.size()) {
            return this.mDatas.get(position);
        } else {
            return null;
        }
    }


}
