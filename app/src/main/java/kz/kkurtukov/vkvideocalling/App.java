package kz.kkurtukov.vkvideocalling;

import android.app.Application;
import android.content.Context;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import kz.kkurtukov.vkvideocalling.utilities.debug.LLog;

/**
 * Created by kkurtukov on 02.02.2016.
 */
public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    /*************************************
     * PRIVATE STATIC FIELDS
     *************************************/
    private static App sInstance;

    /*************************************
     * PRIVATE FIELDS
     *************************************/
    private VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                // VKAccessToken is invalid
            }
        }
    };

    /*************************************
     * PUBLIC METHODS
     *************************************/
    @Override
    public void onCreate() {
        super.onCreate();
        LLog.e(TAG, "onCreate");
        sInstance = this;
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(sInstance);
    }

    /*************************************
     * PUBLIC STATIC METHODS
     *************************************/
    public static Context getContext() {
        return sInstance;
    }
}
