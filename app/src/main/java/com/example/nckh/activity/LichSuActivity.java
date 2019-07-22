package com.example.nckh.activity;

import android.content.Intent;
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

import static com.example.nckh.util.Constant.DANG_MUON_XE_ACTIVITY;
import static com.example.nckh.util.Constant.MAPS_ACTIVITY;

public class LichSuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private LichSuMuonXeFrag mLichSuMuonXeFrag;
    private LichSuBaoHuHongFrag mLichSuBaoHuHongFrag;
    private LichSuViPhamFrag mLichSuViPhamFrag;
    private TabLayout mTabLayout;
    private int mActivityIntent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su);

        Intent intentRecive = getIntent();
        if (intentRecive != null) {
            if (intentRecive.getIntExtra("ACTIVITY", 0) == MAPS_ACTIVITY) {
                mActivityIntent = MAPS_ACTIVITY;

            } else if (intentRecive.getIntExtra("ACTIVITY", 0) == DANG_MUON_XE_ACTIVITY) {
                mActivityIntent = DANG_MUON_XE_ACTIVITY;
            } else finish();
        }

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


    public void trơVeManHinhTruoc(View view) {
        if (mActivityIntent == MAPS_ACTIVITY) {
            this.startActivity(new Intent(this, MapsActivity.class));
            finish();
        } else if (mActivityIntent == DANG_MUON_XE_ACTIVITY) {
            this.startActivity(new Intent(this, DangMuonXeActivity.class));
            finish();
        } else finish();
    }
}
