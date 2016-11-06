package com.roix.testtaskgooglebooks;

import com.roix.testtaskgooglebooks.pojo.Book;

import java.util.List;

/**
 * Created by u5 on 11/6/16.
 */
public interface MainView {
    void showSearchResult(List<Book> list,String title);
    void showFavorites(List<Book> list);
    void showBookDetails(Book book);
}
