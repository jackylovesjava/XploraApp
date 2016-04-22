package cn.com.xplora.xploraapp.adapter;

import android.content.Context;
import android.media.Image;
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

import java.util.List;

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.model.TrendsetterModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;

/**
 * Created by jackylovesjava on 16/4/19.
 */
public class GalleryAdapter extends
        RecyclerView.Adapter<GalleryAdapter.ViewHolder>
{

    private LayoutInflater mInflater;
    private List<TrendsetterModel> mDatas;
    private Context mContext;
    public GalleryAdapter(Context context, List<TrendsetterModel> datats)
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
        TextView mTrendsetterNameText;
        TextView mTrendsetterIntroText;
        TextView mTrendsetterFollowersText;
        ImageView mFollowBtn;
        ImageView mUnFollowBtn;
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
        TrendsetterModel trendsetterModel = mDatas.get(viewHolder.getLayoutPosition());
        if(TextUtils.isEmpty(trendsetterModel.getImageName())){
            viewHolder.mImg.setImageResource(R.drawable.profile_image_no);
        }else {
            imageLoader.displayImage(trendsetterModel.getImageUrl(), viewHolder.mImg);
        }
        if("CHN".equalsIgnoreCase(CommonUtil.getLang(mContext))){
            viewHolder.mTrendsetterNameText.setText(trendsetterModel.getFullName());
            viewHolder.mTrendsetterIntroText.setText(trendsetterModel.getIntro());
            viewHolder.mTrendsetterFollowersText.setText(trendsetterModel.getFollowers()+" 粉丝");
        }else{
            viewHolder.mTrendsetterNameText.setText(trendsetterModel.getFullNameEn());
            viewHolder.mTrendsetterIntroText.setText(trendsetterModel.getIntroEn());
            viewHolder.mTrendsetterFollowersText.setText(trendsetterModel.getFollowers()+" Followers");
        }
    }

}

