
package com.example.rastreoapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UbicacionSender {
    public static void enviar(Context context, String nombre, double lat, double lon, String fecha, String hora) {
        String url = "https://vgbqvalyqimjndlwvhur.supabase.co/rest/v1/recorridos";
        String apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZnYnF2YWx5cWltam5kbHd2aHVyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDc4NTY0MzIsImV4cCI6MjA2MzQzMjQzMn0.DqlM-NcNjdXPcpzlMplgvZV6sxxd2i9WcVes8xZvt3U";

        JSONObject data = new JSONObject();
        try {
            data.put("nombre", nombre);
            data.put("latitud", lat);
            data.put("longitud", lon);
            data.put("fecha", fecha);
            data.put("hora", hora);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, data,
            response -> Log.d("SUPABASE", "Ubicación enviada"),
            error -> Log.e("SUPABASE", "Error al enviar", error)
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("apikey", apiKey);
                headers.put("Authorization", "Bearer " + apiKey);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(request);
    }
}
