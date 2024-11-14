package com.example.crampter;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;


public class MainActivity extends AppCompatActivity {



    int[] salam(int a){
        int[] crampteur;
        crampteur = new int[10];
        return crampteur;
    }
    Quiz q ;
    Random r;
    int ith ;
    int good_ans;
    int tot_ans;
    int no_tries = 0;
    int nb_help = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Log.i("salut", "coucou");
        r = new Random();
        q = new Quiz();
        good_ans = 0;
        tot_ans = 0;
       // LinearLayout layout = new LinearLayout(this);
        //setContentView(layout);

        ViewGroup layout = findViewById(android.R.id.content);
        layout.removeAllViews();
        Button bouton = new Button (getApplicationContext());
        bouton.setText("clique");
        layout.addView(bouton);
        ViewGroup.LayoutParams layoutParams = bouton.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        bouton.setLayoutParams(layoutParams);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "salut", Toast.LENGTH_SHORT).show();

            }
        });
        Log.i("gugu", "g");
/*
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Response.Listener<String> responseListener = new TriviaResponseListener(q, getApplicationContext());
        Response.ErrorListener errorListener = new TriviaErrorListener();
        StringRequest request = new StringRequest(Request.Method.GET, "https://the-trivia-api.com/v2/questions/", responseListener, errorListener);
        requestQueue.add(request);
        Log.i("gugu", "g2");
        */
        Intent curI = getIntent();
        q = (Quiz) curI.getSerializableExtra("q");

        load_card(q.getCartes().get(0));
        ith = 1;
        Log.i("gugu", "g3");










    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    public void load_card(Carte c){
        LinearLayout layout = new LinearLayout(getApplicationContext());

        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);
        Vector<String> propositions = c.getMauvaisesReponses();
        propositions.add(c.getBonneReponse());
        Collections.shuffle(propositions);
        TextView txt = new TextView(getApplicationContext());
        txt.setText(" \n" + c.getQuestion());
       // txt.setText(getString(R.string.tonScoreEst).formatted("s", 0.3, 0.1));




       // txt.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
        layout.addView(txt);
        ViewGroup.LayoutParams layouttParams = txt.getLayoutParams();
        layouttParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        txt.setLayoutParams(layouttParams);
        Button help = new Button(getApplicationContext());
        help.setText(R.string.texteAide);


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nb_help <=1 ){
                    Intent search_intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    //    search_intent.setType("text/plain");

                    search_intent.putExtra(SearchManager.QUERY, c.getQuestion());
                    startActivity(search_intent);
                    nb_help++;
                }

            }
        });

        layout.addView(help);
        ViewGroup.LayoutParams layoutParams = help.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        help.setLayoutParams(layoutParams);
        no_tries = 0;
        for(int i = 0; i < propositions.size(); ++i){
            Button button = new Button(getApplicationContext());
            button = new Button(getApplicationContext());
            button.setText(propositions.get(i));


            if(propositions.get(i).equals( c.getBonneReponse()) ){
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ++tot_ans;
                        ++no_tries;
                        bonne_rep(view);

                    }
                });
            }else{
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ++tot_ans;
                        ++no_tries;

                        mauvaise_rep(view);

                    }
                });
            }
            layout.addView(button);
            ViewGroup.LayoutParams layouutParams = button.getLayoutParams();
            layouutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            button.setLayoutParams(layouutParams);
        }
        ViewCompat.setOnApplyWindowInsetsListener(layout, (v, insets) -> {
            int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top;
            v.setPadding(0, statusBarHeight, 0, 0);
            return insets;
        });

   /*     Button t1 = findViewById(R.id.button1);
        Button t2 = findViewById(R.id.button2);
        Button t3 = findViewById(R.id.button3);
        Button t4 = findViewById(R.id.button4);
        TextView t = findViewById(R.id.textView_question);
        t1.setText(c.getBonneReponse());
        t2.setText(c.getMauvaisesReponses().get(0));
        t3.setText(c.getMauvaisesReponses().get(1));
        t4.setText(c.getMauvaisesReponses().get(2));
        t.setText(c.getQuestion());
        */


    }
    public void tirer(View view) {

        int n = r.nextInt(6)+1;
        System.out.println(n);
        TextView t = findViewById(R.id.textView_resultat_tirage);

        t.setText(String.valueOf(n));
        Toast to = new Toast(getApplicationContext());
        to.setText("bois!");
        to.show();
    }

    public void bonne_rep(View view) {
        Toast t = new Toast(getApplicationContext());
        t.setText("Bravo !");
        t.show();
        if(no_tries == 1){
            good_ans+=2;
        }else if(no_tries == 2){
            good_ans ++;
        }

        if(ith < q.getCartes().size()){
            load_card(q.getCarte(ith));
            ++ith;

        }else{ // go to score page
            Intent intent_score = new Intent(this, ScoreActivity.class);
            intent_score.putExtra("good_ans", String.valueOf(good_ans));
            tot_ans= q.getNbCartes()*2;
            intent_score.putExtra("tot_ans", String.valueOf(tot_ans));

            startActivity(intent_score);
            finish();

        }

    }
    public void mauvaise_rep(View view){
        Toast t = new Toast(getApplicationContext());
        t.setText("Mauvaise réponse !");
        t.show();
    }
}