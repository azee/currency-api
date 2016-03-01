package ru.greatbit.currency.fakeprovider;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by azee on 29.01.16.
 */
public interface OERClient {

    @GET("/api/latest.json")
    public OERData getCurrency(@Query("app_id") String apiKey);
}
