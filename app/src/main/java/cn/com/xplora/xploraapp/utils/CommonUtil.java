package cn.com.xplora.xploraapp.utils;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.Locale;

import cn.com.xplora.xploraapp.R;

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
}
