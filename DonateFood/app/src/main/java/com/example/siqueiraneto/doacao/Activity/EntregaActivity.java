package com.example.siqueiraneto.doacao.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.siqueiraneto.doacao.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import Config.ConfiguracaoFirebase;
import Model.Deposito;
import Model.Entrega;

public class EntregaActivity extends AppCompatActivity {


    private ImageView im;
    private Entrega entrega;
    private Deposito deposito;
    private EditText quantidadeDoacao;
    DatabaseReference firebaseref = ConfiguracaoFirebase.getFirebaseDatabase();
    DatabaseReference entregas = firebaseref.child("entregas");

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega);

        this.quantidadeDoacao = findViewById(R.id.qtdDoacao);

        Bundle extras = getIntent().getExtras();
        deposito = (Deposito) extras.getSerializable("deposito");

        this.im = findViewById(R.id.foto);
        this.entrega = new Entrega();
        this.entrega.setTipo("racao");
        this.entrega.setId_usuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
        this.entrega.setId_deposito(deposito.getId());
        this.entrega.setNomeDeposito(deposito.getNome());
    }

    public void entregarDoacao(View view){

        String quantidadeDoacao = this.quantidadeDoacao.getText().toString();
        if(quantidadeDoacao.isEmpty()){
            Toast.makeText(EntregaActivity.this, "Digite uma quantidade",
                    Toast.LENGTH_SHORT).show();
        }else{
            Double quantidade = Double.parseDouble(quantidadeDoacao);
            this.entrega.setQuantidade(quantidade);

            this.entregas.push().setValue(this.entrega);
            Intent intent = new Intent();
            intent.setAction("com.example.siqueiraneto.doacao.Activity.VERIFICA_RANK");
            sendBroadcast(intent);
            startActivity(new Intent(EntregaActivity.this, Historico.class));
        }
    }

    public void racao(View view){

        this.im.setImageResource(R.drawable.racoes);
        this.entrega.setTipo("racao");
    }

    public void agua(View view){

        this.im.setImageResource(R.drawable.tijela);
        this.entrega.setTipo("agua");
    }
}
