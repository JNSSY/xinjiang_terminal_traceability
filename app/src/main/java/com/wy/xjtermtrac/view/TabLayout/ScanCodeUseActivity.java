package com.wy.xjtermtrac.view.TabLayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wy.xjtermtrac.R;
import com.wy.xjtermtrac.code2.activity.CaptureActivity;

public class ScanCodeUseActivity extends AppCompatActivity {

    private Toolbar tb_back;
    private ImageView iv_scan;
    private TextView tv_result;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scan_use);

        init();
    }

    private void init () {


        tb_back = findViewById(R.id.tb_back);
        tb_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                finish();
            }
        });


        iv_scan = findViewById(R.id.iv_scan);
        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(ScanCodeUseActivity.this, CaptureActivity.class);
                startActivityForResult(intent, 0x003);
            }
        });


        tv_result = findViewById(R.id.tv_result);

    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result;
        if (data != null) {
            result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
            if (result != null) {
                tv_result.setText(result);
            } else {
                tv_result.setText("不能识别");
            }
        } else {
            tv_result.setText("不能识别");
        }
    }


}
