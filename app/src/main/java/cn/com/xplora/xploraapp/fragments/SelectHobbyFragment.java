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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.adapter.MyCoverFlowAdapter;
import cn.com.xplora.xploraapp.asyncTasks.ActiveHobbysAsyncTask;
import cn.com.xplora.xploraapp.customUI.CoverFlowView;
import cn.com.xplora.xploraapp.customUI.JingDongHeaderLayout;
import cn.com.xplora.xploraapp.customUI.SpaceItemDecoration;
import cn.com.xplora.xploraapp.json.ActiveHobbysResult;
import cn.com.xplora.xploraapp.json.ActiveHobbysResultJsonResolver;
import cn.com.xplora.xploraapp.model.HobbyModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.HttpUtil;

/**
 * Created by lt on 2015/12/14.
 */
public class SelectHobbyFragment extends Fragment{

    private int mUserId;
    private int mCurrentPage=1;
    private int mPageSize=10;
    private List<HobbyModel> hobbyList;
    private PullToRefreshRecyclerView mPullRefreshRecyclerView;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private Context mContext;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_select_hobby, null);

        mPullRefreshRecyclerView = (PullToRefreshRecyclerView)(view.findViewById(R.id.pull_refresh_hobby));
        mPullRefreshRecyclerView.setHeaderLayout(new JingDongHeaderLayout(this.getActivity()));
        mPullRefreshRecyclerView.setHasPullUpFriction(false); // 设置没有上拉阻力

        mRecyclerView = mPullRefreshRecyclerView.getRefreshableView();
//		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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
            }
        });
        mAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);

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
                mAdapter.notifyDataSetChanged();
            }
            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshRecyclerView.onRefreshComplete();

            super.onPostExecute(result);
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
            HobbyModel hobbyModel = hobbyList.get(holder.getLayoutPosition());
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
                Drawable selectedForeground = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.selector_hobby_item, null);
                ((MyViewHolder) holder).hobbyItemFrame.setForeground(selectedForeground);
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView hobbyImageView;
            FrameLayout hobbyItemFrame;
            TextView hobbyItemNameText;
            ImageView hobbyItemCheck;
            public MyViewHolder(View view) {
                super(view);
                hobbyImageView = (ImageView) view.findViewById(R.id.hobby_item_image);
                hobbyItemFrame = (FrameLayout)view.findViewById(R.id.hobby_item_frame);
                hobbyItemNameText = (TextView)view.findViewById(R.id.hobby_item_name);
                hobbyItemCheck = (ImageView) view.findViewById(R.id.hobby_item_check);
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
