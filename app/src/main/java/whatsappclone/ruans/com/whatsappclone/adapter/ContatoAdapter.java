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
import whatsappclone.ruans.com.whatsappclone.model.ContatoModel;

/**
 * Created by ruancaetano on 03/02/18.
 */

public class ContatoAdapter extends ArrayAdapter<ContatoModel> {


    private Context context;
    private ArrayList<ContatoModel> contatos;

    public ContatoAdapter(@NonNull Context c, @NonNull ArrayList<ContatoModel> objects) {
        super(c, 0, objects);
        this.contatos = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if (contatos != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_contato,parent,false);

            TextView nomeContato = view.findViewById(R.id.tv_nome_contato);
            nomeContato.setText(contatos.get(position).getNome());

            TextView emailContato = view.findViewById(R.id.tv_email_contato);
            emailContato.setText(contatos.get(position).getEmail());
        }

        return view;
    }
}
