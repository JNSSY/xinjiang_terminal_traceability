package com.wy.devicerecycle.acitivty;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.wy.devicerecycle.Constant;
import com.wy.devicerecycle.Permission;
import com.wy.devicerecycle.R;
import com.wy.devicerecycle.code2.activity.CaptureActivity;

import java.util.Timer;
import java.util.TimerTask;

import static com.wy.devicerecycle.Permission.index;
import static com.wy.devicerecycle.Permission.lengh;


public class MainActivity extends AppCompatActivity {

    private WebView wv;
    private static boolean mBackKeyPressed = false;//记录是否有首次按键

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wv = findViewById(R.id.wv);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission(index);
        }

        setWeb();

        loadData();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission (int i) {
        if (ContextCompat.checkSelfPermission(this, Permission.pesmissions[i])
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(Permission.pesmissions, Constant.REQUEST_PERMISSION);
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void setWeb () {
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        wv.addJavascriptInterface(new JSInterface(wv, this), "android");
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading (WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void loadData () {
        wv.loadUrl("file:///android_asset/index.html");
    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result;
        if (data != null) {
            result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
            if (result != null) {
                wv.loadUrl("javascript:callback('" + result + "')");
            } else {
                Toast.makeText(this, "不能识别", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "不能识别", Toast.LENGTH_SHORT).show();
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

}