package whatsappclone.ruans.com.whatsappclone.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;

import java.util.HashMap;

/**
 * Created by ruans on 13/01/2018.
 */

public class Preferencias {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static final String CHAVE_NOME = "nome";
    public static final String CHAVE_TELEFONE = "telefone";
    public static final String CHAVE_TOKEN = "token";
    private final String NOME_ARQUIVO = "whatsappClonePreferencias";
    private final int MODE = 0;

    public Preferencias(Context contextParametro){
        context = contextParametro;
        preferences = context.getSharedPreferences(NOME_ARQUIVO,MODE);
        editor = preferences.edit();
    }

    public void salvarUsuarioPreferencias(String nome, String telefone, String token){
        editor.putString(CHAVE_NOME,nome);
        editor.putString(CHAVE_TELEFONE,telefone);
        editor.putString(CHAVE_TOKEN,token);
        editor.commit();
    }

    public HashMap<String,String> getUsuarioPreferencias(){
        HashMap<String,String> hashMap = new HashMap<String,String>();

        hashMap.put(CHAVE_NOME,preferences.getString(CHAVE_NOME,""));
        hashMap.put(CHAVE_TELEFONE,preferences.getString(CHAVE_TELEFONE,""));
        hashMap.put(CHAVE_TOKEN,preferences.getString(CHAVE_TOKEN,""));

        return hashMap;
    }

}
