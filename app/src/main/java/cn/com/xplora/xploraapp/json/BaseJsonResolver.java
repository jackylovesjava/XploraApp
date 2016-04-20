package cn.com.xplora.xploraapp.json;

/**
 * Created by jackylovesjava on 16/4/16.
 */
public class BaseJsonResolver {

    public static String ignoreNullValue(String params){

        if(params!=null&&params.equalsIgnoreCase("null")){
            return "";
        }else{
            return params;
        }

    }
}
