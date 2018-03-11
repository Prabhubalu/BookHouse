package com.example.prabhubalu.bookhouse;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private List<Book> bookList;
    FirebaseAuth firebaseAuth;
    public String myuid;
    private Context context;
    Book book;

    public BooksAdapter(List<Book> bookList,Context context) {
        this.bookList = bookList;
        this.context=context;
    }


    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list_row, parent, false);

        firebaseAuth = FirebaseAuth.getInstance();
        myuid=firebaseAuth.getUid();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BooksAdapter.ViewHolder holder, final int position) {
         book = bookList.get(position);

        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        Picasso.with(holder.itemView.getContext()).load(book.getImage()).into(holder.image);
        holder.description.setText(book.getDescription());
        holder.postedBy.setText(book.getPostedBy());
        holder.price.setText(book.getPrice().toString());

        holder.contactSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+bookList.get(position).getPhoneNumber().toString()));
                context.startActivity(intent);
            }
        });

        holder.messageseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("address",bookList.get(position).getPhoneNumber().toString());
                sendIntent.putExtra("sms_body","Type your message here");
                sendIntent.setData(Uri.parse("sms:"));
                context.startActivity(sendIntent);

            }
        });

        if (myuid.equals(book.getUid())) {
            holder.deletebook.setVisibility(View.VISIBLE);
            holder.deletebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, book.getUid(), Toast.LENGTH_SHORT).show();

                  FirebaseDatabase.getInstance().getReference().child("gbooks").child(bookList.get(position).getPushid()).removeValue();

                    bookList.remove(position);
                    notifyItemRemoved(position);


                }
            });
       }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, author, description, postedBy, price;
        public ImageView image;
        public Button contactSeller,messageseller, deletebook;


        public ViewHolder(View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.book_title);
            this.author = itemView.findViewById(R.id.book_author);
            this.image = itemView.findViewById(R.id.book_image);
            this.description = itemView.findViewById(R.id.book_description);
            this.postedBy = itemView.findViewById(R.id.book_postedby);
            this.price = itemView.findViewById(R.id.book_price);

            this.contactSeller = itemView.findViewById(R.id.contact_seller);
            this.messageseller = itemView.findViewById(R.id.message_seller);
            this.deletebook =  itemView.findViewById(R.id.add_delete);
        }
    }

}
