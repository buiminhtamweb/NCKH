package com.example.nckh.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nckh.R;
import com.example.nckh.data.ConnectServer;
import com.example.nckh.object.XeDangMuon;
import com.example.nckh.util.DialogSupport;
import com.example.nckh.util.SharedPreferencesHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.nckh.util.Constant.DANG_MUON_XE_ACTIVITY;
import static com.example.nckh.util.Constant.TK_ID;
import static com.example.nckh.util.Constant.TOKEN;

public class DangMuonXeActivity extends AppCompatActivity {
    private TextView mTvXeMuon;
    private AlertDialog mAlertDialog;
    private List<String> mDSLoi = new ArrayList<>();
    private String mToaDo;
    private String mToken;
    private String mTK_ID;
    private TextView mTvThoiGianMuon;
    private DialogSupport mDialogSupport;
    private ImageView mImgLichSuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_muon_xe);

        mToken = SharedPreferencesHandler.getString(this, TOKEN);
        mTK_ID = SharedPreferencesHandler.getString(this, TK_ID);

        mTvXeMuon = (TextView) findViewById(R.id.tv_stt_xe_dang_muon);
        mTvThoiGianMuon = (TextView) findViewById(R.id.tv_thoi_gian_muon);
        mImgLichSuIcon = (ImageView) findViewById(R.id.img_lich_su_icon);
        mImgLichSuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MUON_XE", "onClick: Inent Lich su Act");
                Intent i = new Intent(DangMuonXeActivity.this, LichSuActivity.class);
                i.putExtra("ACTIVITY", DANG_MUON_XE_ACTIVITY);
                startActivity(i);
                finish();
            }
        });

        mDialogSupport = new DialogSupport(this, mAlertDialog, null);

        Intent intent = getIntent();
        if (null != intent) {
            mTvXeMuon.setText(intent.getStringExtra("STTXE"));
            mToaDo = intent.getStringExtra("TOA_DO");
        }

        //Khoi tạo danh sach loi
        mDSLoi.addAll(Arrays.asList(getResources().getStringArray(R.array.danh_sach_hu_hong)));


    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataFromServer();
    }

    private void getDataFromServer() {
        ConnectServer.getInstance().getApi().layThongTinXeDangMuon(mToken, mTK_ID).enqueue(new Callback<XeDangMuon>() {
            @Override
            public void onResponse(Call<XeDangMuon> call, Response<XeDangMuon> response) {

                if (response.code() == 401) {
                    mDialogSupport.viewErrorSignOut(DangMuonXeActivity.this, "Đã hết phiên đăng nhập !");
                }
                if (response.code() == 400) {
                    try {
                        if (response.errorBody() != null) {
                            mDialogSupport.viewError(response.errorBody().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if (response.code() == 200) {
                    if (response.body() != null) {
//                        Log.e("MUON_XE", "onResponse: "+response.body().getXEID() );

                        mTvXeMuon.setText(response.body().getxEID() + "");
                        mTvThoiGianMuon.setText(response.body().getmUONTHOIGIAN());
                    } else {
                        startActivity(new Intent(DangMuonXeActivity.this, MapsActivity.class));
                    }


                }
            }

            @Override
            public void onFailure(Call<XeDangMuon> call, Throwable t) {
                mDialogSupport.viewErrorExitApp();
            }
        });
    }

    public void anUngDung(View view) {
        minimizeApp();
    }

    public void baoHuHong(View view) {
        viewDialogBaoHuHong();
    }

    public void traXe(View view) {
    }

    private void viewDialogBaoHuHong() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Báo hư hỏng");
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_bao_hu_hong, null);
        final EditText editTextHuHongKhac = dialogLayout.findViewById(R.id.edt_hu_hong_khac);
        final Spinner spinner = dialogLayout.findViewById(R.id.spin_ten_hu_hong);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mDSLoi);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,)
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinner.setAdapter(adapter);
        //thiết lập sự kiện chọn phần tử cho Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == mDSLoi.size() - 1) { //Kiểm tra và hiện thị edit thêm lỗi khác
                    editTextHuHongKhac.setVisibility(View.VISIBLE);
                } else {
                    if (editTextHuHongKhac.getVisibility() == View.VISIBLE)
                        editTextHuHongKhac.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setSelection(0);

        builder.setView(dialogLayout);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
                if (mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                }

            }
        });
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                }
                if(spinner.getSelectedItemPosition() == 0){
                    Toast.makeText(DangMuonXeActivity.this, "Chưa chọn loại hư hỏng", Toast.LENGTH_SHORT).show();
                }else {

                }
            }
        });
        mAlertDialog = builder.create();
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
    }

    public void chiDuongDenXe(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=" + mToaDo));
        startActivity(intent);
    }

    private void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

}
