package com.example.nckh.data;

import com.example.nckh.object.LSBaoHuHong.LSBaoHuHong;
import com.example.nckh.object.LSMuonXe.LSMuonXe;
import com.example.nckh.object.LSViPham.LSViPham;
import com.example.nckh.object.Message;
import com.example.nckh.object.ThongTinTaiKhoan;
import com.example.nckh.object.Token;
import com.example.nckh.object.XeDangMuon;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    //Tài khoản người dùng
    @FormUrlEncoded
    @POST("taikhoan")
    Call<Token> signInAcc(@Field("TK_ID") String TK_ID,
                          @Field("TK_PASSWORD") String password);

    @GET("taikhoan/{TK_ID}")
    Call<ThongTinTaiKhoan> layThongTinNguoiDung(@Header("Authorization") String token,
                                                @Path("TK_ID") String TK_ID);

    //Lịch sử
    @GET("taikhoan/{TK_ID}/lich-su-muon-xe")
    Call<LSMuonXe> layLSMuonXe(@Header("Authorization") String token,
                               @Path("TK_ID") String TK_ID,
                               @Query("page") int page);

    @GET("taikhoan/{TK_ID}/lich-su-bao-hu-hong")
    Call<LSBaoHuHong> layLSBaoHuHong(@Header("Authorization") String token,
                                     @Path("TK_ID") String TK_ID,
                                     @Query("page") int page);

    @GET("taikhoan/{TK_ID}/lich-su-vi-pham")
    Call<LSViPham> layLSViPham(@Header("Authorization") String token,
                               @Path("TK_ID") String TK_ID,
                               @Query("page") int page);

    //Mượn xe
    @FormUrlEncoded
    @POST("taikhoan/{TK_ID}/muonxe")
    Call<Message> muonXe(@Header("Authorization") String token,
                         @Path("TK_ID") String TK_ID,
                         @Field("XE_ID") int XE_ID);

    //Thông tin xe đang mượn
    @GET("taikhoan/{TK_ID}/muonxe")
    Call<XeDangMuon> layThongTinXeDangMuon(@Header("Authorization") String token,
                                           @Path("TK_ID") String TK_ID);

}
