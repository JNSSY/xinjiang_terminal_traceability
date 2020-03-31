package com.wy.xjtermtrac.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.wy.xjtermtrac.Constant;
import com.wy.xjtermtrac.Permission;
import com.wy.xjtermtrac.acitivty.JSInterface;
import com.wy.xjtermtrac.code2.activity.CaptureActivity;

import java.util.Timer;
import java.util.TimerTask;

import static com.wy.xjtermtrac.Permission.index;
import static com.wy.xjtermtrac.Permission.lengh;


public abstract class BaseActivity extends AppCompatActivity {

    private static boolean mBackKeyPressed = false;//记录是否有首次按键
    public WebView wv;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(initLayout());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission(index);
        }

        findViewById();
        initListener();
        init();
        setWeb();

    }


    //初始化页面

    protected abstract int initLayout ();

    protected abstract void findViewById ();

    protected abstract void initListener ();

    protected abstract void init ();


    public void setWeb () {
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv.addJavascriptInterface(new JSInterface(wv, this), "android");

        wv.setWebViewClient(new MyWebViewClient());

        wv.setWebChromeClient(new MyWebChromeClient());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission (int i) {
        if (ContextCompat.checkSelfPermission(this, Permission.pesmissions[i])
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(Permission.pesmissions, Constant.REQUEST_PERMISSION);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {
                if (index < lengh) {
                    requestPermission(index++);
                }
            } else {
                Toast.makeText(this, "请开启权限，否则部分功能将不可用", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result;
        if (data != null) {
            result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
            if (result != null) {
                wv.loadUrl("javascript:resultCallback('" + result + "')");
            } else {
                Toast.makeText(this, "不能识别", Toast.LENGTH_SHORT).show();
            }
        } else {
//            Toast.makeText(this, "不能识别", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed () {
        if (wv.canGoBack()) {
            wv.goBack();
        } else {
            doFinish();
        }
    }

    private void doFinish () {
        if (!mBackKeyPressed) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run () {
                    mBackKeyPressed = false;
                }
            }, 1000);
        } else {
            finish();
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading (WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted (WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished (WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        public void onGeolocationPermissionsShowPrompt (String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }

        @Override
        public boolean onJsAlert (WebView view, String url, String message, JsResult result) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
            builder.setMessage(message);
            builder.show();
            result.confirm();
            return true;
        }

        /**
         * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
         */
        @Override
        public boolean onJsConfirm (WebView view, String url, String message,
                                    final JsResult result) {
            new android.app.AlertDialog.Builder(BaseActivity.this).setTitle("确认框")
                    .setMessage(message).setCancelable(false)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick (DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                public void onClick (DialogInterface dialog, int which) {
                    result.cancel();
                }
            }).show();
            return true;
        }

        @Override
        public boolean onJsPrompt (WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
            final EditText editText = new EditText(BaseActivity.this);
            new android.app.AlertDialog.Builder(BaseActivity.this).setTitle("对话框")
                    .setMessage(message).setView(editText).setCancelable(false)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick (DialogInterface dialog, int which) {
                            result.confirm(editText.getText().toString());
                        }
                    }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                public void onClick (DialogInterface dialog, int which) {
                    result.cancel();
                }
            }).show();
            return true;
        }

        /**
         * 进度条改变
         */
        @Override
        public void onProgressChanged (WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }


}
