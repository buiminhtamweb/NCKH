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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.nckh.R;
import com.example.nckh.data.ConnectServer;
import com.example.nckh.data.ConnectSocketIO;
import com.example.nckh.object.Message;
import com.example.nckh.object.ThongTinTaiKhoan;
import com.example.nckh.object.XeDangRanh;
import com.example.nckh.util.DialogSupport;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.nckh.util.Constant.MAPS_ACTIVITY;
import static com.example.nckh.util.Constant.TK_ID;
import static com.example.nckh.util.Constant.TOKEN;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, GoogleMap.OnInfoWindowClickListener {

    private static final int MY_LOCATION_REQUEST_CODE = 2;
    private static final int REQUEST_LOCATION = 123;
    private static final String TAG = "MAPS_ACT";
    private LocationManager locationManager;
    private GoogleMap mMap;
    private AlertDialog mAlertDialog;

    private TextView mTvHoten;
    private ActionBar mActionBar;
    private Location mMyLocation;

    private String mToken;
    private String mTK_ID;
    private DialogSupport mDialogSupport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        mToken = SharedPreferencesHandler.getString(this, TOKEN);
        mTK_ID = SharedPreferencesHandler.getString(this, TK_ID);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        toolbar.collapseActionView();
        // Sử dụng Actionbar
        mActionBar = getActionBar();
//        mActionBar.setTitle(" Xin chào Bùi Minh Tâm");
        mActionBar.setLogo(R.mipmap.ic_logo);
        ((TextView) toolbar.getChildAt(0)).setTextSize(15);


        mDialogSupport = new DialogSupport(this, mAlertDialog, null);
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

        getDataFromServer();
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

        //--- Hien vi tri cua minh----
        //      Cap quyen truy cap GPS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            System.out.println("Vui lòng cho phép quyền truy cập vị trí");

            ActivityCompat.requestPermissions(this,

                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,

                            Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);

        } else {
            System.out.println("Đang định vị");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    3000,   // 5 sec
                    10, this);
            mMap.setMyLocationEnabled(true);
        }


//        mMap.setMyLocationEnabled(true);

        //Set event Maps
//        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        layDSXeDangRanh();

    }


    private void getDataFromServer() {
        ConnectServer.getInstance().getApi().layThongTinNguoiDung(mToken, mTK_ID).enqueue(new Callback<ThongTinTaiKhoan>() {
            @Override
            public void onResponse(Call<ThongTinTaiKhoan> call, Response<ThongTinTaiKhoan> response) {

                if (response.code() == 401) {
                    mDialogSupport.viewErrorSignOut(MapsActivity.this, "Đã hết phiên đăng nhập !");
                }
                if (response.code() == 400) {
                    try {
                        mDialogSupport.viewError(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if (response.code() == 200) {
                    if (response.body() != null) {
                        mActionBar.setTitle(" Xin chào " + response.body().getTKHOTEN());
                        mActionBar.setSubtitle(" " + response.body().getTKID());
                    }

                }
            }

            @Override
            public void onFailure(Call<ThongTinTaiKhoan> call, Throwable t) {
                mDialogSupport.viewErrorExitApp();
            }
        });
    }

    private void viewDialogMuonXe(final String sttXe, final double lat, final double longt) {
        final String toaDo = lat + "," + longt;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mượn xe");
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_muon_xe, null);
        final TextView textViewSTTXe = dialogLayout.findViewById(R.id.tv_stt_xe);
        textViewSTTXe.setText(sttXe);
        Button btnMuonXe = dialogLayout.findViewById(R.id.btn_muon_xe);
        btnMuonXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gửi socket.IO
                muonXe(Integer.parseInt(sttXe), lat, longt);
//                senDataSocket(0, textViewSTTXe.getText().toString(), toaDo);

            }
        });

        Button chiDuong = dialogLayout.findViewById(R.id.btn_chi_duong);
        chiDuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=" + toaDo));
                startActivity(intent);
            }
        });

        builder.setView(dialogLayout);

        Button btnHuy = dialogLayout.findViewById(R.id.btn_huy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                }
            }
        });
