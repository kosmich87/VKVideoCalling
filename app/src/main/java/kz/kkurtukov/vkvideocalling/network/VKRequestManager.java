package kz.kkurtukov.vkvideocalling.network;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONObject;

import kz.kkurtukov.vkvideocalling.utilities.debug.LLog;

/**
 * Created by kkurtukov on 18.02.2016.
 */
public class VKRequestManager {
    /*************************************
     * PRIVATE STATIC FIELDS
     *************************************/
    private static final String TAG = VKRequestManager.class.getSimpleName();
    /*************************************
     * PRIVATE FIELDS
     *************************************/
    private OnResultRequestListener mListener;

    /*************************************
     * PRIVATE STATIC FIELDS
     *************************************/
    private static VKRequestManager mInstance;

    /*************************************
     * PUBLIC STATIC METHODS
     *************************************/
    public static synchronized VKRequestManager getInstance() {
        LLog.e(TAG, "getInstance");
        if (mInstance == null) {
            mInstance = new VKRequestManager();
        }
        return mInstance;
    }

    /*************************************
     * PUBLIC METHODS
     *************************************/
    public void setListener(OnResultRequestListener listener) {
        LLog.e(TAG, "setListener");
        mListener = listener;
    }

    public void sendBaseRequest(VKRequest request, final RequestType requestType) {
        LLog.e(TAG, "sendBaseRequest");
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                LLog.e(TAG, "onComplete");
                if (mListener != null){
                    mListener.onResultSuccess(requestType, response.json);
                }
            }

            @Override
            public void onError(VKError error) {
                LLog.e(TAG, "onError - " + error.toString());
                if (mListener != null){
                    mListener.onResultFailed(error);
                }
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                LLog.e(TAG, "attemptFailed");
                if (mListener != null){
                    mListener.onAttamptFailed(request);
                }
            }
        });
    }

    /*************************************
     * PUBLIC INTERFACES
     *************************************/
    public interface OnResultRequestListener{
        public void onResultSuccess(RequestType requestType, JSONObject jsonObject);
        public void onResultFailed(VKError error);
        public void onAttamptFailed(VKRequest request);
    }
}
