package com.wy.xjtermtrac.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wy.xjtermtrac.R;
import com.wy.xjtermtrac.acitivty.MainActivity;
import com.wy.xjtermtrac.utils.LocalStorage;


public class MsgLoginFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView tv_sc_code;
    private CountDownTimer timer;
    private int time = 59;
    private EditText et_account, et_sc_code;
    private String account, scCode;
    private Button bt_login;


    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.msg_login_fragment, null);
        init();
        return view;
    }

    private void init () {
        tv_sc_code = view.findViewById(R.id.tv_sc_code);
        tv_sc_code.setOnClickListener(this);

        et_account = view.findViewById(R.id.et_account);
        et_sc_code = view.findViewById(R.id.et_sc_code);


        bt_login = view.findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);

    }

    private void doLogin () {
        account = et_account.getText().toString().trim();
        scCode = et_sc_code.getText().toString().trim();

        if (TextUtils.isEmpty(account) || "".equals(account)) {
            Toast.makeText(getActivity(), "请输入账号", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(scCode) || "".equals(scCode)) {
            Toast.makeText(getContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
        }

        if ("13728390298".equals(account) && "123456".equals(scCode)) {
            LocalStorage localStorage = new LocalStorage(getContext());
            localStorage.putValue("account", "13728390298");
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else if ("13387383783".equals(account) && "123456".equals(scCode)) {
            LocalStorage localStorage = new LocalStorage(getContext());
            localStorage.putValue("account", "13387383783");
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), "账号或验证码错误，请稍后重试", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.tv_sc_code:
                countDown();
                break;
            case R.id.bt_login:
                doLogin();
                break;
            default:
                break;
        }
    }

    private void countDown () {
        if (timer == null) {
            timer = new CountDownTimer(59000, 1000) {
                @Override
                public void onTick (long millisUntilFinished) {
                    time--;
                    tv_sc_code.setText(time + "s后重新获取");
                    tv_sc_code.setClickable(false);
                }

                @Override
                public void onFinish () {
                    time = 59;
                    tv_sc_code.setText("获取验证码");
                    tv_sc_code.setClickable(true);
                }
            }.start();
        } else {
            timer.start();
        }
    }
}
