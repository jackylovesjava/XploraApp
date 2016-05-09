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
import cn.com.xplora.xploraapp.json.LocateCityResult;
import cn.com.xplora.xploraapp.json.LocateCityResultJsonResolver;
import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.HttpUtil;
import cn.com.xplora.xploraapp.utils.IConstant;

/**
 * Created by yckj on 2016/4/14.
 */
public class LocateCityAsyncTask extends AsyncTask {

    private String TAG = "XPLORA";
    private String apiUrl = "http://www.xplora.com.cn/admin/api/city/locateCity";

    private Context context;
    private String locateCityName;
    public LocateCityAsyncTask(Context context,String locateCityName){
        this.context = context;
        this.locateCityName = locateCityName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        ((DoAfterResultInterface)context).doAfterResult((LocateCityResult)o, IConstant.TASK_SOURCE_LOCATECITY);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        HttpUtil http = new HttpUtil(apiUrl);
        String result = http.doGet("userCityName="+locateCityName);
        LocateCityResult apiResult = LocateCityResultJsonResolver.parse(result);
        return apiResult;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
