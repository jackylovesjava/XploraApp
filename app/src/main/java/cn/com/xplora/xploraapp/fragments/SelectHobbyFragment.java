package cn.com.xplora.xploraapp.fragments;

/**
 * Created by jackylovesjava on 16/4/16.
 */
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.asyncTasks.DoAfterConfirm;
import cn.com.xplora.xploraapp.customUI.CustomProgressDialog;
import cn.com.xplora.xploraapp.customUI.JingDongHeaderLayout;
import cn.com.xplora.xploraapp.customUI.SpaceItemDecoration;
import cn.com.xplora.xploraapp.db.UserDAO;
import cn.com.xplora.xploraapp.db.XploraDBHelper;
import cn.com.xplora.xploraapp.json.ActiveHobbysResult;
import cn.com.xplora.xploraapp.json.ActiveHobbysResultJsonResolver;
import cn.com.xplora.xploraapp.json.BaseJsonResolver;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.model.HobbyModel;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.HttpUtil;

/**
 * Created by lt on 2015/12/14.
 */
public class SelectHobbyFragment extends Fragment{

    private int mUserId;
    private int mCurrentPage=1;
    private int mPageSize=10;
    private UserModel mCurrentUser;
    private List<HobbyModel> selectedHobbyList = new ArrayList<HobbyModel>();
    private List<HobbyModel> hobbyList;
    private PullToRefreshRecyclerView mPullRefreshRecyclerView;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private Context mContext;
    private Button mConfirmBtn;
    private CustomProgressDialog mLoadingDialog;
    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }
    public void setContext(Context context){
        this.mContext = context;
    }
    public Context getContext(){
        return mContext;
    }

    public int getmUserId() {
        return mUserId;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getmCurrentPage() {
        return mCurrentPage;
    }

    public void setmCurrentPage(int mCurrentPage) {
        this.mCurrentPage = mCurrentPage;
    }

    public int getmPageSize() {
        return mPageSize;
    }

    public void setmPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
    }

    public UserModel getmCurrentUser() {
        return mCurrentUser;
    }

    public void setmCurrentUser(UserModel mCurrentUser) {
        this.mCurrentUser = mCurrentUser;
    }

    public CustomProgressDialog getmLoadingDialog() {
        return mLoadingDialog;
    }

    public void setmLoadingDialog(CustomProgressDialog mLoadingDialog) {
        this.mLoadingDialog = mLoadingDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_select_hobby, null);

        mPullRefreshRecyclerView = (PullToRefreshRecyclerView)(view.findViewById(R.id.pull_refresh_hobby));
        mPullRefreshRecyclerView.setHeaderLayout(new JingDongHeaderLayout(this.getActivity()));
        mPullRefreshRecyclerView.setHasPullUpFriction(false); // 设置没有上拉阻力
        mPullRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mRecyclerView = mPullRefreshRecyclerView.getRefreshableView();
//		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        SpaceItemDecoration decoration=new SpaceItemDecoration(2);
        mRecyclerView.addItemDecoration(decoration);
        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
