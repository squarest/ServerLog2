package com.example.chudofom.serverlog.ConnectionToServer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chudofom on 06.07.16.
 */
public class UserLogin {
    @SerializedName("app_name")
    String mName;

    @SerializedName("key")
    String mKey;

    @SerializedName("device_id")
    String mId;

    public UserLogin(String  name, String key, String id) {
        this.mName = name;
        this.mKey = key;
        this.mId = id;

    }
}
