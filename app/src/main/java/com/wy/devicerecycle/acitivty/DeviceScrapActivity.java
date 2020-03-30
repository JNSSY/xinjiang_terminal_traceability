package com.wy.devicerecycle.acitivty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wy.devicerecycle.R;
import com.wy.devicerecycle.code2.activity.CaptureActivity;

public class DeviceScrapActivity extends AppCompatActivity {

    private Toolbar tb_back;
    private Button bt_scan;
    private TextView tv_result;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_scrap);

        tb_back = findViewById(R.id.tb_back);
        tb_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                finish();
            }
        });


        bt_scan = findViewById(R.id.bt_scan);
        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(DeviceScrapActivity.this, CaptureActivity.class);
                startActivityForResult(intent, 0x002);
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
