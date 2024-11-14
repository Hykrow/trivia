package com.example.crampter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class TriviaResponseListener implements Response.Listener<String> {
    Quiz q;
    Context context;
    public TriviaResponseListener(Quiz q, Context context ) {
        this.q = q;
        this.context = context;
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONArray jsonResponse = new JSONArray(response);
            for(int i = 0; i < jsonResponse.length(); ++i){
                Carte carte = new Carte();
                Log.i("gugu", "q");

                JSONObject curObj = jsonResponse.getJSONObject(i);
                JSONObject arr = curObj.getJSONObject("question");
                String question = arr.getString("text");
                carte.setQuestion(question);
                JSONArray test = curObj.getJSONArray("incorrectAnswers");
                carte.setBonneReponse(curObj.getString("correctAnswer"));

                for(int j = 0; j < test.length(); ++j){
                    String q = test.getString(j);
                    carte.addMauvaiseReponse(q);
                   Log.i("gugu", q);

                }
                Log.i("gugu", curObj.getString("category"));
                q.ajouterCarte(carte);
            }
         //   JSONObject q0 = (JSONObject) jsonResponse.get("question");
     //       String q0txt = q0.getString("text");
       //     Log.i("salala", q0txt);
        } catch (JSONException e) {
            q.creerQuizDefaut();


        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("q", q);
        startActivity(context, intent, null);

    }
}
