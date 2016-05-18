package kz.kkurtukov.vkvideocalling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import butterknife.Bind;
import butterknife.ButterKnife;
import kz.kkurtukov.vkvideocalling.fragments.FriendsListFragment;
import kz.kkurtukov.vkvideocalling.utilities.debug.LLog;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String FRIEND_ID = "friendId";
    public static final String FRIEND_NAME = "friendName";

    /*************************************
     * BINDS
     *************************************/
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    /*************************************
     * LIFECYCLE METHODS
     *************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LLog.e(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        LLog.setDebuggable(BuildConfig.DEBUG);
        ButterKnife.bind(this);
        initToolbar();
        init();
    }

    @Override
    protected void onDestroy() {
        LLog.e(TAG, "onDestroy");
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LLog.e(TAG, "onActivityResult");
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                //TO DO
                //load friends list
                showFriendListScreen();
            }

            @Override
            public void onError(VKError error) {
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    /*************************************
     * PUBLIC METHODS
     *************************************/
    public Toolbar getToolbar() {
        LLog.e(TAG, "getToolbar");
        return mToolbar;
    }

    public void showFriendListScreen() {
        LLog.e(TAG, "showFriendListScreen");
        showFragment(new FriendsListFragment(), false);
    }

    public void makeCall(Integer friendId, String friendName) {
        LLog.e(TAG, "makeCall");

        Intent intent = new Intent(this, RtcActivity.class);
        Bundle b = new Bundle();
        b.putInt(FRIEND_ID, friendId);
        b.putString(FRIEND_NAME, friendName);
        intent.putExtras(b);
        startActivity(intent);
    }
    /*************************************
     * PROTECTED METHODS
     *************************************/
    protected void initToolbar() {
        LLog.e(TAG, "initToolbar");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
//        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    /*************************************
     * PRIVATE METHODS
     *************************************/
    private void init() {
        LLog.e(TAG, "init");
        if (VKSdk.wakeUpSession(App.getContext())){
            showFriendListScreen();
        }else {
            VKSdk.login(this, "friends,offline");
        }
    }

    private void showFragment(Fragment fragment, boolean addToBackStack) {
        LLog.e(TAG, "showFragment");
        if (addToBackStack) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }
}
