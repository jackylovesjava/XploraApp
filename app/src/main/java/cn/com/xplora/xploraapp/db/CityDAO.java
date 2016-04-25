package cn.com.xplora.xploraapp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.model.UserModel;

/**
 * Created by yckj on 2016/4/14.
 */
public class CityDAO {


    private XploraDBHelper dbHelper;

    public CityDAO(XploraDBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void insert(CityModel city){

        ContentValues cValue = new ContentValues();

        cValue.put("imageName",city.getImageName());
        cValue.put("imageUrl",city.getImageUrl());
        cValue.put("cityName",city.getCityName());
        cValue.put("cityNameEn",city.getCityNameEn());
        cValue.put("uuidInBack", city.getUuidInBack());

        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        db.insert("city", null, cValue);
        db.close();
    }

    public CityModel getByUuidInBack(int uuidInBack){
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        Cursor cursor = db.query("city", new String[]{"uuid", "uuidInBack", "imageName",
                        "imageUrl", "cityName", "cityNameEn"},
                "uuidInBack=?", new String[]{String.valueOf(uuidInBack)}, null, null, null);
        CityModel city = new CityModel();
        if(cursor.moveToFirst()){
            city.setCityNameEn(cursor.getString(cursor.getColumnIndex("cityNameEn")));
            city.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
            city.setUuid(cursor.getInt(cursor.getColumnIndex("uuid")));
            city.setImageUrl(cursor.getString(cursor.getColumnIndex("imageUrl")));
            city.setImageName(cursor.getString(cursor.getColumnIndex("imageName")));
            city.setUuidInBack(uuidInBack);
            cursor.close();

            db.close();
        }
        return city;
    }

    public void update(CityModel city){
        ContentValues cValue = new ContentValues();
        cValue.put("imageName",city.getImageName());
        cValue.put("imageUrl",city.getImageUrl());
        cValue.put("cityName",city.getCityName());
        cValue.put("cityNameEn",city.getCityNameEn());
        cValue.put("uuidInBack",city.getUuidInBack());

        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        db.update("user", cValue, "uuid=?", new String[]{String.valueOf(city.getUuid())});
        db.close();
    }

    public void deleteByUuidInBack(int uuidInBack){
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        db.delete("city","uuidInBack=?",new String[]{String.valueOf(uuidInBack)});
        db.close();
    }
}