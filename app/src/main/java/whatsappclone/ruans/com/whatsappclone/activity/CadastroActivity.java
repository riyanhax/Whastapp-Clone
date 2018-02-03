package whatsappclone.ruans.com.whatsappclone.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

import whatsappclone.ruans.com.whatsappclone.R;
import whatsappclone.ruans.com.whatsappclone.config.ConfiguracaoFirebase;
import whatsappclone.ruans.com.whatsappclone.helper.Base64Custom;
import whatsappclone.ruans.com.whatsappclone.helper.Preferencias;
import whatsappclone.ruans.com.whatsappclone.model.UsuarioModel;

public class CadastroActivity extends AppCompatActivity {

    private EditText inputNome;
    private EditText inputEmail;
    private EditText inputSenha;
    private EditText inputConfirmarSenha;
    private Button botaoCadastrar;
    private UsuarioModel usuario;
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
    private DatabaseReference database = ConfiguracaoFirebase.getFirebaseDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        usuario = new UsuarioModel();
        inputNome = findViewById(R.id.inputCadastroNome);
        inputEmail = findViewById(R.id.inputCadastroEmail);
        inputSenha = findViewById(R.id.inputCadastroSenha);
        inputConfirmarSenha = findViewById(R.id.inputCadastroConfirmaSenha);
        botaoCadastrar = findViewById(R.id.botaoCadastrar);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = inputNome.getText().toString();
                String email = inputEmail.getText().toString();
                String senha = inputSenha.getText().toString();
                String confirmacaoSenha = inputConfirmarSenha.getText().toString();

                if (senha.equals(confirmacaoSenha)){
                    usuario.setEmail(email);
                    usuario.setNome(nome);
                    usuario.setSenha(senha);
                    cadastrarUsuario();
                }else{
                    Toast.makeText(getApplicationContext(),"As senhas digitadas não são iguais!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void cadastrarUsuario(){
        firebaseAuth.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String idUser = Base64Custom.encode(usuario.getEmail());
                    usuario.setId(idUser);
                    usuario.salvar().addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Preferencias preferencias = new Preferencias(CadastroActivity.this);
                                preferencias.setID(Base64Custom.encode(usuario.getEmail()));
                                abrirLoginUsuario();
                            }else{
                                Toast.makeText(getApplicationContext(),"Falha ao realizar cadastro, tente novamente!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    String mensagemErro = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                       mensagemErro = "Você precisa digitar uma senha mais forte, com pelo menos 6 caracteres!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        mensagemErro = "O e-mail digitado é inválido, digite um novo e-mail!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        mensagemErro = "Esse e-mail já está sendo usado!";
                    } catch (Exception e) {
                        e.printStackTrace();
                        mensagemErro = "Falha ao efetuar cadastro!";
                    }
                    Toast.makeText(getApplicationContext(),"Erro: " + mensagemErro,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
