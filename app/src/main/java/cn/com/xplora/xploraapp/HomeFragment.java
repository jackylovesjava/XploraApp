package cn.com.xplora.xploraapp;



import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.com.xplora.xploraapp.asyncTasks.DoAfterResultInterface;
import cn.com.xplora.xploraapp.asyncTasks.FetchEventListAsyncTask;
import cn.com.xplora.xploraapp.customUI.CustomProgressDialog;
import cn.com.xplora.xploraapp.customUI.JingDongHeaderLayout;
import cn.com.xplora.xploraapp.customUI.SpaceItemDecoration;
import cn.com.xplora.xploraapp.customUI.likeanimation.LikeButtonView;
import cn.com.xplora.xploraapp.customUI.likeanimation.SmallLikeButtonView;
import cn.com.xplora.xploraapp.fragments.BaseFragment;
import cn.com.xplora.xploraapp.json.ActiveHobbysResult;
import cn.com.xplora.xploraapp.json.ActiveHobbysResultJsonResolver;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.json.FetchEventListResult;
import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.model.EventModel;
import cn.com.xplora.xploraapp.model.HobbyModel;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.AbDialogUtil;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.HttpUtil;
import cn.com.xplora.xploraapp.utils.IConstant;
import cn.com.xplora.xploraapp.wheel.AbNumericWheelAdapter;
import cn.com.xplora.xploraapp.wheel.AbWheelUtil;
import cn.com.xplora.xploraapp.wheel.AbWheelView;

public class HomeFragment extends BaseFragment implements  DoAfterResultInterface {

	private ImageView mDateFilterIV;
	private ImageView mDistrictFilterIV;
	private ImageView mHobbyFilterIV;
	private View mDateWheelView;
	private View mDistrictWheelView;
	private View mHobbyWheelView;
	private CityModel mLocateCityModel;
	private LayoutInflater mInflater;
	private TextView mPageTitleTV;
	private List<EventModel> mEventList = new ArrayList<EventModel>();

	private PullToRefreshRecyclerView mPullRefreshRecyclerView;
	private RecyclerView mRecyclerView;
	private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
	private RecyclerViewAdapter mAdapter;

	private FetchEventListAsyncTask mFetchDataTask;

	private int mCurrentPage=1;
	private int mPageSize=10;
	private int mCityId;
	private int mDistrictId;
	private int mHobbyId;
	private String mDateline;
	private int mStep;
	private int mUserId;
	private CustomProgressDialog mLoadingDialog;

	@Override
	protected View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub

		View root = inflater.inflate(R.layout.layout_content, null);

		mDateFilterIV = (ImageView)root.findViewById(R.id.iv_date_filter);
		mDistrictFilterIV = (ImageView)root.findViewById(R.id.iv_district_filter);
		mHobbyFilterIV = (ImageView)root.findViewById(R.id.iv_hobby_filter);
		mInflater = inflater;
		mPageTitleTV = (TextView)root.findViewById(R.id.tv_load_course);

