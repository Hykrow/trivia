package com.example.crampter;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accueil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Quiz q = new Quiz();

       RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Response.Listener<String> responseListener = new TriviaResponseListener(q, getApplicationContext());
        Response.ErrorListener errorListener = new TriviaErrorListener(q, getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, "https://the-trivia-api.com/v2/questions/", responseListener, errorListener);
        requestQueue.add(request);

    }
}