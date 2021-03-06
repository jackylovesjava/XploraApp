package cn.com.xplora.xploraapp.fragments;

/**
 * Created by jackylovesjava on 16/4/16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.adapter.GalleryAdapter;
import cn.com.xplora.xploraapp.adapter.UserListAdapter;
import cn.com.xplora.xploraapp.asyncTasks.DoAfterExplore;
import cn.com.xplora.xploraapp.customUI.JingDongHeaderLayout;
import cn.com.xplora.xploraapp.customUI.SpaceItemDecoration;
import cn.com.xplora.xploraapp.model.TrendsetterModel;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;

/**
 * Created by lt on 2015/12/14.
 */
public class ExplorePeopleFragment extends Fragment{


    private List<TrendsetterModel> mTrendsetterList;
    private int mTrendsetterCurrentPage = 1;
    private int mTrendsetterStep = 0;
    private int mUserCurrentPage = 1;
    private int mUserStep = 0;
    private List<UserModel> mUserList;
    private RecyclerView mTrendsetterListView;
    private RecyclerView mUserListView;
    private GalleryAdapter mTrendsetterAdapter;
    private UserListAdapter mUserListAdapter;
    private Context mContext;
    private Button mConfirmBtn;
    private UserModel mCurrentUser;
    private TextView mNoMoreDataTV;
    private TextView mNoMoreUserDataTV;
    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }
    public void setContext(Context context){
        this.mContext = context;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_explore_people, null);

        //得到控件
        mTrendsetterListView = (RecyclerView) view.findViewById(R.id.id_recyclerview_horizontal);
        mUserListView = (RecyclerView)view.findViewById(R.id.id_recyclerview_vertical);
        mConfirmBtn = (Button)view.findViewById(R.id.btn_explore_confirm);
        mCurrentUser = CommonUtil.getCurrentUser(mContext);
        mNoMoreDataTV = (TextView)view.findViewById(R.id.tv_no_more_trendsetter);
        mNoMoreUserDataTV = (TextView)view.findViewById(R.id.tv_no_more_user);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTrendsetterListView.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManagerUserList = new LinearLayoutManager(mContext);
        linearLayoutManagerUserList.setOrientation(LinearLayoutManager.VERTICAL);
        mUserListView.setLayoutManager(linearLayoutManagerUserList);

        if(mTrendsetterList==null||mTrendsetterList.size()==0){
            mNoMoreDataTV.setVisibility(View.VISIBLE);
            mTrendsetterListView.setVisibility(View.GONE);
        }else {
            //设置适配器
            mTrendsetterAdapter = new GalleryAdapter(mContext, mTrendsetterList, mCurrentUser, mTrendsetterCurrentPage, mTrendsetterStep,mNoMoreDataTV);
            mTrendsetterListView.setAdapter(mTrendsetterAdapter);
        }
        mUserListAdapter = new UserListAdapter(mContext,mUserList, mCurrentUser, mUserCurrentPage, mUserStep,mNoMoreUserDataTV);
        mUserListView.setAdapter(mUserListAdapter);

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DoAfterExplore)mContext).doAfterExplore();
            }
        });
        return view;
    }

    public List<UserModel> getmUserList() {
        return mUserList;
    }

    public void setmUserList(List<UserModel> mUserList) {
        this.mUserList = mUserList;
    }

    public List<TrendsetterModel> getmTrendsetterList() {
        return mTrendsetterList;
    }

    public void setmTrendsetterList(List<TrendsetterModel>  mTrendsetterList) {
        this.mTrendsetterList = mTrendsetterList;
    }

    public int getmTrendsetterCurrentPage() {
        return mTrendsetterCurrentPage;
    }

    public void setmTrendsetterCurrentPage(int mTrendsetterCurrentPage) {
        this.mTrendsetterCurrentPage = mTrendsetterCurrentPage;
    }

    public int getmTrendsetterStep() {
        return mTrendsetterStep;
    }

    public void setmTrendsetterStep(int mTrendsetterStep) {
        this.mTrendsetterStep = mTrendsetterStep;
    }

    public int getmUserCurrentPage() {
        return mUserCurrentPage;
    }

    public void setmUserCurrentPage(int mUserCurrentPage) {
        this.mUserCurrentPage = mUserCurrentPage;
    }

    public int getmUserStep() {
        return mUserStep;
    }

    public void setmUserStep(int mUserStep) {
        this.mUserStep = mUserStep;
    }
}
