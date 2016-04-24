package cn.com.xplora.xploraapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import cn.com.xplora.xploraapp.LoginActivity;
import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.SettingEditMobileActivity;
import cn.com.xplora.xploraapp.SettingEditUsernameActivity;
import cn.com.xplora.xploraapp.SettingUpdateCityActivity;
import cn.com.xplora.xploraapp.SettingUpdateHobbyActivity;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;

public class SettingFragment extends BaseFragment {

	private Context mContext;
	private ImageView mUserImageIV;
	private TextView mUsernameTV;
	private ImageView mUsernameIV;
	private TextView mMobileTV;
	private ImageView mMobileIV;
	private TextView mHobbyTV;
	private ImageView mHobbyIV;
	private TextView mCityTV;
	private ImageView mCityIV;
	private ToggleButton mPushTB;
	private TextView mVersionTV;
	private Button mCheckUpdateBtn;
	private TextView mOrderTermsTV;
	private ImageView mOrderTermsIV;

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		//====================  得到当前用户 =======================
		UserModel currentUser = CommonUtil.getCurrentUser(mContext);
		//==================== 未登录 =========================
		if(currentUser==null||currentUser.getUuidInBack()<=0){
			Intent goToLoginIntent = new Intent(mContext,LoginActivity.class);
			startActivity(goToLoginIntent);
		}
		//=================== 根据当前用户显示数据 =================
		ImageLoader imageLoader = CommonUtil.getImageLoader(mContext);
		DisplayImageOptions options = CommonUtil.getDefaultImageLoadOption();
		imageLoader.displayImage(currentUser.getImageUrl(), mUserImageIV, options);
		if(!TextUtils.isEmpty(currentUser.getUserName())){
			mUsernameTV.setText(currentUser.getUserName());
		}
		if(!TextUtils.isEmpty(currentUser.getMobile())){
			mMobileTV.setText(currentUser.getMobile());
		}
		if("CHN".equalsIgnoreCase(CommonUtil.getLang(mContext))) {
			if (!TextUtils.isEmpty(currentUser.getHobby())) {
				mHobbyTV.setText(currentUser.getHobby());
			}
			if (!TextUtils.isEmpty(currentUser.getCityName())) {
				mCityTV.setText(currentUser.getCityName());
			}
		}else{
			if (!TextUtils.isEmpty(currentUser.getHobbyEn())) {
				mHobbyTV.setText(currentUser.getHobbyEn());
			}
			if (!TextUtils.isEmpty(currentUser.getCityNameEn())) {
				mCityTV.setText(currentUser.getCityNameEn());
			}
		}
		if(currentUser.isAutoPush()){
			mPushTB.setChecked(true);
		}else{
			mPushTB.setChecked(false);
		}
	}

	@Override
	protected void setListener() {
		//================= 绑定事件 ============================
		mUsernameTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToEdit(SettingEditUsernameActivity.class);
			}
		});
		mUsernameIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToEdit(SettingEditUsernameActivity.class);
			}
		});
		mMobileTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToEdit(SettingEditMobileActivity.class);
			}
		});
		mMobileIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToEdit(SettingEditMobileActivity.class);
			}
		});
		mHobbyIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToEdit(SettingUpdateHobbyActivity.class);
			}
		});
		mHobbyTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToEdit(SettingUpdateHobbyActivity.class);
			}
		});
		mCityTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToEdit(SettingUpdateCityActivity.class);
			}
		});
		mCityIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToEdit(SettingUpdateCityActivity.class);
			}
		});
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View root = inflater.inflate(R.layout.frag_setting, null);
		//==================== 设置页面title =======================
		((TextView)root.findViewById(R.id.tv_load_course)).setText(R.string.title_setting);
		((ImageButton)root.findViewById(R.id.ib_menu_right)).setVisibility(View.GONE);

		//==================== 初始化各个view ============================
		mUserImageIV = (ImageView)root.findViewById(R.id.setting_image);
		mUsernameTV = (TextView)root.findViewById(R.id.tv_setting_username);
		mUsernameIV = (ImageView)root.findViewById(R.id.icon_edit_username);
		mMobileTV = (TextView)root.findViewById(R.id.tv_setting_mobile);
		mMobileIV = (ImageView)root.findViewById(R.id.icon_edit_mobile);
		mHobbyTV = (TextView)root.findViewById(R.id.tv_setting_hobby);
		mHobbyIV = (ImageView)root.findViewById(R.id.icon_edit_hobby);
		mCityTV = (TextView)root.findViewById(R.id.tv_setting_city);
		mCityIV = (ImageView)root.findViewById(R.id.icon_edit_city);
		mPushTB = (ToggleButton)root.findViewById(R.id.tb_setting_push);
		mVersionTV = (TextView)root.findViewById(R.id.setting_version);
		mCheckUpdateBtn = (Button)root.findViewById(R.id.btn_check_update);
		mOrderTermsTV = (TextView)root.findViewById(R.id.setting_order_terms);
		mOrderTermsIV = (ImageView)root.findViewById(R.id.iv_setting_order_terms);

		return root;
	}


	private void goToEdit(Class editableClass){

		Intent intent = new Intent(mContext,editableClass);
		mContext.startActivity(intent);
	}
}
