package whatsappclone.ruans.com.whatsappclone.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import whatsappclone.ruans.com.whatsappclone.R;
import whatsappclone.ruans.com.whatsappclone.config.ConfiguracaoFirebase;
import whatsappclone.ruans.com.whatsappclone.model.UsuarioModel;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputSenha;
    private Button botaoEntrar;
    private UsuarioModel usuario;
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Verificando se usuário já está logado
        FirebaseUser userAtual = firebaseAuth.getCurrentUser();
        if (userAtual != null){
            irParaHome();
        }


        usuario = new UsuarioModel();
        inputEmail = findViewById(R.id.inputLoginEmail);
        inputSenha = findViewById(R.id.inputLoginSenha);
        botaoEntrar = findViewById(R.id.botaoEntrar);
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String senha = inputSenha.getText().toString();
                usuario.setEmail(email);
                usuario.setSenha(senha);
                logar();
            }
        });
    }

    private void logar(){
        firebaseAuth.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha())
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            irParaHome();
                        }else{
                            String mensagemErro = "";
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthInvalidUserException e){
                                mensagemErro = "Esse e-mail ainda não foi cadastrado!";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                mensagemErro = "Senha inválida";
                            } catch (Exception e) {
                                e.printStackTrace();
                                mensagemErro = "Falha ao efetuar cadastro!";
                            }
                            Toast.makeText(getApplicationContext(),"Erro: " + mensagemErro,Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void irParaHome(){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }
    public void abrirActivityCadastro(View view){
        startActivity(new Intent(LoginActivity.this,CadastroActivity.class));
    }

}
