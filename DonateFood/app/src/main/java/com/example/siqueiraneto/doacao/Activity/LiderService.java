package com.example.siqueiraneto.doacao.Activity;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

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

public class LiderService extends Service {

    DatabaseReference firebaseref = ConfiguracaoFirebase.getFirebaseDatabase();
    DatabaseReference usuariosDoBanco = firebaseref.child("usuarios");
    DatabaseReference entregasDoBanco = firebaseref.child("entregas");
    DatabaseReference lider = firebaseref.child("lider");
    NotificationCompat.Builder mBuilder;

    List<Usuario> usuarios;

    public void notifica(String liderAtual){
        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.doeracao)
                .setContentTitle("O líder mudou!")
                .setContentText(liderAtual + " agora está liderando.");

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        usuarios = new ArrayList();

        usuariosDoBanco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                forzim:
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
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lider.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String liderAtual = dataSnapshot.getValue().toString();
                Usuario liderFirebase = usuarios.get(0);

                if(!liderAtual.equals(liderFirebase.getNome())){

                    lider.setValue(liderFirebase.getNome());
                    notifica(liderFirebase.getNome());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }
}
