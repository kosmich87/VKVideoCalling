package kz.kkurtukov.vkvideocalling.network;

/**
 * Created by kkurtukov on 18.02.2016.
 */
public class UrlConfiguration {
    private static final String TAG = UrlConfiguration.class.getSimpleName();

    public static String getBaseRequestGET(String method, String params, String accessToken){
        return String.format("https://api.vk.com/method/%s?%s&access_token=%s",
                method,
                params,
                accessToken);
    }

    public static String getOnlineMobileFriendsGET(String accessToken) {
        return getBaseRequestGET("friends.getOnline", "params[online_mobile]=1&params[v]=5.44", accessToken);

    }

    public static String getBaseRequestPOST(String method){
        return String.format("https://api.vk.com/method/%s",
                method);
    }

    public static String getOnlineMobileFriendsPOST() {
        return getBaseRequestPOST("friends.getOnline");

    }
}
