package com.example.nckh.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViPhamObject {
    @SerializedName("STT_XE")
    @Expose
    private String sttXe;
    @SerializedName("THOI_GIAN_VI_PHAM")
    @Expose
    private String thoiGianViPham;
    @SerializedName("VI_TRI_VI_PHAM")
    @Expose
    private String viTriViPham;
    @SerializedName("LOI_VI_PHAM")
    @Expose
    private String loiViPham;
    @SerializedName("HINH_THUC_XU_LY")
    @Expose
    private String hinhThucXuLy;

    public String getSttXe() {
        return sttXe;
    }

    public void setSttXe(String sttXe) {
        this.sttXe = sttXe;
    }

    public String getThoiGianViPham() {
        return thoiGianViPham;
    }

    public void setThoiGianViPham(String thoiGianViPham) {
        this.thoiGianViPham = thoiGianViPham;
    }

    public String getViTriViPham() {
        return viTriViPham;
    }

    public void setViTriViPham(String viTriViPham) {
        this.viTriViPham = viTriViPham;
    }

    public String getLoiViPham() {
        return loiViPham;
    }

    public void setLoiViPham(String loiViPham) {
        this.loiViPham = loiViPham;
    }

    public String getHinhThucXuLy() {
        return hinhThucXuLy;
    }

    public void setHinhThucXuLy(String hinhThucXuLy) {
        this.hinhThucXuLy = hinhThucXuLy;
    }
}
