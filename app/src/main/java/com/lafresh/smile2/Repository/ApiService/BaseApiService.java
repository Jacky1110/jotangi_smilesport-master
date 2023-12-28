package com.lafresh.smile2.Repository.ApiService;

import com.lafresh.smile2.Repository.Model.BaseModel;
import com.lafresh.smile2.Repository.Model.Store;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiService {
    @Headers("Authorization: Basic ZG91Ymxlc2VydmljZTp2T2x1TjN4T3Nt")
    @POST("v1/auth/{action}")
    Call<BaseModel<Map<String, String>>> postGetMemberToken(@Path("action") String action, @Body RequestBody body);

    @Headers("Authorization: Basic ZG91Ymxlc2VydmljZTp2T2x1TjN4T3Nt")
    @POST("Location/GetList")
    Call<BaseModel<List<Store>>> postGetStoreList(@Body RequestBody body);
}
