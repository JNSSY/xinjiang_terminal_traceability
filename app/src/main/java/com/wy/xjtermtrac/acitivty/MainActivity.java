package com.wy.xjtermtrac.acitivty;

import android.view.View;

import com.wy.xjtermtrac.Constant;
import com.wy.xjtermtrac.R;
import com.wy.xjtermtrac.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected int initLayout () {
        return R.layout.activity_main;
    }

    @Override
    protected void findViewById () {
        wv = findViewById(R.id.wv);
        pb_loading = findViewById(R.id.pb_loading);
    }

    @Override
    protected void initListener () {

    }

    @Override
    protected void init () {
        initPermission();
        wv.loadUrl(Constant.INDEX_PAGE);
    }


}