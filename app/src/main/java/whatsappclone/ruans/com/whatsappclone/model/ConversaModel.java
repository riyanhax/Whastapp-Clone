package whatsappclone.ruans.com.whatsappclone.model;

/**
 * Created by ruancaetano on 04/02/18.
 */

public class ConversaModel {

    private String idUsuario;
    private String nome;
    private String mensagem;

    public ConversaModel() {
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

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
