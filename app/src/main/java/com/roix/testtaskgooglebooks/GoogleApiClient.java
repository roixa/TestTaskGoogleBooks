package com.roix.testtaskgooglebooks;

import com.roix.testtaskgooglebooks.pojo.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by u5 on 11/6/16.
 */
public interface GoogleApiClient {

    @GET("books/v1/volumes")
    Call<SearchResponse> search( @Query("q") String body, @Query("key") String apiKey);

}
