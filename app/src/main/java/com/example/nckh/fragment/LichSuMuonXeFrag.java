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
import com.example.nckh.adapter.RecyLSMuonXeAdapter;
import com.example.nckh.data.ConnectServer;
import com.example.nckh.object.LSMuonXe.Doc;
import com.example.nckh.object.LSMuonXe.LSMuonXe;
import com.example.nckh.util.DialogSupport;
import com.example.nckh.util.SharedPreferencesHandler;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.nckh.util.Constant.TK_ID;
import static com.example.nckh.util.Constant.TOKEN;

public class LichSuMuonXeFrag extends Fragment implements RecyLSMuonXeAdapter.onClickListener {

    private static final String TABLE_CALL = "call-table-";
    private static final String TAG = "LichSuMuonXeFrag";

    private ProgressDialog mProgress;
    private AlertDialog mAlertDialog;
    private RecyclerView mRecyLichSuMuonXe;

    private RecyLSMuonXeAdapter mMuonXeAdapter;
    private List<Doc> mMuonXeObjectList = new ArrayList<>();
    private TextView mTvViewErr;
    private ProgressDialog mProgressDialog;
    private String mToken;
    private String mTK_ID;
    private int mPageCurent = 1, mPageMax = 1;
    private DialogSupport mDialogSupport;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_lich_su_muon_xe, container, false);

        mDialogSupport = new DialogSupport(getContext(), mAlertDialog, mProgressDialog);

        mRecyLichSuMuonXe = (RecyclerView) view.findViewById(R.id.recy_lich_su_muon_xe);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyLichSuMuonXe.setLayoutManager(layoutManager);

        mMuonXeAdapter = new RecyLSMuonXeAdapter(mMuonXeObjectList);
        mRecyLichSuMuonXe.setAdapter(mMuonXeAdapter);

        mMuonXeAdapter.setOnClickListener(this);
        mMuonXeAdapter.setOnScrollListener(new RecyLSMuonXeAdapter.onScrollListener() {
            @Override
            public void onScroll(int position) {
                if (position + 2 == mMuonXeObjectList.size() && mPageCurent <= mPageMax) {
                    layLSMuonXe(mPageCurent + 1);
                }
            }
        });
        mTvViewErr = (TextView) view.findViewById(R.id.tv_view_err);

        mToken = SharedPreferencesHandler.getString(getContext(), TOKEN);
        mTK_ID = SharedPreferencesHandler.getString(getContext(), TK_ID);



        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        mMuonXeObjectList.clear();
        layLSMuonXe(1);
    }

    private void viewSucc(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    private void layLSMuonXe(int page) {
        mPageCurent = page;
        mDialogSupport.viewProgressDialog("Đang tải ... ");
        ConnectServer.getInstance().getApi().layLSMuonXe(mToken, mTK_ID, page).enqueue(new Callback<LSMuonXe>() {
            @Override
            public void onResponse(Call<LSMuonXe> call, Response<LSMuonXe> response) {
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
                    mRecyLichSuMuonXe.setVisibility(View.GONE);
                } else {
//                    for (Doc doc : response.body().getDocs() ) {
                    mTvViewErr.setVisibility(View.GONE);
                    mRecyLichSuMuonXe.setVisibility(View.VISIBLE);
                    if (response.body() != null) {
                        mMuonXeObjectList.addAll(response.body().getDocs());
                        mPageMax = response.body().getPages();
                        mMuonXeAdapter.notifyDataSetChanged();
                    }
//                    }

                }

            }

            @Override
            public void onFailure(Call<LSMuonXe> call, Throwable t) {
                mDialogSupport.hideProgressDialog();
                showErrDisconnect(mRecyLichSuMuonXe);
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.cancel();
        }
    }


    public void showErrDisconnect(View view) {
        // Create snackbar
        if (view != null) {
            final Snackbar snackbar = Snackbar.make(view, "Mất kết nối đến máy chủ", Snackbar.LENGTH_INDEFINITE);

            // Set an action on it, and a handler
            snackbar.setAction("Thử lại", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMuonXeObjectList.clear();
                    layLSMuonXe(1);
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
