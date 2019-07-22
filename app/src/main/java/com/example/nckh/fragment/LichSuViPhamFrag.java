package com.example.nckh.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nckh.R;
import com.example.nckh.adapter.RecyLSViPhamAdapter;
import com.example.nckh.object.ViPhamObject;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

public class LichSuViPhamFrag extends Fragment {
    private RecyclerView mRecyclerView;
    private AlertDialog mAlertDialog;
    private TextView mTvViewErr;

    private RecyLSViPhamAdapter mLsViPhamAdapter;
    private List<ViPhamObject> mViPhamObjectList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_lich_su_vi_pham, container, false);

        initView(v);

        return v;
    }

    private void initView(View v) {
        mRecyclerView = v.findViewById(R.id.recy_lich_su_vi_pham);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mLsViPhamAdapter = new RecyLSViPhamAdapter(mViPhamObjectList);
        mRecyclerView.setAdapter(mLsViPhamAdapter);

        mTvViewErr = (TextView) v.findViewById(R.id.tv_view_err);
    }

    private void layDSSanPham(int page) {



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

    public void showErrDisconnect(View view) {
        // Create snackbar
        if (view.isShown()) {
            final Snackbar snackbar = Snackbar.make(view, "Mất kết nối đến máy chủ", Snackbar.LENGTH_INDEFINITE);

            // Set an action on it, and a handler
            snackbar.setAction("Thử lại", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mSanPhams.clear();
                    layDSSanPham(1);
                }
            });
            snackbar.show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
//        mSanPhams.clear();
        layDSSanPham(1);

    }
}
