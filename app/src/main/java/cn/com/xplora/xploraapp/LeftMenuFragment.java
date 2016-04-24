package cn.com.xplora.xploraapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import cn.com.xplora.xploraapp.db.UserDAO;
import cn.com.xplora.xploraapp.db.XploraDBHelper;
import cn.com.xplora.xploraapp.fragments.BaseFragment;
import cn.com.xplora.xploraapp.fragments.FocusFragment;
import cn.com.xplora.xploraapp.fragments.PicsFragment;
import cn.com.xplora.xploraapp.fragments.TiesFragment;
import cn.com.xplora.xploraapp.fragments.UgcFragment;
import cn.com.xplora.xploraapp.fragments.SettingFragment;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;

public class LeftMenuFragment extends Fragment implements OnClickListener {

	private MainActivity mAct;
	private View view;
	private UserDAO mUserDao;
	private TextView setupTV;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.menu_left_frag, null);
		mAct = (MainActivity) getActivity();
		view.findViewById(R.id.tab_news).setOnClickListener(this);
		view.findViewById(R.id.tab_read).setOnClickListener(this);
		view.findViewById(R.id.tab_local).setOnClickListener(this);
		view.findViewById(R.id.tab_ties).setOnClickListener(this);
		view.findViewById(R.id.tab_pics).setOnClickListener(this);
		view.findViewById(R.id.tab_focus).setOnClickListener(this);
		view.findViewById(R.id.tab_settings).setOnClickListener(this);
		view.findViewById(R.id.tab_ugc).setOnClickListener(this);
		view.findViewById(R.id.login_label).setOnClickListener(this);
		view.findViewById(R.id.logout_btn).setOnClickListener(this);
		setupTV = (TextView)view.findViewById(R.id.setup);
		setupTV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SettingFragment settingFragment = new SettingFragment();
				settingFragment.setmContext(mAct);
				mAct.switchContent(settingFragment);
			}
		});
		mUserDao = new UserDAO(new XploraDBHelper(mAct,"XPLORA"));

		//得到当前登录的用户

		UserModel user = mUserDao.getLastLoginUser();
		if(user!=null&&user.getUuid()>0){
			//得到用户数据
			String userName = user.getUserName();
			int followings = user.getFollowings();
			int followers = user.getFollowers();
			String hobby = user.getHobby();
			String hobbyEn = user.getHobbyEn();
			String mobile = user.getMobile();
			String imageName = user.getImageName();
			String imageUrl = user.getImageUrl();
			//初始化控件
			TextView fullNameText = (TextView)view.findViewById(R.id.full_name);
			TextView setupText = (TextView)view.findViewById(R.id.setup);
			TextView hobbyText = (TextView)view.findViewById(R.id.hobbies);
			TextView followingText = (TextView)view.findViewById(R.id.following_count);
			TextView followerText = (TextView)view.findViewById(R.id.follower_count);
			ImageView profileImage = (ImageView)view.findViewById(R.id.profile_image);

			//当用户资料不完整时，显示setup文本
			if(TextUtils.isEmpty(userName)
					||TextUtils.isEmpty(hobby)
					||TextUtils.isEmpty(imageName)){
				//need to setup nickname
				setupText.setText(getString(R.string.setup_profile));
				setupText.setVisibility(View.VISIBLE);
			}else{
				setupText.setVisibility(View.GONE);
			}
			//当用户昵称未设置时，将手机号码隐藏四位作为昵称显示
			if(userName==null||TextUtils.isEmpty(userName)){
				if(!TextUtils.isEmpty(mobile)) {
					String needToReplace = mobile.substring(3, 7);
					userName = mobile.replace(needToReplace, "****");
				}
				}
			fullNameText.setText(userName);


			if("CHN".equalsIgnoreCase(CommonUtil.getLang(mAct))) {
				hobbyText.setText(hobby);
			}else{
				hobbyText.setText(hobbyEn);
			}

			followingText.setText(String.valueOf(followings));
			followerText.setText(String.valueOf(followers));


			if(TextUtils.isEmpty(imageName)){
				profileImage.setImageResource(R.drawable.profile_image_no);
			}else{
				ImageLoader imageLoader = CommonUtil.getImageLoader(mAct);
				DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
						.cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).showImageForEmptyUri(R.drawable.profile_image_no).build();
				imageLoader.displayImage(imageUrl, profileImage, displayImageOptions);
			}

			view.findViewById(R.id.profile).setVisibility(View.VISIBLE);
			view.findViewById(R.id.profile_no).setVisibility(View.GONE);
			view.findViewById(R.id.logout_btn).setVisibility(View.VISIBLE);
		}else{
			view.findViewById(R.id.profile_no).setVisibility(View.VISIBLE);
			view.findViewById(R.id.profile).setVisibility(View.GONE);
			view.findViewById(R.id.logout_btn).setVisibility(View.GONE);

		}

		return view;
	}

	@Override
	public void onClick(View v) {
		BaseFragment fragment = null;
		switch (v.getId()) {
		case R.id.tab_news:
//			fragment = new HomeFragment();
			Intent intent3 = new Intent(mAct,NewUserGuideActivity.class);
			startActivity(intent3);
			break;
		case R.id.tab_read:
//			fragment = new ReadFragment();
			Intent intent4 = new Intent(mAct,FancyFlowDemoActivity.class);
			startActivity(intent4);
			break;
		case R.id.tab_local:
			Intent intent5 = new Intent(mAct,PullToRefreshRecycleActivity.class);
			startActivity(intent5);
			break;
		case R.id.tab_ties:
			fragment = new TiesFragment();
			break;
		case R.id.tab_pics:
			fragment = new PicsFragment();
			break;
		case R.id.tab_focus:
			fragment = new FocusFragment();
			break;
		case R.id.tab_settings:
			SettingFragment settingFragment = new SettingFragment();
			settingFragment.setmContext(mAct);
			fragment = settingFragment;
			break;
		case R.id.tab_ugc:
			fragment = new UgcFragment();
			break;
		case R.id.login_label:
			Intent intent = new Intent(mAct,LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.logout_btn:
			mUserDao.updateAllUserStatusForLogout();
			Intent intent2 = new Intent(mAct,LoginActivity.class);
			startActivity(intent2);
			break;
		default:
			break;
		}
		if(fragment!=null) {
			mAct.switchContent(fragment);
		}
		fragment = null;
	}

}
