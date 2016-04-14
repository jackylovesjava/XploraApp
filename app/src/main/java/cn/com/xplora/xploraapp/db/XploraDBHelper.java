package cn.com.xplora.xploraapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jackylovesjava on 16/4/12.
 */
public class XploraDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DATABASE";
    public static final int VERSION = 1;

    //必须要有构造函数
    public XploraDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    // 当第一次创建数据库的时候，调用该方法
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(uuid integer primary key autoincrement,username text,mobile text,hobby text, hobbyen text," +
                "password text,fullname text,imageurl text,imagename text,followings int,followers int, status int,logindate date)";
        //输出创建数据库的日志信息
        Log.i(TAG, "create Database USER------------->");
        //execSQL函数用于执行SQL语句
        db.execSQL(sql);
    }

    //当更新数据库的时候执行该方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //输出更新数据库的日志信息
        Log.i(TAG, "update Database------------->");
    }
}
