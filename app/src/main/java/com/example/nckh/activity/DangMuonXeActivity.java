package com.example.nckh.activity;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nckh.R;
import com.example.nckh.data.ConnectServer;
import com.example.nckh.object.Message;
import com.example.nckh.object.XE;
import com.example.nckh.object.XeDangMuon;
import com.example.nckh.util.DialogSupport;
import com.example.nckh.util.SharedPreferencesHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.nckh.util.Constant.DANG_MUON_XE_ACTIVITY;
import static com.example.nckh.util.Constant.TK_ID;
import static com.example.nckh.util.Constant.TOKEN;

public class DangMuonXeActivity extends AppCompatActivity {
    private static final String TAG = "DAG_MUON_XE";
    private static final int TIME_VIBRATE = 2000;

    private TextView mTvXeMuon;
    private AlertDialog mAlertDialog;
    private List<String> mDSLoi = new ArrayList<>();
    private String mToaDo;
    private String mToken;
    private String mTK_ID;
    private String mXE_ID = "";
    private TextView mTvThoiGianMuon;
    private DialogSupport mDialogSupport;
    private ImageView mImgLichSuIcon;
    private Timer mTimer = new Timer();
    private TimerTask mTimerTask;
    private boolean isOnstart = false;
    private boolean isShowNotifi = false;

    private RelativeLayout mRelativeLayoutBG;
    private TextView mTvCanhBao;
    private NotificationManager mNotificationManager;
    private Notification mNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_muon_xe);

        mToken = SharedPreferencesHandler.getString(this, TOKEN);
        mTK_ID = SharedPreferencesHandler.getString(this, TK_ID);

        mTvXeMuon = (TextView) findViewById(R.id.tv_stt_xe_dang_muon);
        mTvThoiGianMuon = (TextView) findViewById(R.id.tv_thoi_gian_muon);
        mImgLichSuIcon = (ImageView) findViewById(R.id.img_lich_su_icon);
        mRelativeLayoutBG = (RelativeLayout) findViewById(R.id.relative_backround);
        mTvCanhBao = (TextView) findViewById(R.id.tv_canh_bao);

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

        Intent intent = getIntent(); //Lấy dữ liệu Intent
        if (null != intent) {
            if (intent.getStringExtra("STTXE") != null)
                mXE_ID = intent.getStringExtra("STTXE");
            else mXE_ID = mTvXeMuon.getText().toString();
            mTvXeMuon.setText(mXE_ID);
            mToaDo = intent.getStringExtra("TOA_DO");
        }

        //Khoi tạo danh sach loi
        mDSLoi.addAll(Arrays.asList(getResources().getStringArray(R.array.danh_sach_hu_hong)));

        //hẹn giờ lấy và kiểm tra liên tục dữ liệu
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                //Called each time when 1000 milliseconds (1 second) (the period parameter)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "run: kiemTraTrangThaiXe->" + mXE_ID);
                        kiemTraTrangThaiXe();
                        if (isOnstart)
                            Log.e(TAG, "run: getDataFromServer");

                        getDataFromServer();
                    }
                });
            }
        };

        mTimer.scheduleAtFixedRate(mTimerTask,
                //Set how long before to start calling the TimerTask (in milliseconds)
                0,
                //Set the amount of time between each execution (in milliseconds)
                3000);

        Intent intentApplication = new Intent(this, DangMuonXeActivity.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intentApplication, 0);

// build notification
// the addAction re-use the same intent to keep the example short
        mNotification = new Notification.Builder(this)
                .setContentTitle("Cảnh báo mượn xe")
                .setContentText("Bạn đang vượt khỏi khuôn viên trường ĐH Cần Thơ")
                .setSmallIcon(R.drawable.logo_ctu)
                .setContentIntent(pIntent)
                .setAutoCancel(false)
                .setColor(Color.RED)
                .addAction(R.drawable.logo_ctu, "Mở ứng dụng", pIntent).build();

        mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

//        n.visibility;



    }

    @Override
    protected void onStart() {
        super.onStart();
        isOnstart = true;
    }


    private void kiemTraTrangThaiXe() {

        if (mXE_ID != null && !mXE_ID.equals(""))
            ConnectServer.getInstance().getApi().layThongTinXe(mTvXeMuon.getText().toString()).enqueue(new Callback<XE>() {
                @Override
                public void onResponse(Call<XE> call, Response<XE> response) {
                    if (response.code() == 200 && response.body() != null) {
                        Log.e(TAG, "onResponse: checkTT: " + response.body().getXeTrangThai());
                        if (response.body().getXeTrangThai() == 3) { // Xe dag vượt khỏi phạm vi
                            mTvCanhBao.setVisibility(View.VISIBLE);
                            mRelativeLayoutBG.setBackgroundColor(getResources().getColor(R.color.dark_red));
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            // Vibrate for 500 milliseconds
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(TIME_VIBRATE, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                //deprecated in API 26
                                v.vibrate(TIME_VIBRATE);
                            }
                            if (!isShowNotifi) {
                                mNotificationManager.notify(0, mNotification);
                            }

                        } else if (response.body().getXeTrangThai() == 1) { // Xe nằm trong trường
                            mTvCanhBao.setVisibility(View.GONE);
                            mRelativeLayoutBG.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary_dark));
//                            if(isShowNotifi) {
                            mNotificationManager.cancel(0);
                            mNotificationManager.cancelAll();
                            isShowNotifi = false;
//                            }
                        }
                    }


                }

                @Override
                public void onFailure(Call<XE> call, Throwable t) {
                    mDialogSupport.viewErrorExitApp();
                }
            });
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
                        finish();
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
                if (spinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(DangMuonXeActivity.this, "Chưa chọn loại hư hỏng", Toast.LENGTH_SHORT).show();
                } else {
                    String huHong = "";
                    Log.e("SPINER", "onClick: " + spinner.getSelectedItem().toString());
                    if (editTextHuHongKhac.getVisibility() == View.VISIBLE) {
                        huHong = editTextHuHongKhac.getText().toString();
                    } else huHong = spinner.getSelectedItem().toString();

                    ConnectServer.getInstance().getApi().baoHuHong(mToken, mTvXeMuon.getText().toString(), mTK_ID, huHong).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
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
                            if (response.code() == 200 && response.body() != null) {
                                Toast.makeText(DangMuonXeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            mDialogSupport.viewErrorExitApp();
                        }
                    });

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

    @Override
    protected void onStop() {
        super.onStop();

        Log.e(TAG, "onStart: ");
        isOnstart = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.purge();
        mTimerTask.cancel();
//        mTimerTask.
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        minimizeApp();
    }
}