//        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(final DialogInterface dialogInterface, int i) {
//
//
//            }
//        });
        mAlertDialog = builder.create();
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();

    }


    private void addMarker(int sttXe, Double lat, Double lng) {
        LatLng dhCanTho = new LatLng(lat, lng);
        Marker markerNew = mMap.addMarker(new MarkerOptions().position(dhCanTho)
                .title(sttXe + "").snippet("Xe số " + sttXe));
        markerNew.showInfoWindow();

    }

    private void senDataSocket(final int n, final String sttXe, final String toado) {

        //...............Test View ................
        Intent test = new Intent(this, DangMuonXeActivity.class);
        test.putExtra("STTXE", sttXe);
        test.putExtra("TOA_DO", toado);
        startActivity(test); //Chuyển sang trạng thái dang mượn xe

        if (ConnectSocketIO.getInstance(MapsActivity.this).sendData("Client-send-unlock", "unlock", true)) { //Succes is true

            //------Gửi thành công---------
            Toast.makeText(this, "Đã gửi yêu cầu mở khóa xe", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DangMuonXeActivity.class);
            intent.putExtra("STTXE", sttXe);
            startActivity(intent); //Chuyển sang trạng thái dang mượn xe
        } else {
            if (n < 3) { //gọi lại 3 lần
                new CountDownTimer(2000, 1000) {
                    String textShowCalling = "Đang gọi";

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        senDataSocket(n + 1, sttXe, toado); //Đệ quy, tiếp tục gọi lại;
                    }
                }.start();
                Toast.makeText(MapsActivity.this, "Đang gọi lại lần " + n, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MapsActivity.this, "Lỗi! Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();

            }


        }
    }

    private void dangXuat() {
        SharedPreferencesHandler.wipeSharedPreferences(this);
    }

    private void muonXe(int idXe, double viTriMuonLat, double viTriMuonLng) {
        ConnectServer.getInstance().getApi().muonXe(mToken, mTK_ID, idXe, viTriMuonLat, viTriMuonLng).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.code() == 400) {
                    try {
                        mDialogSupport.viewError(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (response.code() == 200 && response.body() != null) {
                    Toast.makeText(MapsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MapsActivity.this, DangMuonXeActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                mDialogSupport.viewErrorExitApp();
            }
        });
    }

    private void layDSXeDangRanh() {
        ConnectServer.getInstance().getApi().layDSXeDangRanh().enqueue(new Callback<List<XeDangRanh>>() {
            @Override
            public void onResponse(Call<List<XeDangRanh>> call, Response<List<XeDangRanh>> response) {

                if (response.code() == 400) {
                    try {
                        mDialogSupport.viewError(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (response.code() == 200 && response.body() != null) {
                    List<XeDangRanh> dsXeDangRanh = new ArrayList<>(response.body());
                    for (XeDangRanh xedangranh : dsXeDangRanh) {
                        addMarker(xedangranh.getxEID(), xedangranh.getxELAT(), xedangranh.getxELNG());
                    }
                }

            }

            @Override
            public void onFailure(Call<List<XeDangRanh>> call, Throwable t) {
                mDialogSupport.viewErrorExitApp();
            }
        });
    }

    @Override
    public boolean onMyLocationButtonClick() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            viewAlertMessageNoGps();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Chưa cấp quyền vị trí", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,

                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,

                                Manifest.permission.ACCESS_COARSE_LOCATION,

                                Manifest.permission.BLUETOOTH,

                                Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_LOCATION);
            }
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000,   // 3 sec
                10, this);

        mMyLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (mMyLocation != null) {
            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(new LatLng(mMyLocation.getLatitude(), mMyLocation.getLongitude()))
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            mMap.animateCamera(cameraUpdate, 2000, null);
        }

        Toast.makeText(this, "Đang định vị tọa dộ....", Toast.LENGTH_SHORT).show();

        return true;
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
                        Toast.makeText(this, "Đang truy cập vị trí", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Chưa cấp quyền truy cập vị trí", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Vị trí hiện tại của tôi:\n" + location, Toast.LENGTH_LONG).show();
//        mMyLocation =
    }

    //----------Sự kiện GPS---------------------
    @Override
    public void onLocationChanged(Location location) {
        String str = "Latitude: " + location.getLatitude() + "Longitude: " + location.getLongitude();
        Log.e(TAG, "onLocationChanged: " + str);
        mMyLocation = location;
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate, 2000, null);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Đã bật GPS !", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent gpsOptionsIntent = new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(gpsOptionsIntent);
    }


    //Click vô vị trí xe
    @Override
    public void onInfoWindowClick(final Marker marker) {
        final LatLng latLng = marker.getPosition();
        //Di chuyen camera den marker
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(marker.getPosition())
                .zoom(18)// ti le zoom
                .build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate, 2000, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                viewDialogMuonXe(marker.getTitle(), latLng.latitude, latLng.longitude);
            }

            @Override
            public void onCancel() {

            }
        });


    }

    private void viewAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}


