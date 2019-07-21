package com.example.nckh.data;

import com.example.nckh.object.Message;
import com.example.nckh.object.ResLogin;
import com.example.nckh.object.UserAcc;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    //Tài khoản người dùng


    @FormUrlEncoded
    @POST("users/login")
    Call<ResLogin> signInAcc(@Header("Authorization") String token,
                             @Field("username") String username,
                             @Field("password") String password);

    @GET("users/{username}")
    Call<UserAcc> layThongTinNguoiDung(@Header("Authorization") String token,
                                       @Path("username") String username);

    @FormUrlEncoded
    @PUT("users/{username}")
    Call<Message> capNhatThongTinNguoiDung(@Header("Authorization") String token,
                                           @Path("username") String username,
                                           @Field("type") int type,
                                           @Field("data") String data);

    @FormUrlEncoded
    @PUT("users/{username}")
    Call<Message> capNhatMatKhau(@Header("Authorization") String token,
                                 @Path("username") String username,
                                 @Field("matKhauCu") String matKhauCu,
                                 @Field("matKhauMoi") String matKhauMoi);

}
