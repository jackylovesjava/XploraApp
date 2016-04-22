package cn.com.xplora.xploraapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.model.TrendsetterModel;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;

/**
 * Created by yckj on 2016/4/22.
 */
public class UserListAdapter  extends
        RecyclerView.Adapter<UserListAdapter.ViewHolder>
{

    private LayoutInflater mInflater;
    private List<UserModel> mDatas;
    private Context mContext;
    public UserListAdapter(Context context, List<UserModel> datats)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        ImageView mImg;
//        TextView mTrendsetterNameText;
//        TextView mTrendsetterIntroText;
//        TextView mTrendsetterFollowersText;
//        ImageView mFollowBtn;
//        ImageView mUnFollowBtn;
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.user_list_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.user_image);
//        viewHolder.mTrendsetterNameText = (TextView)view.findViewById(R.id.trendsetter_name);
//        viewHolder.mTrendsetterIntroText = (TextView)view.findViewById(R.id.trendsetter_intro);
//        viewHolder.mTrendsetterFollowersText = (TextView)view.findViewById(R.id.trendsetter_followers);
//        viewHolder.mFollowBtn = (ImageView)view.findViewById(R.id.btn_follow_trendsetter);
//        viewHolder.mUnFollowBtn = (ImageView)view.findViewById(R.id.btn_unfollow_trendsetter);
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
        UserModel userModel = mDatas.get(viewHolder.getLayoutPosition());
        if(TextUtils.isEmpty(userModel.getImageName())){
            viewHolder.mImg.setImageResource(R.drawable.profile_image_no);
        }else {
            imageLoader.displayImage(userModel.getImageUrl(), viewHolder.mImg,displayImageOptions);
        }
//        if("CHN".equalsIgnoreCase(CommonUtil.getLang(mContext))){
//            viewHolder.mTrendsetterNameText.setText(trendsetterModel.getFullName());
//            viewHolder.mTrendsetterIntroText.setText(trendsetterModel.getIntro());
//            viewHolder.mTrendsetterFollowersText.setText(trendsetterModel.getFollowers()+" 粉丝");
//        }else{
//            viewHolder.mTrendsetterNameText.setText(trendsetterModel.getFullNameEn());
//            viewHolder.mTrendsetterIntroText.setText(trendsetterModel.getIntroEn());
//            viewHolder.mTrendsetterFollowersText.setText(trendsetterModel.getFollowers()+" Followers");
//        }
    }

}

