package com.tencent.mobileqq.jbzy.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tencent.mobileqq.jbzy.R;
import com.tencent.mobileqq.jbzy.util.AtKeyBoardUp;

import butterknife.BindView;

/**
 * Created by Zhangchen on 2018/3/6.
 */

public class RegistActivity extends BaseActivity {
    @BindView(R.id.webview)
    WebView webView;

    private Bundle bundle;
    private WebSettings webSettings;

    @Override
    protected int setLayout() {
        return R.layout.activity_regist;
    }

    @Override
    protected void setDeal() {

        bundle = this.getIntent().getExtras();
        String url = bundle.getString("url");

        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        AtKeyBoardUp.assistActivity(this); //防止软件盘遮挡网页输入框内容

//        webView.loadUrl("http://jbzy.emsily.top/app/index.php?i=4&c=entry&do=register&m=jiubiezhiye");

        webView.setWebViewClient(webViewClient);
        if (!TextUtils.isEmpty(url)) {
            Log.d("registActivity",url);
            webView.loadUrl(url);
        }

        saveCacheData(webSettings);
    }

    private void saveCacheData(WebSettings webSettings) {

        webSettings = webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBlockNetworkImage(false);//同步请求图片
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);//允许DCOM
    }

    WebViewClient webViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("registActivity2",url);
            return false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
