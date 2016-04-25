package cn.com.xplora.xploraapp.json;

import org.json.JSONObject;

/**
 * Created by yckj on 2016/4/14.
 */
public class UpdateUserPortraitResultJsonResolver extends BaseJsonResolver {

     public static UpdateUserPortraitResult parse(String response){
         UpdateUserPortraitResult result = new UpdateUserPortraitResult();
         try {
             JSONObject root = new JSONObject(response);
             boolean apiResult = root.getBoolean("result");
             String errorMsg = root.getString("errorMsg");
             if(!apiResult){
                 result.setResult(apiResult);
                 result.setErrorMsg(errorMsg);
                 return result;
             }else{
                 result.setResult(apiResult);
                 result.setErrorMsg(errorMsg);
                 String imageName = root.getString("imageName");
                 String imageUrl = root.getString("imageUrl");
                 result.setImageName(imageName);
                 result.setImageUrl(imageUrl);
                 return result;
             }
         }catch (Exception ex){
             ex.printStackTrace();
             result.setResult(false);
             result.setErrorMsg("SYSTEM ERROR");
             return result;
         }

     }
}
