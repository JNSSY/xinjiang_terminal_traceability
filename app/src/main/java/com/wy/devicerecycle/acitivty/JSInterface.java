package com.wy.devicerecycle.acitivty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.wy.devicerecycle.Constant;
import com.wy.devicerecycle.code2.activity.CaptureActivity;

public class JSInterface {
    private WebView wv;
    private Activity activity;

    public JSInterface (WebView wv, MainActivity activity) {
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

}
