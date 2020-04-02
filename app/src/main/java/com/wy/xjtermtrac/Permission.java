package com.wy.xjtermtrac;

import android.Manifest;

public class Permission {
    public static int index = 0;
    public static int lengh = 4;

    public static String pesmissions[] = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE
    };
}
