package cn.com.xplora.xploraapp.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.model.UserModel;

/**
 * Created by yckj on 2016/4/14.
 */
public class ActiveCitiesResult extends BaseJson{

    public static List<CityModel> parse(String response){
        UserModel user = new UserModel();
        try {
            JSONObject root = new JSONObject(response);
            boolean result = root.getBoolean("result");
            String errorMsg = root.getString("errorMsg");
            if(!result){

                return null;

            }else{
                List<CityModel> cityList = new ArrayList<CityModel>();
                JSONArray cityArray = root.getJSONArray("cityList");
                if(cityArray!=null&&cityArray.length()>0){

                    for(int i = 0;i<cityArray.length();i++){
                        CityModel city = new CityModel();
                        JSONObject cityJson = (JSONObject)cityArray.opt(i);
                        String cityName = ignoreNullValue(cityJson.getString("cityName"));
                        String cityNameEn = ignoreNullValue(cityJson.getString("cityNameEn"));
                        String imageName = ignoreNullValue(cityJson.getString("imageName"));
                        String imageUrl = ignoreNullValue(cityJson.getString("imageUrl"));
                        int uuidInBack = cityJson.getInt("uuid");

                        city.setCityName(cityName);
                        city.setCityNameEn(cityNameEn);
                        city.setImageName(imageName);
                        city.setImageUrl(imageUrl);
                        city.setUuidInBack(uuidInBack);
                        cityList.add(city);
                    }

                }
                return cityList;


            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
