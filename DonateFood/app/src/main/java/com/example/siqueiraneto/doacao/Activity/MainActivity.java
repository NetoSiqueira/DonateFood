package com.example.siqueiraneto.doacao.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.siqueiraneto.doacao.R;
import com.google.firebase.database.DatabaseReference;

import Config.ConfiguracaoFirebase;
import Model.Deposito;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    public void abrirTelaLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));

    }

    public void abrirTelaCadastro(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }
}
