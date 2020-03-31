package com.wy.xjtermtrac.acitivty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.wy.xjtermtrac.R;
import com.wy.xjtermtrac.view.TabLayout.CommonTabLayout;
import com.wy.xjtermtrac.view.TabLayout.CustomTabEntity;
import com.wy.xjtermtrac.view.TabLayout.OnTabSelectListener;
import com.wy.xjtermtrac.fragment.MsgLoginFragment;
import com.wy.xjtermtrac.fragment.PwLoginFragment;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ViewPager mPagerVp;
    private CommonTabLayout mTabCt;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    protected String[] mTitles = {"222", "333"};

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments = initFragment();
        setContentView(R.layout.activity_login);

        initView();

    }


    private ArrayList<Fragment> initFragment () {
        mTitles = new String[]{"账号密码", "短信验证"};
        fragments.add(new PwLoginFragment());
        fragments.add(new MsgLoginFragment());
        return fragments;
    }

    private void initView () {
        mTabCt = findViewById(R.id.tl);
        mPagerVp = findViewById(R.id.vp);

        mPagerVp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        for (int i = 0; i < mTitles.length; i++) {
            final int finalI = i;
            mTabEntities.add(new CustomTabEntity() {
                @Override
                public String getTabTitle () {
                    return mTitles[finalI];
                }

                @Override
                public int getTabSelectedIcon () {
                    return 0;
                }

                @Override
                public int getTabUnselectedIcon () {
                    return 0;
                }
            });
        }

        mTabCt.setTabData(mTabEntities);


        mTabCt.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect (int position) {
                if (mPagerVp.getCurrentItem() != position)
                    mPagerVp.setCurrentItem(position);

            }

            @Override
            public void onTabReselect (int position) {

            }
        });

        mPagerVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled (int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected (int position) {
                if (mTabCt.getCurrentTab() != position)
                    mTabCt.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged (int state) {

            }
        });
        mPagerVp.setOffscreenPageLimit(mTitles.length);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem (int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount () {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle (int position) {
            return mTitles[position];
        }
    }
}