		mPullRefreshRecyclerView = (PullToRefreshRecyclerView)(root.findViewById(R.id.pull_refresh_event));
		mPullRefreshRecyclerView.setHeaderLayout(new JingDongHeaderLayout(this.getActivity()));
		mPullRefreshRecyclerView.setHasPullUpFriction(false); // 设置没有上拉阻力
		mPullRefreshRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
		mRecyclerView = mPullRefreshRecyclerView.getRefreshableView();
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		SpaceItemDecoration decoration=new SpaceItemDecoration(2);
		mRecyclerView.addItemDecoration(decoration);
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
//                new GetDataTask().execute();
				mCurrentPage = 1;//加载第一页
				mStep = 0;//第一步
				mFetchDataTask = new FetchEventListAsyncTask(getActivity(),mCurrentPage,mPageSize,mUserId,mCityId,mStep,mDistrictId,mHobbyId,mDateline,HomeFragment.this);
				mFetchDataTask.execute();
				mAdapter = null;//重新加载adapter;
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
//				new GetDataTask().execute();
				mCurrentPage = mCurrentPage + 1;//加载下一页
				mFetchDataTask = new FetchEventListAsyncTask(getActivity(),mCurrentPage,mPageSize,mUserId,mCityId,mStep,mDistrictId,mHobbyId,mDateline,HomeFragment.this);
				mFetchDataTask.execute();
			}
		});

		UserModel currentUser = CommonUtil.getCurrentUser(getActivity());

		if(currentUser!=null){
			mUserId = currentUser.getUuidInBack();
		}

		mCityId = CommonUtil.getSharedPreferencesIntValue(getActivity(),IConstant.SHARE_PREFERENCE_KEY_LOCATECITY_UUID);


		mFetchDataTask = new FetchEventListAsyncTask(getActivity(),mCurrentPage,mPageSize,mUserId,mCityId,mStep,mDistrictId,mHobbyId,mDateline,this);

		mFetchDataTask.execute();

		//==================加载Loading动画=====================================
		mLoadingDialog = new CustomProgressDialog(getActivity(),getString(R.string.loading),R.anim.loading_frame);
		mLoadingDialog.setInverseBackgroundForced(true);
		mLoadingDialog.show();

		return root;
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//初始化当前城市数据
		mLocateCityModel = new CityModel();
		int cityId = CommonUtil.getSharedPreferencesIntValue(getActivity(), IConstant.SHARE_PREFERENCE_KEY_LOCATECITY_UUID);
		String cityName = CommonUtil.getSharedPreferencesStringValue(getActivity(), IConstant.SHARE_PREFERENCE_KEY_LOCATECITY_NAME);
		String cityNameEn = CommonUtil.getSharedPreferencesStringValue(getActivity(), IConstant.SHARE_PREFERENCE_KEY_LOCATECITY_NAME_EN);
		mLocateCityModel.setCityName(cityName);
		mLocateCityModel.setCityNameEn(cityNameEn);
		mLocateCityModel.setUuidInBack(cityId);

		//根据语言，设置标题栏文本
		if("CHN".equalsIgnoreCase(CommonUtil.getLang(getActivity()))){
			mPageTitleTV.setText(getResources().getString(R.string.title_start_event)+cityName);
		}else{
			mPageTitleTV.setText(getResources().getString(R.string.title_start_event)+cityNameEn);

		}

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

		mDateFilterIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDateWheelView = mInflater.inflate(R.layout.choose_two, null);
				initWheelTime2(mDateWheelView);
				AbDialogUtil.showDialog(mDateWheelView, Gravity.BOTTOM);
			}
		});
		mDistrictFilterIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDistrictWheelView = mInflater.inflate(R.layout.choose_one, null);
				initWheelData1(mDistrictWheelView);
				AbDialogUtil.showDialog(mDistrictWheelView,Gravity.BOTTOM);
			}
		});
		mHobbyFilterIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mHobbyWheelView = mInflater.inflate(R.layout.choose_one, null);
				initWheelData1(mHobbyWheelView);
				AbDialogUtil.showDialog(mHobbyWheelView,Gravity.BOTTOM);
			}
		});

	}


	public void initWheelTime2(View mTimeView){
		final AbWheelView mWheelViewMM = (AbWheelView)mTimeView.findViewById(R.id.wheelView1);
		final AbWheelView mWheelViewHH = (AbWheelView)mTimeView.findViewById(R.id.wheelView2);
		Button okBtn = (Button)mTimeView.findViewById(R.id.okBtn);
		Button cancelBtn = (Button)mTimeView.findViewById(R.id.cancelBtn);

		mWheelViewMM.setCenterSelectDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.wheel_select));
		mWheelViewHH.setCenterSelectDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.wheel_select));
		AbWheelUtil.initWheelTimePicker2(getActivity(), mWheelViewMM, mWheelViewHH, okBtn, cancelBtn, 1, 1, true);
		//AbWheelUtil.initWheelTimePicker2(this, mText,mWheelViewMM, mWheelViewHH,okBtn,cancelBtn,16,23,false);

	}

	public void initWheelData1(View mDataView1){
		final AbWheelView mWheelView1 = (AbWheelView)mDataView1.findViewById(R.id.wheelView1);
		mWheelView1.setAdapter(new AbNumericWheelAdapter(40, 190));
		// 可循环滚动
		mWheelView1.setCyclic(true);
		// 添加文字
//		mWheelView1.setLabel(getResources().getString(R.string.data1_unit));
		// 初始化时显示的数据
		mWheelView1.setCurrentItem(40);
		mWheelView1.setValueTextSize(35);
		mWheelView1.setLabelTextSize(35);
		mWheelView1.setLabelTextColor(0x80000000);
		mWheelView1.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));

		Button okBtn = (Button)mDataView1.findViewById(R.id.okBtn);
		Button cancelBtn = (Button)mDataView1.findViewById(R.id.cancelBtn);
		okBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(v.getContext());
				int index = mWheelView1.getCurrentItem();
				String val = mWheelView1.getAdapter().getItem(index);
