package com.example.nckh.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.nckh.R;
import com.example.nckh.adapter.ViewPagerAdapter;
import com.example.nckh.fragment.LichSuBaoHuHongFrag;
import com.example.nckh.fragment.LichSuMuonXeFrag;
import com.example.nckh.fragment.LichSuViPhamFrag;
import com.google.android.material.tabs.TabLayout;

public class LichSuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private LichSuMuonXeFrag mLichSuMuonXeFrag;
    private LichSuBaoHuHongFrag mLichSuBaoHuHongFrag;
    private LichSuViPhamFrag mLichSuViPhamFrag;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su);

        mLichSuMuonXeFrag = new LichSuMuonXeFrag();
        mLichSuBaoHuHongFrag = new LichSuBaoHuHongFrag();
        mLichSuViPhamFrag = new LichSuViPhamFrag();

        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mLichSuMuonXeFrag, mLichSuBaoHuHongFrag, mLichSuViPhamFrag);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(1);

        mTabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
