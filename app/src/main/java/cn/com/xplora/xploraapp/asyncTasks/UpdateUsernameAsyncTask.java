package cn.com.xplora.xploraapp.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import cn.com.xplora.xploraapp.customUI.CustomProgressDialog;
import cn.com.xplora.xploraapp.json.BaseJsonResolver;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.json.LoginResult;
import cn.com.xplora.xploraapp.json.LoginResultJsonResolver;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.HttpUtil;
import cn.com.xplora.xploraapp.utils.IConstant;

/**
 * Created by yckj on 2016/4/14.
 */
public class UpdateUsernameAsyncTask extends AsyncTask {

    private String TAG = "XPLORA";
    private String apiUrl = "http://www.xplora.com.cn/admin/api/profile/modify_username";

    private Context mContext;
    private String mUsername;
    private int mUserId;
    private CustomProgressDialog mLoadingDialog;
    public UpdateUsernameAsyncTask(String username, int userId, Context context, CustomProgressDialog loadingDialog){
        this.mUsername = username;
        this.mUserId = userId;
        this.mContext = context;
        this.mLoadingDialog = loadingDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mLoadingDialog.show();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        mLoadingDialog.hide();
        ((DoAfterResultInterface)mContext).doAfterResult((BaseResult)o, IConstant.TASK_SOURCE_SETTINGEDITUSERNAME);
    }

    @Override
    protected Object doInBackground(Object[] params) {

        HttpUtil http = new HttpUtil(apiUrl);
        String lang = CommonUtil.getLang(mContext);
        String data = "userId="+mUserId+"&userName="+mUsername+"&lang="+lang;
        String result = http.doGet(data);
        BaseResult apiResult = BaseJsonResolver.parseSimpleResult(result);
        return apiResult;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
