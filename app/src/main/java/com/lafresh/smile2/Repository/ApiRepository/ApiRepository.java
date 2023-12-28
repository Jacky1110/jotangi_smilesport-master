package com.lafresh.smile2.Repository.ApiRepository;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.AbsApiCallback;
import com.lafresh.smile2.Repository.ApiService.BaseApiService;
import com.lafresh.smile2.Repository.Model.BaseModel;
import com.lafresh.smile2.Repository.SmileGsonConverterFactory;
import com.lafresh.smile2.SmileApplication;
import com.lafresh.smile2.Utils.ApiUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.lafresh.smile2.SmileApplication.DOMAIN;

public class ApiRepository {
    public static final String TAG = ApiRepository.class.getSimpleName();

    private final String CLIENT_ID = "doubleservice";

    protected Retrofit retrofit;
    private static ApiRepository repository;

    public static ApiRepository getInstance() {
        if (repository == null) {
            repository = new ApiRepository();
        }

        return repository;
    }

    ApiRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(DOMAIN + "/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(SmileGsonConverterFactory.create())
                .client(getClient())
                .build();
    }

    protected void getMemberToken(String action, final BaseContract.ValueCallback<String> tokenCallback, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", CLIENT_ID);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(BaseApiService.class).postGetMemberToken(action, requestBody).enqueue(new Callback<BaseModel<Map<String, String>>>() {
            @Override
            public void onResponse(Call<BaseModel<Map<String, String>>> call, Response<BaseModel<Map<String, String>>> response) {
                if (response.isSuccessful()) {
                    String token = response.body().getItems().get("token");
                    tokenCallback.onValueCallback(0, token);
                } else {
                    callback.onTokenExpired();
                }
            }

            @Override
            public void onFailure(Call<BaseModel<Map<String, String>>> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void getStoreList(int area, String sNum, final AbsApiCallback apiCallback) {
        Map<String, String> map = new HashMap<>();
        map.put("area_id", Integer.toString(area));
        map.put("location_type", sNum);
        map.put("isApp", "true");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(BaseApiService.class).postGetStoreList(requestBody).enqueue(apiCallback);
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(SmileApplication.getInstance())))
                .build();
    }
}
