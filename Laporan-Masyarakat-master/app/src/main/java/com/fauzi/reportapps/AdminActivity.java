package com.fauzi.reportapps;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.fauzi.reportapps.R;
import com.fauzi.reportapps.ui.history.HistoryActivity;
import com.fauzi.reportapps.utils.Constant;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import im.delight.android.location.SimpleLocation;

public class AdminActivity extends AppCompatActivity {

    int REQ_PERMISSION = 100;
    double strCurrentLatitude;
    double strCurrentLongitude;
    String strCurrentLocation, strTitle;
    SimpleLocation simpleLocation;
    CardView cvPemadam, cvAmbulance, cvBencana, cvHistory, cvTentang, cvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        setStatusBar();
        setPermission();
        setLocation();
        setInitLayout();
        setCurrentLocation();
    }

    private void setLocation() {
        simpleLocation = new SimpleLocation(this);

        if (!simpleLocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(this);
        }

        //get location
        strCurrentLatitude = simpleLocation.getLatitude();
        strCurrentLongitude = simpleLocation.getLongitude();

        //set location lat long
        strCurrentLocation = strCurrentLatitude + "," + strCurrentLongitude;
    }

    private void setInitLayout() {
        cvPemadam = findViewById(R.id.cvPemadam);
        cvAmbulance = findViewById(R.id.cvAmbulance);
        cvBencana = findViewById(R.id.cvBencana);
        cvHistory = findViewById(R.id.cvHistory);
        cvTentang = findViewById(R.id.cvTentang);
        cvLogout = findViewById(R.id.cvLogout);



        cvHistory.setOnClickListener(v -> {
            Intent intent = new Intent(com.fauzi.reportapps.AdminActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        cvLogout.setOnClickListener(v -> {
            Intent intent = new Intent(com.fauzi.reportapps.AdminActivity.this, ActivityRegister.class);
            startActivity(intent);
        });

    }

    private void setCurrentLocation() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(strCurrentLatitude, strCurrentLongitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Constant.lokasiPengaduan = addressList.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_PERMISSION && resultCode == RESULT_OK) {

        }
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }

}