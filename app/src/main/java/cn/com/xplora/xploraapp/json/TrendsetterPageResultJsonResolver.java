package cn.com.xplora.xploraapp.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.model.TrendsetterModel;

/**
 * 解析返回的JSON，得到ActiveCitiesResult对象
 * Created by yckj on 2016/4/14.
 */
public class TrendsetterPageResultJsonResolver extends BaseJsonResolver {

    public static TrendsetterPageResult parse(String response){
        TrendsetterPageResult result = new TrendsetterPageResult();
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
                List<TrendsetterModel> resultList = new ArrayList<TrendsetterModel>();
                int currentPage = root.getInt("currentPage");
                int pageSize = root.getInt("pageSize");
                int totalPage = root.getInt("totalPage");
                int totalCount = root.getInt("totalCount");
                int step = root.getInt("step");
                JSONArray array = root.getJSONArray("trendsetterList");
                if(array!=null&&array.length()>0){

                    for(int i = 0;i<array.length();i++){
                        TrendsetterModel model = new TrendsetterModel();
                        JSONObject json = (JSONObject)array.opt(i);
                        String fullName = ignoreNullValue(json.getString("fullName"));
                        String fullNameEn = ignoreNullValue(json.getString("fullNameEn"));
                        String imageName = ignoreNullValue(json.getString("imageName"));
                        String imageUrl = ignoreNullValue(json.getString("imageUrl"));
                        String intro = ignoreNullValue(json.getString("intro"));
                        String introEn = ignoreNullValue(json.getString("introEn"));
                        String userName = ignoreNullValue(json.getString("userName"));
                        int followings = json.getInt("followings");
                        int followers = json.getInt("followers");
                        int uuidInBack = json.getInt("uuid");

                        model.setFollowers(followers);
                        model.setFollowings(followings);
                        model.setFullName(fullName);
                        model.setFullNameEn(fullNameEn);
                        model.setUserName(userName);
                        model.setImageName(imageName);
                        model.setImageUrl(imageUrl);
                        model.setUuidInBack(uuidInBack);
                        resultList.add(model);
                    }

                }
                result.setTrendsetterList(resultList);
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
