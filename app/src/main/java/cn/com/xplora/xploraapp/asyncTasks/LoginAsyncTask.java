package cn.com.xplora.xploraapp.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import cn.com.xplora.xploraapp.LoginActivity;
import cn.com.xplora.xploraapp.customUI.CustomProgressDialog;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.json.LoginResult;
import cn.com.xplora.xploraapp.json.LoginResultJsonResolver;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.HttpUtil;
import cn.com.xplora.xploraapp.utils.IConstant;

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
        ((DoAfterResultInterface)context).doAfterResult((BaseResult)o, IConstant.TASK_SOURCE_DOLOGIN);
    }

    @Override
    protected Object doInBackground(Object[] params) {

        try {
            Thread.currentThread().sleep(2000);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        HttpUtil http = new HttpUtil(apiUrl);
        String lang = CommonUtil.getLang(context);
        String data = "mobile="+mobile+"&code="+code+"&lang="+lang;
        String result = http.doGet(data);
        Log.i(TAG, result);
        LoginResult loginResult = LoginResultJsonResolver.parse(result);
        return loginResult;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
