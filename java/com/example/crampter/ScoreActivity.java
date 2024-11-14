package com.example.crampter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ScoreActivity extends AppCompatActivity {


    float s;
    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();

  //      LinearLayout layout = findViewById(android.R.id.content);
    //    layout.setOrientation(LinearLayout.VERTICAL);


        int good_ans = Integer.valueOf(intent.getStringExtra("good_ans"));
        int tot_ans =  Integer.valueOf(intent.getStringExtra("tot_ans"));
        //   double s = (double)Integer.parseInt(intent.getStringExtra("good_ans"))/(double)Integer.parseInt(intent.getStringExtra("tot_ans"));
        s = (float)good_ans / (float)tot_ans;
        TextView txtv = findViewById(R.id.textViewCongrats);
        txtv.setText("GG, ton score est " + good_ans + ", tu aurais pu avoir " + tot_ans);
        EditText psdo = findViewById(R.id.editTextPseudo);
        Button postTxtv = findViewById(R.id.button);
        postTxtv.setVisibility(View.INVISIBLE);
        psdo.setVisibility(View.INVISIBLE);


        SharedPreferences sharedPreferences = getSharedPreferences("historique", Context.MODE_PRIVATE);

        if(sharedPreferences.contains("best_score")){
            float bscore = sharedPreferences.getFloat("best_score", -1);
            String bscore_player = sharedPreferences.getString("player", "");
            if(bscore <s){ // nouveau best score.
                txtv.setText(getString(R.string.bravo) + s);
                postTxtv.setVisibility(View.VISIBLE);
                psdo.setVisibility(View.VISIBLE);

            }else{
              //  txtv.setText(getString(R.string.tonScoreEst)+ s+  getString(R.string.TuTesFaitBattrePar) + bscore_player + getString(R.string.avecUnScoreDe) + bscore);
                txtv.setText(String.format(getString(R.string.scoreBeaten), s, bscore_player, bscore));

            }
        }else{
            txtv.setText(getString(R.string.firstPlayer) + s );

            postTxtv.setVisibility(View.VISIBLE);
            psdo.setVisibility(View.VISIBLE);


        }

    }

    public void button_post_pseudo(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("historique", Context.MODE_PRIVATE);
        EditText psdo = findViewById(R.id.editTextPseudo);


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("best_score", s);
        editor.putString("player", psdo.getText().toString());
        editor.apply();
    }

}