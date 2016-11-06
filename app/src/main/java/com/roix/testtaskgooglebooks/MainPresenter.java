package com.roix.testtaskgooglebooks;

import android.util.Log;
import android.widget.Toast;

import com.roix.testtaskgooglebooks.pojo.Book;
import com.roix.testtaskgooglebooks.pojo.SearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by u5 on 11/6/16.
 */
public class MainPresenter {
    private MainView view;
    private GoogleApiClient client;

    public void init(MainView view){
        this.view=view;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        client= retrofit.create(GoogleApiClient.class);

        view.showFavorites(CacheManager.getFavoredItems());

    }

    public void search(final String text){
        client.search(text,Constants.ApiKey).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    List<Book> books = response.body().getItems();
                    view.showSearchResult(CacheManager.prepareSearchList(books), text);
                } else Log.i("@@@", "on faled");
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }

    public void favoritesButtonClicked(){
        view.showFavorites(CacheManager.getFavoredItems());
    }

    public void addToFavoritesButtonClicked(Book book){
        Log.i("@@@", "" + book.getVolumeInfo().getTitle());
        if(!book.isFavored()) CacheManager.removeItemInFavored(book);

        else CacheManager.saveItemInFavored(book);

    }
}
