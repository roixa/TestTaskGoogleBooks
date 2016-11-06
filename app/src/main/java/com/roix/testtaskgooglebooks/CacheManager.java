package com.roix.testtaskgooglebooks;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.roix.testtaskgooglebooks.pojo.Book;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by u5 on 11/6/16.
 */
public class CacheManager {
    private static Context context;
    private static final String PREFS_NAME="PREFS_NAME";
    private static final String PREFS_FAVE="PREFS_FAVE";
    public static void init(Context c){
        context=c;
    }


    public static void saveItemInFavored(Book item){
        if(context==null)return;
        SharedPreferences preferencesReader = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String listJson=preferencesReader.getString(PREFS_FAVE, "");

        Type listType = new TypeToken<List<Book>>() {}.getType();
        List<Book> books=new Gson().fromJson(listJson, listType);
        if(books==null) books=new ArrayList<>();
        if(!has(books,item))books.add(item);

        String serializedData=new Gson().toJson(books);

        SharedPreferences.Editor editor = preferencesReader.edit();
        editor.putString(PREFS_FAVE, serializedData);
        editor.commit();

    }

    public static void removeItemInFavored(Book item){
        if(context==null)return;
        SharedPreferences preferencesReader = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String listJson=preferencesReader.getString(PREFS_FAVE, "");

        Type listType = new TypeToken<List<Book>>() {}.getType();
        List<Book> books=new Gson().fromJson(listJson, listType);
        if(books==null) return;
        List<Book> newBooks=new ArrayList<>();
        for (Book book:books){
            if(!item.getId().equals(book.getId())) newBooks.add(book);
        }

        String serializedData=new Gson().toJson(newBooks);

        SharedPreferences.Editor editor = preferencesReader.edit();
        editor.putString(PREFS_FAVE, serializedData);
        editor.commit();

    }


    public static List<Book> getFavoredItems(){
        if(context==null)return null;
        if(context==null)return null;
        SharedPreferences preferencesReader = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String listJson=preferencesReader.getString(PREFS_FAVE, "");

        Type listType = new TypeToken<List<Book>>() {}.getType();
        List<Book> books=new Gson().fromJson(listJson, listType);
        for (Book book:books)book.setIsFavored(true);
        return books;

    }

    public static List<Book> prepareSearchList(List<Book> books){
        List<Book> booksFavored=getFavoredItems();
        for(Book book:books){
            if(has(booksFavored,book))book.setIsFavored(true);
        }
        return books;
    }



    public static boolean has(List<Book> list,Book book){
        for(Book item:list){
            if(item.getId().equals(book.getId()))return true;
        }
        return false;
    }

}
