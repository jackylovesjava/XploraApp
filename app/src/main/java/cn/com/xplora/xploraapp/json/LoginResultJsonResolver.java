package cn.com.xplora.xploraapp.json;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.com.xplora.xploraapp.model.UserModel;

/**
 * Created by yckj on 2016/4/14.
 */
public class LoginResultJsonResolver extends BaseJsonResolver {

    public static LoginResult parse(String response){
        LoginResult result = new LoginResult();
        UserModel user = new UserModel();
        try {
            JSONObject root = new JSONObject(response);
            boolean flag = root.getBoolean("result");
            String errorMsg = root.getString("errorMsg");
            if(!flag){
                result.setResult(flag);
                result.setErrorMsg(errorMsg);
                result.setUserModel(user);
                return result;
            }else{
                result.setResult(flag);
                result.setErrorMsg(errorMsg);
                String userName = ignoreNullValue(root.getString("userName"));
                user.setUserName(userName);
                String mobile = ignoreNullValue(root.getString("mobile"));
                user.setMobile(mobile);
                String imageUrl = ignoreNullValue(root.getString("imageUrl"));
                user.setImageUrl(imageUrl);
                String imageName = ignoreNullValue(root.getString("imageName"));
                user.setImageName(imageName);
                int followings = root.getInt("followings");
                int followers = root.getInt("followers");
                user.setFollowings(followings);
                user.setFollowers(followers);
                boolean newUser = root.getBoolean("newUser");
                user.setNewUser(newUser);
                int uuidInBack = root.getInt("userId");
                user.setUuidInBack(uuidInBack);
                try {//当返回city为空时
                    JSONObject cityJson = root.getJSONObject("city");
                    if (cityJson != null) {
                        int cityId = cityJson.getInt("uuid");
                        String cityName = ignoreNullValue(cityJson.getString("cityName"));
                        String cityNameEn = ignoreNullValue(cityJson.getString("cityNameEn"));
                        user.setCityNameEn(cityNameEn);
                        user.setCityName(cityName);
                        user.setCityId(cityId);
                    }
                }catch (Exception ex){

                }

                StringBuilder hobbyEnSB = new StringBuilder("");
                StringBuilder hobbySB = new StringBuilder("");
                StringBuilder hobbyIdsSB = new StringBuilder("");
                try {//当返回hobbyList为空时
                JSONArray hobbyList = root.getJSONArray("hobbyList");

                if(hobbyList!=null&&hobbyList.length()>0){
                    for(int i = 0;i<hobbyList.length();i++){
                        JSONObject hobbyJson = (JSONObject)hobbyList.opt(i);
                        if(i>4){
                            hobbyEnSB.append("...and "+(hobbyList.length()-5)+" more");
                            hobbySB.append("...等"+hobbyList.length()+"项");
                            break;
                        }
                        hobbyEnSB.append("#");
                        hobbyEnSB.append(ignoreNullValue(hobbyJson.getString("hobbyNameEn")));

                        hobbySB.append("#");
                        hobbySB.append(ignoreNullValue(hobbyJson.getString("hobbyName")));
                    }

                    for(int i = 0;i<hobbyList.length();i++){
                        JSONObject hobbyJson = (JSONObject)hobbyList.opt(i);
                        hobbyIdsSB.append(hobbyJson.getInt("uuid"));
                        hobbyIdsSB.append("|");

                    }
                }

                }
                catch (Exception ex){

                }
                user.setHobbyEn(hobbyEnSB.toString());
                user.setHobby(hobbySB.toString());
                user.setHobbyIds(hobbyIdsSB.toString());
                result.setUserModel(user);
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
