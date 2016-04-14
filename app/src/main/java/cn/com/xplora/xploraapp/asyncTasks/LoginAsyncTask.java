package cn.com.xplora.xploraapp.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import cn.com.xplora.xploraapp.LoginActivity;
import cn.com.xplora.xploraapp.customUI.CustomProgressDialog;
import cn.com.xplora.xploraapp.json.LoginResultJson;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.HttpUtil;

/**
 * Created by yckj on 2016/4/14.
 */
public class LoginAsyncTask extends AsyncTask {

    private String TAG = "XPLORA";
    private String apiUrl = "http://120.76.98.160:8080/admin/api/login/doLogin";

    private Context context;
    private String mobile;
    private String code;
    private CustomProgressDialog loadingDialog;
    public LoginAsyncTask(String mobile,String code,Context context, CustomProgressDialog loadingDialog){
        this.mobile = mobile;
        this.code = code;
        this.context = context;
        this.loadingDialog = loadingDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog.show();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        loadingDialog.hide();
        LoginActivity loginActivity = (LoginActivity)context;
        loginActivity.doAfterTask((UserModel)o);
    }

    @Override
    protected Object doInBackground(Object[] params) {

        HttpUtil http = new HttpUtil(apiUrl);
        String lang = CommonUtil.getLang(context);
        String data = "mobile="+mobile+"&code="+code+"&lang="+lang;
        String result = http.doGet(data);
        Log.i(TAG, result);
        UserModel user = LoginResultJson.parse(result);
        return user;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
