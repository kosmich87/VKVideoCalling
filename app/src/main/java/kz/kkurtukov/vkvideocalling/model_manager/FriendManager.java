package kz.kkurtukov.vkvideocalling.model_manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kz.kkurtukov.vkvideocalling.model.Friend;
import kz.kkurtukov.vkvideocalling.utilities.debug.LLog;

/**
 * Created by kkurtukov on 04.02.2016.
 */
public class FriendManager {
    private static final String TAG = FriendManager.class.getSimpleName();
    /*************************************
     * PRIVATE STATIC FIELDS
     *************************************/
    private static FriendManager mInstance;

    /*************************************
     * PUBLIC STATIC METHODS
     *************************************/
    public static FriendManager getInstance() {
        if (mInstance == null){
            mInstance = new FriendManager();
        }
        return mInstance;
    }

    public ArrayList<Friend> parseFriendsGetList(JSONObject response,
                                                 ArrayList<Integer> friendsListOnlineMobile,
                                                 ArrayList<Integer> friendsAppUsersList) {
        LLog.e(TAG, "parseFriendsGetList");
        ArrayList<Friend> friendsList = new ArrayList<Friend>();
        try {
            LLog.e(TAG, "parseFriendsGetList - " + response.toString(1));
            JSONObject jsonObject = response.getJSONObject("response");
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++){
                Friend friend = new Friend();
                friend.getFromJson(jsonArray.getJSONObject(i));
                friend.setIsOnlineMobile(friendsListOnlineMobile.contains(friend.getFriendId()));
                friend.setIsAppUse(friendsAppUsersList.contains(friend.getFriendId()));
                friendsList.add(friend);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return friendsList;
    }

    public ArrayList<Integer> parseFriendsGetOnlineList(JSONObject response) {
        LLog.e(TAG, "parseFriendsGetOnlineList");
        ArrayList<Integer> friendsIdListOnline = new ArrayList<Integer>();
        try {
            LLog.e(TAG, "parseFriendsGetOnlineList - " + response.toString(1));
            JSONObject jsonObject = response.getJSONObject("response");
            JSONArray jsonArray = jsonObject.getJSONArray("online_mobile");
            for (int i = 0; i < jsonArray.length(); i++){
                friendsIdListOnline.add(jsonArray.getInt(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return friendsIdListOnline;
    }

    public ArrayList<Integer> parseFriendsAppUsersList(JSONObject response) {
        LLog.e(TAG, "parseFriendsAppUsersList");
        ArrayList<Integer> friendsAppUsers = new ArrayList<Integer>();
        try {
            LLog.e(TAG, "parseFriendsAppUsersList - " + response.toString(1));
            JSONArray jsonArray = response.getJSONArray("response");
            for (int i = 0; i < jsonArray.length(); i++){
                friendsAppUsers.add(jsonArray.getInt(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return friendsAppUsers;
    }
}
