
package com.example.nckh.object.LSViPham;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doc {

    @SerializedName("VP_ID")
    @Expose
    private Integer vPID;
    @SerializedName("VP_THOIGIAN")
    @Expose
    private String vPTHOIGIAN;
    @SerializedName("DA_XU_LY_VP")
    @Expose
    private Boolean dAXULYVP;
    @SerializedName("VP_LAT")
    @Expose
    private Integer vPLAT;
    @SerializedName("VP_LNG")
    @Expose
    private Integer vPLNG;
    @SerializedName("loi")
    @Expose
    private Loi loi;
    @SerializedName("muontra")
    @Expose
    private Muontra muontra;

    public Integer getVPID() {
        return vPID;
    }

    public void setVPID(Integer vPID) {
        this.vPID = vPID;
    }

    public String getVPTHOIGIAN() {
        return vPTHOIGIAN;
    }

    public void setVPTHOIGIAN(String vPTHOIGIAN) {
        this.vPTHOIGIAN = vPTHOIGIAN;
    }

    public Boolean getDAXULYVP() {
        return dAXULYVP;
    }

    public void setDAXULYVP(Boolean dAXULYVP) {
        this.dAXULYVP = dAXULYVP;
    }

    public Integer getVPLAT() {
        return vPLAT;
    }

    public void setVPLAT(Integer vPLAT) {
        this.vPLAT = vPLAT;
    }

    public Integer getVPLNG() {
        return vPLNG;
    }

    public void setVPLNG(Integer vPLNG) {
        this.vPLNG = vPLNG;
    }

    public Loi getLoi() {
        return loi;
    }

    public void setLoi(Loi loi) {
        this.loi = loi;
    }

    public Muontra getMuontra() {
        return muontra;
    }

    public void setMuontra(Muontra muontra) {
        this.muontra = muontra;
    }

}
