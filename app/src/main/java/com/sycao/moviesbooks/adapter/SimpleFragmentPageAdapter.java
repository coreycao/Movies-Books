package com.sycao.moviesbooks.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sycao.moviesbooks.fragment.BookFragment;
import com.sycao.moviesbooks.fragment.MovieFragment;
import com.sycao.moviesbooks.fragment.PageFragment;
import com.sycao.moviesbooks.model.Book;

/**
 * Created by sycao on 2017/5/18.
 */

public class SimpleFragmentPageAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"图书", "电影", "音乐"};
    private Context context;


    public SimpleFragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return BookFragment.newInstance(position + 1);
        } else if (position == 1) {
            return MovieFragment.newInstance(position + 1);
        } else {
            return PageFragment.newInstance(position + 1);
        }
    }

    @Override
    public int getCount() {
        return this.PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.tabTitles[position];
    }
}
