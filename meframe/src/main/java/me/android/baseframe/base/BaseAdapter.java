package me.android.baseframe.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import me.android.baseframe.utils.LogUtils;

/**
 * Created by 余香鑫 on 16/8/16.
 *
 * @Desc RecyclerView.Adapter的BaseAdapter
 */

public abstract class BaseAdapter<E> extends RecyclerView.Adapter<BaseHolder<E>> {

    protected final String TAG = getTAG();
    protected ArrayList<E> mDatas = new ArrayList<>();
    protected Context mContext;
    protected RequestManager mRequestManager;

    public void setRequestManager (RequestManager requestManager) {
        mRequestManager = requestManager;
    }

    public BaseAdapter (Context context, List<E> datas) {
        this.mContext = context;
        setData(datas);
    }

    public BaseAdapter (Context context, E[] datas) {
        this.mContext = context;
        setData(datas);
    }

    public void reSetAndNotifyDatas (List<? extends E> datas) {
        int oriSize = mDatas.size();
        this.mDatas.clear();
        notifyItemRangeRemoved(0, oriSize);
        if (datas != null && datas.size() > 0) {
            this.mDatas.addAll(datas);
        }
        this.notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder (@Nullable BaseHolder<E> holder, int position) {
        E item = getItem(position);
        if (item == null || holder == null)
            return;
        holder.bindView(item);
        if (holder.onNeedBindClickEvent()) {
            holder.itemView.setOnClickListener(v -> {
                E item2 = getItem(holder.getAdapterPosition());
                if (item2 != null) {
                    holder.onClick(v, item2);
                }
            });
        } else if (mOnItemClick != null) {
            holder.itemView.setOnClickListener(v -> mOnItemClick.onItemClick(v, position));
        }
    }

    public void setData (List<? extends E> datas) {
        this.mDatas.clear();
        if (datas != null) {
            this.mDatas.addAll(datas);
        }
    }

    public void setData (E[] datas) {
        this.mDatas.clear();
        if (datas != null) {
            this.mDatas.addAll(Arrays.asList(datas));
        }
    }

    public void addData (List<? extends E> datas) {
        if (datas != null && datas.size() > 0) {
            this.mDatas.addAll(datas);
        }
    }

    public void addData (E[] datas) {
        if (datas != null && datas.length > 0) {
            this.mDatas.addAll(Arrays.asList(datas));
        }
    }

    public boolean addData (E data) {
        if (data != null) {
            this.mDatas.add(data);
            return true;
        }
        return false;
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
            boolean remove = this.mDatas.remove(data);
            LogUtils.v(TAG, "移除:" + remove);
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


    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public int getItemCount () {
        return this.mDatas.size();
    }

    public boolean setTextWithVisible (TextView view, String content) {
        if (TextUtils.isEmpty(content)) {
            view.setVisibility(View.GONE);
            return true;
        } else {
            view.setVisibility(View.VISIBLE);
            view.setText(content);
            return false;
        }
    }


    public void releaseData () {
        mContext = null;
        if (mDatas != null) {
            mDatas.clear();
        }
    }

    public String getTAG () {
        return getClass().getSimpleName();
    }

    protected OnItemClickListen mOnItemClick;


    public interface OnItemClickListen {
        void onItemClick (View itemView, int position);
    }

    public OnItemClickListen getOnItemClick () {
        return mOnItemClick;
    }

    public void setOnItemClick (OnItemClickListen onItemClick) {
        mOnItemClick = onItemClick;
    }


}
