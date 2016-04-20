package cn.com.xplora.xploraapp.asyncTasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.com.xplora.xploraapp.json.ActiveCitiesResult;
import cn.com.xplora.xploraapp.json.ActiveCitiesResultJsonResolver;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.HttpUtil;
import cn.com.xplora.xploraapp.utils.IConstant;

/**
 * Created by yckj on 2016/4/14.
 */
public class ActiveCitiesAsyncTask extends AsyncTask {

    private String TAG = "XPLORA";
    private String apiUrl = "http://120.76.98.160:8080/admin/api/city/activeCities";

    private Context context;
    public ActiveCitiesAsyncTask( Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        ((DoAfterResultInterface)context).doAfterResult((BaseResult)o, IConstant.TASK_SOURCE_ACTIVECITIES);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        HttpUtil http = new HttpUtil(apiUrl);
        String result = http.doGet(null);
        ActiveCitiesResult activeCitiesResult = ActiveCitiesResultJsonResolver.parse(result);
        List<CityModel> cityList = activeCitiesResult.getCityList();
        if(cityList!=null){
            ImageLoader imageLoader = CommonUtil.getImageLoader(context);
            DisplayImageOptions displayImageOptions = CommonUtil.getDefaultImageLoadOption();
            for (CityModel cityModel:cityList){
                Bitmap cityImage = imageLoader.loadImageSync(cityModel.getImageUrl(),displayImageOptions);
                cityModel.setBitmap(cityImage);
            }

        }
        return activeCitiesResult;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
