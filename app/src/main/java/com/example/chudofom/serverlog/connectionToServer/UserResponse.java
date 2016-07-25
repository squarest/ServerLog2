package com.example.chudofom.serverlog.connectionToServer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chudofom on 07.07.16.
 */
public class UserResponse {

    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("patronymic")
    public String patronymic;
    @SerializedName("age")
    public String age;
    @SerializedName("phone")
    public String phone;
    @SerializedName("email")
    public String email;
    @SerializedName("city")
    public String city;


}
