package me.android.baseframe.runable;

/**
 * Created by yuxiangxin on 2020/08/26
 * 描述: 执行通用回调
 */
public interface IExecute<V> {
    void onExecute (V v);
}