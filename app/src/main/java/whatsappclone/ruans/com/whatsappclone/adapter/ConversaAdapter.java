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
import whatsappclone.ruans.com.whatsappclone.model.ConversaModel;

/**
 * Created by ruancaetano on 04/02/18.
 */

public class ConversaAdapter extends ArrayAdapter<ConversaModel> {

    private Context context;
    private ArrayList<ConversaModel> conversas;

    public ConversaAdapter(@NonNull Context c, @NonNull ArrayList<ConversaModel> objects) {
        super(c, 0, objects);
        this.context = c;
        this.conversas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if (conversas != null){
            ConversaModel conversaModel = conversas.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_lista_conversa, parent,false);

            TextView nome = view.findViewById(R.id.tv_nome_conversa);
            nome.setText(conversaModel.getNome());

            TextView mensagem = view.findViewById(R.id.tv_mensagem_conversa);
            mensagem.setText("Última mensagem: " + conversaModel.getMensagem());
        }

        return view;
    }
}
