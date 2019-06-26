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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import Config.ConfiguracaoFirebase;
import Model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText campoEmail, campoSenha;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.editLoginEmail);
        campoSenha = findViewById(R.id.editLoginSenha);
    }

    public void logarUsuario(Usuario usuario){
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, tela_mapaActivity.class));
                }else{
                    String excecao = "";
                    try{
                        throw task.getException();
                    }catch(FirebaseAuthInvalidUserException e){excecao = "Usuário não cadastrado";}
                    catch(FirebaseAuthInvalidCredentialsException e){excecao = "Email e senha não correspondem";}
                    catch (Exception e){excecao = "Erro: " + e.getMessage(); e.printStackTrace();}

                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void validarLoginUsuario(View view){
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if(email.isEmpty()){
            Toast.makeText(LoginActivity.this, "Digite email e senha para entrar",
                    Toast.LENGTH_SHORT).show();
        }else{
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setSenha(senha);

            logarUsuario(usuario);
        }
    }

    public void voltarTela(View view){
       startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}
