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
import com.example.nckh.adapter.RecyLSViPhamAdapter;
import com.example.nckh.data.ConnectServer;
import com.example.nckh.object.LSViPham.Doc;
import com.example.nckh.object.LSViPham.LSViPham;
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

public class LichSuViPhamFrag extends Fragment implements RecyLSViPhamAdapter.onClickListener {
    private RecyclerView mRecyclerView;
    private AlertDialog mAlertDialog;
    private TextView mTvViewErr;

    private RecyLSViPhamAdapter mLsViPhamAdapter;
    private List<Doc> mViPhamObjectList = new ArrayList<>();
    private int mPageCurent = 1, mPageMax = 1;
    private ProgressDialog mProgressDialog;

    private DialogSupport mDialogSupport;
    private String mToken;
    private String mTK_ID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_lich_su_vi_pham, container, false);

        initView(v);

        mToken = SharedPreferencesHandler.getString(getContext(), TOKEN);
        mTK_ID = SharedPreferencesHandler.getString(getContext(), TK_ID);
        mDialogSupport = new DialogSupport(getContext(), mAlertDialog, mProgressDialog);


        return v;
    }

    private void initView(View v) {
        mRecyclerView = v.findViewById(R.id.recy_lich_su_vi_pham);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mLsViPhamAdapter = new RecyLSViPhamAdapter(mViPhamObjectList);
        mRecyclerView.setAdapter(mLsViPhamAdapter);
        mLsViPhamAdapter.setOnClickListener(this);
        mLsViPhamAdapter.setOnScrollListener(new RecyLSViPhamAdapter.onScrollListener() {
            @Override
            public void onScroll(int position) {
                if (position + 2 == mViPhamObjectList.size() && mPageCurent <= mPageMax) {
                    layLSViPham(mPageCurent + 1);
                }
            }
        });

        mTvViewErr = (TextView) v.findViewById(R.id.tv_view_err);
    }

    private void layLSViPham(int page) {
        mPageCurent = page;
        mDialogSupport.viewProgressDialog("Đang tải ... ");
        ConnectServer.getInstance().getApi().layLSViPham(mToken, mTK_ID, page).enqueue(new Callback<LSViPham>() {
            @Override
            public void onResponse(Call<LSViPham> call, Response<LSViPham> response) {
                mDialogSupport.hideProgressDialog();
                if (response.code() == 400) {
                    try {
                        mDialogSupport.viewError(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (response.code() == 200 && response.body() != null && response.body().getDocs().size() == 0) {
                    mTvViewErr.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                } else if (response.body() != null) {
//                    for (Doc doc : response.body().getDocs() ) {

                    mTvViewErr.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mViPhamObjectList.addAll(response.body().getDocs());
                    mPageMax = response.body().getPages();
                    mLsViPhamAdapter.notifyDataSetChanged();
//                    }

                }

            }

            @Override
            public void onFailure(Call<LSViPham> call, Throwable t) {
                mDialogSupport.hideProgressDialog();
                showErrDisconnect(mRecyclerView);
            }
        });

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
                    layLSViPham(1);
                }
            });
            snackbar.show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mViPhamObjectList.clear();
        layLSViPham(1);

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
