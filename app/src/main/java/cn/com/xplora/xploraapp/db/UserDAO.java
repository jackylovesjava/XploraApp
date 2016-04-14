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
        cValue.put("uuid",user.getUuid());
        cValue.put("followers", user.getFollowers());
        cValue.put("followings",user.getFollowings());
        cValue.put("hobby",user.getHobby());
        cValue.put("hobbyEn",user.getHobbyEn());
        cValue.put("imageName",user.getImageName());
        cValue.put("imageUrl",user.getImageUrl());
        cValue.put("mobile",user.getMobile());
        cValue.put("logined",user.isLogined());
        cValue.put("userName",user.getUserName());

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

//    public User getLastLoginUser(){
//        SQLiteDatabase db =  dbHelper.getWritableDatabase();
//        Cursor cursor = db.query("user", new String[]{"userId", "followers", "followings", "fullname", "hobby", "hobbyen", "imagename", "imageurl", "mobile", "password", "status", "username"},
//                null, null, null, null, "lastLoginDate desc");
//        User user = new User();
//        if(cursor.moveToFirst()){
//
//            user.setFollowers(cursor.getInt(cursor.getColumnIndex("followers")));
//            user.setFollowings(cursor.getInt(cursor.getColumnIndex("followings")));
//            user.setFullname(cursor.getString(cursor.getColumnIndex("fullname")));
//            user.setHobby(cursor.getString(cursor.getColumnIndex("hobby")));
//            user.setHobbyEn(cursor.getString(cursor.getColumnIndex("hobbyen")));
//            user.setImageName(cursor.getString(cursor.getColumnIndex("imagename")));
//            user.setImageUrl(cursor.getString(cursor.getColumnIndex("imageurl")));
//            user.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
//            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
//            user.setStatus(cursor.getInt(cursor.getColumnIndex("status")));;
//            user.setUserId(cursor.getInt(cursor.getColumnIndex("userId")));
//            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
//
//            cursor.close();
//            db.close();
//        }
//        return user;
//    }

//    public void updateAllUserStatusForLogout(){
//        SQLiteDatabase db =  dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("status", 0);
//        db.update("user", cv, null, null);
//        db.close();
//    }

}