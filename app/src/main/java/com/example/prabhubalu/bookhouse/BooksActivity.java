package com.example.prabhubalu.bookhouse;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static java.security.AccessController.getContext;

public class BooksActivity extends AppCompatActivity {

    private List<Book> bookList = new ArrayList<>();

    LinearLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;
    Uri imuri;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    EditText bookTitle, bookAuthor, bookDescription, bookCost;

    String uid, myphone, myemail;



    private Button addBookButton;

    private DatabaseReference rootDbRef, BooksDatabase;
    ImageView book_image;
   public  TextView selecttext;

    public static final int PICK_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        book_image = findViewById(R.id.et_book_image);

        rootDbRef = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid()).child("books");

        uid = FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance().getReference("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myemail = dataSnapshot.child("emailAddress").getValue().toString();
                myphone = dataSnapshot.child("phoneNumber").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        selecttext = findViewById(R.id.selectimage);


        BooksDatabase = FirebaseDatabase.getInstance().getReference().child("gbooks");


        layoutBottomSheet = findViewById(R.id.bottom_sheet);
        mRecyclerView = findViewById(R.id.recyclerView);
        addBookButton = findViewById(R.id.addBookButton);

        book_image = findViewById(R.id.et_book_image);
        bookTitle = findViewById(R.id.et_book_title);
        bookAuthor = findViewById(R.id.et_book_author);
        bookDescription = findViewById(R.id.et_book_description);
        bookCost = findViewById(R.id.et_book_cost);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new BooksAdapter(bookList, BooksActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("BookActivity").withEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail())//.withIcon(getResources().getDrawable(R.drawable.profile))
                )
                .build();
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)

                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(1).withName("My Books"),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withIdentifier(2).withName("Logout")
                )

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        long drawe = drawerItem.getIdentifier();

                        if (drawe == 1) {
                            Toast.makeText(BooksActivity.this, "MyBooks", Toast.LENGTH_SHORT).show();
                        }

                        if (drawe == 2) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(BooksActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    }
                })
                .build();


        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        fab.show();
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        fab.hide();
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        fab.show();
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        fab.hide();
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });

        BooksDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //   Log.e("TAG",dataSnapshot.toString());
                try {

                    Book book = dataSnapshot.getValue(Book.class);
                    bookList.add(book);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.e("TAG", e.toString());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    Map bookMap;
    public void addBook() {
        String strBookTitle, strBookAuthor, strBookDescription, strBookCost;

         bookMap = new HashMap();
        bookMap.put("title", bookTitle.getText().toString());
        bookMap.put("author", bookAuthor.getText().toString());
        bookMap.put("description", bookDescription.getText().toString());
        bookMap.put("price", bookCost.getText().toString());


        final String imageref = UUID.randomUUID().toString() + ".jpg";
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+imageref);

        storageReference.putFile(imuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                String url=task.getResult().getDownloadUrl().toString();
                bookMap.put("image",url);

                rootDbRef.child(bookTitle.getText().toString()).setValue(bookMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            Toast.makeText(getApplicationContext(), "Book Added to Firebase", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Error pushing content to db", Toast.LENGTH_SHORT).show();
                    }
                });
                String push_id = FirebaseDatabase.getInstance().getReference().child("gbooks").push().getKey();

                bookMap.put("pushid", push_id);


                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("gbooks").child(push_id);


                db.setValue(bookMap);
                db.child("uid").setValue(uid);
                db.child("emailAddress").setValue(myemail);
                db.child("phoneNumber").setValue(myphone);


                Toast.makeText(BooksActivity.this, "Add Book", Toast.LENGTH_SHORT).show();

                bookTitle.setText("");
                bookAuthor.setText("");
                bookDescription.setText("");
                bookCost.setText("");
                book_image.setImageResource(R.color.md_white_1000);
                selecttext.setVisibility(View.VISIBLE);

            }
        });
      }

    public void chooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        selecttext.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            Log.e("TAG", data.getData().getPath().toString());
            imuri = data.getData();
            Picasso.with(BooksActivity.this).load(data.getData()).into(book_image);
        }
    }



}


