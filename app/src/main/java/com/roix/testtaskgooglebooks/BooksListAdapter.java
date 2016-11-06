package com.roix.testtaskgooglebooks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.roix.testtaskgooglebooks.pojo.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by u5 on 11/6/16.
 */
public class BooksListAdapter extends RecyclerView.Adapter<BooksListAdapter.BooksListViewHolder>  {
    private List<Book> books;
    private MainActivity activity;
    private OnButtonClickListener listener;

    public BooksListAdapter(final MainActivity activity, final List<Book> books){
        this.books=books;
        this.activity=activity;
        listener=new OnButtonClickListener() {
            @Override
            public void onClick(BooksListAdapter.BooksListViewHolder holder) {

                int pos=holder.getAdapterPosition();
                Book book=books.get(pos);
                if(!book.isFavored()){
                    holder.favorites.setImageResource(R.drawable.ic_favorite_black_24dp);
                    book.setIsFavored(true);
                }
                else {
                    book.setIsFavored(false);
                    holder.favorites.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }

                activity.presenter.addToFavoritesButtonClicked(book);
            }
        };
    }

    @Override
    public BooksListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.book_list_item, parent, false);
        //view.setOnClickListener(activity);
        BooksListViewHolder holder=new BooksListViewHolder(view,listener);
        //holder.favorites.setOnClickListener(activity);
        return holder;
    }

    @Override
    public void onBindViewHolder(BooksListViewHolder holder, int pos) {
        Book book=books.get(pos);


        holder.author.setText(book.getVolumeInfo().getAuthors().toString());
        holder.title.setText(book.getVolumeInfo().getTitle());
        holder.link.setText(book.getVolumeInfo().getInfoLink());
        if(book.isFavored())holder.favorites.setImageResource(R.drawable.ic_favorite_black_24dp);
        else holder.favorites.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        String imageUrl="";
        try {
            imageUrl=book.getVolumeInfo().getImageLinks().getThumbnail();
            Picasso.with(activity).load(imageUrl).into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(books!=null)
            return books.size();
        return 0;
    }




    public interface OnButtonClickListener{
        void onClick(BooksListAdapter.BooksListViewHolder holder);
    }

    public static class BooksListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView author;
        public TextView title;
        public TextView link;
        public ImageView image;
        public ImageButton favorites;
        private OnButtonClickListener listener;

        public BooksListViewHolder(View itemView,OnButtonClickListener listener) {
            super(itemView);
            this.listener=listener;
            author=(TextView)itemView.findViewById(R.id.author);
            title=(TextView)itemView.findViewById(R.id.title);
            link=(TextView)itemView.findViewById(R.id.link);
            image=(ImageView)itemView.findViewById(R.id.imageView);
            favorites=(ImageButton)itemView.findViewById(R.id.favoritesButton);
            favorites.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(this);
        }
    }
}
