package cn.com.xplora.xploraapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import cn.com.xplora.xploraapp.asyncTasks.DoAfterResultInterface;
import cn.com.xplora.xploraapp.asyncTasks.UpdateUsernameAsyncTask;
import cn.com.xplora.xploraapp.customUI.CustomProgressDialog;
import cn.com.xplora.xploraapp.db.UserDAO;
import cn.com.xplora.xploraapp.db.XploraDBHelper;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;

public class SettingEditUsernameActivity extends Activity implements DoAfterResultInterface {

    private EditText mUsernameET;
    private Button mConfirmBtn;
    private String mUsername;
    private UserModel mCurrentUser;
    private CustomProgressDialog mLoadingDialog;
    private UpdateUsernameAsyncTask updateUsernameAsyncTask;
    private ImageButton backBtn;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_edit_username);
        mUsernameET = (EditText) findViewById(R.id.et_username);
        mConfirmBtn = (Button) findViewById(R.id.btn_edit_username_confirm);
        mCurrentUser = CommonUtil.getCurrentUser(SettingEditUsernameActivity.this);
        mLoadingDialog = new CustomProgressDialog(SettingEditUsernameActivity.this, getResources().getString(R.string.loading), R.anim.loading_frame);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername = mUsernameET.getText().toString();
                if (!TextUtils.isEmpty(mUsername)) {
                    mUsernameET.setError(null);
                    if(updateUsernameAsyncTask==null){
                        updateUsernameAsyncTask = new UpdateUsernameAsyncTask(mUsername, mCurrentUser.getUuidInBack(),SettingEditUsernameActivity.this, mLoadingDialog);
                        updateUsernameAsyncTask.execute();
                    }

                }else{
                    mUsernameET.setError(getResources().getString(R.string.error_field_required));
                    mUsernameET.requestFocus();
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    @Override
    public void doAfterResult(BaseResult result, int taskSource) {

        mCurrentUser.setUserName(mUsername);
        UserDAO userDao = new UserDAO(new XploraDBHelper(SettingEditUsernameActivity.this,"XPLORA"));
        userDao.updateUser(mCurrentUser);
        Intent intent = new Intent(SettingEditUsernameActivity.this,MainActivity.class);
        intent.putExtra("DESTINY_FRAGMENT","SETTING");
        startActivity(intent);

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SettingEditUsername Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cn.com.xplora.xploraapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SettingEditUsername Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cn.com.xplora.xploraapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
