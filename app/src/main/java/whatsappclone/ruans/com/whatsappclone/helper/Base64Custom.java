package whatsappclone.ruans.com.whatsappclone.helper;

import android.util.Base64;

/**
 * Created by ruancaetano on 03/02/18.
 */

public class Base64Custom {

    public static String encode(String input){
        return Base64.encodeToString(input.getBytes(),Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String decode(String input){
        return new String(Base64.decode(input,Base64.DEFAULT));
    }

}
