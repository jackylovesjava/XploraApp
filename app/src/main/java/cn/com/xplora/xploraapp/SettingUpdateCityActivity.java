package cn.com.xplora.xploraapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

import cn.com.xplora.xploraapp.asyncTasks.ActiveCitiesAsyncTask;
import cn.com.xplora.xploraapp.asyncTasks.ActiveHobbysAsyncTask;
import cn.com.xplora.xploraapp.asyncTasks.DoAfterConfirm;
import cn.com.xplora.xploraapp.asyncTasks.DoAfterResultInterface;
import cn.com.xplora.xploraapp.asyncTasks.DoAfterSelectCity;
import cn.com.xplora.xploraapp.customUI.CustomProgressDialog;
import cn.com.xplora.xploraapp.fragments.SelectCityFragment;
import cn.com.xplora.xploraapp.fragments.SelectHobbyFragment;
import cn.com.xplora.xploraapp.json.ActiveCitiesResult;
import cn.com.xplora.xploraapp.json.ActiveHobbysResult;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.model.HobbyModel;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.IConstant;

public class SettingUpdateCityActivity extends FragmentActivity implements DoAfterSelectCity,DoAfterResultInterface{
    private UserModel mCurrentUser;
    private SelectCityFragment mContent;
    private ActiveCitiesAsyncTask mActiveCitiesTask;
    private CustomProgressDialog mLoadingDialog;
    private ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_update_city);
        mCurrentUser = CommonUtil.getCurrentUser(SettingUpdateCityActivity.this);
        //=================获取后台兴趣列表==========================================
        if(mActiveCitiesTask==null) {
            mLoadingDialog = new CustomProgressDialog(SettingUpdateCityActivity.this,getString(R.string.loading),R.anim.loading_frame);
            mLoadingDialog.setInverseBackgroundForced(true);
            mLoadingDialog.show();
            mActiveCitiesTask = new ActiveCitiesAsyncTask(SettingUpdateCityActivity.this);
            mActiveCitiesTask.execute();

        }
        backBtn = (ImageButton)findViewById(R.id.ib_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBack();
            }
        });
    }

    public void doBack(){
        new Thread(){
            public void run() {
                try{
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                }
                catch (Exception e) {
                    Log.e("Exception when onBack", e.toString());
                }
            }
        }.start();
    }

    /**
     * 保存Fragment的状态
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        if(mContent!=null) {
            getSupportFragmentManager().putFragment(outState, "Content", mContent);
        }
    }

    @Override
    public void doAfterResult(BaseResult result, int taskSource) {
        if(taskSource== IConstant.TASK_SOURCE_ACTIVECITIES){
            mLoadingDialog.hide();
            if(mContent==null) {
                mContent = new SelectCityFragment();
            }
            ActiveCitiesResult activeCitiesResult = (ActiveCitiesResult)result;
            List<CityModel> cityList = activeCitiesResult.getCityList();
            mContent.setCityList(cityList);
            mContent.setmLoadingDialog(mLoadingDialog);
            mContent.setmCurrentUser(mCurrentUser);
            mActiveCitiesTask = null;
            getSupportFragmentManager().beginTransaction().replace(R.id.update_city_content, mContent, "Content").commit();
        }
    }

    @Override
    public void doAfterSelectCity() {
        Intent intent = new Intent(SettingUpdateCityActivity.this,MainActivity.class);
        intent.putExtra("DESTINY_FRAGMENT","SETTING");
        startActivity(intent);
    }
}
