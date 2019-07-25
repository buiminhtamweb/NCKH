
package com.example.nckh.object.LSMuonXe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doc {

    @SerializedName("MUONTRA_ID")
    @Expose
    private Integer mUONTRAID;
    @SerializedName("TK_ID")
    @Expose
    private String tKID;
    @SerializedName("XE_ID")
    @Expose
    private Integer xEID;
    @SerializedName("MUON_THOIGIAN")
    @Expose
    private String mUONTHOIGIAN;
    @SerializedName("TRA_THOIGIAN")
    @Expose
    private Object tRATHOIGIAN;
    @SerializedName("MUON_VITRI_LAT")
    @Expose
    private Integer mUONVITRILAT;
    @SerializedName("MUON_VITRI_LNG")
    @Expose
    private Integer mUONVITRILNG;
    @SerializedName("TRA_VITRI_LAT")
    @Expose
    private Integer tRAVITRILAT;
    @SerializedName("TRA_VITRI_LNG")
    @Expose
    private Integer tRAVITRILNG;
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

    public Object getTRATHOIGIAN() {
        return tRATHOIGIAN;
    }

    public void setTRATHOIGIAN(Object tRATHOIGIAN) {
        this.tRATHOIGIAN = tRATHOIGIAN;
    }

    public Integer getMUONVITRILAT() {
        return mUONVITRILAT;
    }

    public void setMUONVITRILAT(Integer mUONVITRILAT) {
        this.mUONVITRILAT = mUONVITRILAT;
    }

    public Integer getMUONVITRILNG() {
        return mUONVITRILNG;
    }

    public void setMUONVITRILNG(Integer mUONVITRILNG) {
        this.mUONVITRILNG = mUONVITRILNG;
    }

    public Integer getTRAVITRILAT() {
        return tRAVITRILAT;
    }

    public void setTRAVITRILAT(Integer tRAVITRILAT) {
        this.tRAVITRILAT = tRAVITRILAT;
    }

    public Integer getTRAVITRILNG() {
        return tRAVITRILNG;
    }

    public void setTRAVITRILNG(Integer tRAVITRILNG) {
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
