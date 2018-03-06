package com.tencent.mobileqq.jbzy.activity;

import android.widget.Button;

import com.tencent.mobileqq.jbzy.R;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.but)
    Button button;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void setDeal() {

    }
}
