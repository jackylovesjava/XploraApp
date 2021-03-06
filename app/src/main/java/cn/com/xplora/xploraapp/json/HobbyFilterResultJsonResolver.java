package cn.com.xplora.xploraapp.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.xplora.xploraapp.model.HobbyModel;

/**
 * 解析返回的JSON，得到ActiveHobbysResult对象
 * Created by yckj on 2016/4/14.
 */
public class HobbyFilterResultJsonResolver extends BaseJsonResolver {

    public static HobbyFilterResult parse(String response){
        HobbyFilterResult result = new HobbyFilterResult();
        try {
            JSONObject root = new JSONObject(response);
            boolean flag = root.getBoolean("result");
            String errorMsg = root.getString("errorMsg");
            if(!flag){
                result.setResult(false);
                result.setErrorMsg(errorMsg);
                return result;

            }else{
                result.setResult(true);
                List<HobbyModel> hobbyList = new ArrayList<HobbyModel>();
                JSONArray hobbyArray = root.getJSONArray("hobbyList");
                if(hobbyArray!=null&&hobbyArray.length()>0){

                    for(int i = 0;i<hobbyArray.length();i++){
                        HobbyModel hobby = new HobbyModel();
                        JSONObject hobbyJson = (JSONObject)hobbyArray.opt(i);
                        String hobbyName = ignoreNullValue(hobbyJson.getString("hobbyName"));
                        String hobbyNameEn = ignoreNullValue(hobbyJson.getString("hobbyNameEn"));
//                        String imageName = ignoreNullValue(hobbyJson.getString("imageName"));
//                        String imageUrl = ignoreNullValue(hobbyJson.getString("imageUrl"));
//                        int selected = hobbyJson.getInt("selected");
                        int uuidInBack = hobbyJson.getInt("uuid");

                        hobby.setHobbyName(hobbyName);
                        hobby.setHobbyNameEn(hobbyNameEn);
//                        hobby.setImageName(imageName);
//                        hobby.setImageUrl(imageUrl);
                        hobby.setUuidInBack(uuidInBack);
//                        hobby.setSelected(selected);
                        hobbyList.add(hobby);

                    }

                }
                result.setHobbyList(hobbyList);
                return result;


            }
        }catch (Exception ex){
            result.setResult(false);
            result.setErrorMsg("SYSTEM ERROR");
            ex.printStackTrace();
            return result;
        }
    }
}
