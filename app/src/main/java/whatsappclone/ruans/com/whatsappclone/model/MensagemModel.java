package whatsappclone.ruans.com.whatsappclone.model;

/**
 * Created by ruancaetano on 03/02/18.
 */

public class MensagemModel {

    private String idUsuario;
    private String mensagem;

    public MensagemModel() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
