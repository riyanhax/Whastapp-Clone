package whatsappclone.ruans.com.whatsappclone.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import whatsappclone.ruans.com.whatsappclone.R;
import whatsappclone.ruans.com.whatsappclone.activity.ConversaActivity;
import whatsappclone.ruans.com.whatsappclone.adapter.ConversaAdapter;
import whatsappclone.ruans.com.whatsappclone.config.ConfiguracaoFirebase;
import whatsappclone.ruans.com.whatsappclone.helper.Preferencias;
import whatsappclone.ruans.com.whatsappclone.model.ContatoModel;
import whatsappclone.ruans.com.whatsappclone.model.ConversaModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {


    private DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
    private Preferencias preferencias;
    private String idUserAtual;
    private ValueEventListener valueEventListenerConversas;
    private ArrayList<ConversaModel> conversas;
    private ConversaAdapter adapter;


    public ConversasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_conversas, container, false);
        preferencias = new Preferencias(getContext());
        idUserAtual = preferencias.getId();

        final ListView listaConversas = view.findViewById(R.id.lv_conversas);

        conversas = new ArrayList<>();
        adapter = new ConversaAdapter(
                getContext(),
                conversas
        );
        listaConversas.setAdapter(adapter);



        valueEventListenerConversas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversas.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    conversas.add(
                            data.getValue(ConversaModel.class)
                    );
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference
                .child("conversas")
                .child(idUserAtual)
                .addValueEventListener(valueEventListenerConversas);



        listaConversas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ConversaModel conversaModel = conversas.get(i);
                Intent intent = new Intent(view.getContext(), ConversaActivity.class);
                intent.putExtra("idContato",conversaModel.getIdUsuario());
                intent.putExtra("nome",conversaModel.getNome());
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        databaseReference
                .child("conversas")
                .child(idUserAtual)
                .removeEventListener(valueEventListenerConversas);
    }
}