//				mDataTextView1.setText(val);
			}

		});

		cancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(v.getContext());
			}

		});
	}

	class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
		private List<EventModel> eventList = new ArrayList<EventModel>();

		public List<EventModel> getEventList() {
			return eventList;
		}

		public void setEventList(List<EventModel> eventList) {
			this.eventList = eventList;
		}

		@Override
		public int getItemCount() {
			return eventList.size();
		}

		@Override
		public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.event_item, parent,
					false));
			return holder;
		}

		@Override
		public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
			final EventModel eventModel = eventList.get(position);
			((MyViewHolder) holder).eventModel = eventModel;
			ImageLoader imageLoader = CommonUtil.getImageLoader(getActivity());
			DisplayImageOptions displayImageOptions = CommonUtil.getDefaultImageLoadOption();
			imageLoader.displayImage(eventModel.getCoverImageUrl(), ((MyViewHolder) holder).eventCoverIV, displayImageOptions);
			if("CHN".equalsIgnoreCase(CommonUtil.getLang(getActivity()))) {
				((MyViewHolder) holder).eventShortAddressTV.setText(eventModel.getShortAddress());
				((MyViewHolder) holder).eventTitleTV.setText(eventModel.getTitle());
				((MyViewHolder) holder).eventDayoffsetTV.setText(eventModel.getDayOffset());
				((MyViewHolder) holder).attendeeCountTV.setText(eventModel.getAttendeeCount()+"人参加");
			}else{
				((MyViewHolder) holder).eventShortAddressTV.setText(eventModel.getShortAddressEn());
				((MyViewHolder) holder).eventTitleTV.setText(eventModel.getTitleEn());
				((MyViewHolder) holder).eventDayoffsetTV.setText(eventModel.getDayOffsetEn());
				((MyViewHolder) holder).attendeeCountTV.setText(eventModel.getAttendeeCount()+" Attendees");
			}
			if(eventModel.getAttendeeList()!=null&&eventModel.getAttendeeList().size()>0){
				for (int i = 0;i<eventModel.getAttendeeList().size();i++){

					UserModel attendee = eventModel.getAttendeeList().get(i);
					ImageView attendeeIV = ((MyViewHolder) holder).attendeeImageList.get(i);
					attendeeIV.setVisibility(View.VISIBLE);
					imageLoader.displayImage(attendee.getImageUrl(), attendeeIV, displayImageOptions);

				}
			}
			if(eventModel.getPrice()>0){
				if("CHN".equalsIgnoreCase(CommonUtil.getLang(getActivity()))) {
					((MyViewHolder) holder).eventPriceTV.setText(eventModel.getPrice()+"元/人");
				}else{
					((MyViewHolder) holder).eventPriceTV.setText("CNY "+eventModel.getPrice());
				}


			}else{//免费
				if("CHN".equalsIgnoreCase(CommonUtil.getLang(getActivity()))) {
					((MyViewHolder) holder).eventPriceTV.setText("免费");
				}else{
					((MyViewHolder) holder).eventPriceTV.setText("FREE");
				}
			}
			if(eventModel.getLikeCount()>0){
				((MyViewHolder) holder).eventLikeSBV.setLikeCount(eventModel.getLikeCount());
			}
			((MyViewHolder) holder).eventLikeSBV.setRelateId(eventModel.getUuidInBack());
			((MyViewHolder) holder).eventCoverIV.setOnTouchListener(new View.OnTouchListener() {
				int count;
				long firClick,secClick;
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(MotionEvent.ACTION_DOWN == event.getAction()){
						count++;
						if(count == 1){
							firClick = System.currentTimeMillis();

						} else if (count == 2){
							secClick = System.currentTimeMillis();
							if(secClick - firClick < 800){
								//双击事件
								UserModel currentUser = CommonUtil.getCurrentUser(getActivity());
								if(currentUser!=null&&currentUser.getUuidInBack()>0) {//登录才会显示like动画
									((MyViewHolder) holder).eventLikeLBV.setVisibility(View.VISIBLE);
									((MyViewHolder) holder).eventLikeLBV.startAnimation();
									((MyViewHolder) holder).eventLikeSBV.setLikeCount(eventModel.getLikeCount() + 1);
									((MyViewHolder) holder).eventLikeSBV.setChecked(true);
								}
							}
							count = 0;
							firClick = 0;
							secClick = 0;

						}
					}
					return true;
				}
			});
		}

		class MyViewHolder extends RecyclerView.ViewHolder {

			ImageView eventCoverIV;
			EventModel eventModel;
			TextView eventTitleTV;
			TextView eventShortAddressTV;
			TextView eventDayoffsetTV;
			List<ImageView> attendeeImageList = new ArrayList<ImageView>();
			ImageView attendee0IV;
			ImageView attendee1IV;
			ImageView attendee2IV;
			ImageView attendee3IV;
			ImageView attendee4IV;
			TextView attendeeCountTV;
			TextView eventPriceTV;
			LikeButtonView eventLikeLBV;
			SmallLikeButtonView eventLikeSBV;
			public MyViewHolder(View view) {
				super(view);
				eventCoverIV = (ImageView) view.findViewById(R.id.iv_event_cover);
				eventTitleTV = (TextView)view.findViewById(R.id.tv_event_title);
				eventShortAddressTV = (TextView) view.findViewById(R.id.tv_event_short_address);
				eventDayoffsetTV = (TextView)view.findViewById(R.id.tv_event_dayoffset);
				attendee0IV = (ImageView)view.findViewById(R.id.civ_event_attendee0);
				attendee1IV = (ImageView)view.findViewById(R.id.civ_event_attendee1);
				attendee2IV = (ImageView)view.findViewById(R.id.civ_event_attendee2);
				attendee3IV = (ImageView)view.findViewById(R.id.civ_event_attendee3);
				attendee4IV = (ImageView)view.findViewById(R.id.civ_event_attendee4);
				attendeeImageList.add(attendee0IV);
				attendeeImageList.add(attendee1IV);
				attendeeImageList.add(attendee2IV);
				attendeeImageList.add(attendee3IV);
				attendeeImageList.add(attendee4IV);
				attendeeCountTV = (TextView)view.findViewById(R.id.tv_event_attendeeCount);
				eventPriceTV = (TextView)view.findViewById(R.id.tv_event_price);
				eventLikeLBV = (LikeButtonView)view.findViewById(R.id.lbv_event_like);
				eventLikeLBV.setVisibility(View.GONE);
				eventLikeSBV = (SmallLikeButtonView)view.findViewById(R.id.btn_event_like);
			}
		}

	}

	@Override
	public void doAfterResult(BaseResult result, int taskSource) {
		if(mLoadingDialog!=null){
			mLoadingDialog.hide();
			mLoadingDialog = null;
		}
		FetchEventListResult apiResult = (FetchEventListResult)result;
		if(apiResult.isResult()){
			List<EventModel> eventList = apiResult.getEventList();
			mCurrentPage = apiResult.getCurrentPage();
			mPageSize = apiResult.getPageSize();
			mStep = apiResult.getStep();
			if(mAdapter==null) {//第一次加载，把api返回的eventList赋给fragement的mEventList，新初始化adapter
				mAdapter = new RecyclerViewAdapter();
				mEventList = eventList;
				mAdapter.setEventList(mEventList);
				mRecyclerView.setAdapter(mAdapter);
				mPullRefreshRecyclerView.onRefreshComplete();//关闭刷新事件
			}else{
				for(EventModel eventModel:eventList){
					if(!mEventList.contains(eventModel)){
						mEventList.add(eventModel);
						mAdapter.notifyItemInserted(mEventList.size());
					}
				}
				mPullRefreshRecyclerView.onRefreshComplete();//关闭刷新事件
			}


		}

	}
}
