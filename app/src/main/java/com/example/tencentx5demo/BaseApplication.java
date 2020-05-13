package com.example.tencentx5demo;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

public class BaseApplication extends Application {

    private final static String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化 TBS X5
        initTencentX5();
    }

    private void initTencentX5() {
        //非wifi情况下，主动下载x5内核
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                Log.i(TAG, "x5 內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核：" + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                Log.i(TAG, "x5 內核初始化 onCoreInitFinished");
            }
        };
        // 监听内核的下载
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                // 内核下载完成回调
                Log.d(TAG, "内核下载完成" );
            }

            @Override
            public void onInstallFinish(int i) {
                // 内核安装完成回调，
                Log.d(TAG, "内核安装完成" );
            }

            @Override
            public void onDownloadProgress(int i) {
                // 下载进度监听  百分比 ： i%
                Log.d(TAG, "内核下载进度:" + i);
            }
        });

        //  预加载X5内核
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}
