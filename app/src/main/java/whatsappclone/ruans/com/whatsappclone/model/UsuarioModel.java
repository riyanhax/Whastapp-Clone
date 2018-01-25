package whatsappclone.ruans.com.whatsappclone.model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import whatsappclone.ruans.com.whatsappclone.activity.CadastroActivity;
import whatsappclone.ruans.com.whatsappclone.config.ConfiguracaoFirebase;

/**
 * Created by ruans on 14/01/2018.
 */

public class UsuarioModel {

    private String id;
    private String nome;
    private String email;
    private String senha;

    public UsuarioModel(){

    }

    @Exclude
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

    public Task<Void> salvar(){
        DatabaseReference database = ConfiguracaoFirebase.getFirebaseDatabase();
        return database.child("usuarios").child(getId()).setValue(this);
    }
}
