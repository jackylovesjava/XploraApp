package cn.com.xplora.xploraapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.xplora.xploraapp.asyncTasks.DoAfterResultInterface;
import cn.com.xplora.xploraapp.asyncTasks.LoginAsyncTask;
import cn.com.xplora.xploraapp.asyncTasks.UpdateMobileAsyncTask;
import cn.com.xplora.xploraapp.customUI.CleanableEditText;
import cn.com.xplora.xploraapp.customUI.CustomProgressDialog;
import cn.com.xplora.xploraapp.db.UserDAO;
import cn.com.xplora.xploraapp.db.XploraDBHelper;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;

public class SettingEditMobileActivity extends Activity implements DoAfterResultInterface{

    private Button mFetchSmsCodeBtn;
    private TimeCount mTime;
    private CustomProgressDialog mLoadingDialog;
    private Button mLoginBtn;
    private EditText mMobileInput;
    private EditText mCodeInput;
    private UserModel mCurrentUser;
    private UpdateMobileAsyncTask mLoginAsyncTask;
    private ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_edit_mobile);
        mFetchSmsCodeBtn = (Button)findViewById(R.id.fetch_smscode);
        mLoadingDialog = new CustomProgressDialog(SettingEditMobileActivity.this, getResources().getString(R.string.loading), R.anim.loading_frame);
        mLoginBtn = (Button)findViewById(R.id.btn_edit_mobile_confirm);
        mMobileInput = (EditText)findViewById(R.id.et_mobile);
        mCodeInput=(EditText)findViewById(R.id.setting_smscode);
        mCurrentUser = CommonUtil.getCurrentUser(SettingEditMobileActivity.this);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();

            }
        });
        mCodeInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login_submit_btn || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
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
    private void attemptLogin() {
        if (mLoginAsyncTask != null){
            return;
        }

        // Reset errors.
        mMobileInput.setError(null);
        mCodeInput.setError(null);
//
//        // Store values at the time of the login attempt.
        String mobile = mMobileInput.getText().toString();
        String code = mCodeInput.getText().toString();
//
        boolean cancel = false;
        View focusView = null;
//
        // Check for a valid mobile, if the user entered one.
        if (TextUtils.isEmpty(mobile) ||!isMobileValid(mobile)) {
            mMobileInput.setError(getString(R.string.error_mobile_invalid));
            focusView = mMobileInput;
            cancel = true;
        }
        if (TextUtils.isEmpty(code)||!isCodeValid(code)) {
            mCodeInput.setError(getString(R.string.error_code_invalid));
            focusView = mCodeInput;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
//            showProgress(true);
            mLoginAsyncTask = new UpdateMobileAsyncTask(mobile,code,mCurrentUser.getUuidInBack(),SettingEditMobileActivity.this,mLoadingDialog);
            mLoginAsyncTask.execute((Void)null);
        }

    }
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            mFetchSmsCodeBtn.setText(getResources().getString(R.string.retry_fetch));
            mFetchSmsCodeBtn.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            mFetchSmsCodeBtn.setClickable(false);
            if("CHN".equalsIgnoreCase(CommonUtil.getLang(SettingEditMobileActivity.this))){
                mFetchSmsCodeBtn.setText(millisUntilFinished / 1000 + "秒");
            }else{
                mFetchSmsCodeBtn.setText(millisUntilFinished / 1000 + "Seconds");
            }

        }
    }

    private boolean isMobileValid(String mobile){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobile);
        return m.matches();
    }
    private boolean isCodeValid(String code){
        Pattern p = Pattern.compile("^\\d{6}$");

        Matcher m = p.matcher(code);
        return m.matches();
    }
    @Override
    public void doAfterResult(BaseResult result, int taskSource) {

        UserDAO userDao = new UserDAO(new XploraDBHelper(SettingEditMobileActivity.this,"XPLORA"));
        mCurrentUser.setMobile(mMobileInput.getText().toString());
        userDao.updateUser(mCurrentUser);
        Intent intent = new Intent(SettingEditMobileActivity.this,MainActivity.class);
        intent.putExtra("DESTINY_FRAGMENT","SETTING");
        startActivity(intent);

    }
}
