
package com.example.rastreoapp;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LocationService extends Service {
    private FusedLocationProviderClient fusedLocationClient;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        runnable = new Runnable() {
            @Override
            public void run() {
                fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        SharedPreferences prefs = getSharedPreferences("datos", MODE_PRIVATE);
                        String nombre = prefs.getString("nombre", "empleado");
                        String fecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        String hora = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        UbicacionSender.enviar(getApplicationContext(), nombre, location.getLatitude(), location.getLongitude(), fecha, hora);
                    }
                });
                handler.postDelayed(this, 30000); // cada 30 segundos
            }
        };

        handler.post(runnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
