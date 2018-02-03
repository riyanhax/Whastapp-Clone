package whatsappclone.ruans.com.whatsappclone.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import whatsappclone.ruans.com.whatsappclone.R;
import whatsappclone.ruans.com.whatsappclone.adapter.TabAdapter;
import whatsappclone.ruans.com.whatsappclone.config.ConfiguracaoFirebase;
import whatsappclone.ruans.com.whatsappclone.helper.Base64Custom;
import whatsappclone.ruans.com.whatsappclone.helper.Preferencias;
import whatsappclone.ruans.com.whatsappclone.helper.SlidingTabLayout;
import whatsappclone.ruans.com.whatsappclone.model.ContatoModel;
import whatsappclone.ruans.com.whatsappclone.model.UsuarioModel;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
    private DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private String idContadoAdd;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferencias = new Preferencias(MainActivity.this);
        toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Whatsapp");
        setSupportActionBar(toolbar);

        slidingTabLayout = findViewById(R.id.sld_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.colorAccent));
        viewPager = findViewById(R.id.vp_pagina);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.item_sair:
                deslogarUsuario();
                return  true;
            case R.id.item_add:
                abrirCadastroContato();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
    private void abrirCadastroContato(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Novo Contato");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);
        final EditText editText = new EditText(MainActivity.this);
        alertDialog.setView(editText);

        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String emailContato = editText.getText().toString();
                if (emailContato.isEmpty()){
                    Toast.makeText(MainActivity.this,"Preencha o campo e-mail", Toast.LENGTH_LONG).show();
                }else{
                    //Verificar se o email é de um usuário cadastrado
                    idContadoAdd = Base64Custom.encode(emailContato);
                    if (idContadoAdd.equals(preferencias.getId())){
                        Toast.makeText(MainActivity.this,"Você não pode se adicionar como contato!", Toast.LENGTH_LONG).show();
                    }else{
                        DatabaseReference userContato = databaseReference.child("usuarios").child(idContadoAdd);
                        userContato.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null){

                                    UsuarioModel dadosUserContato = dataSnapshot.getValue(UsuarioModel.class);
                                    DatabaseReference refereceNovoContato = databaseReference.child("contatos")
                                            .child(preferencias.getId())
                                            .child(idContadoAdd);

                                    ContatoModel novoContato = new ContatoModel();
                                    novoContato.setIdUsuario(idContadoAdd);
                                    novoContato.setEmail(dadosUserContato.getEmail());
                                    novoContato.setNome(dadosUserContato.getNome());

                                    refereceNovoContato.setValue(novoContato).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(MainActivity.this,"Contato Adicionado com sucesso!",Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }else{
                                    Toast.makeText(MainActivity.this,"Esse usuário não existe", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.create();
        alertDialog.show();

    }

    private void deslogarUsuario(){
        firebaseAuth.signOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
