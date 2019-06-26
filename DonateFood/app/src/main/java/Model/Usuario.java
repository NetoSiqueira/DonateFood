package Model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import Config.ConfiguracaoFirebase;

public class    Usuario implements Comparable<Usuario>{

    private String id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private double pontuacao;

    public void salvar(){

        DatabaseReference firebaseref = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference usuarios = firebaseref.child("usuarios").child(getId());

        usuarios.setValue(this);
    }

    public double getPontuacao() {
        return pontuacao;
    }

    public void incrementPontuacao(double quantidade){
        this.pontuacao += quantidade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString(){
        return this.nome + ": " + this.pontuacao;
    }

    @Override
    public int compareTo(@NonNull Usuario outroUsuario) {

        if(this.pontuacao < outroUsuario.getPontuacao())
            return 1;
        if(this.pontuacao > outroUsuario.getPontuacao())
            return -1;

        return 0;
    }
}