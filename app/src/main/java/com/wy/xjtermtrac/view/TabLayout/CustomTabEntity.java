package com.wy.xjtermtrac.view.TabLayout;

import android.support.annotation.DrawableRes;

public interface CustomTabEntity {
    String getTabTitle ();

    @DrawableRes
    int getTabSelectedIcon ();

    @DrawableRes
    int getTabUnselectedIcon ();
}