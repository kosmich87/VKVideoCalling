package kz.kkurtukov.vkvideocalling.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import kz.kkurtukov.vkvideocalling.MainActivity;
import kz.kkurtukov.vkvideocalling.R;
import kz.kkurtukov.vkvideocalling.model.Friend;
import kz.kkurtukov.vkvideocalling.network.RequestQueueSingleton;
import kz.kkurtukov.vkvideocalling.utilities.debug.LLog;
import kz.kkurtukov.vkvideocalling.views.CircularNetworkImageView;
import kz.kkurtukov.vkvideocalling.views.OnlineMobileImageView;
import kz.kkurtukov.vkvideocalling.views.RobotoBoldTextView;

/**
 * Created by kkurtukov on 23.03.2016.
 */
public class FriendsListFragmentAdapter extends RecyclerView.Adapter<FriendsListFragmentAdapter.ViewHolder> {

    private static final String TAG = FriendsListFragmentAdapter.class.getSimpleName();

    /*************************************
     * PRIVATE FIELDS
     *************************************/
    private List<Friend> mFriends;
    private Bundle mBundle;
    private Context mContext;

    /*************************************
     * PUBLIC METHODS
     *************************************/
    public FriendsListFragmentAdapter(List<Friend> friends, Context context) {
        LLog.e(TAG, "FriendsListFragmentAdapter");
        mFriends = friends;
        mBundle = new Bundle();
        mContext = context;
    }

    @Override
    public FriendsListFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LLog.e(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_friends_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        LLog.e(TAG, "onBindViewHolder - " + position);
        final Friend friend = mFriends.get(position);
        holder.mPhoto.setImageUrl(friend.getPhoto50(), RequestQueueSingleton.getInstance(mContext).getImageLoader());
        holder.mFullName.setText(friend.toString());
        holder.isOnlineMobile.setFriendOnline(friend.isOnlineMobile());
        if (friend.isOnlineMobile()){
            holder.isOnlineMobile.setVisibility(View.VISIBLE);
        }else {
            holder.isOnlineMobile.setVisibility(View.INVISIBLE);
        }
        if (friend.isAppUse()){
            holder.mMayCall.setVisibility(View.VISIBLE);
        }else {
            holder.mMayCall.setVisibility(View.INVISIBLE);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)mContext).makeCall(friend.getFriendId(), friend.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    /*************************************
     * PUBLIC CLASSES
     *************************************/
    public static class ViewHolder extends RecyclerView.ViewHolder {

        /*************************************
         * PUBLIC FIELDS
         *************************************/
        public View mView;
        public CircularNetworkImageView mPhoto;
        public OnlineMobileImageView isOnlineMobile;
        public RobotoBoldTextView mFullName;
        public ImageView mMayCall;

        /*************************************
         * PUBLIC METHODS
         *************************************/
        public ViewHolder(View view) {
            super(view);
            mView = view;
            LLog.e(TAG, "ViewHolder");
            mPhoto = (CircularNetworkImageView) itemView.findViewById(R.id.view_friends_list_item_photo);
            isOnlineMobile = (OnlineMobileImageView) itemView.findViewById(R.id.view_friends_list_item_status);
            mFullName = (RobotoBoldTextView) itemView.findViewById(R.id.view_friends_list_item_full_name);
            mMayCall = (ImageView) itemView.findViewById(R.id.view_friends_list_item_may_call);
        }

    }
}
