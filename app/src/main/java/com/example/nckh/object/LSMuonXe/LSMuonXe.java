
package com.example.nckh.object.LSMuonXe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LSMuonXe {

    @SerializedName("docs")
    @Expose
    private List<Doc> docs = null;
    @SerializedName("pages")
    @Expose
    private Integer pages;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
