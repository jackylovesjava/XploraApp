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

import cn.com.xplora.xploraapp.asyncTasks.ActiveHobbysAsyncTask;
import cn.com.xplora.xploraapp.asyncTasks.DoAfterConfirm;
import cn.com.xplora.xploraapp.asyncTasks.DoAfterResultInterface;
import cn.com.xplora.xploraapp.customUI.CustomProgressDialog;
import cn.com.xplora.xploraapp.fragments.SelectHobbyFragment;
import cn.com.xplora.xploraapp.json.ActiveHobbysResult;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.model.HobbyModel;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.IConstant;

public class SettingUpdateHobbyActivity extends FragmentActivity implements DoAfterResultInterface,DoAfterConfirm {

    private UserModel mCurrentUser;
    private SelectHobbyFragment mContent;
    private ActiveHobbysAsyncTask mActiveHobbysAsyncTask;
    private CustomProgressDialog mLoadingDialog;
    private ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_update_hobby);
        mCurrentUser = CommonUtil.getCurrentUser(SettingUpdateHobbyActivity.this);
        //=================获取后台兴趣列表==========================================
        if(mActiveHobbysAsyncTask==null) {
            mLoadingDialog = new CustomProgressDialog(SettingUpdateHobbyActivity.this,getString(R.string.loading),R.anim.loading_frame);
            mLoadingDialog.setInverseBackgroundForced(true);
            mLoadingDialog.show();
            mActiveHobbysAsyncTask = new ActiveHobbysAsyncTask(SettingUpdateHobbyActivity.this);
            mActiveHobbysAsyncTask.setmUserId(mCurrentUser.getUuidInBack());
            mActiveHobbysAsyncTask.execute();

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
        if(taskSource== IConstant.TASK_SOURCE_ACTIVEHOBBYS){
            mLoadingDialog.hide();
            if(mContent==null) {
                mContent = new SelectHobbyFragment();
                mContent.setContext(SettingUpdateHobbyActivity.this);
            }
            ActiveHobbysResult activeHobbysResult = (ActiveHobbysResult)result;
            List<HobbyModel> hobbyList = activeHobbysResult.getHobbyList();
            mContent.setHobbyList(hobbyList);
            mContent.setmUserId(mCurrentUser.getUuidInBack());
            mContent.setmCurrentPage(activeHobbysResult.getCurrentPage());
            mContent.setmCurrentUser(mCurrentUser);
            mContent.setmLoadingDialog(mLoadingDialog);
            mActiveHobbysAsyncTask = null;
            getSupportFragmentManager().beginTransaction().replace(R.id.update_hobby_content, mContent, "Content").commit();
        }
    }

    @Override
    public void doAfterConfirm() {
        Intent intent = new Intent(SettingUpdateHobbyActivity.this,MainActivity.class);
        intent.putExtra("DESTINY_FRAGMENT","SETTING");
        startActivity(intent);
    }
}
