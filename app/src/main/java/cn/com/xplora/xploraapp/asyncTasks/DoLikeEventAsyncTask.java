package cn.com.xplora.xploraapp.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import cn.com.xplora.xploraapp.customUI.CustomProgressDialog;
import cn.com.xplora.xploraapp.json.BaseJsonResolver;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.HttpUtil;
import cn.com.xplora.xploraapp.utils.IConstant;

/**
 * Created by yckj on 2016/4/14.
 */
public class DoLikeEventAsyncTask extends AsyncTask {

    private String TAG = "XPLORA";
    private String apiUrl = "http://www.xplora.com.cn/admin/api/event/likeEvent";

    private int mUserId;
    private int mEventId;
    public DoLikeEventAsyncTask(int eventId,  int userId){
        this.mUserId = userId;
        this.mEventId = eventId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected Object doInBackground(Object[] params) {

        HttpUtil http = new HttpUtil(apiUrl);
        String data = "userId="+mUserId+"&eventId="+mEventId;
        String result = http.doGet(data);
        BaseResult apiResult = BaseJsonResolver.parseSimpleResult(result);
        return apiResult;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
