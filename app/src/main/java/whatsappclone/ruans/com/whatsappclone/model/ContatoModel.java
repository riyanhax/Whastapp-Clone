package whatsappclone.ruans.com.whatsappclone.model;

/**
 * Created by ruancaetano on 03/02/18.
 */

public class ContatoModel {
    private String idUsuario;
    private String nome;
    private String email;


    public ContatoModel() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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
}
