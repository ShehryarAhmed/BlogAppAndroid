package com.example.android.simpleblogapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.storage.StorageVolume;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.example.android.simpleblogapp.databinding.binding_post;

public class PostActivity extends AppCompatActivity {

    binding_post bind_post;

    private static final int GALLERY_REQUEST = 1;

    private Uri imageUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind_post = DataBindingUtil.setContentView(this,R.layout.activity_post);
        bind_post.postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/jpeg");
                galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(galleryIntent,"Complete Action Using"),GALLERY_REQUEST);
            }
        });

        bind_post.postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost();


            }
        });
    }

    private void sendPost() {
        String title_val = bind_post.postTitle.getText().toString().trim();
        String desc_val = bind_post.postDesc.getText().toString().trim();
        if (!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && imageUri != null){
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            bind_post.postImage.setImageURI(imageUri);
        }
    }
}
