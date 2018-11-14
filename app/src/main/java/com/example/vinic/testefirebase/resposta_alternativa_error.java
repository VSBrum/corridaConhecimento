package com.example.vinic.testefirebase;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class resposta_alternativa_error extends AppCompatActivity {

    FirebaseDatabase databaseE;
    DatabaseReference myRefPerguntaE;

    TextView rspErrada;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.resposta_alternativa_error);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        rspErrada = findViewById(R.id.rspCorreta);

        databaseE = FirebaseDatabase.getInstance();
        myRefPerguntaE = databaseE.getReference("pergunta");

        rspErrada.setTextColor(getResources().getColor(R.color.Black));
        rspErrada.setTextSize(18);
        rspErrada.setText("Alternativa Correta: " + MainActivity.textoRespostCorreta);

        myRefPerguntaE.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                final int MILISEGUNDOS = 7000;
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        Intent mainActivity = new Intent(resposta_alternativa_error.this, MainActivity.class);
                        startActivity(mainActivity);
                    }
                }, MILISEGUNDOS);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
