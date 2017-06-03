package com.example.android.simpleblogapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.simpleblogapp.accountSetup.LoginActivity;
import com.example.android.simpleblogapp.model.BlogPost;
import com.example.android.simpleblogapp.accountSetup.ProfileSetting;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    private RecyclerView mRecyclerView;

    private DatabaseReference mDBReference;

    private StorageReference mstorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();

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

                blogsViewHolder.getPost(getApplicationContext(),blogPost.getTitle(),blogPost.getDescription(),blogPost.getThumnail(),blogPost.getUserName(),blogPost.getProfile_img());
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

        public void getPost(Context ctx, String title, String desc, String image,String uname,String prof_img){

            TextView username = (TextView) itemView.findViewById(R.id.username);
            username.setText(""+uname);
            ImageView prof_image = (ImageView) itemView.findViewById(R.id.profile_image);
            Picasso.with(ctx).load(prof_img).into(prof_image);
            TextView titleView = (TextView) itemView.findViewById(R.id.blog_title);
            titleView.setText(""+title);
            TextView descView = (TextView) itemView.findViewById(R.id.blog_desc);
            descView.setText(""+desc);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.blog_image);
            Picasso.with(ctx).load(image).into(imageView);

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
        int id = item.getItemId();
        switch (id){
            case R.id.action_add_post:
                startActivity(new Intent(MainActivity.this,PostActivity.class));
                break;
            case R.id.profile_setting:
                startActivity(new Intent(MainActivity.this,ProfileSetting.class));
                break;
            case R.id.log_out:
                mFirebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                break;

        }


        return super.onOptionsItemSelected(item);
    }
}
