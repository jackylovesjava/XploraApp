package cn.com.xplora.xploraapp.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.xplora.xploraapp.model.TrendsetterModel;
import cn.com.xplora.xploraapp.model.UserModel;

/**
 * 解析返回的JSON，得到ActiveCitiesResult对象
 * Created by yckj on 2016/4/14.
 */
public class UserPageResultJsonResolver extends BaseJsonResolver {

    public static UserPageResult parse(String response) {
        UserPageResult result = new UserPageResult();
        try {
            JSONObject root = new JSONObject(response);
            boolean flag = root.getBoolean("result");
            String errorMsg = root.getString("errorMsg");
            if (!flag) {
                result.setResult(false);
                result.setErrorMsg(errorMsg);
                return result;

            } else {
                result.setResult(true);
                List<UserModel> resultList = new ArrayList<UserModel>();
                int currentPage = root.getInt("currentPage");
                int pageSize = root.getInt("pageSize");
                int totalPage = root.getInt("totalPage");
                int totalCount = root.getInt("totalCount");
                int step = root.getInt("step");
                result.setCurrentPage(currentPage);
                result.setPageSize(pageSize);
                result.setTotalCount(totalCount);
                result.setTotalPage(totalPage);
                result.setStep(step);
                JSONArray array = root.getJSONArray("userList");
                if (array != null && array.length() > 0) {

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject json = array.getJSONObject(i);
                        UserModel user = new UserModel();
                        String userName = ignoreNullValue(json.getString("userName"));
                        user.setUserName(userName);
                        String mobile = ignoreNullValue(json.getString("mobile"));
                        user.setMobile(mobile);
                        String imageUrl = ignoreNullValue(json.getString("imageUrl"));
                        user.setImageUrl(imageUrl);
                        String imageName = ignoreNullValue(json.getString("imageName"));
                        user.setImageName(imageName);
                        int followings = json.getInt("followings");
                        int followers = json.getInt("followers");
                        user.setFollowings(followings);
                        user.setFollowers(followers);
                        int uuidInBack = json.getInt("uuid");
                        user.setUuidInBack(uuidInBack);
//                        JSONObject cityJson = json.getJSONObject("city");
//                        if (cityJson != null) {
                            int cityId = json.getInt("cityId");
//                            String cityName = ignoreNullValue(cityJson.getString("cityName"));
//                            String cityNameEn = ignoreNullValue(cityJson.getString("cityNameEn"));
//                            user.setCityNameEn(cityNameEn);
//                            user.setCityName(cityName);
                            user.setCityId(cityId);
//                        }

                        StringBuilder hobbyEnSB = new StringBuilder("");
                        StringBuilder hobbySB = new StringBuilder("");
                        StringBuilder hobbyIdsSB = new StringBuilder("");
                        JSONArray hobbyList = json.getJSONArray("hobbyList");

                        if (hobbyList != null && hobbyList.length() > 0) {
                            for (int j = 0; j < hobbyList.length(); j++) {
                                JSONObject hobbyJson = (JSONObject) hobbyList.opt(j);
                                if (j > 4) {
                                    hobbyEnSB.append("...and " + (hobbyList.length() - 5) + " more");
                                    hobbySB.append("...等" + hobbyList.length() + "项");
                                    break;
                                }
                                hobbyEnSB.append("#");
                                hobbyEnSB.append(ignoreNullValue(hobbyJson.getString("hobbyNameEn")));

                                hobbySB.append("#");
                                hobbySB.append(ignoreNullValue(hobbyJson.getString("hobbyName")));
                            }

                            for (int j = 0; j < hobbyList.length(); j++) {
                                JSONObject hobbyJson = (JSONObject) hobbyList.opt(j);
                                hobbyIdsSB.append(hobbyJson.getInt("uuid"));
                                hobbyIdsSB.append("|");

                            }
                        }
                        user.setHobby(hobbySB.toString());
                        user.setHobbyEn(hobbyEnSB.toString());

                        int followedByCurrentUser = json.getInt("followedByCurrentUser");
                        user.setFollowedByCurrentUser(followedByCurrentUser);
                        resultList.add(user);

                    }
                }
                result.setUserList(resultList);
                return result;
            }
        } catch (Exception ex) {
            result.setErrorMsg("SYSTEM ERROR");
            result.setResult(false);
            ex.printStackTrace();
            return result;
        }
    }
}
