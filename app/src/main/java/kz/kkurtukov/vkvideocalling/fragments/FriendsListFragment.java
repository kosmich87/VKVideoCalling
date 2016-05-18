package kz.kkurtukov.vkvideocalling.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import kz.kkurtukov.vkvideocalling.R;
import kz.kkurtukov.vkvideocalling.adapters.FriendsListFragmentAdapter;
import kz.kkurtukov.vkvideocalling.model.Friend;
import kz.kkurtukov.vkvideocalling.model_manager.FriendManager;
import kz.kkurtukov.vkvideocalling.network.RequestType;
import kz.kkurtukov.vkvideocalling.network.VKRequestManager;
import kz.kkurtukov.vkvideocalling.utilities.debug.LLog;
import kz.kkurtukov.vkvideocalling.views.RobotoTextView;

/**
 * Created by kkurtukov on 18.02.2016.
 */
public class FriendsListFragment extends BaseFragment implements VKRequestManager.OnResultRequestListener {
    /*************************************
     * PRIVATE STATIC FIELDS
     *************************************/
    private static final String TAG = FriendsListFragment.class.getSimpleName();
    private static final double API_VERSION = 5.45;

    /*************************************
     * PRIVATE FIELDS
     *************************************/
    private ArrayList<Integer> friendsListOnlineMobile;
    private ArrayList<Integer> friendsAppUsersList;
    private ArrayList<Friend> friendsList = new ArrayList<Friend>();

    /*************************************
     * BINDS
     *************************************/
    @Bind(R.id.friend_list)
    RecyclerView mFriendList;
    @Bind(R.id.friend_list_empty_view)
    RobotoTextView mEmptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends_list, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    @Override
    public void onResultSuccess(RequestType requestType, JSONObject jsonObject) {
        LLog.e(TAG, "onResultSuccess");
        switch (requestType) {
            case FriendsGetOnline:
                friendsListOnlineMobile = FriendManager.getInstance().parseFriendsGetOnlineList(jsonObject);
                sendFriendsAppUsersRequest();
                break;
            case AppUsers:
                friendsAppUsersList = FriendManager.getInstance().parseFriendsAppUsersList(jsonObject);
                sendFriendsGetRequest();
                break;
            case FriendsGet:
                friendsList.addAll(FriendManager.getInstance().parseFriendsGetList(jsonObject,
                        friendsListOnlineMobile,
                        friendsAppUsersList));
                checkFiendsListIsEmpty();
                mFriendList.getAdapter().notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onResultFailed(VKError error) {

    }

    @Override
    public void onAttamptFailed(VKRequest request) {

    }

    /*************************************
     * PRIVATE METHODS
     *************************************/
    private void init() {
        LLog.e(TAG, "init");
        VKRequestManager.getInstance().setListener(this);
        setupRecyclerView(mFriendList);
        sendFriendsGetOnlineRequest();
        checkFiendsListIsEmpty();
    }

    private void checkFiendsListIsEmpty() {
        if (friendsList.isEmpty()){
            LLog.e(TAG, "checkFiendsListIsEmpty - empty");
            mEmptyView.setVisibility(View.VISIBLE);
            mFriendList.setVisibility(View.INVISIBLE);
        }else {
            LLog.e(TAG, "checkFiendsListIsEmpty - not empty");
            mEmptyView.setVisibility(View.INVISIBLE);
            mFriendList.setVisibility(View.VISIBLE);
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new FriendsListFragmentAdapter(friendsList, mMainActivity));
    }

    private void sendFriendsGetOnlineRequest() {
        LLog.e(TAG, "sendFriendsGetOnlineRequest");
        VKRequest request = VKApi.friends().getOnline(VKParameters.from(
                "online_mobile", 1,
                VKApiConst.VERSION, API_VERSION));
        VKRequestManager.getInstance().sendBaseRequest(request, RequestType.FriendsGetOnline);
    }

    private void sendFriendsAppUsersRequest() {
        LLog.e(TAG, "sendFriendsAppUsersRequest");
        VKRequest request = VKApi.friends().getAppUsers(VKParameters.from(
                VKApiConst.VERSION, API_VERSION));
        VKRequestManager.getInstance().sendBaseRequest(request, RequestType.AppUsers);
    }

    private void sendFriendsGetRequest() {
        LLog.e(TAG, "sendFriendsGetRequest");
        VKRequest request = VKApi.friends().get(VKParameters.from(
                VKApiConst.NAME_CASE, "nom",
                VKApiConst.FIELDS, "photo_50, photo_100",
                VKApiConst.VERSION, API_VERSION));
        VKRequestManager.getInstance().sendBaseRequest(request, RequestType.FriendsGet);
    }
}
