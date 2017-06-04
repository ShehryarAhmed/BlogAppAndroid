package com.example.android.simpleblogapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by android on 6/5/2017.
 */

public class SinglePostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepost);
        String post_id = getIntent().getExtras().getString("blog_id");
        Toast.makeText(SinglePostActivity.this, post_id, Toast.LENGTH_SHORT).show();
    }
}
