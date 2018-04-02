package com.tencent.mobileqq.jbzy.applica;

import com.yolanda.nohttp.NoHttp;

/**
 * Created by Zhangchen on 2018/3/6.
 */

public class Application extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
    }
}
