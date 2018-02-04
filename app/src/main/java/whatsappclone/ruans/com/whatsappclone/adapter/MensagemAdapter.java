package whatsappclone.ruans.com.whatsappclone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import whatsappclone.ruans.com.whatsappclone.R;
import whatsappclone.ruans.com.whatsappclone.helper.Preferencias;
import whatsappclone.ruans.com.whatsappclone.model.MensagemModel;

/**
 * Created by ruancaetano on 03/02/18.
 */

public class MensagemAdapter extends ArrayAdapter<MensagemModel> {

    private Context context;
    private ArrayList<MensagemModel> mensagens;

    public MensagemAdapter(@NonNull Context c, @NonNull ArrayList<MensagemModel> objects) {
        super(c,0, objects);
        this.context = c;
        this.mensagens = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if (mensagens != null){
            Preferencias preferencias = new Preferencias(context);
            String idUserAtual = preferencias.getId();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


            MensagemModel mensagemModel = mensagens.get(position);

            if (idUserAtual.equals(mensagemModel.getIdUsuario())){
                view = inflater.inflate(R.layout.item_mensagem_enviada,parent,false);
                TextView textoMensagem = view.findViewById(R.id.tv_mensagem_enviada);
                textoMensagem.setText(mensagemModel.getMensagem());
            }else{
                view = inflater.inflate(R.layout.item_mensagem_recebida,parent,false);
                TextView textoMensagem = view.findViewById(R.id.tv_mensagem_recebida);
                textoMensagem.setText(mensagemModel.getMensagem());
            }
        }

        return view;
    }
}
