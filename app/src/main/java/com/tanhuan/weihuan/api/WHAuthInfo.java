package com.tanhuan.weihuan.api;

import android.content.Context;
import android.os.Bundle;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.utils.Utility;

/**
 * Created by chenyaning on 2017/12/23.
 */

public class WHAuthInfo extends AuthInfo {
    private String mAppKey = "";
    private String mRedirectUrl = "";
    private String mScope = "";
    private String mPackageName = "";
    private String mKeyHash = "";

    public WHAuthInfo(Context context, String appKey, String redirectUrl, String scope, String packageName) {
        super(context, appKey, redirectUrl, scope);
        this.mAppKey = appKey;
        this.mRedirectUrl = redirectUrl;
        this.mScope = scope;
        this.mPackageName = packageName;
        this.mKeyHash = Utility.getSign(context, this.mPackageName);
    }

    public String getAppKey() {
        return this.mAppKey;
    }

    public String getRedirectUrl() {
        return this.mRedirectUrl;
    }

    public String getScope() {
        return this.mScope;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getKeyHash() {
        return this.mKeyHash;
    }

    public Bundle getAuthBundle() {
        Bundle mBundle = new Bundle();
        mBundle.putString("appKey", this.mAppKey);
        mBundle.putString("redirectUri", this.mRedirectUrl);
        mBundle.putString("scope", this.mScope);
        mBundle.putString("packagename", this.mPackageName);
        mBundle.putString("key_hash", this.mKeyHash);
        return mBundle;
    }

    public static AuthInfo parseBundleData(Context context, Bundle data) {
        String appKey = data.getString("appKey");
        String redirectUrl = data.getString("redirectUri");
        String scope = data.getString("scope");
        return new AuthInfo(context, appKey, redirectUrl, scope);
    }
}
