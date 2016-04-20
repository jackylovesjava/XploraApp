package cn.com.xplora.xploraapp.asyncTasks;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import cn.com.xplora.xploraapp.json.ActiveCitiesResult;
import cn.com.xplora.xploraapp.json.ActiveHobbysResult;
import cn.com.xplora.xploraapp.json.ActiveHobbysResultJsonResolver;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.utils.HttpUtil;
import cn.com.xplora.xploraapp.utils.IConstant;

/**
 * Created by yckj on 2016/4/14.
 */
public class ActiveHobbysAsyncTask extends AsyncTask {

    private String TAG = "XPLORA";
    private String apiUrl = "http://120.76.98.160:8080/admin/api/hobby/activeHobbys";
    private int mCurrentPage=1;
    private int mPageSize=10;
    private int mUserId = 0;
    private Context context;
    public ActiveHobbysAsyncTask(Context context){
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(context!=null) {
            ((DoAfterResultInterface) context).doAfterResult((BaseResult) o, IConstant.TASK_SOURCE_ACTIVEHOBBYS);
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        HttpUtil http = new HttpUtil(apiUrl);
        String result = http.doGet("userId=" + mUserId + "&nowPage=" + mCurrentPage + "&pageShow=" + mPageSize);
        ActiveHobbysResult activeHobbysResult = ActiveHobbysResultJsonResolver.parse(result);
        return activeHobbysResult;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
