package com.tencent.mobileqq.jbzy.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;

import com.tencent.mobileqq.jbzy.R;
import com.tencent.mobileqq.jbzy.util.AtKeyBoardUp;

import butterknife.BindView;

/**
 * Created by Zhangchen on 2018/3/6.
 */

public class RegistActivity extends BaseActivity{
    @BindView(R.id.webview)
    WebView webView;

    private WebSettings webSettings;

    @Override
    protected int setLayout() {
        return R.layout.activity_regist;
    }

    @Override
    protected void setDeal() {
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        AtKeyBoardUp.assistActivity(this); //防止软件盘遮挡网页输入框内容

        webView.loadUrl("http://jbzy.emsily.top/app/index.php?i=4&c=entry&do=register&m=jiubiezhiye");

        webSettings = webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBlockNetworkImage(false);//同步请求图片

        saveCacheData(webSettings);
    }

    private void saveCacheData(WebSettings webSettings) {
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);//允许DCOM
    }

}
