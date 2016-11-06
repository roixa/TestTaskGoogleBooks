package com.roix.testtaskgooglebooks;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.ProgressBar;

import com.roix.testtaskgooglebooks.pojo.Book;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, SearchView.OnQueryTextListener{
    public MainPresenter presenter;
    private List<Book> books;
    private RecyclerView recyclerView;
    private MenuItem searchViewMenuItem;
    private MenuItem favoritesMenuItem;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar=(ProgressBar)findViewById(R.id.toolbar_progress_bar);
        progressBar.setVisibility(View.GONE);

        CacheManager.init(this);
        presenter=new MainPresenter();
        presenter.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        favoritesMenuItem=menu.findItem(R.id.favorites);
        searchViewMenuItem =menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(searchViewMenuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.favorites)
            presenter.favoritesButtonClicked();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSearchResult(List<Book> list,String title) {
        books=list;
        getSupportActionBar().setTitle(title);
        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(new BooksListAdapter(this,list));
    }

    @Override
    public void showFavorites(List<Book> list) {
        books=list;
        getSupportActionBar().setTitle(R.string.app_name);
        recyclerView.setAdapter(new BooksListAdapter(this,list));
    }

    @Override
    public void showBookDetails(Book book) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        presenter.search(query);
        searchViewMenuItem.collapseActionView();
        progressBar.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


}
