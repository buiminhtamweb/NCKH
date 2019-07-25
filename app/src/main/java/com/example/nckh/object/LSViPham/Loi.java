
package com.example.nckh.object.LSViPham;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Loi {

    @SerializedName("LOI_TEN")
    @Expose
    private String lOITEN;
    @SerializedName("LOI_MOTA")
    @Expose
    private String lOIMOTA;

    public String getLOITEN() {
        return lOITEN;
    }

    public void setLOITEN(String lOITEN) {
        this.lOITEN = lOITEN;
    }

    public String getLOIMOTA() {
        return lOIMOTA;
    }

    public void setLOIMOTA(String lOIMOTA) {
        this.lOIMOTA = lOIMOTA;
    }

}
