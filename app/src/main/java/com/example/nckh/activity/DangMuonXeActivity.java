package com.example.nckh.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nckh.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DangMuonXeActivity extends AppCompatActivity {
    private TextView mTvXeMuon;
    private AlertDialog mAlertDialog;
    private List<String> mDSLoi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_muon_xe);

        mTvXeMuon = (TextView) findViewById(R.id.tv_stt_xe_dang_muon);
        Intent intent = getIntent();
        if (null != intent) {
            mTvXeMuon.setText(intent.getStringExtra("STTXE"));
        }

        //Khoi tạo danh sach loi
        mDSLoi.addAll(Arrays.asList(getResources().getStringArray(R.array.danh_sach_hu_hong)));


    }


    public void anUngDung(View view) {

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

        String toaDo = "10.0306221,105.7684058";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=" + toaDo));
        startActivity(intent);
    }
}
