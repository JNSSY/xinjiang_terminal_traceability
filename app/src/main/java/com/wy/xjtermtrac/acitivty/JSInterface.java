package com.wy.xjtermtrac.acitivty;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.wy.xjtermtrac.Constant;
import com.wy.xjtermtrac.code2.activity.CaptureActivity;
import com.wy.xjtermtrac.utils.LocalStorage;

public class JSInterface {
    private WebView wv;
    private Activity activity;
    private AlertDialog.Builder builder;


    public JSInterface (WebView wv, Activity activity) {
        this.wv = wv;
        this.activity = activity;
    }

    /**
     * 扫码
     */
    @JavascriptInterface
    public void scanCode () {
        Intent intent = new Intent(activity, CaptureActivity.class);
        activity.startActivityForResult(intent, Constant.REQUEST_SCANCODE);
    }

    /**
     * 取工号
     */
    @JavascriptInterface
    public void getUserNum () {
        wv.post(new Runnable() {
            @Override
            public void run () {
                wv.loadUrl("javascript:loginInfoCallback('" + getLoginInfo() + "')");
            }
        });
    }

    public String getLoginInfo () {
        LocalStorage localStorage = new LocalStorage(activity);
        String account = localStorage.getValue("account");
        if ("13728390298".equals(account)) {
            return "TS";
        } else {
            return "TSZWA";
        }
    }

    public void show () {
        builder = new AlertDialog.Builder(activity);
        builder.setMessage(getLoginInfo());
        builder.show();
    }

}
