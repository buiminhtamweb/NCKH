
package com.example.nckh.object.LSBaoHuHong;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doc {

    @SerializedName("HH_ID")
    @Expose
    private Integer hHID;
    @SerializedName("TK_ID")
    @Expose
    private String tKID;
    @SerializedName("XE_ID")
    @Expose
    private Integer xEID;
    @SerializedName("HH_MOTA")
    @Expose
    private String hHMOTA;
    @SerializedName("HH_TRANGTHAI")
    @Expose
    private String hHTRANGTHAI;
    @SerializedName("HH_THOIGIAN")
    @Expose
    private String hHTHOIGIAN;
    @SerializedName("DA_XU_LY_HH")
    @Expose
    private Boolean dAXULYHH;
    @SerializedName("HU_HONG_LAT")
    @Expose
    private Double hUHONGLAT;
    @SerializedName("HU_HONG_LNG")
    @Expose
    private Double hUHONGLNG;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Integer getHHID() {
        return hHID;
    }

    public void setHHID(Integer hHID) {
        this.hHID = hHID;
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

    public String getHHMOTA() {
        return hHMOTA;
    }

    public void setHHMOTA(String hHMOTA) {
        this.hHMOTA = hHMOTA;
    }

    public String getHHTRANGTHAI() {
        return hHTRANGTHAI;
    }

    public void setHHTRANGTHAI(String hHTRANGTHAI) {
        this.hHTRANGTHAI = hHTRANGTHAI;
    }

    public String getHHTHOIGIAN() {
        return hHTHOIGIAN;
    }

    public void setHHTHOIGIAN(String hHTHOIGIAN) {
        this.hHTHOIGIAN = hHTHOIGIAN;
    }

    public Boolean getDAXULYHH() {
        return dAXULYHH;
    }

    public void setDAXULYHH(Boolean dAXULYHH) {
        this.dAXULYHH = dAXULYHH;
    }

    public Double getHUHONGLAT() {
        return hUHONGLAT;
    }

    public void setHUHONGLAT(Double hUHONGLAT) {
        this.hUHONGLAT = hUHONGLAT;
    }

    public Double getHUHONGLNG() {
        return hUHONGLNG;
    }

    public void setHUHONGLNG(Double hUHONGLNG) {
        this.hUHONGLNG = hUHONGLNG;
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
