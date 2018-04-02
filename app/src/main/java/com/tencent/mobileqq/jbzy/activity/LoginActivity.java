package com.tencent.mobileqq.jbzy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mobileqq.jbzy.R;
import com.tencent.mobileqq.jbzy.configer.Configer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Zhangchen on 2018/3/6.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.img_wx)
    ImageView img;
    @BindView(R.id.bt_regist)
    Button bt_regist;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void setDeal() {

    }

    @OnClick({R.id.img_wx,R.id.bt_regist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_wx:
                setWXLogin();
//                Bundle bundles = new Bundle();
//                bundles.putString("url", Configer.HTTP_MAIN);
//                Intent it = new Intent(this,MainActivity.class);
//                it.putExtras(bundles);
//                startActivity(it);
                break;
            case R.id.bt_regist:
                Bundle bundle = new Bundle();
                bundle.putString("url",Configer.HTTP_REGIST);
                Intent it2 = new Intent(this,RegistActivity.class);
                it2.putExtras(bundle);
                startActivity(it2);
                break;
            default:
                break;
        }
    }

    private void setWXLogin() {
        IWXAPI api = WXAPIFactory.createWXAPI(this, Configer.WEIXIN_APP_ID, false);
        api.unregisterApp();
        api.registerApp(Configer.WEIXIN_APP_ID);
        if (api.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "login_state";
            api.sendReq(req);
        } else {
            Toast.makeText(this, "您尚未安装微信", Toast.LENGTH_SHORT).show();
        }
    }
}
