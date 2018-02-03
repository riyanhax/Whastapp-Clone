package whatsappclone.ruans.com.whatsappclone.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ruans on 13/01/2018.
 */

public class Preferencias {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static final String CHAVE_ID_USER = "idUser";
    private final String NOME_ARQUIVO = "whatsappClonePreferencias";
    private final int MODE = 0;

    public Preferencias(Context contextParametro){
        context = contextParametro;
        preferences = context.getSharedPreferences(NOME_ARQUIVO,MODE);
        editor = preferences.edit();
    }

    public void setID(String idUserAtual){
        editor.putString(CHAVE_ID_USER,idUserAtual);
        editor.commit();
    }

    public String getId(){
        return preferences.getString(CHAVE_ID_USER,null);
    }


}
