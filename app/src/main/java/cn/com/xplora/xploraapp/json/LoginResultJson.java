package cn.com.xplora.xploraapp.json;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.com.xplora.xploraapp.model.UserModel;

/**
 * Created by yckj on 2016/4/14.
 */
public class LoginResultJson {

    public static UserModel parse(String response){
        UserModel user = new UserModel();
        try {
            JSONObject root = new JSONObject(response);
            boolean result = root.getBoolean("result");
            String errorMsg = root.getString("errorMsg");
            if(!result){
                user.setResult(result);
                user.setErrorMsg(errorMsg);
                return user;
            }else{
                user.setResult(result);
                user.setErrorMsg(errorMsg);
                String userName = root.getString("userName");
                user.setUserName(userName);
                String mobile = root.getString("mobile");
                user.setMobile(mobile);
                String imageUrl = root.getString("imageUrl");
                user.setImageUrl(imageUrl);
                String imageName = root.getString("imageName");
                user.setImageName(imageName);
                int followings = root.getInt("followings");
                int followers = root.getInt("followers");
                user.setFollowings(followings);
                user.setFollowers(followers);
                boolean newUser = root.getBoolean("newUser");
                user.setNewUser(newUser);
                int uuidInBack = root.getInt("userId");
                try {//当返回city为空时
                    JSONObject cityJson = root.getJSONObject("city");
                    if (cityJson != null) {
                        int cityId = cityJson.getInt("uuid");
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
                        hobbyEnSB.append(hobbyJson.getString("hobbyNameEn"));

                        hobbySB.append("#");
                        hobbySB.append(hobbyJson.getString("hobbyName"));
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
                return user;

            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
