
package com.example.rastreoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnStart, btnStop;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        SharedPreferences prefs = getSharedPreferences("datos", MODE_PRIVATE);
        String userName = prefs.getString("nombre", null);

        if (userName == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ingresa tu nombre");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setCancelable(false);
            builder.setPositiveButton("Guardar", (dialog, which) -> {
                String nombreIngresado = input.getText().toString().trim();
                prefs.edit().putString("nombre", nombreIngresado).apply();
            });

            builder.show();
        }

        serviceIntent = new Intent(this, LocationService.class);

        btnStart.setOnClickListener(v -> startService(serviceIntent));
        btnStop.setOnClickListener(v -> stopService(serviceIntent));
    }
}
