package whatsappclone.ruans.com.whatsappclone.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import whatsappclone.ruans.com.whatsappclone.R;
import whatsappclone.ruans.com.whatsappclone.activity.ConversaActivity;
import whatsappclone.ruans.com.whatsappclone.adapter.ContatoAdapter;
import whatsappclone.ruans.com.whatsappclone.config.ConfiguracaoFirebase;
import whatsappclone.ruans.com.whatsappclone.helper.Preferencias;
import whatsappclone.ruans.com.whatsappclone.model.ContatoModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listaContatos;
    private ContatoAdapter adapter;
    private ArrayList<ContatoModel> contatos;
    private DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
    private Preferencias preferencias;
    private ValueEventListener valueEventListenerContatos;

    public ContatosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contatos, container, false);

        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contatos.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    ContatoModel contato = dados.getValue(ContatoModel.class);
                    contatos.add(contato);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        preferencias = new Preferencias(getContext());

        contatos = new ArrayList<>();

        listaContatos = view.findViewById(R.id.lv_contatos);
        adapter = new ContatoAdapter(
                getActivity(),
                contatos
        );
        listaContatos.setAdapter(adapter);

        //Recuperar dados firebase
        databaseReference = databaseReference.child("contatos").child(preferencias.getId());



        listaContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ConversaActivity.class);
                ContatoModel contato = contatos.get(i);
                intent.putExtra("idContato",contato.getIdUsuario());
                intent.putExtra("nome",contato.getNome());

                startActivity(intent);
            }
        });
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(valueEventListenerContatos);
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListenerContatos);
    }
}
