package com.lafresh.smile2.Repository.Model;

import com.google.gson.Gson;

public class Message {
    public static final String TAG = Message.class.getSimpleName();

    public int id;
    public String date;
    public String content;
    public String name;
    public String is_reply;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
