package com.example.android.simpleblogapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.simpleblogapp.databinding.Databind_singlepost;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by android on 6/5/2017.
 */

public class SinglePostActivity extends AppCompatActivity {

    Databind_singlepost singlepost;

    private DatabaseReference DBref;
    String mPost_id = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singlepost = DataBindingUtil.setContentView(this, R.layout.activity_singlepost);

        mPost_id = getIntent().getExtras().getString("blog_id");

        DBref = FirebaseDatabase.getInstance().getReference().child("BLogs");

        DBref.child(mPost_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userPic = (String) dataSnapshot.child("profile_img").getValue();
                String userName = (String) dataSnapshot.child("userName").getValue();
                String blogImg = (String) dataSnapshot.child("thumnail").getValue();
                String title = (String) dataSnapshot.child("title").getValue();
                String desc = (String) dataSnapshot.child("description").getValue();

                setBlogValue(getApplicationContext(),userPic,userName,blogImg,title,desc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setBlogValue(Context ctx,String userprofpic,
                              String username,
                              String userblogImg,
                              String usertitle,
                              String userdesc){

        Picasso.with(ctx).load(userprofpic).into(singlepost.profileImage);
        singlepost.username.setText(""+username);
        Picasso.with(ctx).load(userblogImg).into(singlepost.blogImage);
        singlepost.blogTitle.setText(""+usertitle);
        singlepost.blogDesc.setText(""+userdesc);

    }
}
