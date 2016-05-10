package cn.com.xplora.xploraapp.customUI.likeanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.asyncTasks.CancelLikeEventAsyncTask;
import cn.com.xplora.xploraapp.asyncTasks.DoLikeEventAsyncTask;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;

/**
 * Created by Miroslaw Stanek on 20.12.2015.
 */
public class SmallLikeButtonView extends RelativeLayout implements View.OnClickListener {
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    @Bind(R.id.iv_event_like)
    ImageView ivStar;
    @Bind(R.id.tvLikeCount)
    TextView tvLikeCount;

    public boolean isChecked;
    private AnimatorSet animatorSet;
    int relateId;
    private View root;
    int likeCount;
    private Context context;
    public void setRelateId(int relateId){
        this.relateId = relateId;
    }
    public SmallLikeButtonView(Context context) {
        super(context);
        init(context);
    }

    public SmallLikeButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SmallLikeButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SmallLikeButtonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        root=LayoutInflater.from(getContext()).inflate(R.layout.view_small_like_button, this, true);
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    public void setChecked(boolean checked){
        isChecked = checked;
        if (isChecked) {
            ivStar.setImageResource(R.drawable.liked);
        }else{
            ivStar.setImageResource(R.drawable.like);

        }
    }
    public void setLikeCount(int likeCount){
        this.likeCount = likeCount;
        if(likeCount==0){
            tvLikeCount.setText("");

        }else{
            tvLikeCount.setText(String.valueOf(likeCount));

        }
    }
    @Override
    public void onClick(View v) {

        UserModel currentUser = CommonUtil.getCurrentUser(context);
        if(currentUser==null){
            Toast.makeText(context,getResources().getString(R.string.need_signin),Toast.LENGTH_LONG).show();
            return;
        }
        int userId = currentUser.getUuidInBack();



        isChecked = !isChecked;
        ivStar.setImageResource(isChecked ? R.drawable.liked : R.drawable.like);

        if (animatorSet != null) {
            animatorSet.cancel();
        }

        if (isChecked) {
            DoLikeEventAsyncTask doLikeTask = new DoLikeEventAsyncTask(relateId,userId);
            doLikeTask.execute();
            setLikeCount(likeCount+1);
            ivStar.animate().cancel();
            ivStar.setScaleX(0);
            ivStar.setScaleY(0);

            animatorSet = new AnimatorSet();

            ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(ivStar, ImageView.SCALE_Y, 0.2f, 1f);
            starScaleYAnimator.setDuration(350);
            starScaleYAnimator.setStartDelay(250);
            starScaleYAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(ivStar, ImageView.SCALE_X, 0.2f, 1f);
            starScaleXAnimator.setDuration(350);
            starScaleXAnimator.setStartDelay(250);
            starScaleXAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);


            animatorSet.playTogether(
                    starScaleYAnimator,
                    starScaleXAnimator
            );

            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    ivStar.setScaleX(1);
                    ivStar.setScaleY(1);
                }
            });

            animatorSet.start();
        }else{

            CancelLikeEventAsyncTask cancelLikeEventAsyncTask = new CancelLikeEventAsyncTask(relateId,userId);
            cancelLikeEventAsyncTask.execute();
            setLikeCount(likeCount-1);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ivStar.animate().scaleX(0.7f).scaleY(0.7f).setDuration(150).setInterpolator(DECCELERATE_INTERPOLATOR);
                setPressed(true);
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                boolean isInside = (x > 0 && x < getWidth() && y > 0 && y < getHeight());
                if (isPressed() != isInside) {
                    setPressed(isInside);
                }
                break;

            case MotionEvent.ACTION_UP:
                ivStar.animate().scaleX(1).scaleY(1).setInterpolator(DECCELERATE_INTERPOLATOR);
                if (isPressed()) {
                    performClick();
                    setPressed(false);
                }
                break;
        }
        return true;
    }
}
