package kz.kkurtukov.vkvideocalling.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;

import kz.kkurtukov.vkvideocalling.R;
import kz.kkurtukov.vkvideocalling.model_manager.FriendManager;
import kz.kkurtukov.vkvideocalling.utilities.debug.LLog;

/**
 * Created by kkurtukov on 04.02.2016.
 */
public class VolleyRequestManager {
    private static final String TAG = VolleyRequestManager.class.getSimpleName();

    /*************************************
     * PRIVATE FIELDS
     *************************************/
    private Context mContext;
    private ResultRequestListener mListener;

    /*************************************
     * PRIVATE STATIC FIELDS
     *************************************/
    private static VolleyRequestManager mInstance;

    /*************************************
     * PUBLIC METHODS
     *************************************/
    public VolleyRequestManager(Context context) {
        mContext = context;
    }

    /*************************************
     * PUBLIC STATIC METHODS
     *************************************/
    public static synchronized VolleyRequestManager getInstance(Context context) {
        LLog.e(TAG, "getInstance");
        if (mInstance == null) {
            mInstance = new VolleyRequestManager(context);
        }
        return mInstance;
    }

    /*************************************
     * PUBLIC METHODS
     *************************************/
    public void setListener(ResultRequestListener mListener) {
        LLog.e(TAG, "setListener");
        this.mListener = mListener;
    }

    public void getFriendsOnline(String accessToken) {
        LLog.e(TAG, "getFriendsOnline");
        sendBaseRequest(Request.Method.POST,
                UrlConfiguration.getOnlineMobileFriendsPOST(),
                RequestFactory.getFriendOnlineMobileJson(),
                RequestType.FriendsGet,
                accessToken);
    }

    /*************************************
     * PRIVATE METHODS
     *************************************/
    private void sendBaseRequest(int method, String url, JSONObject jsonObject, final RequestType type, final Object... args) {
        LLog.e(TAG, "sendBaseRequest");
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (method,
                        url,
                        jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                switch (type) {
                                    case FriendsGet:
                                            FriendManager.getInstance().parseFriendsGetOnlineList(response);
                                            if (mListener != null) {
                                                mListener.onResultSuccess(RequestType.FriendsGet);
                                            }
                                        break;
                                }

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LLog.e(TAG, "onErrorResponse - " + error);
                        if (error.networkResponse == null) {
                            if (mListener != null) {
                                mListener.onConnectionFailed();
                            }
                        } else {
                            switch (type) {
                                case FriendsGet:
                                    if (mListener != null) {
                                        mListener.onResultFailed(R.string.friends_list_failed);
                                    }
                                    break;
                            }
                        }
                    }
                }) {
            @Override
            public HashMap<String, String> getHeaders() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("accessToken", String.valueOf(args[0]));
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }
        };

        RequestQueueSingleton.getInstance(mContext).addToRequestQueue(jsObjRequest);
    }

    /*************************************
     * PUBLIC INTERFACES
     *************************************/
    public interface ResultRequestListener{
        public void onResultSuccess(RequestType requestType);
        public void onResultFailed(int resId);
        public void onConnectionFailed();
    }
}
