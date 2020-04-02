package com.wy.xjtermtrac.acitivty;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.amap.api.location.AMapLocation;
import com.wy.xjtermtrac.Constant;
import com.wy.xjtermtrac.code2.activity.CaptureActivity;
import com.wy.xjtermtrac.utils.LocalStorage;
import com.wy.xjtermtrac.utils.LocationInterface;
import com.wy.xjtermtrac.utils.LocationUtils;

public class JSInterface {
    private WebView wv;
    private Activity activity;
    private LocationUtils locationUtils;
    private String loc;


    public JSInterface (WebView wv, Activity activity) {
        this.wv = wv;
        this.activity = activity;
        locationUtils = new LocationUtils(activity);
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


    @JavascriptInterface
    public void getLocation () {
        wv.post(new Runnable() {
            @Override
            public void run () {
                locationUtils.startLocation();
            }
        });

        locationUtils.getLocationInfo(new LocationInterface() {
            @Override
            public void getLocInfo (AMapLocation location) {
                if (location != null) {
                    loc = location.getAddress();
                    wv.loadUrl("javascript:getLocationCallBack('" + loc + "')");
                }
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


}
