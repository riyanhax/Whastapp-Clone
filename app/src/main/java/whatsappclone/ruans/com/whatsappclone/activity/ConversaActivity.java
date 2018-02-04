package whatsappclone.ruans.com.whatsappclone.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import whatsappclone.ruans.com.whatsappclone.R;
import whatsappclone.ruans.com.whatsappclone.adapter.MensagemAdapter;
import whatsappclone.ruans.com.whatsappclone.config.ConfiguracaoFirebase;
import whatsappclone.ruans.com.whatsappclone.helper.Preferencias;
import whatsappclone.ruans.com.whatsappclone.model.ConversaModel;
import whatsappclone.ruans.com.whatsappclone.model.MensagemModel;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String nome;
    private String idContato;
    private String idUserAtual;
    private ImageButton botaoEnviar;
    private ListView listaMensagens;
    private EditText inputMensagem;
    private Preferencias preferencias;
    private ArrayList<MensagemModel> mensagens;
    private MensagemAdapter adapter;
    private DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
    private ValueEventListener eventListenerMensagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);
        preferencias = new Preferencias(ConversaActivity.this);
        idUserAtual = preferencias.getId();

        botaoEnviar = findViewById(R.id.bt_enviar);
        inputMensagem = findViewById(R.id.input_mensagem);
        listaMensagens = findViewById(R.id.lv_mensagens);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            nome = extra.getString("nome");
            idContato = extra.getString("idContato");
        }

        toolbar = findViewById(R.id.tb_conversa);
        toolbar.setTitle(nome);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);


        mensagens = new ArrayList<>();
        adapter = new MensagemAdapter(
                ConversaActivity.this,
                mensagens
        );
        listaMensagens.setAdapter(adapter);

        //Recuperando mensagens
        eventListenerMensagens = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mensagens.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    MensagemModel mensagemModel = data.getValue(MensagemModel.class);
                    mensagens.add(mensagemModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        databaseReference.child("mensagens")
                .child(idUserAtual)
                .child(idContato)
                .addValueEventListener(eventListenerMensagens);




        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensagem = inputMensagem.getText().toString();
                if (!mensagem.isEmpty()){
                    MensagemModel mensagemModel = new MensagemModel();
                    mensagemModel.setIdUsuario(idUserAtual);
                    mensagemModel.setMensagem(mensagem);

                    if (!salvarMensagem(idContato,idUserAtual,mensagemModel)){
                        Toast.makeText(ConversaActivity.this,"Falha ao salvar mensagem!",Toast.LENGTH_LONG).show();
                    }else{
                        if (!salvarMensagem(idUserAtual,idContato,mensagemModel)){
                            Toast.makeText(ConversaActivity.this,"Falha ao enviar mensagem!",Toast.LENGTH_LONG).show();
                        }else{

                            ConversaModel conversaModel = new ConversaModel();
                            conversaModel.setNome(nome);
                            conversaModel.setIdUsuario(idContato);
                            conversaModel.setMensagem(mensagemModel.getMensagem());

                            if (!salvarConversa(idContato,idUserAtual,conversaModel)){
                                Toast.makeText(ConversaActivity.this,"Falha ao registrar conversa!",Toast.LENGTH_LONG).show();
                            }else{
                                conversaModel = new ConversaModel();
                                conversaModel.setMensagem(mensagemModel.getMensagem());
                                conversaModel.setIdUsuario(idUserAtual);
                                conversaModel.setNome(preferencias.getNome());
                                if (!salvarConversa(idUserAtual,idContato,conversaModel)){
                                    Toast.makeText(ConversaActivity.this,"Falha ao registrar conversa!",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }





                    inputMensagem.setText("");
                }
            }
        });
    }

    private boolean salvarMensagem(String destinatario, String remetente, MensagemModel msg){
        try{
            databaseReference.child("mensagens")
            .child(remetente)
            .child(destinatario)
            .push()
            .setValue(msg);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean salvarConversa(String destinatario, String remetente, ConversaModel conversaModel){
        try{
            databaseReference.child("conversas")
                    .child(remetente)
                    .child(destinatario)
                    .setValue(conversaModel);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.child("mensagens")
                .child(idUserAtual)
                .child(idContato)
                .removeEventListener(eventListenerMensagens);
    }
}
