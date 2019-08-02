
package com.example.nckh.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class XE {

    @SerializedName("XE_ID")
    @Expose
    private Integer xEID;
    @SerializedName("XE_LAT")
    @Expose
    private Double xELAT;
    @SerializedName("XE_LNG")
    @Expose
    private Double xELNG;
    @SerializedName("XE_TRANGTHAI")
    @Expose
    private Integer xeTrangThai;


    public Integer getXEID() {
        return xEID;
    }

    public void setXEID(Integer xEID) {
        this.xEID = xEID;
    }

    public Double getXELAT() {
        return xELAT;
    }

    public void setXELAT(Double xELAT) {
        this.xELAT = xELAT;
    }

    public Double getXELNG() {
        return xELNG;
    }

    public void setXELNG(Double xELNG) {
        this.xELNG = xELNG;
    }


    public Integer getXeTrangThai() {
        return xeTrangThai;
    }

    public void setXeTrangThai(Integer xeTrangThai) {
        this.xeTrangThai = xeTrangThai;
    }

}
