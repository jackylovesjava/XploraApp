package cn.com.xplora.xploraapp.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.xplora.xploraapp.model.CityModel;

/**
 * 解析返回的JSON，得到ActiveCitiesResult对象
 * Created by yckj on 2016/4/14.
 */
public class LocateCityResultJsonResolver extends BaseJsonResolver {

    public static LocateCityResult parse(String response){
        LocateCityResult result = new LocateCityResult();
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
                CityModel cityModel = new CityModel();
                JSONObject cityJson = root.getJSONObject("city");
                String cityName = ignoreNullValue(cityJson.getString("cityName"));
                String cityNameEn = ignoreNullValue(cityJson.getString("cityNameEn"));
                String imageName = ignoreNullValue(cityJson.getString("imageName"));
                String imageUrl = ignoreNullValue(cityJson.getString("imageUrl"));
                int uuidInBack = cityJson.getInt("uuid");

                cityModel.setCityName(cityName);
                cityModel.setCityNameEn(cityNameEn);
                cityModel.setImageName(imageName);
                cityModel.setImageUrl(imageUrl);
                cityModel.setUuidInBack(uuidInBack);
                result.setCityModel(cityModel);
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
