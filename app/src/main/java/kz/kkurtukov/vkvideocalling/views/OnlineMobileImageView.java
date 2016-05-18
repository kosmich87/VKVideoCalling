package kz.kkurtukov.vkvideocalling.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import kz.kkurtukov.vkvideocalling.R;

/**
 * Created by kkurtukov on 24.03.2016.
 */
public class OnlineMobileImageView extends ImageView {

    private static final int[] STATE_ONLINE = {R.attr.state_mobile_online};

    private boolean isOnline;

    public OnlineMobileImageView(Context context) {
        super(context);
    }

    public OnlineMobileImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OnlineMobileImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public OnlineMobileImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        if (isOnline) {
            // We are going to add 1 extra state.
            final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

            mergeDrawableStates(drawableState, STATE_ONLINE);
            return drawableState;
        } else {
            return super.onCreateDrawableState(extraSpace);
        }
    }

    public void setFriendOnline(boolean isOnline) {
        if (this.isOnline != isOnline) {
            this.isOnline = isOnline;

            // Refresh the drawable state so that it includes the message unread
            // state if required.
            refreshDrawableState();
        }
    }
}
