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
public class UpdateMobileAsyncTask extends AsyncTask {

    private String TAG = "XPLORA";
    private String apiUrl = "http://www.xplora.com.cn/admin/api/profile/modify_mobile";

    private Context mContext;
    private String mobile;
    private String code;
    private int mUserId;
    private CustomProgressDialog mLoadingDialog;
    public UpdateMobileAsyncTask(String mobile, String code,int userId, Context context, CustomProgressDialog loadingDialog){
        this.mobile = mobile;
        this.code = code;
        this.mContext = context;
        this.mUserId = userId;
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
        ((DoAfterResultInterface)mContext).doAfterResult((BaseResult)o, IConstant.TASK_SOURCE_SETTINGEDITMOBILE);
    }

    @Override
    protected Object doInBackground(Object[] params) {

        HttpUtil http = new HttpUtil(apiUrl);
        String lang = CommonUtil.getLang(mContext);
        String data = "code="+code+"&mobile="+mobile+"&lang="+lang+"&userId="+mUserId;
        String result = http.doGet(data);
        BaseResult apiResult = BaseJsonResolver.parseSimpleResult(result);
        return apiResult;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
