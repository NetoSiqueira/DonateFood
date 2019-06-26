package com.example.siqueiraneto.doacao.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.siqueiraneto.doacao.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Config.ConfiguracaoFirebase;
import Model.Entrega;

public class Historico extends AppCompatActivity {

    DatabaseReference firebaseref = ConfiguracaoFirebase.getFirebaseDatabase();
    DatabaseReference entregas = firebaseref.child("entregas");


    List<Entrega> minhasEntregas;
    ListView entregasLista;
    ArrayAdapter<Entrega> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        minhasEntregas = new ArrayList<Entrega>();
        entregasLista = findViewById(R.id.entregasLista);

        Query minhasEntregasConsulta = entregas.orderByChild("id_usuario").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        adapter = new ArrayAdapter<Entrega>(this, android.R.layout.simple_list_item_1, minhasEntregas);

        minhasEntregasConsulta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                forzim:
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Entrega entrega = data.getValue(Entrega.class);
                    minhasEntregas.add(entrega);
                }
                entregasLista.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                
            }
        });


    }

    public void voltarParaMenu(View view){
        startActivity(new Intent(Historico.this, tela_mapaActivity.class));
    }
}
