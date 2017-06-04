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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.simpleblogapp.accountSetup.LoginActivity;
import com.example.android.simpleblogapp.model.BlogPost;
import com.example.android.simpleblogapp.accountSetup.ProfileSetting;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    private RecyclerView mRecyclerView;

    private DatabaseReference mDBReference;
    private DatabaseReference mUserDB;
    private DatabaseReference mDBLikes;

    private boolean mProcesslike = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mDBReference = FirebaseDatabase.getInstance().getReference().child("BLogs");
        mUserDB = FirebaseDatabase.getInstance().getReference().child("Users");
        mDBLikes = FirebaseDatabase.getInstance().getReference().child("Likes");

        mDBReference.keepSynced(true);
        mUserDB.keepSynced(true);
        mDBLikes.keepSynced(true);

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
            protected void populateViewHolder(final BlogsViewHolder blogsViewHolder, BlogPost blogPost, int i) {

                final String post_id = getRef(i).getKey().toString();
                blogsViewHolder.setLikeBtn(post_id);
                blogsViewHolder.getPost(getApplicationContext(),

                        blogPost.getTitle(),
                        blogPost.getDescription(),
                        blogPost.getThumnail(),
                        blogPost.getUserName(),
                        blogPost.getProfile_img());

                        blogsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent singleBlog = new Intent(MainActivity.this,SinglePostActivity.class);
                                singleBlog.putExtra("blog_id",post_id);
                                startActivity(singleBlog);
                            }
                        });

                blogsViewHolder.thumb_up.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {
                    mProcesslike = true;

                            mDBLikes.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (mProcesslike) {

                                        if (dataSnapshot.child(post_id).hasChild(mFirebaseAuth.getCurrentUser().getUid())) {

                                        mDBLikes.child(post_id).child(mFirebaseAuth.getCurrentUser().getUid()).removeValue();

                                            mProcesslike=false;

                                        }

                                        else {

                                            mDBLikes.child(post_id).child(mFirebaseAuth.getCurrentUser().getUid()).setValue("something");
                                            mProcesslike = false;
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                         }

                });
                }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private static class BlogsViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageButton thumb_up;

        DatabaseReference DBref;
        FirebaseAuth mAuth;


        public BlogsViewHolder(View itemView) {

        super(itemView);
        mView = itemView;
            thumb_up = (ImageButton) mView.findViewById(R.id.thumb_up);
            DBref = FirebaseDatabase.getInstance().getReference().child("Likes");
            mAuth = FirebaseAuth.getInstance();

        }

        public void setLikeBtn(final String post_key){
            DBref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())) {
                    thumb_up.setImageResource(R.drawable.ic_thumb_down_black_24dp);
                    }
                    else {
                        thumb_up.setImageResource(R.drawable.ic_thumb_up_black_24dp);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
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
