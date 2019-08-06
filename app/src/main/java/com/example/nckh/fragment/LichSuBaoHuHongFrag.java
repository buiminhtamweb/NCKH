package com.example.nckh.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.example.nckh.adapter.RecyLSBaoHuHongAdapter;
import com.example.nckh.data.ConnectServer;
import com.example.nckh.object.LSBaoHuHong.Doc;
import com.example.nckh.object.LSBaoHuHong.LSBaoHuHong;
import com.example.nckh.util.DialogSupport;
import com.example.nckh.util.SharedPreferencesHandler;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;
import static com.example.nckh.util.Constant.TK_ID;
import static com.example.nckh.util.Constant.TOKEN;

public class LichSuBaoHuHongFrag extends Fragment implements RecyLSBaoHuHongAdapter.onClickListener {
    private RecyclerView mRecyclerView;
    private AlertDialog mAlertDialog;
    private ProgressDialog mProgressDialog;
    private RecyLSBaoHuHongAdapter mRecyLSBaoHuHongAdapter;
    private List<Doc> mHuHongObjectList = new ArrayList<>();
    private TextView mTvViewErr;
    private String mToken;
    private String mTK_ID;
    private int pageCurent = 1;
    private int pageMax = 1;
    private DialogSupport mDialogSupport;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_lich_su_bao_hu_hong, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recy_lich_su_bao_hu_hong);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyLSBaoHuHongAdapter = new RecyLSBaoHuHongAdapter(mHuHongObjectList);
        mRecyLSBaoHuHongAdapter.setOnClickListener(this);
        mRecyLSBaoHuHongAdapter.setOnScrollListener(new RecyLSBaoHuHongAdapter.onScrollListener() {
            @Override
            public void onScroll(int position) {
                if (position + 2 == mHuHongObjectList.size() && pageCurent <= pageMax) {
                    layDanhSachHuHong(pageCurent + 1);
                }
            }
        });

        mDialogSupport = new DialogSupport(getContext(), mAlertDialog, mProgressDialog);

        mRecyclerView.setAdapter(mRecyLSBaoHuHongAdapter);

        mTvViewErr = (TextView) view.findViewById(R.id.tv_view_err);
        mToken = SharedPreferencesHandler.getString(getContext(), TOKEN);
        mTK_ID = SharedPreferencesHandler.getString(getContext(), TK_ID);

        return view;
    }

    private void layDanhSachHuHong(int page) {
        pageCurent = page;
        mDialogSupport.viewProgressDialog("Đang tải ... ");
        ConnectServer.getInstance().getApi().layLSBaoHuHong(mToken, mTK_ID, page).enqueue(new Callback<LSBaoHuHong>() {
            @Override
            public void onResponse(Call<LSBaoHuHong> call, Response<LSBaoHuHong> response) {
                mDialogSupport.hideProgressDialog();
                if (response.code() == 400) {
                    try {
                        mDialogSupport.viewError(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (response.code() == 200 && response.body() != null) {
                    mTvViewErr.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mHuHongObjectList.addAll(response.body().getDocs());
                    pageMax = response.body().getPages();
                    mRecyLSBaoHuHongAdapter.notifyDataSetChanged();
                    if (mHuHongObjectList.size() == 0) {
                        mTvViewErr.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }
                }


            }

            @Override
            public void onFailure(Call<LSBaoHuHong> call, Throwable t) {
                mDialogSupport.hideProgressDialog();
                showErrDisconnect(mRecyclerView);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mHuHongObjectList.clear();
        layDanhSachHuHong(1);
    }


    public void showErrDisconnect(View view) {
        // Create snackbar
        if (view.isShown()) {
            final Snackbar snackbar = Snackbar.make(view, "Mất kết nối đến máy chủ", Snackbar.LENGTH_INDEFINITE);

            // Set an action on it, and a handler
            snackbar.setAction("Thử lại", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHuHongObjectList.clear();
                    layDanhSachHuHong(1);
                }
            });

            snackbar.show();
        }

    }

    @Override
    public void onTextGPSClick(String sttXe, String toaDo) {
        String[] parts = toaDo.split("-");
        String myLatitude = parts[0]; // 004
        String myLongitude = parts[1];
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + myLatitude + ">,<" + myLongitude + ">?q=<" + myLatitude + ">,<" + myLongitude + ">(" + sttXe + ")"));
        startActivity(intent);
    }
}
