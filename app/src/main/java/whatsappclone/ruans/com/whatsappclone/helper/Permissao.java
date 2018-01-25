package whatsappclone.ruans.com.whatsappclone.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruans on 13/01/2018.
 */

public class Permissao {
    public  static boolean validaPermissoes (int requestCode,Activity activity, String[] permissoes){

        List<String> listaPermissoes = new ArrayList<String>();

        if (Build.VERSION.SDK_INT >= 23){
            for (String permissao : permissoes){
                boolean validacao = ContextCompat.checkSelfPermission(activity,permissao) == PackageManager.PERMISSION_GRANTED;

                if (!validacao){
                    listaPermissoes.add(permissao);
                }
            }


            if (!listaPermissoes.isEmpty()){
                String[] novasPermisoes = new String[listaPermissoes.size()];
                listaPermissoes.toArray(novasPermisoes);
                ActivityCompat.requestPermissions(activity, novasPermisoes,requestCode);
            }
        }
        return true;
    }
}