//                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                new GetDataTask().execute();
//                mStaggeredGridLayoutManager.invalidateSpanAssignments();
            }
        });
        mAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mConfirmBtn = (Button)view.findViewById(R.id.select_hobby_confirm);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new UpdateHobbyTask().execute();
            }
        });
        return view;
    }

    private class GetDataTask extends AsyncTask<Void, Void, ActiveHobbysResult> {

        @Override
        protected ActiveHobbysResult doInBackground(Void... params) {
            // Simulates a background job.
                HttpUtil http = new HttpUtil("http://120.76.98.160:8080/admin/api/hobby/activeHobbys");
                String result = http.doGet("userId=" + mUserId + "&nowPage=" + (mCurrentPage+1) + "&pageShow=" + mPageSize);
                ActiveHobbysResult activeHobbysResult = ActiveHobbysResultJsonResolver.parse(result);
                return activeHobbysResult;
        }

        @Override
        protected void onPostExecute(ActiveHobbysResult result) {

            mCurrentPage = result.getCurrentPage();
            if(!hobbyList.containsAll(result.getHobbyList())){
                hobbyList.addAll(result.getHobbyList());
                mAdapter.notifyItemRangeInserted(hobbyList.size(),result.getHobbyList().size());

            }
            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshRecyclerView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }


    private class UpdateHobbyTask extends AsyncTask<Void, Void, BaseResult> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingDialog.show();
        }

        @Override
        protected BaseResult doInBackground(Void... params) {
            // Simulates a background job.

            StringBuffer hobbyIDsSb = new StringBuffer();
            for(int i = 0;i<hobbyList.size();i++){
                HobbyModel hobbyModel = hobbyList.get(i);
                if(hobbyModel.getSelected()==1){
                    selectedHobbyList.add(hobbyModel);
                }
            }
            for(int i = 0;i<selectedHobbyList.size();i++){
                HobbyModel hobbyModel = selectedHobbyList.get(i);
                hobbyIDsSb.append(hobbyModel.getUuidInBack());
                if(i<selectedHobbyList.size()-1){
                    hobbyIDsSb.append("-");
                }
            }
            HttpUtil http = new HttpUtil("http://120.76.98.160:8080/admin/api/hobby/updateUserHobbys");
            String result = http.doGet("userId=" + mUserId + "&hobbys=" + hobbyIDsSb.toString());
            BaseResult updateHobbyResult = BaseJsonResolver.parseSimpleResult(result);
            return updateHobbyResult;
        }

        @Override
        protected void onPostExecute(BaseResult result) {
            super.onPostExecute(result);
            mLoadingDialog.hide();
            StringBuffer hobbyEnSB = new StringBuffer();
            StringBuffer hobbySB = new StringBuffer();
            StringBuffer hobbyIdsSB = new StringBuffer();
            if(selectedHobbyList!=null&&selectedHobbyList.size()>0){
                for(int i = 0;i<selectedHobbyList.size();i++){
                    HobbyModel hobbyModel = selectedHobbyList.get(i);
                        if(i>4){
                            hobbyEnSB.append("...and "+(selectedHobbyList.size()-5)+" more");
                            hobbySB.append("...等"+selectedHobbyList.size()+"项");
                            break;
                        }
                        hobbyEnSB.append("#");
                        hobbyEnSB.append(hobbyModel.getHobbyNameEn());

                        hobbySB.append("#");
                        hobbySB.append(hobbyModel.getHobbyName());
                    }


                }

                for(int i = 0;i<selectedHobbyList.size();i++){
                    HobbyModel hobbyModel = selectedHobbyList.get(i);
                    hobbyIdsSB.append(hobbyModel.getUuidInBack());
                    if(i<selectedHobbyList.size()-1) {
                        hobbyIdsSB.append("-");
                    }

                }
            mCurrentUser.setHobbyEn(hobbyEnSB.toString());
            mCurrentUser.setHobby(hobbySB.toString());
            mCurrentUser.setHobbyIds(hobbyIdsSB.toString());

            XploraDBHelper dbHelper = new XploraDBHelper(mContext,"XPLORA");
            UserDAO userDAO = new UserDAO(dbHelper);

            userDAO.updateUser(mCurrentUser);

            ((DoAfterConfirm)mContext).doAfterConfirm();
        }
    }


    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public int getItemCount() {
            return hobbyList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.hobby_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            HobbyModel hobbyModel = hobbyList.get(position);
            Log.i("XPLORA"," FRAME INIT HOBBY NAME "+hobbyModel.getHobbyName()+" HOBBY SELECTED "+hobbyModel.getSelected());
            ((MyViewHolder) holder).hobbyModel = hobbyModel;
            ImageLoader imageLoader = CommonUtil.getImageLoader(mContext);
            DisplayImageOptions displayImageOptions = CommonUtil.getDefaultImageLoadOption();
            imageLoader.displayImage(hobbyModel.getImageUrl(),((MyViewHolder) holder).hobbyImageView,displayImageOptions);
            String lang = CommonUtil.getLang(mContext);
            if("CHN".equalsIgnoreCase(lang)) {
                ((MyViewHolder) holder).hobbyItemNameText.setText("#"+hobbyModel.getHobbyName());
            }else{
                ((MyViewHolder) holder).hobbyItemNameText.setText("#"+hobbyModel.getHobbyNameEn());
            }
            if(hobbyModel.getSelected()==1){
                Drawable selectedForeground = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.selector_hobby_item_selected, null);
                ((MyViewHolder) holder).hobbyItemFrame.setForeground(selectedForeground);
                ((MyViewHolder) holder).hobbyItemFrame.setSelected(true);
                ((MyViewHolder) holder).hobbyItemCheck.setVisibility(View.VISIBLE);
            }else{
                Drawable normalForeground = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.selector_hobby_item_normal, null);
                ((MyViewHolder) holder).hobbyItemFrame.setForeground(normalForeground);
                ((MyViewHolder) holder).hobbyItemFrame.setSelected(false);
                ((MyViewHolder) holder).hobbyItemCheck.setVisibility(View.GONE);
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView hobbyImageView;
            FrameLayout hobbyItemFrame;
            TextView hobbyItemNameText;
            ImageView hobbyItemCheck;
            HobbyModel hobbyModel;
            public MyViewHolder(View view) {
                super(view);
                hobbyImageView = (ImageView) view.findViewById(R.id.hobby_item_image);
                hobbyItemFrame = (FrameLayout)view.findViewById(R.id.hobby_item_frame);
                hobbyItemNameText = (TextView)view.findViewById(R.id.hobby_item_name);
                hobbyItemCheck = (ImageView) view.findViewById(R.id.hobby_item_check);
                hobbyItemFrame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(hobbyItemFrame.isSelected()){
                            Drawable normalForeground = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.selector_hobby_item_normal, null);
                            hobbyItemFrame.setForeground(normalForeground);
                            hobbyItemFrame.setSelected(false);
                            hobbyModel.setSelected(0);
                            hobbyItemCheck.setVisibility(View.GONE);
                        }else{
                            Drawable selectedForeground = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.selector_hobby_item_selected, null);
                            hobbyItemFrame.setForeground(selectedForeground);
                            hobbyItemFrame.setSelected(true);
                            hobbyModel.setSelected(1);
                            hobbyItemCheck.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }

    }

    public List<HobbyModel> getHobbyList() {
        return hobbyList;
    }

    public void setHobbyList(List<HobbyModel> hobbyList) {
        this.hobbyList = hobbyList;
    }
}
