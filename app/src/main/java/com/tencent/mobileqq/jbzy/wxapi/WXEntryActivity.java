package com.tencent.mobileqq.jbzy.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mobileqq.jbzy.configer.Configer;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者    xmx
 * 时间    2018/3/6 14:58
 * 描述
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    //第三方app和微信通信的openapid接口
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private IWXAPI api;
    private JsonObject jsonObject = null;
    private RequestQueue queue = NoHttp.newRequestQueue();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoHttp.initialize(getApplication());
        api = WXAPIFactory.createWXAPI(this, Configer.WEIXIN_APP_ID, true);
        api.handleIntent(this.getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);//必须调用此句话
    }

    /**
     * 微信发送的请求将回调此方法
     *
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {
        Log.d("reqs", baseReq.toString());
        finish();
    }

    /**
     * 发送到微信请求的相应结果
     *
     * @param resp
     */
    @Override
    public void onResp(BaseResp resp) {
        Log.d("WX",resp.toString());
        switch (resp.errCode) {
            //发送成功
            case BaseResp.ErrCode.ERR_OK:
                SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                if (sendResp != null) {
                    String code = sendResp.code;
                    getAccess_token(code);
                }
//                finish();
                break;
            //发送取消
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;
            //发送拒绝
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
            default:
                //发送返回
                break;
        }
    }

    /**
     * 获取openid  accessToken值用于后期操作
     *
     * @param code 请求码
     */
    private void getAccess_token(String code) {

        String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + Configer.WEIXIN_APP_ID
                + "&secret="
                + Configer.APP_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";

        Request<String> info = NoHttp.createStringRequest(path, RequestMethod.GET);
        queue.add(0, info, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json=response.get();
                try {
                    JSONObject object=new JSONObject(json);
                    //获取用户的openid
                    String openid=object.getString("openid");
                    String access_token=object.getString("access_token");
                    getUserMsg(access_token,openid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 获取微信个人信息
     *
     * @param access_token
     * @param openid
     */
    private void getUserMsg(final String access_token, final String openid) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;


        Request<String> info = NoHttp.createStringRequest(path, RequestMethod.GET);
        queue.add(0, info, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
//
                Log.i("wch",response.get()+"");

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                Toast.makeText(WXEntryActivity.this, "获取失败:" + responseCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {

            }
        });

    }


}