package com.wy.xjtermtrac.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class LocalStorage {
    private SharedPreferences sp;
    private Context context;

    public LocalStorage (Context context) {
        this.context = context;
        sp = context.getSharedPreferences("userInfo", MODE_PRIVATE);
    }

    public void putValue (String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getValue (String key) {
        String string = sp.getString(key, null);
        return string;
    }
}
