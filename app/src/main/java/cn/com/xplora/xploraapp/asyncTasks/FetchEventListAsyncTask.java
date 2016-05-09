package cn.com.xplora.xploraapp.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import cn.com.xplora.xploraapp.json.ActiveHobbysResult;
import cn.com.xplora.xploraapp.json.ActiveHobbysResultJsonResolver;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.json.FetchEventListResult;
import cn.com.xplora.xploraapp.json.FetchEventListResultJsonResolver;
import cn.com.xplora.xploraapp.utils.HttpUtil;
import cn.com.xplora.xploraapp.utils.IConstant;

/**
 * Created by yckj on 2016/4/14.
 */
public class FetchEventListAsyncTask extends AsyncTask {

    private String TAG = "XPLORA";
    private String apiUrl = "http://www.xplora.com.cn/admin/api/event/fetchEventList";
    private int mCurrentPage=1;
    private int mPageSize=10;
    private int mUserId = 0;
    private int mCityId = 0;
    private int mStep = 0;
    private int mDistrictId = 0;
    private int mHobbyId = 0;
    private String mDateline = null;
    private Context context;
    private DoAfterResultInterface caller;
    public FetchEventListAsyncTask(Context context,
                                   int mCurrentPage,
                                   int mPageSize,
                                   int mUserId,
                                   int mCityId,
                                   int mStep,
                                   int mDistrictId,
                                   int mHobbyId,
                                   String mDateline,
                                   DoAfterResultInterface caller){
        this.context = context;
        this.mDateline = mDateline;
        this.mUserId = mUserId;
        this.mHobbyId = mHobbyId;
        this.mStep = mStep;
        this.mCurrentPage = mCurrentPage;
        this.mPageSize = mPageSize;
        this.mCityId = mCityId;
        this.mDistrictId = mDistrictId;
        this.caller = caller;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(caller!=null) {
            caller.doAfterResult((FetchEventListResult) o, IConstant.TASK_SOURCE_FETCHEVENTLIST);
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        HttpUtil http = new HttpUtil(apiUrl);
        StringBuffer paramsSB = new StringBuffer();
        paramsSB.append("cityId="+mCityId+"&nowPage="+mCurrentPage+"&pageShow="+
                mPageSize+"&step="+mStep+"&districtId="+mDistrictId+"&hobbyId="+mHobbyId+"&userId="+mUserId);
        if(mDateline!=null&&!"".equals(mDateline)){
            paramsSB.append("&dateline="+mDateline);
        }
        String result = http.doGet(paramsSB.toString());
        FetchEventListResult apiResult = FetchEventListResultJsonResolver.parse(result);
        return apiResult;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public int getmCurrentPage() {
        return mCurrentPage;
    }

    public void setmCurrentPage(int mCurrentPage) {
        this.mCurrentPage = mCurrentPage;
    }

    public int getmPageSize() {
        return mPageSize;
    }

    public void setmPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
    }

    public int getmUserId() {
        return mUserId;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getmCityId() {
        return mCityId;
    }

    public void setmCityId(int mCityId) {
        this.mCityId = mCityId;
    }

    public int getmStep() {
        return mStep;
    }

    public void setmStep(int mStep) {
        this.mStep = mStep;
    }

    public int getmDistrictId() {
        return mDistrictId;
    }

    public void setmDistrictId(int mDistrictId) {
        this.mDistrictId = mDistrictId;
    }

    public int getmHobbyId() {
        return mHobbyId;
    }

    public void setmHobbyId(int mHobbyId) {
        this.mHobbyId = mHobbyId;
    }

    public String getmDateline() {
        return mDateline;
    }

    public void setmDateline(String mDateline) {
        this.mDateline = mDateline;
    }
}
