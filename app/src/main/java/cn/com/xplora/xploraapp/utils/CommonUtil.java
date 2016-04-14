package cn.com.xplora.xploraapp.utils;

import android.content.Context;

import java.util.Locale;

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
}
