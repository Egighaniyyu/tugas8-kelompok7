package com.example.tugaspraktikum8;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView txt_positif, txt_rawat, txt_sembuh, txt_meninggal;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_positif = findViewById(R.id.txt_positif);
        txt_rawat = findViewById(R.id.txt_rawat);
        txt_sembuh = findViewById(R.id.txt_sembuh);
        txt_meninggal = findViewById(R.id.txt_meninggal);

        tampilData();
    }

    private void tampilData(){
        loading = ProgressDialog.show(MainActivity.this, "Memuat data", "Harap menunggu");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://apicovid19indonesia-v2.vercel.app/api/indonesia";
        JSONObject jsonObject = new JSONObject();
        final String requestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response.toString());
                    String positif = jo.getString("positif");
                    String rawat = jo.getString("dirawat");
                    String sembuh = jo.getString("sembuh");
                    String meninggal = jo.getString("meninggal");

                    txt_positif.setText(positif);
                    txt_rawat.setText(rawat);
                    txt_sembuh.setText(sembuh);
                    txt_meninggal.setText(meninggal);

                    loading.cancel();
                    Toast.makeText(MainActivity.this, "Berhasil mengambil rest api", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.cancel();
                Toast.makeText(MainActivity.this, "Gagal mengambil rest api" + error, Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(stringRequest);
    }
}