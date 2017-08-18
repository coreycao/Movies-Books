package com.sycao.moviesbooks.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sycao.moviesbooks.R;
import com.sycao.moviesbooks.adapter.SimpleFragmentPageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sycao on 2017/5/18.
 * 带顶部Tab导航 Activity
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.main_tabs)
    TabLayout mTabLayout;

    SimpleFragmentPageAdapter simpleFragmentPageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        simpleFragmentPageAdapter = new SimpleFragmentPageAdapter(getSupportFragmentManager(), this);

        mViewPager.setAdapter(simpleFragmentPageAdapter);
        mViewPager.setCurrentItem(1);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(1).select();
    }

    @Override
    public void onBackPressed() {
        /*if (mViewPager.getCurrentItem()==0){
            super.onBackPressed();
        }else{
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
        }*/
        super.onBackPressed();
    }
}
