package cn.com.xplora.xploraapp.json;

import org.json.JSONObject;

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

    public static BaseResult parseSimpleResult(String response){
        BaseResult result = new BaseResult();
        try {
            JSONObject root = new JSONObject(response);
            result.setResult(root.getBoolean("result"));
            result.setErrorMsg(root.getString("errorMsg"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
