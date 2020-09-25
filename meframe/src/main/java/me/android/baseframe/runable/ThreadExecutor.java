package me.android.baseframe.runable;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by yuxiangxin on 2020/08/31
 * 描述: 线程池
 */
public class ThreadExecutor {

    private static final Executor SINGLE_THREAD_EXECUTOR = Executors.newScheduledThreadPool(2);

    public static void execute (Runnable runnable) {
        SINGLE_THREAD_EXECUTOR.execute(runnable);
    }
}
