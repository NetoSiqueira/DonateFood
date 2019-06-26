package com.example.siqueiraneto.doacao.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.siqueiraneto.doacao.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Config.ConfiguracaoFirebase;
import Model.Entrega;
import Model.Usuario;

public class RankActivity extends AppCompatActivity {

    DatabaseReference firebaseref = ConfiguracaoFirebase.getFirebaseDatabase();
    DatabaseReference usuariosDoBanco = firebaseref.child("usuarios");
    DatabaseReference entregasDoBanco = firebaseref.child("entregas");


    ListView usuariosRankeados;
    ArrayAdapter<Usuario> adapter;

    List<Usuario> usuarios;
    List<Entrega> entregas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        usuarios = new ArrayList<Usuario>();
        usuariosRankeados = findViewById(R.id.usuariosRankeados);

        adapter = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_1, usuarios);

        usuariosDoBanco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Usuario usuario = data.getValue(Usuario.class);
                    usuarios.add(usuario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        entregasDoBanco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Entrega entrega = data.getValue(Entrega.class);
                    for(Usuario u : usuarios){
                        if(u.getId().equals(entrega.getId_usuario()))
                            u.incrementPontuacao(entrega.getQuantidade());
                    }
                }
                Collections.sort(usuarios);
                usuariosRankeados.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void voltarParaMenu(View view){
        startActivity(new Intent(RankActivity.this, tela_mapaActivity.class));
    }
}
