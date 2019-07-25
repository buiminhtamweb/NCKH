package com.example.nckh.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.nckh.util.Constant;
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


    }

    public void login(final View v) {
//        viewProgressDialog("Đang đăng nhập ... ");
        Intent i = new Intent(mContext, MapsActivity.class);
        startActivity(i);
        if (checkNullDangNhap()) {

            Call<Token> call = ConnectServer.getInstance().getApi().signInAcc(mEdtUserName.getText().toString(),
                    mEdtPassword.getText().toString());

            call.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    hideProgressDialog();

                    if (response.code() == 400) {
                        try {
                            viewError(response.errorBody().string());
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


                        viewSucc(mCBRememberMe, "Đã đăng nhập thành công");
                        SharedPreferencesHandler.writeString(mContext, Constant.TK_ID, mEdtUserName.getText().toString());
                        SharedPreferencesHandler.writeString(mContext, Constant.TOKEN, "Bearer " + response.body().getToken());
                        Intent i = new Intent(mContext, MapsActivity.class);
                        startActivity(i);
                        finish();
                    }


                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    viewErrorExitApp();
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

//    private void kiemTraDangNhap() {
//        viewProgressDialog("Đang đăng nhập ....");
//
//        ConnectServer.getInstance(getApplicationContext()).getApi().kiemTraTrangThaiDangNhap(mToken).enqueue(new Callback<Message>() {
//            @Override
//            public void onResponse(Call<Message> call, Response<Message> response) {
//                Log.e("TAG", "onResponse: " + response.code());
//                hideProgressDialog();
//
//                if (response.code() == 200) {
//                    Intent i = new Intent(getBaseContext(), MainActivity.class);
//                    startActivity(i);
//                    viewSucc(mEdtUserName, "Đã đăng nhập");
//                    finish();
//
//                } else if (response.code() == 401) {
//                    SharedPreferences.Editor memes = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
//                    memes.clear();
//                    memes.apply();
//                    viewError("Đã hết phiên đăng nhập \nVui lòng đăng nhập lại");
//                }
//                if (response.code() == 400) {
//                    try {
//                        viewError(response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Message> call, Throwable t) {
//
//                Log.e("TAG", "onFailure: " + t.getMessage());
//                viewErrorExitApp();
//
//            }
//        });
//    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            String messsage = bundle.getString("message", null);
            if (messsage != null) {
                viewError(messsage);
            }
        } else {
            boolean remember_me = SharedPreferencesHandler.getBoolean(mContext, "remember_me");
            String token = SharedPreferencesHandler.getString(mContext, Constant.TOKEN);

            Log.e("LOGIN", "onStart: " + remember_me + token);

            if (remember_me && !token.equals("")) {
                startActivity(new Intent(this, MapsActivity.class));
            }
        }

    }




//    @Override
//    protected void onStop() {
//        super.onStop();
//    }

    private void viewError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cảnh báo");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    private void viewSucc(View view, String message) {
        mSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }

    private void viewProgressDialog(String message) {
        if (null == mProgressDialog) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    private void viewErrorExitApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cảnh báo");
        builder.setMessage("Không thể kết nối đến máy chủ ! \nThoát ứng dụng.");
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                System.exit(1);
            }
        });
        AlertDialog mAlertDialog = builder.create();
        mAlertDialog.show();
    }
}

