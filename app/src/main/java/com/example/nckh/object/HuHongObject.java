package com.example.nckh.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HuHongObject {

    @SerializedName("STT_XE")
    @Expose
    private String sttXe;
    @SerializedName("THOI_GIAN_HU_HONG")
    @Expose
    private String thoiGianHuhong;
    @SerializedName("VI_TRI_HU_HONG")
    @Expose
    private String viTriHuHong;
    @SerializedName("LOAI_HU_HONG")
    @Expose
    private String loaiHuHong;

    public HuHongObject(String sttXe, String thoiGianHuhong, String viTriHuHong, String loaiHuHong) {
        this.sttXe = sttXe;
        this.thoiGianHuhong = thoiGianHuhong;
        this.viTriHuHong = viTriHuHong;
        this.loaiHuHong = loaiHuHong;
    }

    public HuHongObject() {
    }

    public String getSttXe() {
        return sttXe;
    }

    public void setSttXe(String sttXe) {
        this.sttXe = sttXe;
    }

    public String getThoiGianHuhong() {
        return thoiGianHuhong;
    }

    public void setThoiGianHuhong(String thoiGianHuhong) {
        this.thoiGianHuhong = thoiGianHuhong;
    }

    public String getViTriHuHong() {
        return viTriHuHong;
    }

    public void setViTriHuHong(String viTriHuHong) {
        this.viTriHuHong = viTriHuHong;
    }

    public String getLoaiHuHong() {
        return loaiHuHong;
    }

    public void setLoaiHuHong(String loaiHuHong) {
        this.loaiHuHong = loaiHuHong;
    }
}
