package kz.kkurtukov.vkvideocalling.network;

import org.json.JSONException;
import org.json.JSONObject;

import kz.kkurtukov.vkvideocalling.utilities.debug.LLog;

/**
 * Created by kkurtukov on 04.02.2016.
 */
public class RequestFactory {
    private static final String TAG = RequestFactory.class.getSimpleName();
    private static final double VERSION_CODE = 5.44;

    public static JSONObject getFriendOnlineMobileJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("v", VERSION_CODE);
            jsonObject.put("online_mobile", 1);
        }catch (JSONException e){
            LLog.e(TAG, "getFriendOnlineMobileJson. Exeption - " + e.toString());
        }
        return jsonObject;
    }
}
