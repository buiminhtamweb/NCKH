package com.example.nckh.fragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nckh.R;
import com.google.android.material.snackbar.Snackbar;

public class LichSuMuonXeFrag extends Fragment {

    private static final String TABLE_CALL = "call-table-";
    private static final String TAG = "LichSuMuonXeFrag";
    private RecyclerView mRecyLichSuMuonXe;
    private ProgressDialog mProgress;
    private AlertDialog mAlertDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_lich_su_bao_hu_hong, container, false);
        mRecyLichSuMuonXe = (RecyclerView) view.findViewById(R.id.recy_lich_su_muon_xe);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyLichSuMuonXe.setLayoutManager(layoutManager);
        getDataFromServer();
        return view;
    }




    private void viewSucc(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    private void getDataFromServer() {
        if (getContext() != null) {
            mProgress = new ProgressDialog(getContext());
            mProgress.setMessage("Đang tải dữ liệu. Vui lòng chờ !");
            mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgress.setIndeterminate(true);
            mProgress.show();
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.cancel();
        }
    }




    private void viewError(String message) {
        if (mRecyLichSuMuonXe != null) {
            final Snackbar snackbar = Snackbar.make(mRecyLichSuMuonXe, message, Snackbar.LENGTH_SHORT);

            snackbar.show();
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
//                    mGoiMonList.clear();
                    getDataFromServer();
                }
            });

            snackbar.show();
        }

    }
}
