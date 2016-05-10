package cn.com.xplora.xploraapp.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import cn.com.xplora.xploraapp.json.ActiveHobbysResult;
import cn.com.xplora.xploraapp.json.ActiveHobbysResultJsonResolver;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.json.HobbyFilterResult;
import cn.com.xplora.xploraapp.json.HobbyFilterResultJsonResolver;
import cn.com.xplora.xploraapp.utils.HttpUtil;
import cn.com.xplora.xploraapp.utils.IConstant;

/**
 * Created by yckj on 2016/4/14.
 */
public class HobbyFilterAsyncTask extends AsyncTask {

    private String TAG = "XPLORA";
    private String apiUrl = "http://www.xplora.com.cn/admin/api/hobby/getEventHobbyByUser";
    private int mUserId = 0;
    private DoAfterResultInterface caller;
    public HobbyFilterAsyncTask(DoAfterResultInterface caller,int userId){
        this.caller = caller;
        this.mUserId = userId;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(caller!=null) {
            caller.doAfterResult((BaseResult) o, IConstant.TASK_SOURCE_HOBBYFILTER);
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        HttpUtil http = new HttpUtil(apiUrl);
        String result = http.doGet("userId=" + mUserId);
        HobbyFilterResult apiResult = HobbyFilterResultJsonResolver.parse(result);
        return apiResult;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public int getmUserId() {
        return mUserId;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
    }
}
