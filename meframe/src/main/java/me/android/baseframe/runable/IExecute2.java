package me.android.baseframe.runable;

/**
 * Created by yuxiangxin on 2020/08/26
 * 描述: 执行通用回调
 */
public interface IExecute2<V, T> {
    void onExecute (V v, T t);
}
