package com.example.nckh.data;

import android.util.Log;

import com.example.nckh.util.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectServer {
    private static ConnectServer mConnectServer;
    private Retrofit mRetrofit;
    private API mApi;


    private ConnectServer() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_SERVER + "/v1/api/")
//                .client(mClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mApi = mRetrofit.create(API.class);
    }

    public static ConnectServer getInstance() {

        if (mConnectServer == null) {
            Log.e("SV", "getInstance: new ConnectServer "  );
            mConnectServer = new ConnectServer();
        }
        return mConnectServer;
    }

    public static void destroy() {
        mConnectServer = null;
        Log.e("SV", "Destroy ConnectServer "  );
    }

    public API getApi() {
        return mApi;
    }

}
