
package com.example.nckh.object.LSViPham;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Muontra {

    @SerializedName("MUONTRA_ID")
    @Expose
    private Integer mUONTRAID;
    @SerializedName("taikhoanTKID")
    @Expose
    private String tKID;
    @SerializedName("xeXEID")
    @Expose
    private Integer xEID;

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

}
