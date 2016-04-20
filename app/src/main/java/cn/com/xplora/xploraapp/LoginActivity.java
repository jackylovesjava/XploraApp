package cn.com.xplora.xploraapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.xplora.xploraapp.asyncTasks.DoAfterResultInterface;
import cn.com.xplora.xploraapp.asyncTasks.LoginAsyncTask;
import cn.com.xplora.xploraapp.customUI.CleanableEditText;
import cn.com.xplora.xploraapp.customUI.CustomProgressDialog;
import cn.com.xplora.xploraapp.db.UserDAO;
import cn.com.xplora.xploraapp.db.XploraDBHelper;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.json.LoginResult;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.IConstant;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements DoAfterResultInterface {


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private UserLoginTask mAuthTask = null;

    private Button mFetchSmsCodeBtn;
    private TimeCount mTime;
    private CustomProgressDialog mLoadingDialog;
    private Button mLoginBtn;
    private CleanableEditText mMobileInput;
    private CleanableEditText mCodeInput;
    private LoginAsyncTask mLoginAsyncTask = null;
    private RelativeLayout mLoginPage = null;
    private UserDAO mUserDAO = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();//初始化视图
        setCountDown();//设置倒数60秒
    }


    private void initView(){
        setContentView(R.layout.activity_login);
        mLoginPage = (RelativeLayout)findViewById(R.id.login_page);
        mLoadingDialog = new CustomProgressDialog(LoginActivity.this, getString(R.string.loading),R.anim.loading_frame);
        mLoginBtn = (Button)findViewById(R.id.login_submit_btn);
        mLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();

            }
        });
        mMobileInput =  (CleanableEditText) findViewById(R.id.login_username_input);
        mCodeInput = (CleanableEditText) findViewById(R.id.login_smscode_input);
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
        mFetchSmsCodeBtn = (Button)findViewById(R.id.login_fetch_smscode);
    }
    private void setCountDown(){
        mTime = new TimeCount(60000, 1000);//构造CountDownTimer对象
        mFetchSmsCodeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTime.start();//开始计时
            }
        });
    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
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
            mLoginAsyncTask = new LoginAsyncTask(mobile,code,LoginActivity.this,mLoadingDialog);
            mLoginAsyncTask.execute((Void)null);
        }

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginPage.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginPage.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginPage.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginPage.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

//        private final String mEmail;
//        private final String mPassword;
//
//        UserLoginTask(String email, String password) {
//            mEmail = email;
//            mPassword = password;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            // TODO: attempt authentication against a network service.
//
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }
//
//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }
//
//            // TODO: register the new account here.
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//
//            if (success) {
//                finish();
//            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }
//    }

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

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            mFetchSmsCodeBtn.setText("重新获取");
            mFetchSmsCodeBtn.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            mFetchSmsCodeBtn.setClickable(false);
            mFetchSmsCodeBtn.setText(millisUntilFinished / 1000 + "秒");
        }
    }
    public static void main(String[]args){
        Pattern p = Pattern.compile("^\\d{6}$");
        Matcher m = p.matcher("565");
        System.out.println(m.matches());
    }

    @Override
    public void doAfterResult(BaseResult result, int taskSource) {
        {
//        showProgress(false);.
            if(taskSource== IConstant.TASK_SOURCE_DOLOGIN){
                LoginResult loginResult = (LoginResult)result;
                mLoginAsyncTask = null;

                if(!loginResult.isResult()){
                    Toast toast= Toast.makeText(LoginActivity.this, loginResult.getErrorMsg(), Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    UserModel user = loginResult.getUserModel();
                    user.setLogined(true);//成功登陆
                    user.setLastLoginDate(new Date());

                    //INSERT LOGINED USER DATA INTO LOCAL DATABASE
                    mUserDAO = new UserDAO(new XploraDBHelper(this,"XPLORA"));

                    mUserDAO.updateAllUserStatusForLogout();//将所有本地账号更新为logout

                    UserModel oldUser = mUserDAO.getUserByUuidInBack(user.getUuidInBack());
                    if(oldUser!=null&&oldUser.getUuid()>0){
                        oldUser.setUserName(user.getUserName());
                        oldUser.setLastLoginDate(new Date());
                        oldUser.setMobile(user.getMobile());
                        oldUser.setAutoPush(user.isAutoPush());
                        oldUser.setFollowers(user.getFollowers());
                        oldUser.setFollowings(user.getFollowings());
                        oldUser.setCityId(user.getCityId());
                        oldUser.setHobby(user.getHobby());
                        oldUser.setHobbyEn(user.getHobbyEn());
                        oldUser.setHobbyIds(user.getHobbyIds());
                        oldUser.setLogined(true);
                        oldUser.setImageName(user.getImageName());
                        oldUser.setImageUrl(user.getImageUrl());
                        mUserDAO.updateUser(oldUser);
                    }else {
                        mUserDAO.insert(user);
                    }

                    if(user.isNewUser()){// new user, go to new user guide

                        Intent intent = new Intent(LoginActivity.this, NewUserGuideActivity.class);
                        startActivity(intent);

                    }else {//login, go to home page
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }


                }

            }

        }
    }
}

