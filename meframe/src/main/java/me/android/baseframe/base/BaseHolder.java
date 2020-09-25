package me.android.baseframe.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yuxiangxin on 2020/08/23
 * 描述:Holder
 */
public abstract class BaseHolder<E> extends RecyclerView.ViewHolder {

    private boolean mBindClickEvent;


    public void setContext (Context context) {
        mmContext = context;
    }

    protected Context mmContext;

    public BaseHolder (ViewGroup parent) {
        super(parent);
        initView(itemView);
    }

    public BaseHolder (View view) {
        super(view);
        initView(view);
        view.setFocusable(true);
    }

    public BaseHolder (Context context, View view) {
        this(view);
        this.mmContext = context;
    }

    public BaseHolder (Context context, ViewGroup parent, int layout) {
        this(context, parent, layout, false);
    }

    public BaseHolder (Context context, ViewGroup parent, int layout, boolean bindClickEvent) {
        this(LayoutInflater.from(context).inflate(layout, parent, false));
        this.mmContext = context;
        this.mBindClickEvent = bindClickEvent;

    }

    public <T extends View> T findViewById (@IdRes int id) {
        return itemView.findViewById(id);
    }

    public void initView (View view) {

    }

    public abstract void bindView (E item);


    public void onClick (View view, E item) {

    }

    public boolean onNeedBindClickEvent () {
        return mBindClickEvent;
    }

    public String getResString (Context context, int resId) {
        if (context == null)
            context = BaseFrame.getContext();
        return context.getResources().getString(resId);
    }


}
