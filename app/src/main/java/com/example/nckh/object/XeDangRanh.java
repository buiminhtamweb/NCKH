
package com.example.nckh.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class XeDangRanh {

    @SerializedName("XE_ID")
    @Expose
    private Integer xEID;
    @SerializedName("XE_LAT")
    @Expose
    private Double xELAT;
    @SerializedName("XE_LNG")
    @Expose
    private Double xELNG;

    public Integer getxEID() {
        return xEID;
    }

    public void setxEID(Integer xEID) {
        this.xEID = xEID;
    }

    public Double getxELAT() {
        return xELAT;
    }

    public void setxELAT(Double xELAT) {
        this.xELAT = xELAT;
    }

    public Double getxELNG() {
        return xELNG;
    }

    public void setxELNG(Double xELNG) {
        this.xELNG = xELNG;
    }
}
