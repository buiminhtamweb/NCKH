package com.example.nckh.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MuonXeObject {
    @SerializedName("STT_XE")
    @Expose
    private String sttXe;
    @SerializedName("THOI_GIAN_MUON")
    @Expose
    private String thoiGianMuon;
    @SerializedName("VI_TRI_MUON")
    @Expose
    private String viTriMuon;
    @SerializedName("THOI_GIAN_TRA")
    @Expose
    private String thoiGianTra;
    @SerializedName("VI_TRI_TRA")
    @Expose
    private String viTriTra;

    public String getSttXe() {
        return sttXe;
    }

    public void setSttXe(String sttXe) {
        this.sttXe = sttXe;
    }

    public String getThoiGianMuon() {
        return thoiGianMuon;
    }

    public void setThoiGianMuon(String thoiGianMuon) {
        this.thoiGianMuon = thoiGianMuon;
    }

    public String getViTriMuon() {
        return viTriMuon;
    }

    public void setViTriMuon(String viTriMuon) {
        this.viTriMuon = viTriMuon;
    }

    public String getThoiGianTra() {
        return thoiGianTra;
    }

    public void setThoiGianTra(String thoiGianTra) {
        this.thoiGianTra = thoiGianTra;
    }

    public String getViTriTra() {
        return viTriTra;
    }

    public void setViTriTra(String viTriTra) {
        this.viTriTra = viTriTra;
    }
}
