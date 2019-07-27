
package com.example.nckh.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class XeDangMuon {

    @SerializedName("MUONTRA_ID")
    @Expose
    private Integer mUONTRAID;
    @SerializedName("taikhoanTKID")
    @Expose
    private String tKID;
    @SerializedName("xeXEID")
    @Expose
    private Integer xEID;
    @SerializedName("MUON_THOIGIAN")
    @Expose
    private String mUONTHOIGIAN;
    @SerializedName("TRA_THOIGIAN")
    @Expose
    private String tRATHOIGIAN;
    @SerializedName("MUON_VITRI_LAT")
    @Expose
    private Double mUONVITRILAT;
    @SerializedName("MUON_VITRI_LNG")
    @Expose
    private Double mUONVITRILNG;
    @SerializedName("TRA_VITRI_LAT")
    @Expose
    private Double tRAVITRILAT;
    @SerializedName("TRA_VITRI_LNG")
    @Expose
    private Double tRAVITRILNG;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Integer getmUONTRAID() {
        return mUONTRAID;
    }

    public void setmUONTRAID(Integer mUONTRAID) {
        this.mUONTRAID = mUONTRAID;
    }

    public String gettKID() {
        return tKID;
    }

    public void settKID(String tKID) {
        this.tKID = tKID;
    }

    public Integer getxEID() {
        return xEID;
    }

    public void setxEID(Integer xEID) {
        this.xEID = xEID;
    }

    public String getmUONTHOIGIAN() {
        return mUONTHOIGIAN;
    }

    public void setmUONTHOIGIAN(String mUONTHOIGIAN) {
        this.mUONTHOIGIAN = mUONTHOIGIAN;
    }

    public String gettRATHOIGIAN() {
        return tRATHOIGIAN;
    }

    public void settRATHOIGIAN(String tRATHOIGIAN) {
        this.tRATHOIGIAN = tRATHOIGIAN;
    }

    public Double getmUONVITRILAT() {
        return mUONVITRILAT;
    }

    public void setmUONVITRILAT(Double mUONVITRILAT) {
        this.mUONVITRILAT = mUONVITRILAT;
    }

    public Double getmUONVITRILNG() {
        return mUONVITRILNG;
    }

    public void setmUONVITRILNG(Double mUONVITRILNG) {
        this.mUONVITRILNG = mUONVITRILNG;
    }

    public Double gettRAVITRILAT() {
        return tRAVITRILAT;
    }

    public void settRAVITRILAT(Double tRAVITRILAT) {
        this.tRAVITRILAT = tRAVITRILAT;
    }

    public Double gettRAVITRILNG() {
        return tRAVITRILNG;
    }

    public void settRAVITRILNG(Double tRAVITRILNG) {
        this.tRAVITRILNG = tRAVITRILNG;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
