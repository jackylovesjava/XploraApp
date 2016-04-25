package cn.com.xplora.xploraapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import cn.com.xplora.xploraapp.asyncTasks.DoAfterResultInterface;
import cn.com.xplora.xploraapp.asyncTasks.UpdateUserPortraitAsyncTask;
import cn.com.xplora.xploraapp.camera.ClipImageActivity;
import cn.com.xplora.xploraapp.customUI.CustomProgressDialog;
import cn.com.xplora.xploraapp.db.UserDAO;
import cn.com.xplora.xploraapp.db.XploraDBHelper;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.json.UpdateUserPortraitResult;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.UploadUtil;

public class SettingUpdatePortraitActivity  extends Activity implements UploadUtil.OnUploadProcessListener,DoAfterResultInterface {
    private final int START_ALBUM_REQUESTCODE = 1;
    private final int CAMERA_WITH_DATA = 2;
    private final int CROP_RESULT_CODE = 3;
    public static final String TMP_PATH = "clip_temp.jpg";
    private String mPath = null;
    private static final String TAG = "uploadImage";

    /**
     * 去上传文件
     */
    protected static final int TO_UPLOAD_FILE = 1;
    /**
     * 上传文件响应
     */
    protected static final int UPLOAD_FILE_DONE = 2;  //
    /**
     * 上传初始化
     */
    private static final int UPLOAD_INIT_PROCESS = 4;
    /**
     * 上传中
     */
    private static final int UPLOAD_IN_PROCESS = 5;
    /***
     * 这里的这个URL是我服务器的javaEE环境URL
     */
    private static String requestURL = "http://120.76.98.160:8080/admin/upload/upload";
    private CustomProgressDialog mLoadingDialog;


    private Button mUploadBtn;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_update_portrait);

        mLoadingDialog = new CustomProgressDialog(SettingUpdatePortraitActivity.this,getString(R.string.tip_upload_loading),R.anim.loading_frame);
        mLoadingDialog.setInverseBackgroundForced(true);
        mUploadBtn = (Button)findViewById(R.id.uploadBtn);

        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(TO_UPLOAD_FILE);
            }
        });
        backBtn = (ImageButton)findViewById(R.id.ib_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBack();
            }
        });
        Intent intent = getIntent();
        String actionType = intent.getStringExtra("actionType");
        if("capture".equalsIgnoreCase(actionType)){
            startCapture();
        }else{
            startAlbum();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // String result = null;
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case CROP_RESULT_CODE:
                mPath = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                Bitmap photo = BitmapFactory.decodeFile(mPath);
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(photo);
                break;
            case START_ALBUM_REQUESTCODE:
                startCropImageActivity(getFilePath(data.getData()));
                break;
            case CAMERA_WITH_DATA:
                // 照相机程序返回的,再次调用图片剪辑程序去修剪图片
                startCropImageActivity(Environment.getExternalStorageDirectory()
                        + "/" + TMP_PATH);
                break;
        }
    }

    // 裁剪图片的Activity
    private void startCropImageActivity(String path) {
        Log.i("XPLORA", " crop path is " + path);
        ClipImageActivity.startActivity(this, path, CROP_RESULT_CODE);
    }

    private void startAlbum() {

        Intent intent = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, START_ALBUM_REQUESTCODE);
//		try {
//			Log.i("CLIP","INTO ACTION_GET_CONTENT");
//			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
//			intent.setType("image/*");
//			startActivityForResult(intent, START_ALBUM_REQUESTCODE);
//		} catch (ActivityNotFoundException e) {
//			e.printStackTrace();
//			try {
//				Log.i("CLIP","INTO ACTION_PICK");
//				Intent intent = new Intent(Intent.ACTION_PICK, null);
//				intent.setDataAndType(
//						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//				startActivityForResult(intent, START_ALBUM_REQUESTCODE);
//			} catch (Exception e2) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
    }

    private void startCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), TMP_PATH)));
        startActivityForResult(intent, CAMERA_WITH_DATA);
    }

    /**
     * 通过uri获取文件路径
     *
     * @param mUri
     * @return
     */
    public String getFilePath(Uri mUri) {
        try {
            if (mUri.getScheme().equals("file")) {
                return mUri.getPath();
            } else {
                return getFilePathByUri(mUri);
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    // 获取文件路径通过url
    private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
        Cursor cursor = getContentResolver()
                .query(mUri, null, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }

    /**
     * 上传服务器响应回调
     */
    @Override
    public void onUploadDone(int responseCode, String message) {
//        progressDialog.dismiss();
        Message msg = Message.obtain();
        msg.what = UPLOAD_FILE_DONE;
        msg.arg1 = responseCode;
        msg.obj = message;
        handler.sendMessage(msg);
    }

    private void toUploadFile()
    {
//        progressDialog.setMessage("正在上传文件...");
        mLoadingDialog.show();
        String fileKey = "file";
        UploadUtil uploadUtil = UploadUtil.getInstance();;
        uploadUtil.setOnUploadProcessListener(this);  //设置监听器监听上传状态

//        Map<String, String> params = new HashMap<String, String>();
//        params.put("orderId", "11111");
        uploadUtil.uploadFile( mPath,fileKey, requestURL,null);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TO_UPLOAD_FILE:
                    toUploadFile();
                    break;

                case UPLOAD_INIT_PROCESS:
//                    progressBar.setMax(msg.arg1);
                    break;
                case UPLOAD_IN_PROCESS:
//                    progressBar.setProgress(msg.arg1);
                    break;
                case UPLOAD_FILE_DONE:
                    if(msg.arg1==UploadUtil.UPLOAD_SUCCESS_CODE){
                        String result = String.valueOf(msg.obj);
                        doUpdateUserPortrait(result);
                    }else{
                        String result = String.valueOf(msg.obj);
                        mLoadingDialog.hide();
                        Toast.makeText(SettingUpdatePortraitActivity.this, getResources().getString(R.string.tip_upload_failed), Toast.LENGTH_LONG);
                    }
//                    uploadImageResult.setText(result);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

    private  void doUpdateUserPortrait(String response){

        try{

            JSONObject root = new JSONObject(response);
            String imageName = root.getString("uploadFileName");
            UpdateUserPortraitAsyncTask task = new UpdateUserPortraitAsyncTask(SettingUpdatePortraitActivity.this,imageName);
            task.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }


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
    public void onUploadProcess(int uploadSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_IN_PROCESS;
        msg.arg1 = uploadSize;
        handler.sendMessage(msg );
    }

    @Override
    public void initUpload(int fileSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_INIT_PROCESS;
        msg.arg1 = fileSize;
        handler.sendMessage(msg );
    }

    @Override
    public void doAfterResult(BaseResult result, int taskSource) {
        mLoadingDialog.hide();
        UpdateUserPortraitResult apiResult = (UpdateUserPortraitResult)result;
        if(apiResult.isResult()){
            UserModel currentUser = CommonUtil.getCurrentUser(SettingUpdatePortraitActivity.this);
            currentUser.setImageUrl(apiResult.getImageUrl());
            currentUser.setImageName(apiResult.getImageName());
            UserDAO userDao = new UserDAO(new XploraDBHelper(SettingUpdatePortraitActivity.this,"XPLORA"));
            userDao.updateUser(currentUser);
            Intent intent = new Intent(SettingUpdatePortraitActivity.this,MainActivity.class);
            intent.putExtra("DESTINY_FRAGMENT","SETTING");
            startActivity(intent);
        }else{
            Toast.makeText(SettingUpdatePortraitActivity.this,apiResult.getErrorMsg(),Toast.LENGTH_LONG);

        }
    }
}
