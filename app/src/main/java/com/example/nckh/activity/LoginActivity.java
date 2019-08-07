package com.example.nckh.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nckh.R;
import com.example.nckh.data.ConnectServer;
import com.example.nckh.object.Token;
import com.example.nckh.object.XeDangMuon;
import com.example.nckh.util.Constant;
import com.example.nckh.util.DialogSupport;
import com.example.nckh.util.SharedPreferencesHandler;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText mEdtUserName;
    private EditText mEdtPassword;
    private Context mContext;
    private CheckBox mCBRememberMe;
    private AlertDialog mAlertDialog;
    private Snackbar mSnackbar;
    private ProgressDialog mProgressDialog;
    private String mToken = "";
    private DialogSupport mDialogSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEdtUserName = (EditText) findViewById(R.id.user_search);
        mEdtPassword = (EditText) findViewById(R.id.pswd);
        mCBRememberMe = (CheckBox) findViewById(R.id.cb_remember);
        mContext = this.getApplicationContext();

        mToken = SharedPreferencesHandler.getString(mContext, Constant.TOKEN);

        mEdtUserName.setText(SharedPreferencesHandler.getString(mContext, Constant.TK_ID));
        mEdtPassword.setText(SharedPreferencesHandler.getString(mContext, Constant.PASSWORD));
        mDialogSupport = new DialogSupport(this, mAlertDialog, mProgressDialog);


    }

    public void login(final View v) {

//        Intent i = new Intent(mContext, MapsActivity.class);
//        startActivity(i);
        if (checkNullDangNhap()) {
            mDialogSupport.viewProgressDialog("Đang đăng nhập ... ");
            Call<Token> call = ConnectServer.getInstance().getApi().signInAcc(mEdtUserName.getText().toString(),
                    mEdtPassword.getText().toString());

            call.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    mDialogSupport.hideProgressDialog();

                    if (response.code() == 400) {
                        try {
                            mDialogSupport.viewError(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (response.code() == 200 && response.body().getToken() != null) {

                        if (mCBRememberMe.isChecked()) {

                            SharedPreferencesHandler.writeString(mContext, Constant.TK_ID, mEdtUserName.getText().toString());
//                                SharedPreferencesHandler.writeString(mContext, Constant.PASSWORD, mEdtPassword.getText().toString());
                            SharedPreferencesHandler.writeBoolean(mContext, "remember_me", true);

                        }

                        Log.e("LOGIN", "onResponse: TOKEN: " + response.body().getToken());

                        String tk_id = mEdtUserName.getText().toString();
                        String token = "Bearer " + response.body().getToken();


                        SharedPreferencesHandler.writeString(mContext, Constant.TK_ID, tk_id);
                        SharedPreferencesHandler.writeString(mContext, Constant.TOKEN, token);
                        kiemTraTrangThai(token, tk_id);
                    }


                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    mDialogSupport.hideProgressDialog();
                    mDialogSupport.viewErrorExitApp();
                }
            });
        }
    }

    private boolean checkNullDangNhap() {
        if (mEdtUserName.getText().toString().equals("")) {
            mEdtUserName.setError("Chưa nhập tên đăng nhập");
            return false;
        } else if (mEdtPassword.getText().toString().equals("")) {
            mEdtPassword.setError("Chưa nhập mật khẩu");
            return false;
        } else return true;
    }

    private void kiemTraTrangThai(String token, String tk_id) {
        mDialogSupport.viewProgressDialog("Đang kiểm tra ....");
        Log.e("LOGIN", "kiemTraTrangThai: Token:->" + token);
        ConnectServer.getInstance().getApi().layThongTinXeDangMuon(token, tk_id).enqueue(new Callback<XeDangMuon>() {
            @Override
            public void onResponse(Call<XeDangMuon> call, Response<XeDangMuon> response) {
                Log.e("TAG", "onResponse: " + response.code());
                mDialogSupport.hideProgressDialog();

                if (response.code() == 200) {
                    if (response.body() == null) {
                        Intent i = new Intent(LoginActivity.this, MapsActivity.class);
                        startActivity(i);
//                        viewSucc(mEdtUserName, "Đã đăng nhập");
                        finish();
                    } else {
                        Intent i = new Intent(LoginActivity.this, DangMuonXeActivity.class);
//                        i.putExtra("data", (Serializable) response.body());
                        startActivity(i);
                        finish();
                    }


                } else if (response.code() == 401) {
                    SharedPreferencesHandler.wipeSharedPreferences(LoginActivity.this);
                    mDialogSupport.viewErrorSignOut(LoginActivity.this, "Đã hết phiên đăng nhập \nVui lòng đăng nhập lại");
                }
                if (response.code() == 400) {
                    try {
                        mDialogSupport.viewError(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<XeDangMuon> call, Throwable t) {
                mDialogSupport.hideProgressDialog();
                Log.e("TAG", "onFailure: " + t.getMessage());
                mDialogSupport.viewErrorExitApp();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            String messsage = bundle.getString("message", null);
            if (messsage != null) {
                mDialogSupport.viewError(messsage);
            }
        } else {
            boolean remember_me = SharedPreferencesHandler.getBoolean(mContext, "remember_me");
            String token = SharedPreferencesHandler.getString(mContext, Constant.TOKEN);
            String tk_id = SharedPreferencesHandler.getString(mContext, Constant.TK_ID);
            Log.e("LOGIN", "onStart: " + remember_me + token);

            if (remember_me && !token.equals("")) {
                kiemTraTrangThai(token, tk_id);
            }
        }

    }


}

