package com.example.vinic.testefirebase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    public static String textoRespostCorreta = "";
    int cont = 1;

    String gabaritoA;
    String gabaritoB;
    String gabaritoC;
    String gabaritoD;
    String perguntaAnteriror;
    String value;

    TextView campoPergunta;
    ImageView imgResposta;

    RadioGroup radiogroup;
    RadioButton alternativaA;
    RadioButton alternativaB;
    RadioButton alternativaC;
    RadioButton alternativaD;
    Button responder;

    FirebaseDatabase database;
    DatabaseReference myRefPergunta;
    DatabaseReference myRefResposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        database = FirebaseDatabase.getInstance();
        myRefPergunta = database.getReference("pergunta");
        myRefResposta = database.getReference("respostas");

        campoPergunta = findViewById(R.id.campoPergunta);
        radiogroup = findViewById(R.id.radioGroup);
        alternativaA = findViewById(R.id.alternativaA);
        alternativaB = findViewById(R.id.alternativaB);
        alternativaC = findViewById(R.id.alternativaC);
        alternativaD = findViewById(R.id.alternativaD);
        responder = findViewById(R.id.btnResposta);

        //Pegando a pergunta do firebase e mostrando na tela
        myRefPergunta.child("0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(String.class);
                perguntaAnteriror = value;
                campoPergunta.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Pegando as alternativas e mostrando na tela
        myRefResposta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot respostaSnapshot) {

                alternativaA.setTextColor(getResources().getColor(R.color.Black));
                alternativaB.setTextColor(getResources().getColor(R.color.Black));
                alternativaC.setTextColor(getResources().getColor(R.color.Black));
                alternativaD.setTextColor(getResources().getColor(R.color.Black));

                Iterable<DataSnapshot> respostaChildren = respostaSnapshot.getChildren();
                for (DataSnapshot resposta : respostaChildren) {

                    String tituloResposta = resposta.child("0").getValue().toString();
                    String respostaCorreta = resposta.child("1").getValue().toString();

                    switch (cont) {
                        case 1:
                            alternativaA.setText(tituloResposta);
                            if (respostaCorreta.equals("true")) {
                                gabaritoA = "alternativa A";
                                textoRespostCorreta = tituloResposta;
                            } else {
                                gabaritoA = "";
                            }
                        case 2:
                            alternativaB.setText(tituloResposta);
                            if (respostaCorreta.equals("true")) {
                                gabaritoB = "alternativa B";
                                textoRespostCorreta = tituloResposta;
                            } else {
                                gabaritoB = "";
                            }
                        case 3:
                            alternativaC.setText(tituloResposta);
                            if (respostaCorreta.equals("true")) {
                                gabaritoC = "alternativa C";
                                textoRespostCorreta = tituloResposta;
                            } else {
                                gabaritoC = "";
                            }
                        case 4:
                            alternativaD.setText(tituloResposta);
                            if (respostaCorreta.equals("true")) {
                                gabaritoD = "alternativa D";
                                textoRespostCorreta = tituloResposta;
                            } else {
                                gabaritoD = "";
                            }
                    }
                    cont++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        /*if(!alternativaA.isChecked() && !alternativaB.isChecked() && !alternativaC.isChecked() && !alternativaD.isChecked()){
            responder.setEnabled(true);
        }else{
            responder.setEnabled(false);
        }*/

        //Botão de responder,mudando a tela de acordo se a resposta esta certa ou errada
        responder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                String url = "https://www.evonegocios.com/api_pi/alguem_respondeu.php";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if ((alternativaA.isChecked() && gabaritoA.equals("alternativa A")) || (alternativaB.isChecked() && gabaritoB.equals("alternativa B")) ||
                                        (alternativaC.isChecked() && gabaritoC.equals("alternativa C")) || (alternativaD.isChecked() && gabaritoD.equals("alternativa D"))
                                        ) {
                                    ToothReadWrite.WriteBuffer((byte) 1);
                                    Intent rC = new Intent(MainActivity.this, resposta_alternativa_success.class);
                                    startActivity(rC);
                                    MainActivity.this.finish();
                                } else {
                                    Intent rE = new Intent(MainActivity.this, resposta_alternativa_error.class);
                                    startActivity(rE);
                                    MainActivity.this.finish();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //mTextView.setText("Serviço indisponível");
                    }
                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });

    }
}


