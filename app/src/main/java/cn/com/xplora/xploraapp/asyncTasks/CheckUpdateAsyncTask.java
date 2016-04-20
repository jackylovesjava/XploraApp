package cn.com.xplora.xploraapp.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import cn.com.xplora.xploraapp.json.ActiveCitiesResult;
import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.utils.HttpUtil;

/**
 * Created by yckj on 2016/4/14.
 */
public class CheckUpdateAsyncTask extends AsyncTask {

    private String TAG = "XPLORA";
    private String apiUrl = "http://120.76.98.160:8080/admin/api/city/activeCities";

    private Context context;
    public CheckUpdateAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
//        ((DoAfterResultInterface)context).doAfterResult(o);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            Thread.currentThread().sleep(2000);
        }catch (Exception ex){
            ex.printStackTrace();
        }
//        HttpUtil http = new HttpUtil(apiUrl);
//        String result = http.doGet(null);
//        List<CityModel> cityList = ActiveCitiesResult.parse(result);
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
