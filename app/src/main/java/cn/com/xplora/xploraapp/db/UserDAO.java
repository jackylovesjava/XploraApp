package cn.com.xplora.xploraapp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.xplora.xploraapp.model.UserModel;

/**
 * Created by yckj on 2016/4/14.
 */
public class UserDAO{


    private XploraDBHelper dbHelper;

    public UserDAO(XploraDBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void insert(UserModel user){

        ContentValues cValue = new ContentValues();
        cValue.put("followers", user.getFollowers());
        cValue.put("followings",user.getFollowings());
        cValue.put("hobbyIds",user.getHobbyIds());
        cValue.put("hobby",user.getHobby());
        cValue.put("hobbyEn",user.getHobbyEn());
        cValue.put("imageName",user.getImageName());
        cValue.put("imageUrl",user.getImageUrl());
        cValue.put("mobile",user.getMobile());
        cValue.put("logined",user.isLogined());
        cValue.put("userName",user.getUserName());
        cValue.put("uuidInBack",user.getUuidInBack());
        cValue.put("autoPush",user.isAutoPush());
        cValue.put("cityId",user.getCityId());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date lastLoginDate = user.getLastLoginDate();
        String lastLoginDateStr = "";
        if(lastLoginDate!=null){
            lastLoginDateStr = dateFormat.format(lastLoginDate);
        }
        cValue.put("lastLoginDate",lastLoginDateStr);


        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        db.insert("user",null,cValue);
    }

    public UserModel getLastLoginUser(){
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        Cursor cursor = db.query("user", new String[]{"uuid", "followers", "followings", "uuidInBack", "hobby", "hobbyEn", "imageName",
                        "cityId","imageUrl", "mobile", "hobbyIds", "logined", "userName"},
                "logined=true", null, null, null, "lastLoginDate desc");
        UserModel user = new UserModel();
        if(cursor.moveToFirst()){

            user.setFollowers(cursor.getInt(cursor.getColumnIndex("followers")));
            user.setFollowings(cursor.getInt(cursor.getColumnIndex("followings")));
            user.setHobby(cursor.getString(cursor.getColumnIndex("hobby")));
            user.setHobbyEn(cursor.getString(cursor.getColumnIndex("hobbyEn")));
            user.setImageName(cursor.getString(cursor.getColumnIndex("imageName")));
            user.setImageUrl(cursor.getString(cursor.getColumnIndex("imageUrl")));
            user.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
            user.setNewUser(false);
            user.setCityId(cursor.getInt(cursor.getColumnIndex("cityId")));
            user.setAutoPush(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("autoPush"))));
//            user.setLastLoginDate();
            user.setLogined(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("logined"))));
            user.setHobbyIds(cursor.getString(cursor.getColumnIndex("hobbyIds")));
            user.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
            cursor.close();

            db.close();
        }
        return user;
    }

    public void updateAllUserStatusForLogout(){
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("logined", false);
        db.update("user", cv, null, null);
        db.close();
    }

}