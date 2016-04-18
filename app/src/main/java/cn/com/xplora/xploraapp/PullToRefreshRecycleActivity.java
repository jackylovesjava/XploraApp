package cn.com.xplora.xploraapp;

import java.util.Arrays;
import java.util.LinkedList;

import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import cn.com.xplora.xploraapp.customUI.JingDongHeaderLayout;
import cn.com.xplora.xploraapp.customUI.SpaceItemDecoration;

public class PullToRefreshRecycleActivity extends Activity{
	
	static final int MENU_SET_MODE = 0;
	
	private LinkedList<String> mListItems;
	private LinkedList<Integer> mListImages;
	private PullToRefreshRecyclerView mPullRefreshRecyclerView;
	private RecyclerView mRecyclerView;
	private RecyclerViewAdapter mAdapter;
	
	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam",
			"Abondance", "Ackawi","Acorn"};
	private Integer[] mImages = {R.drawable.img04,R.drawable.img05,R.drawable.img06,
			R.drawable.img09,R.drawable.img08};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_recycler);
		mPullRefreshRecyclerView = (PullToRefreshRecyclerView) this.findViewById(R.id.pull_refresh_recycler);
		mPullRefreshRecyclerView.setHeaderLayout(new JingDongHeaderLayout(this));
		mPullRefreshRecyclerView.setHasPullUpFriction(false); // 设置没有上拉阻力

		mRecyclerView = mPullRefreshRecyclerView.getRefreshableView();
//		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
		SpaceItemDecoration decoration=new SpaceItemDecoration(2);
		mRecyclerView.addItemDecoration(decoration);
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshRecyclerView.setOnRefreshListener(new OnRefreshListener2<RecyclerView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
				Toast.makeText(PullToRefreshRecycleActivity.this, "Pull Down!", Toast.LENGTH_SHORT).show();
				new GetDataTask().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
				Toast.makeText(PullToRefreshRecycleActivity.this, "Pull Up!", Toast.LENGTH_SHORT).show();
				new GetDataTask().execute();
			}
		});
		
		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));
		mListImages = new LinkedList<Integer>();
		mListImages.addAll(Arrays.asList(mImages));
		mAdapter = new RecyclerViewAdapter();
		mRecyclerView.setAdapter(mAdapter);
		
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_SET_MODE, 0,
				mPullRefreshRecyclerView.getMode() == Mode.BOTH ? "Change to MODE_PULL_DOWN"
						: "Change to MODE_PULL_BOTH");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem setModeItem = menu.findItem(MENU_SET_MODE);
		setModeItem.setTitle(mPullRefreshRecyclerView.getMode() == Mode.BOTH ? "Change to MODE_PULL_FROM_START"
				: "Change to MODE_PULL_BOTH");

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_SET_MODE:
				mPullRefreshRecyclerView
						.setMode(mPullRefreshRecyclerView.getMode() == Mode.BOTH ? Mode.PULL_FROM_START
								: Mode.BOTH);
				break;
		}

		return super.onOptionsItemSelected(item);
	}
	
	
	class RecyclerViewAdapter extends Adapter<ViewHolder> {

		@Override
		public int getItemCount() {
			return mListImages.size();
		}

		@Override
		public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
            		PullToRefreshRecycleActivity.this).inflate(R.layout.hobby_item, parent,
                    false));
            return holder;
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			((MyViewHolder)holder).hobbyImageView.setImageResource(mListImages.get(position));
//			int bg = Color.rgb((int) Math.floor(Math.random() * 128) + 64,
//					(int) Math.floor(Math.random() * 128) + 64,
//					(int) Math.floor(Math.random() * 128) + 64);
//			((MyViewHolder)holder).tv.setBackgroundColor(bg);
		}

        class MyViewHolder extends ViewHolder {
            ImageView hobbyImageView;
            public MyViewHolder(View view) {
                super(view);
				hobbyImageView = (ImageView) view.findViewById(R.id.hobby_item_image);
            }
        }
		
	}

}
