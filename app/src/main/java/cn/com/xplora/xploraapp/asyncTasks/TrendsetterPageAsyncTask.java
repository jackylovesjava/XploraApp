package cn.com.xplora.xploraapp.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import cn.com.xplora.xploraapp.json.ActiveCitiesResult;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.json.TrendsetterPageResult;
import cn.com.xplora.xploraapp.json.TrendsetterPageResultJsonResolver;
import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.utils.HttpUtil;
import cn.com.xplora.xploraapp.utils.IConstant;

/**
 * Created by yckj on 2016/4/14.
 */
public class TrendsetterPageAsyncTask extends AsyncTask {

    private String TAG = "XPLORA";
    private String apiUrl = "http://120.76.98.160:8080/admin/api/people/trendsetterPage";

    private Context context;
    private int mCurrentPage=1;
    private int mPageSize=10;
    private int mUserId = 0;
    private int mStep=0;//0 find similar hobby users;1 find different hobby users;

    public TrendsetterPageAsyncTask(Context context,int currentPage,int userId){
        this.context = context;
        this.mCurrentPage = currentPage;
        this.mUserId = userId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        ((DoAfterResultInterface)context).doAfterResult((BaseResult)o, IConstant.TASK_SOURCE_TRENDSETTERPAGE);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        HttpUtil http = new HttpUtil(apiUrl);
        String result = http.doGet("userId=" + mUserId + "&nowPage=" + mCurrentPage + "&pageShow=" + mPageSize+"&step="+mStep);
        TrendsetterPageResult trendsetterPageResult = TrendsetterPageResultJsonResolver.parse(result);
        return trendsetterPageResult;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
