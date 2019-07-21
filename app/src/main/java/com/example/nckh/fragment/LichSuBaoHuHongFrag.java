package com.example.nckh.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nckh.R;
import com.google.android.material.snackbar.Snackbar;
import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

public class LichSuBaoHuHongFrag extends Fragment {
    private RecyclerView mRecyclerView;
    private AlertDialog mAlertDialog;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_lich_su_bao_hu_hong, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recy_lich_su_bao_hu_hong);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setAdapter();

        return view;
    }

    public void layDuLieuGioHang() {


    }



    @Override
    public void onStart() {
        super.onStart();
        layDuLieuGioHang();
    }



    private void viewError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    private void viewProgressDialog(String message) {
        if (null == mProgressDialog) {
            mProgressDialog = new ProgressDialog(getContext());
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    public void showErrDisconnect(View view) {
        // Create snackbar
        if (view.isShown()) {
            final Snackbar snackbar = Snackbar.make(view, "Mất kết nối đến máy chủ", Snackbar.LENGTH_INDEFINITE);

            // Set an action on it, and a handler
            snackbar.setAction("Thử lại", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mGioHang.clear();
                    layDuLieuGioHang();
                }
            });

            snackbar.show();
        }

    }

}
