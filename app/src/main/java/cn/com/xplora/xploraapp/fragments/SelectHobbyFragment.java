package cn.com.xplora.xploraapp.fragments;

/**
 * Created by jackylovesjava on 16/4/16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;

import java.util.Arrays;
import java.util.LinkedList;

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.adapter.MyCoverFlowAdapter;
import cn.com.xplora.xploraapp.customUI.CoverFlowView;
import cn.com.xplora.xploraapp.customUI.JingDongHeaderLayout;
import cn.com.xplora.xploraapp.customUI.SpaceItemDecoration;

/**
 * Created by lt on 2015/12/14.
 */
public class SelectHobbyFragment extends Fragment{


    private LinkedList<String> mListItems;
    private LinkedList<Integer> mListImages;
    private PullToRefreshRecyclerView mPullRefreshRecyclerView;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private Context mContext;
    private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam",
            "Abondance", "Ackawi","Acorn"};
    private Integer[] mImages = {R.drawable.img04,R.drawable.img05,R.drawable.img06,
            R.drawable.img09,R.drawable.img08};
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
                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                new GetDataTask().execute();
            }
        });

        mListItems = new LinkedList<String>();
        mListItems.addAll(Arrays.asList(mStrings));
        mListImages = new LinkedList<Integer>();
        mListImages.addAll(Arrays.asList(mImages));
        mAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return mStrings;
        }

        @Override
        protected void onPostExecute(String[] result) {
            mListItems.addFirst("Added after refresh...");
            mListItems.addAll(Arrays.asList(result));
            mAdapter.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshRecyclerView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }




    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public int getItemCount() {
            return mListImages.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.hobby_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((MyViewHolder)holder).hobbyImageView.setImageResource(mListImages.get(position));
//			int bg = Color.rgb((int) Math.floor(Math.random() * 128) + 64,
//					(int) Math.floor(Math.random() * 128) + 64,
//					(int) Math.floor(Math.random() * 128) + 64);
//			((MyViewHolder)holder).tv.setBackgroundColor(bg);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView hobbyImageView;
            public MyViewHolder(View view) {
                super(view);
                hobbyImageView = (ImageView) view.findViewById(R.id.hobby_item_image);
            }
        }

    }
}
