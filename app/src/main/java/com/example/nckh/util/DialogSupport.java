package com.example.nckh.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.example.nckh.activity.LoginActivity;
import com.google.android.material.snackbar.Snackbar;

public class DialogSupport {

    private Context context;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    public DialogSupport(Context context, AlertDialog alertDialog, ProgressDialog progressDialog) {
        this.context = context;
        this.alertDialog = alertDialog;
        this.progressDialog = progressDialog;
    }

    public void viewErrorExitApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void viewProgressDialog(String message) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (null != progressDialog) {
            progressDialog.dismiss();
            progressDialog.cancel();

        }
    }

    public void viewSucc(View view, String message) {
        Snackbar mSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }

    public void viewError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Cảnh báo");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void viewErrorSignOut(final Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Cảnh báo");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                context.startActivity(new Intent(context, LoginActivity.class));
                activity.finish();

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}
