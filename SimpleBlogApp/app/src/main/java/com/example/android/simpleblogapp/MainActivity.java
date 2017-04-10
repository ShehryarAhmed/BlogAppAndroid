package com.example.android.simpleblogapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.simpleblogapp.model.BlogPost;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private DatabaseReference mDBReference;

    private StorageReference mstorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBReference = FirebaseDatabase.getInstance().getReference().child("BLogs");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<BlogPost,BlogsViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<BlogPost, BlogsViewHolder>
                (BlogPost.class,R.layout.activity_blogpost,BlogsViewHolder.class,mDBReference) {
            @Override
            protected void populateViewHolder(BlogsViewHolder blogsViewHolder, BlogPost blogPost, int i) {

                blogsViewHolder.setTitleDesc(blogPost.getTitle(),blogPost.getDescription());
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private static class BlogsViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public BlogsViewHolder(View itemView) {
            super(itemView);
        mView = itemView;
        }

        public void setTitleDesc(String title,String desc){
            TextView titleView = (TextView) itemView.findViewById(R.id.blog_title);
            titleView.setText(""+title);
            TextView descView = (TextView) itemView.findViewById(R.id.blog_desc);
            descView.setText(""+desc);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_post:
                startActivity(new Intent(MainActivity.this,PostActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }
}
