package cn.com.xplora.xploraapp.adapter;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.asyncTasks.DoAfterResultInterface;
import cn.com.xplora.xploraapp.asyncTasks.DoAfterSelectCity;
import cn.com.xplora.xploraapp.db.UserDAO;
import cn.com.xplora.xploraapp.db.XploraDBHelper;
import cn.com.xplora.xploraapp.fragments.ExplorePeopleFragment;
import cn.com.xplora.xploraapp.json.BaseJsonResolver;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.json.TrendsetterPageResult;
import cn.com.xplora.xploraapp.json.TrendsetterPageResultJsonResolver;
import cn.com.xplora.xploraapp.model.TrendsetterModel;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.HttpUtil;
import cn.com.xplora.xploraapp.utils.IConstant;

/**
 * Created by jackylovesjava on 16/4/19.
 */
public class GalleryAdapter extends
        RecyclerView.Adapter<GalleryAdapter.ViewHolder>
{

    private LayoutInflater mInflater;
    private List<TrendsetterModel> mDatas;
    private List<TrendsetterModel> mShowDatas = new ArrayList<TrendsetterModel>();//用来显示的数据
    private List<TrendsetterModel> mPrepareDatas = new ArrayList<TrendsetterModel>();//用来替补显示的数据
    private int SHOW_DATA = 5;
    private int REFETCH_DATA_LINE = 2;//当mPrepareDatas小于等于REFETCH_DATA_LINE时，向服务器请求更多数据
    private Context mContext;
    private UserModel mCurrentUser;
    private int mCurrentPage=1;
    private int mPageSize=10;
    private int mStep=0;//0 find similar hobby users;1 find different hobby users;
    private RefetchDataTask mRefetchDataTask;
    private TextView noMoreDataTV;
    public GalleryAdapter(Context context, List<TrendsetterModel> datas,UserModel mCurrentUser,int mCurrentPage,int mStep,TextView noMoreDataTV)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        mContext = context;
        this.noMoreDataTV = noMoreDataTV;
        this.mCurrentUser = mCurrentUser;
        this.mCurrentPage = mCurrentPage;
        this.mStep = mStep;
        if(mDatas.size()>SHOW_DATA){//超过show_data个数，需要分一部分用于替补显示
            for(int i =0;i<SHOW_DATA;i++){
                mShowDatas.add(mDatas.get(i));
            }
            for(int i = SHOW_DATA;i<mDatas.size();i++){
                mPrepareDatas.add(mDatas.get(i));
            }
        }else{
            mShowDatas = mDatas;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        ImageView mImg;
        TextView mTrendsetterNameText;
        TextView mTrendsetterIntroText;
        TextView mTrendsetterFollowersText;
        ImageView mFollowBtn;
        ImageView mUnFollowBtn;
    }

    @Override
    public int getItemCount()
    {
        return mShowDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.trendsetter_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.id_index_gallery_item_image);
        viewHolder.mTrendsetterNameText = (TextView)view.findViewById(R.id.trendsetter_name);
        viewHolder.mTrendsetterIntroText = (TextView)view.findViewById(R.id.trendsetter_intro);
        viewHolder.mTrendsetterFollowersText = (TextView)view.findViewById(R.id.trendsetter_followers);
        viewHolder.mFollowBtn = (ImageView)view.findViewById(R.id.btn_follow_trendsetter);
        viewHolder.mUnFollowBtn = (ImageView)view.findViewById(R.id.btn_unfollow_trendsetter);

        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position)
    {
        ImageLoader imageLoader = CommonUtil.getImageLoader(mContext);
        DisplayImageOptions displayImageOptions = CommonUtil.getDefaultImageLoadOption();
        TrendsetterModel trendsetterModel = mShowDatas.get(viewHolder.getLayoutPosition());
        if(TextUtils.isEmpty(trendsetterModel.getImageName())){
            viewHolder.mImg.setImageResource(R.drawable.profile_image_no);
        }else {
            imageLoader.displayImage(trendsetterModel.getImageUrl(), viewHolder.mImg);
        }
        if(trendsetterModel.getFollowedByCurrentUser()==1){
            viewHolder.mUnFollowBtn.setVisibility(View.VISIBLE);
            viewHolder.mFollowBtn.setVisibility(View.GONE);
        }else{
            viewHolder.mUnFollowBtn.setVisibility(View.GONE);
            viewHolder.mFollowBtn.setVisibility(View.VISIBLE);
        }

        if("CHN".equalsIgnoreCase(CommonUtil.getLang(mContext))){
            viewHolder.mTrendsetterNameText.setText(trendsetterModel.getFullName());
            viewHolder.mTrendsetterIntroText.setText(trendsetterModel.getIntro());
            viewHolder.mTrendsetterFollowersText.setText(trendsetterModel.getFollowers() + " 粉丝");
        }else{
            viewHolder.mTrendsetterNameText.setText(trendsetterModel.getFullNameEn());
            viewHolder.mTrendsetterIntroText.setText(trendsetterModel.getIntroEn());
            viewHolder.mTrendsetterFollowersText.setText(trendsetterModel.getFollowers()+" Followers");
        }

        viewHolder.mFollowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.mUnFollowBtn.setVisibility(View.VISIBLE);
                viewHolder.mFollowBtn.setVisibility(View.GONE);
                new DoFollowTask().execute(viewHolder.getLayoutPosition());
            }
        });

        viewHolder.mUnFollowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.mFollowBtn.setVisibility(View.VISIBLE);
                viewHolder.mUnFollowBtn.setVisibility(View.GONE);
                new DoUnFollowTask().execute(viewHolder.getLayoutPosition());
            }
        });
    }

    private class DoFollowTask extends AsyncTask<Integer, Void, HashMap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected HashMap doInBackground(Integer... params) {
            // Simulates a background job.
            try {

                Thread.sleep(1000);

            }catch (Exception ex){
                ex.printStackTrace();;
            }
            HashMap map = new HashMap();

            int followToTrendsetterPosition = params[0];
            TrendsetterModel followToTrendsetter = mShowDatas.get(followToTrendsetterPosition);
            HttpUtil http = new HttpUtil("http://120.76.98.160:8080/admin/api/people/doFollow");
            String result = http.doGet("followFrom=" + mCurrentUser.getUuidInBack() + "&followTo=" + followToTrendsetter.getUuidInBack());
            BaseResult apiResult = BaseJsonResolver.parseSimpleResult(result);
            map.put("apiResult",apiResult);
            map.put("followToTrendsetterPosition", followToTrendsetterPosition);

            return map;
        }

        @Override
        protected void onPostExecute(HashMap result) {
            super.onPostExecute(result);
            BaseResult apiResult = (BaseResult)result.get("apiResult");
            if(apiResult.isResult()) {
                int followings = mCurrentUser.getFollowings();
                mCurrentUser.setFollowings(followings + 1);

                XploraDBHelper dbHelper = new XploraDBHelper(mContext, "XPLORA");
                UserDAO userDAO = new UserDAO(dbHelper);
                userDAO.updateUser(mCurrentUser);
                Integer followToTrendsetterPosition = (Integer) result.get("followToTrendsetterPosition");
                TrendsetterModel removedTrendsetter = mShowDatas.get(followToTrendsetterPosition);
                mShowDatas.remove(removedTrendsetter);
                notifyItemRemoved(followToTrendsetterPosition);

                if(mPrepareDatas.size()>0) {
                    TrendsetterModel trendsetterModelToAdd = mPrepareDatas.get(0);//从替补中选择第一位数据放入showDatas进行显示
                    mShowDatas.add(followToTrendsetterPosition,trendsetterModelToAdd);
                    notifyItemInserted(followToTrendsetterPosition);
                    mPrepareDatas.remove(trendsetterModelToAdd);
                    if(mPrepareDatas.size()<=REFETCH_DATA_LINE){
                        if(mRefetchDataTask==null) {
                            new RefetchDataTask(mContext).execute();
                        }
                    }
                }
                if(mShowDatas.size()==0){
                    noMoreDataTV.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    private class DoUnFollowTask extends AsyncTask<Integer, Void, BaseResult> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected BaseResult doInBackground(Integer... params) {
            // Simulates a background job.
            HashMap map = new HashMap();

            int followToTrendsetterPosition = params[0];
            TrendsetterModel followToTrendsetter = mShowDatas.get(followToTrendsetterPosition);
            HttpUtil http = new HttpUtil("http://120.76.98.160:8080/admin/api/people/cancelFollow");
            String result = http.doGet("followFrom=" + mCurrentUser.getUuidInBack() + "&followTo=" + followToTrendsetter.getUuidInBack());
            BaseResult apiResult = BaseJsonResolver.parseSimpleResult(result);

            return apiResult;
        }

        @Override
        protected void onPostExecute(BaseResult result) {
            super.onPostExecute(result);
            if(result.isResult()) {
                int followings = mCurrentUser.getFollowings();
                mCurrentUser.setFollowings(followings - 1);

                XploraDBHelper dbHelper = new XploraDBHelper(mContext, "XPLORA");
                UserDAO userDAO = new UserDAO(dbHelper);
                userDAO.updateUser(mCurrentUser);

            }
        }
    }
    private class RefetchDataTask extends AsyncTask {

        private String TAG = "XPLORA";
        private String apiUrl = "http://120.76.98.160:8080/admin/api/people/trendsetterPage";

        private Context context;


        public RefetchDataTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mRefetchDataTask = null;
            TrendsetterPageResult result = (TrendsetterPageResult)o;
            if(result.isResult()){
                for (TrendsetterModel trendsetterModel: result.getTrendsetterList()) {
                    if(!mDatas.contains(trendsetterModel)){
                        mDatas.add(trendsetterModel);
                        mPrepareDatas.add(trendsetterModel);
                    }
                }
                mCurrentPage = result.getCurrentPage();
                mStep = result.getStep();
            }
        }

        @Override
        protected Object doInBackground(Object[] params) {
            HttpUtil http = new HttpUtil(apiUrl);
            String result = http.doGet("userId=" + mCurrentUser.getUuidInBack() + "&nowPage=" + (mCurrentPage+1) + "&pageShow=" + mPageSize + "&step=" + mStep);
            TrendsetterPageResult trendsetterPageResult = TrendsetterPageResultJsonResolver.parse(result);
            return trendsetterPageResult;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

}

