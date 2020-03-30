package com.wy.xjtermtrac.acitivty;

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

public class DeviceRecycleActivity extends AppCompatActivity {

    private Toolbar tb_back;
    private TextView tv_result;
    private ImageView iv_scan;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_recycle);

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

        tv_result = findViewById(R.id.tv_result);
        iv_scan = findViewById(R.id.iv_scan);

        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(DeviceRecycleActivity.this, CaptureActivity.class);
                startActivityForResult(intent, 0x001);
            }
        });
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
