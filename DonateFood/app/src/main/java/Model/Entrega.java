package Model;

import android.widget.ImageView;

import com.example.siqueiraneto.doacao.R;

import java.io.Serializable;

public class Entrega implements Serializable {


    private String tipo;
    private double quantidade;
    private String id_deposito;
    private String id_usuario;
    private String nomeDeposito;
    private ImageView im;


    public String getNomeDeposito() {
        return nomeDeposito;
    }

    public void setNomeDeposito(String nomeDeposito) {
        this.nomeDeposito = nomeDeposito;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public String getId_deposito() {
        return id_deposito;
    }

    public void setId_deposito(String id_deposito) {
        this.id_deposito = id_deposito;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString(){
        return "Tipo escolhido: " + this.getTipo().toUpperCase()  + "\nQuantidade: " + this.getQuantidade()
                 + "\nLocalização: " + this.getNomeDeposito() + "\n";
    }
}
