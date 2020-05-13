package com.example.tencentx5demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.tencent.smtt.export.external.interfaces.PermissionRequest;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5DemoActivity extends AppCompatActivity {

    private final static String TAG = "X5DemoActivity";
    private final static String WEBVIEW_CORE_URL = "https://www.liulanmi.com/labs/core.html";
    private final static String SAFE_ENTRY_URL = "https://www.safeentry.gov.sg/logins/new_clicker_login";
    private RelativeLayout rlContainer;
    private WebView wvX5;
    private ProgressBar pbWvX5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x5_demo);

        rlContainer = (RelativeLayout) findViewById(R.id.rl_container);
        pbWvX5 = (ProgressBar) findViewById(R.id.pb_wv_x5);
        wvX5 = (WebView) findViewById(R.id.wv_x5);

        initX5WebViewSettings();
    }

    private void initX5WebViewSettings() {

        initWebSettings();

        initChromeClient();

        wvX5.loadUrl(WEBVIEW_CORE_URL);
    }

    private void initWebSettings() {
        WebSettings webSettings = wvX5.getSettings();
        // 开启 JS
        webSettings.setJavaScriptEnabled(true);
        // 开启 Dom
        webSettings.setDomStorageEnabled(true);
        // 将图片调整适合 WebView 大小（响应式）
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕大小
        webSettings.setLoadWithOverviewMode(true);
        // 支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        // 隐藏默认缩放控件
        webSettings.setDisplayZoomControls(false);
        // 支持重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 开启多窗口
        webSettings.supportMultipleWindows();
        // 不使用缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 是否可访问Content Provider的资源，默认值 true
        webSettings.setAllowContentAccess(true);
        // 允许访问文件
        webSettings.setAllowFileAccess(true);
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        webSettings.setAllowFileAccessFromFileURLs(true);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        // 允许通过 JS 打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 允许自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        // 允许定位
        webSettings.setGeolocationEnabled(true);
        // 设置默认编码格式
        webSettings.setDefaultTextEncodingName("UTF-8");
        // 不显示水平滚动条
        wvX5.setHorizontalScrollBarEnabled(false);
        // 不显示垂直滚动条
        wvX5.setVerticalScrollBarEnabled(false);
    }

    private void initChromeClient() {

        // 使用内置浏览器打开
        wvX5.setWebViewClient(new WebViewClient() {

            /**
             * 开始加载网页
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG,"onPageStarted");
            }

            /**
             * 网页加载完成
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG,"onPageFinished");
            }

            /**
             * 网页加载失败处理
             */
            @Override
            public void onReceivedError(WebView view, int errorCode, String desc, String url) {
                super.onReceivedError(view, errorCode, desc, url);
                Log.d(TAG,"onReceivedError");
            }

            /**
             * Https 处理
             */
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError sslError) {
                super.onReceivedSslError(view, handler, sslError);
                Log.d(TAG,"onReceivedError");
            }
        });

        wvX5.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d(TAG,"onProgressChanged");

                pbWvX5.setProgress(newProgress);
                if (newProgress == 100) {
                    pbWvX5.setVisibility(View.GONE);
                }
            }

            // Grant permissions for cam
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                Log.d(TAG, "onPermissionRequest");
                request.grant(request.getResources());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (wvX5 != null) {
            wvX5.resumeTimers();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wvX5 != null) {
            wvX5.onResume();
        }
    }

    @Override
    protected void onPause() {
        if (wvX5 != null) {
            wvX5.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (wvX5 != null) {
            wvX5.pauseTimers();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (wvX5 != null) {
            rlContainer.removeView(wvX5);
            wvX5.destroy();
            wvX5 = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (wvX5.canGoBack()) {
            wvX5.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果按下的是回退键且历史记录里确实还有页面
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wvX5.canGoBack()) {
            wvX5.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.refresh:
                wvX5.reload();
                break;
            case R.id.webview_core:
                wvX5.loadUrl(WEBVIEW_CORE_URL);
                break;
            case R.id.safe_entry:
                wvX5.loadUrl(SAFE_ENTRY_URL);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}