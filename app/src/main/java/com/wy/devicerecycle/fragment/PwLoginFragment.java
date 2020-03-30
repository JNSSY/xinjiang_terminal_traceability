package com.wy.devicerecycle.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wy.devicerecycle.R;
import com.wy.devicerecycle.acitivty.MainActivity;

public class PwLoginFragment extends Fragment implements View.OnClickListener {

    private View view;
    private EditText et_account, et_pwd;
    private String account;
    private String pwd;
    private Button bt_login;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pw_login_fragment, null);
        init();
        return view;
    }

    private void init () {
        et_account = view.findViewById(R.id.et_account);
        et_pwd = view.findViewById(R.id.et_pwd);



        bt_login = view.findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);

    }

    private void doLogin () {
        account = et_account.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();

        if (TextUtils.isEmpty(account)||"".equals(account)) {
            Toast.makeText(getActivity(), "请输入账号", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pwd)||"".equals(pwd)) {
            Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
        }

        if ("18356548423".equals(account) && "123456".equals(pwd)) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }else {
            Toast.makeText(getActivity(), "账号或者密码错误，请重试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                doLogin();
                break;
            default:
                break;
        }
    }
}
