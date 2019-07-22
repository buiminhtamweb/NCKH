package com.example.nckh.activity;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.nckh.R;
import com.example.nckh.data.ConnectSocketIO;
import com.example.nckh.util.Constant;
import com.example.nckh.util.SharedPreferencesHandler;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.nckh.util.Constant.MAPS_ACTIVITY;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, GoogleMap.OnInfoWindowClickListener {

    private static final int MY_LOCATION_REQUEST_CODE = 2;
    private static final int REQUEST_LOCATION = 123;
    private static final String TAG = "MAPS_ACT";
    private LocationManager locationManager;
    private GoogleMap mMap;
    private AlertDialog mAlertDialog;

    private TextView mTvHoten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);


        // Sử dụng sactionbar
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Xin chào Minh Tâm Bùi !");
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


//        SharedPreferencesHandler.writeString(this,Constant.SERVER_SOCKET_URL, "http://nckh-xedap.herokuapp.com" );

//      GPS
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mapFragment.getMapAsync(this);

//        mTvHoten = (TextView) findViewById(R.id.tv_ho_ten);
//        if (SharedPreferencesHandler.getString(MapsActivity.this, Constant.SERVER_SOCKET_URL).equals("")) {
//            mTvHoten.setText("Chưa có địa chỉ");
//        } else
//            mTvHoten.setText(SharedPreferencesHandler.getString(MapsActivity.this, Constant.SERVER_SOCKET_URL));
//        mTvHoten.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogDiaChiServer();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_lich_su:
                Intent intent = new Intent(this, LichSuActivity.class);
                intent.putExtra("ACTIVITY", MAPS_ACTIVITY);
                startActivity(intent);
                break;
            case R.id.menu_dang_xuat:
                dangXuat();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Set Maps Ui
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);

        //Zoom maps đến CTU
        LatLng dhCanTho = new LatLng(10.030718, 105.7680055);
//        mMap.addMarker(new MarkerOptions().position(dhCanTho).title("Đại học Cần Thơ"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dhCanTho, 4));
        mMap.setMinZoomPreference(15.5f);
        mMap.setMaxZoomPreference(19f);

        addMarker();

        //--- Hien vi tri cua minh----
        //      Cap quyen truy cap GPS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||

                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||

                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,

                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,

                            Manifest.permission.ACCESS_COARSE_LOCATION,

                            Manifest.permission.BLUETOOTH,

                            Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_LOCATION);

        } else {
            System.out.println("Location permissions available, starting location");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5000,   // 5 sec
                    10, this);
            mMap.setMyLocationEnabled(true);
        }


//        mMap.setMyLocationEnabled(true);

        //Set event Maps
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

    }

    private void dialogDiaChiServer() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nhap dia chi máy chủ: ");

        final EditText input = new EditText(MapsActivity.this);
        input.setText(mTvHoten.getText().toString());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
                mTvHoten.setText(input.getText().toString());
                SharedPreferencesHandler.writeString(MapsActivity.this, Constant.SERVER_SOCKET_URL, input.getText().toString());

            }
        });
        mAlertDialog = builder.create();
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();

    }

    private void viewDialogMuonXe() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mượn xe");
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_muon_xe, null);
        final TextView textViewSTTXe = dialogLayout.findViewById(R.id.tv_stt_xe);
        Button btnMuonXe = dialogLayout.findViewById(R.id.btn_muon_xe);
        btnMuonXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gửi socket.IO
                senDataSocket(0,textViewSTTXe.getText().toString());

            }
        });

        builder.setView(dialogLayout);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
                if (mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                }

            }
        });
        mAlertDialog = builder.create();
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();

    }


    private void addMarker() {
        LatLng dhCanTho = new LatLng(10.030718, 105.7680055);
        Marker markerNew = mMap.addMarker(new MarkerOptions().position(dhCanTho)
                .title("Xe đạp 1").snippet("Đang rảnh"));
        markerNew.showInfoWindow();

    }

    private boolean senDataSocket(final int n, final String sttXe) {
        if (ConnectSocketIO.getInstance(MapsActivity.this).sendData("Client-send-unlock", "unlock", true)) { //Succes is true
//            new CountDownTimer(5000, 1000) {
//                String textShowCalling = "Đang gọi";
//
//                public void onTick(long millisUntilFinished) {
//                    textShowCalling += ".";
//                    mTvCall.setText(textShowCalling);
//                }
//
//                public void onFinish() {
//                    mTvCall.setText("");
//                    mReViewCall.setVisibility(View.GONE);
//                    mFAB.setEnabled(true);
//                    viewSucc(mReViewCall, "Đã gọi thanh toán! Xin quí khách chờ trong vài giây");
//                }
//            }.start();

            //------Gửi thành công---------
            Toast.makeText(this, "Đã gửi yêu cầu mở khóa xe", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DangMuonXeActivity.class);
            intent.putExtra("STTXE", sttXe);
            startActivity(intent); //Chuyển sang trạng thái dang mượn xe
            return true;
        } else {
            if (n < 3) { //gọi lại 3 lần
                new CountDownTimer(2000, 1000) {
                    String textShowCalling = "Đang gọi";

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        senDataSocket(n + 1,sttXe); //Đệ quy, tiếp tục gọi lại;
                    }
                }.start();
                Toast.makeText(MapsActivity.this, "Đang gọi lại lần " + n, Toast.LENGTH_SHORT).show();

                return false;
            } else {
                Toast.makeText(MapsActivity.this, "Lỗi! Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();

                return true;
            }


        }
    }

    private void dangXuat() {
    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                3000,   // 3 sec
                                10, this);
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "Chưa cấp quyền truy cập vị trí", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    //----------Sự kiện GPS---------------------
    @Override
    public void onLocationChanged(Location location) {
        String str = "Latitude: " + location.getLatitude() + "Longitude: " + location.getLongitude();
        Log.e(TAG, "onLocationChanged: " + str);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    //Click vô vị trí xe
    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "marker Click............", Toast.LENGTH_SHORT).show();
        //Di chuyen camera den marker
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(marker.getPosition())
                .tilt(45)//goc nhin
                .zoom(17)// ti le zoom
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate, 1000, null);
        viewDialogMuonXe();

    }
}


