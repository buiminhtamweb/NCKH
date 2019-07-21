package com.example.nckh.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.nckh.fragment.LichSuBaoHuHongFrag;
import com.example.nckh.fragment.LichSuMuonXeFrag;
import com.example.nckh.fragment.LichSuViPhamFrag;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private LichSuMuonXeFrag mLichSuMuonXeFrag;
    private LichSuBaoHuHongFrag mLichSuBaoHuHongFrag;
    private LichSuViPhamFrag mLichSuViPhamFrag;
    private String[] mTitle;

    public ViewPagerAdapter(FragmentManager fm, LichSuMuonXeFrag mLichSuMuonXeFrag, LichSuBaoHuHongFrag mLichSuBaoHuHongFrag, LichSuViPhamFrag mLichSuViPhamFrag) {
        super(fm);
        this.mLichSuMuonXeFrag = mLichSuMuonXeFrag;
        this.mLichSuBaoHuHongFrag = mLichSuBaoHuHongFrag;
        this.mLichSuViPhamFrag = mLichSuViPhamFrag;


        mTitle = new String[]{"Trang chủ", "Đặt hàng", "Tài khoản"};
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mLichSuMuonXeFrag;
            case 1:
                return mLichSuBaoHuHongFrag;
            case 2:
                return mLichSuViPhamFrag;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
