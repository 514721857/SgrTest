package com.sgr.dyg.test.http;



import com.sgr.dyg.test.bean.EntityResult;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {


    @GET("entity/search")
    Observable<EntityResult> getOnLineNum(@Query("ak") String ak, @Query("service_id") String service_id, @Query("query") String query,
                                          @Query("page_size") int size);
}
