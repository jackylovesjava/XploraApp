package cn.com.xplora.xploraapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.Locale;

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.db.UserDAO;
import cn.com.xplora.xploraapp.db.XploraDBHelper;
import cn.com.xplora.xploraapp.model.UserModel;

/**
 * Created by yckj on 2016/4/14.
 */
public class CommonUtil {

    /**
     * 得到当前系统语言
     * ENG：英文
     * CHN：中文
     * @return
     */
    public static String getLang(Context context){
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh")){
            return "CHN";
        }else{
            return "ENG";
        }
    }

    public static ImageLoader getImageLoader(Context context){
        ImageLoader imageLoader = ImageLoader.getInstance();
        if(!imageLoader.isInited()){
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }
        return  imageLoader;
    }

    public static DisplayImageOptions getDefaultImageLoadOption(){
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).showImageForEmptyUri(R.drawable.profile_image_no).build();
        return displayImageOptions;
    }

    public static UserModel getCurrentUser(Context context){

       UserDAO mUserDao = new UserDAO(new XploraDBHelper(context,"XPLORA"));
        if(mUserDao!=null){
            return mUserDao.getLastLoginUser();
        }else{
            return null;
        }
    }

    public static SharedPreferences.Editor getSharedPreferenceEditor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(IConstant.COMMON_PREFERENCE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor;
    }


    public static String getSharedPreferencesStringValue(Context context,String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(IConstant.COMMON_PREFERENCE_FILE, Context.MODE_PRIVATE);

        String value = sharedPreferences.getString(key, "");
        return value;
    }

    public static Integer getSharedPreferencesIntValue(Context context,String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(IConstant.COMMON_PREFERENCE_FILE, Context.MODE_PRIVATE);

        Integer value = sharedPreferences.getInt(key, 0);
        return value;
    }
}
