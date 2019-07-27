
package com.example.nckh.object.LSMuonXe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doc {

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

    public Integer getMUONTRAID() {
        return mUONTRAID;
    }

    public void setMUONTRAID(Integer mUONTRAID) {
        this.mUONTRAID = mUONTRAID;
    }

    public String getTKID() {
        return tKID;
    }

    public void setTKID(String tKID) {
        this.tKID = tKID;
    }

    public Integer getXEID() {
        return xEID;
    }

    public void setXEID(Integer xEID) {
        this.xEID = xEID;
    }

    public String getMUONTHOIGIAN() {
        return mUONTHOIGIAN;
    }

    public void setMUONTHOIGIAN(String mUONTHOIGIAN) {
        this.mUONTHOIGIAN = mUONTHOIGIAN;
    }

    public String getTRATHOIGIAN() {
        return tRATHOIGIAN;
    }

    public void setTRATHOIGIAN(String tRATHOIGIAN) {
        this.tRATHOIGIAN = tRATHOIGIAN;
    }

    public Double getMUONVITRILAT() {
        return mUONVITRILAT;
    }

    public void setMUONVITRILAT(Double mUONVITRILAT) {
        this.mUONVITRILAT = mUONVITRILAT;
    }

    public Double getMUONVITRILNG() {
        return mUONVITRILNG;
    }

    public void setMUONVITRILNG(Double mUONVITRILNG) {
        this.mUONVITRILNG = mUONVITRILNG;
    }

    public Double getTRAVITRILAT() {
        return tRAVITRILAT;
    }

    public void setTRAVITRILAT(Double tRAVITRILAT) {
        this.tRAVITRILAT = tRAVITRILAT;
    }

    public Double getTRAVITRILNG() {
        return tRAVITRILNG;
    }

    public void setTRAVITRILNG(Double tRAVITRILNG) {
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
