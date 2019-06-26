package com.example.siqueiraneto.doacao.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.siqueiraneto.doacao.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import Config.ConfiguracaoFirebase;
import Model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText campoNome, campoCpf, campoEmail, campoSenha;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.editCadastroNome);
        campoCpf = findViewById(R.id.editCadastroCpf);
        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
    }

    public void validarCadastroUsuario(View view){

        String nome = campoNome.getText().toString();
        String cpf = campoCpf.getText().toString();
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if(nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || senha.isEmpty()){
            Toast.makeText(CadastroActivity.this,
                    "Preencha todos os dados", Toast.LENGTH_SHORT).show();
        }else if(senha.length() < 4){
            Toast.makeText(CadastroActivity.this,
                    "Senha muito curta", Toast.LENGTH_SHORT).show();
        }else{
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setCpf(cpf);
            usuario.setEmail(email);
            usuario.setSenha(senha);

            this.cadastrarUsuario(usuario);
        }

    }

    public void cadastrarUsuario(final Usuario usuario){
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){


                            String idUsuario = task.getResult().getUser().getUid();
                            usuario.setId(idUsuario);
                            usuario.salvar();

                            startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
                            finish();
                            Toast.makeText(CadastroActivity.this,
                                    "Cadastrado!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CadastroActivity.this,
                                    "Falhou!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void voltarTela(View view){
        startActivity(new Intent(CadastroActivity.this, MainActivity.class));
    }
}
