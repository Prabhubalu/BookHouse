package com.example.prabhubalu.bookhouse;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private List<Book> bookList;

    public BooksAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BooksAdapter.ViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.image.setText(book.getImage());
        holder.description.setText(book.getDescription());
        holder.postedBy.setText(book.getPostedBy());
        holder.price.setText(book.getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, author, image, description, postedBy, price;
        public Button contactSeller;


        public ViewHolder(View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.book_title);
            this.author = itemView.findViewById(R.id.book_author);
            this.image = itemView.findViewById(R.id.book_image);
            this.description = itemView.findViewById(R.id.book_description);
            this.postedBy = itemView.findViewById(R.id.book_postedby);
            this.price = itemView.findViewById(R.id.book_price);

            this.contactSeller = itemView.findViewById(R.id.contact_seller);
        }
    }
}
