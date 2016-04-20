package cn.com.xplora.xploraapp.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.model.UserModel;

/**
 * 解析返回的JSON，得到ActiveCitiesResult对象
 * Created by yckj on 2016/4/14.
 */
public class ActiveCitiesResultJsonResolver extends BaseJsonResolver {

    public static ActiveCitiesResult parse(String response){
        ActiveCitiesResult result = new ActiveCitiesResult();
        try {
            JSONObject root = new JSONObject(response);
            boolean flag = root.getBoolean("result");
            String errorMsg = root.getString("errorMsg");
            if(!flag){

                result.setErrorMsg(errorMsg);
                result.setResult(false);
                return result;

            }else{
                result.setResult(true);
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
                result.setCityList(cityList);
                return result;


            }
        }catch (Exception ex){
            result.setErrorMsg("SYSTEM ERROR");
            result.setResult(false);
            ex.printStackTrace();
            return result;
        }
    }
}
