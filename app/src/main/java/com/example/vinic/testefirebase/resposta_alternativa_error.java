package com.example.vinic.testefirebase;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class resposta_alternativa_error extends AppCompatActivity {

    FirebaseDatabase databaseE;
    DatabaseReference myRefPerguntaE;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.resposta_alternativa_error);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        databaseE = FirebaseDatabase.getInstance();
        myRefPerguntaE = databaseE.getReference("pergunta");

        /*myRefPerguntaE.child("0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setContentView(R.layout.activity_main);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }
}
