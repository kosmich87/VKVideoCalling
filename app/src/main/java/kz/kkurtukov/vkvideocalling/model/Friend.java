package kz.kkurtukov.vkvideocalling.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kkurtukov on 24.02.2016.
 */
public class Friend {
    /*************************************
     * PRIVATE FIELDS
     *************************************/
    private Integer friendId;
    private String firstName;
    private String lastName;
    private String photo50;
    private String photo100;
    private boolean isAppUse;
    private boolean isOnlineMobile;

    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }

    /*************************************
     * PUBLIC METHODS
     *************************************/

    public Friend getFromJson(JSONObject jsonObject){
        try {
            friendId = jsonObject.getInt("id");
            firstName = jsonObject.isNull("first_name") ? "" : jsonObject.getString("first_name");
            lastName = jsonObject.isNull("last_name") ? "" : jsonObject.getString("last_name");
            photo50 = jsonObject.isNull("photo_50") ? "" : jsonObject.getString("photo_50");
            photo100 = jsonObject.isNull("photo_100") ? "" : jsonObject.getString("photo_100");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public String getPhoto50() {
        return photo50;
    }

    public String getPhoto100() {
        return photo100;
    }

    public boolean isAppUse() {
        return isAppUse;
    }

    public void setIsAppUse(boolean isAppUse) {
        this.isAppUse = isAppUse;
    }

    public boolean isOnlineMobile() {
        return isOnlineMobile;
    }

    public void setIsOnlineMobile(boolean isOnline) {
        this.isOnlineMobile = isOnline;
    }
}
