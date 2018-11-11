package com.example.vinic.testefirebase;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class resposta_alternativa_success extends AppCompatActivity {

    FirebaseDatabase databaseS;
    DatabaseReference myRefPerguntaS;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.resposta_alternativa_success);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        databaseS = FirebaseDatabase.getInstance();
        myRefPerguntaS = databaseS.getReference("pergunta");

        myRefPerguntaS.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }
            @Override

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                final int MILISEGUNDOS = 7000;
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        Intent mainActivity = new Intent(resposta_alternativa_success.this, MainActivity.class);
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


