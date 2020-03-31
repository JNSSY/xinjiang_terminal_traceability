package com.wy.xjtermtrac.acitivty;

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
    }

    @Override
    protected void initListener () {

    }

    @Override
    protected void init () {
        wv.loadUrl(Constant.INDEX_PAGE);
    }





    private void loadData () {
//        JSInterface jsInterface = new JSInterface(wv, this);
//        wv.loadUrl("javascript:loginInfoCallback('" + jsInterface.getLoginInfo() + "')");
//        Log.e("wy",jsInterface.getLoginInfo());
    }


}