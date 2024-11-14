package com.example.crampter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class TriviaErrorListener implements Response.ErrorListener {
    Quiz q;
    Context context;

    public TriviaErrorListener(Quiz q, Context context) {
        this.q = q;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        q.creerQuizDefaut();
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("q", q);
        startActivity(context, intent, null);
    }

}
