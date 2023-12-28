package com.lafresh.smile2;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPost {

    private String TAG = "HttpPost.java(TAG)";
    private Context context;

    public HttpPost(Context ct) {
        this.context = ct;
    }

    private HttpPost.Post_Call_Back listener;

    public interface Post_Call_Back {
        void Success();

        void Fail();
    }

    public void setOnPostBack(HttpPost.Post_Call_Back l) {
        listener = l;
    }

    public void httprequest(final String postUrl, final String pattern) {

        Log.d(TAG, "httprequest: =============33");

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection conn = null;

                try {
                    URL url = new URL(postUrl);
                    // 打開和URL之間的連接
                    conn = (HttpURLConnection) url.openConnection();
                    // 設定請求的方法
                    conn.setRequestMethod("POST");
                    // .setDoOutput默認是false，如果是GET請求不用設定
                    // POST的話要設定true
                    conn.setDoOutput(true);
                    // 設定連線逾時
                    conn.setConnectTimeout(2 * 1000);
                    // 設定讀取數據逾時
                    conn.setReadTimeout(2 * 1000);
                    // 設定請求屬性
                    conn.setRequestProperty("Content-Type"
                            , "application/json; charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    // 抓取輸出管道
                    OutputStream outputStream = conn.getOutputStream();
                    // 建立輸出物件
                    OutputStreamWriter osw = new OutputStreamWriter(outputStream);
                    // 把連線樣式輸出
                    osw.write(pattern);

                    // 文字容量超過8KB就要使用，為了安全起見一律使用
                    osw.flush();
                    // 關閉輸出物件
                    osw.close();


                    // 獲取連線後取得的回應碼
                    // 200 = OK 、 401 = 未經授權 、 -1 = 不是有效的HTTP
                    Log.d(TAG, "getResponseCode: " + conn.getResponseCode());
                    switch (conn.getResponseCode()) {
                        case 200:
                            Log.d(TAG, "連線回應 - 成功");
                            break;
                        case 401:
                            Log.d(TAG, "連線回應碼 - 未經授權");
                            break;
                        case -1:
                            Log.d(TAG, "連線回應 - 不是有效的HTTP");
                            break;
                        default:
                            Log.d(TAG, "連線回應 - 其他錯誤");
                            break;
                    }


                    // 抓取輸入管道
                    InputStream inputStream = conn.getInputStream();
                    // 建立輸入物件
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    // 建立容器
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuilder sb = new StringBuilder();
                    // 文字內容
                    String line = null;
                    // 讀取一行文字，直到null
                    while ((line = reader.readLine()) != null) {
                        // sb如有內容，則在之後添加
                        sb.append(line + "\n");
                    }
                    // 把sb轉文字
                    String response = sb.toString();

                    Log.d(TAG, "伺服器回應: " + response);

                    reader.close();

                    JSONObject jsonObject = new JSONObject(response);
                    String data = jsonObject.getString("Data");
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                    MemberBean.Global_Code = jsonObject1.getString("GlobalCode");
                    Log.d(TAG, "MemberBean.Global_Code: " + MemberBean.Global_Code);
                    Log.d(TAG, "data: " + data);

                    if (response.contains("\"isSuccess\":\"true\"")) {
                        listener.Success();
                    } else {
                        listener.Fail();
                    }

                } catch (Exception e) {
                    String err = e.toString().substring(e.toString().indexOf(":") + 1);
                    Log.e(TAG, "error: " + err);
                    e.printStackTrace();
                    listener.Fail();
                }
            }
        }).start();
    }

    public void httprequest2(final String postUrl, final String pattern) {

        Log.d(TAG, "httprequest: =============33");

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection conn = null;

                try {
                    URL url = new URL(postUrl);
                    // 打開和URL之間的連接
                    conn = (HttpURLConnection) url.openConnection();
                    // 設定請求的方法
                    conn.setRequestMethod("POST");
                    // .setDoOutput默認是false，如果是GET請求不用設定
                    // POST的話要設定true
                    conn.setDoOutput(true);
                    // 設定連線逾時
                    conn.setConnectTimeout(2 * 1000);
                    // 設定讀取數據逾時
                    conn.setReadTimeout(2 * 1000);
                    // 設定請求屬性
                    conn.setRequestProperty("Content-Type"
                            , "application/json; charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    // 抓取輸出管道
                    OutputStream outputStream = conn.getOutputStream();
                    // 建立輸出物件
                    OutputStreamWriter osw = new OutputStreamWriter(outputStream);
                    // 把連線樣式輸出
                    osw.write(pattern);

                    // 文字容量超過8KB就要使用，為了安全起見一律使用
                    osw.flush();
                    // 關閉輸出物件
                    osw.close();


                    // 獲取連線後取得的回應碼
                    // 200 = OK 、 401 = 未經授權 、 -1 = 不是有效的HTTP
                    Log.d(TAG, "getResponseCode: " + conn.getResponseCode());
                    switch (conn.getResponseCode()) {
                        case 200:
                            Log.d(TAG, "連線回應 - 成功");
                            break;
                        case 401:
                            Log.d(TAG, "連線回應碼 - 未經授權");
                            break;
                        case -1:
                            Log.d(TAG, "連線回應 - 不是有效的HTTP");
                            break;
                        default:
                            Log.d(TAG, "連線回應 - 其他錯誤");
                            break;
                    }


                    // 抓取輸入管道
                    InputStream inputStream = conn.getInputStream();
                    // 建立輸入物件
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    // 建立容器
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuilder sb = new StringBuilder();
                    // 文字內容
                    String line = null;
                    // 讀取一行文字，直到null
                    while ((line = reader.readLine()) != null) {
                        // sb如有內容，則在之後添加
                        sb.append(line + "\n");
                    }
                    // 把sb轉文字
                    String response = sb.toString();

                    Log.d(TAG, "伺服器回應: " + response);

                    reader.close();

                    if (response.contains("\"isSuccess\":\"true\"")) {
                        listener.Success();
                    } else {
                        listener.Fail();
                    }

                } catch (Exception e) {
                    String err = e.toString().substring(e.toString().indexOf(":") + 1);
                    Log.e(TAG, "error: " + err);
                    e.printStackTrace();
                    listener.Fail();
                }
            }
        }).start();
    }
}
