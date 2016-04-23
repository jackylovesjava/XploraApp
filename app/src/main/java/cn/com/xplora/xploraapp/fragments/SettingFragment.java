package cn.com.xplora.xploraapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.com.xplora.xploraapp.LoginActivity;
import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;

public class SettingFragment extends BaseFragment {

	private Context mContext;
	private ImageView mUserImage;

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	protected View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		View root = inflater.inflate(R.layout.frag_setting, null);
		((TextView)root.findViewById(R.id.tv_load_course)).setText(R.string.title_setting);
		((ImageButton)root.findViewById(R.id.ib_menu_right)).setVisibility(View.GONE);
		mUserImage = (ImageView)root.findViewById(R.id.setting_image);

		UserModel currentUser = CommonUtil.getCurrentUser(mContext);
		if(currentUser==null||currentUser.getUuidInBack()<=0){
			Intent goToLoginIntent = new Intent(mContext,LoginActivity.class);
			startActivity(goToLoginIntent);
		}
		ImageLoader imageLoader = CommonUtil.getImageLoader(mContext);
		DisplayImageOptions options = CommonUtil.getDefaultImageLoadOption();
		imageLoader.displayImage(currentUser.getImageUrl(),mUserImage,options);
		return root;
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

}
