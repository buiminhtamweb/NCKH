
package com.example.nckh.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThongTinTaiKhoan {

    @SerializedName("TK_ID")
    @Expose
    private String tKID;
    @SerializedName("TK_HOTEN")
    @Expose
    private String tKHOTEN;
    @SerializedName("TK_DONVI")
    @Expose
    private String tKDONVI;
    @SerializedName("TK_LOAI")
    @Expose
    private String tKLOAI;

    public String getTKID() {
        return tKID;
    }

    public void setTKID(String tKID) {
        this.tKID = tKID;
    }

    public String getTKHOTEN() {
        return tKHOTEN;
    }

    public void setTKHOTEN(String tKHOTEN) {
        this.tKHOTEN = tKHOTEN;
    }

    public String getTKDONVI() {
        return tKDONVI;
    }

    public void setTKDONVI(String tKDONVI) {
        this.tKDONVI = tKDONVI;
    }

    public String getTKLOAI() {
        return tKLOAI;
    }

    public void setTKLOAI(String tKLOAI) {
        this.tKLOAI = tKLOAI;
    }

}
