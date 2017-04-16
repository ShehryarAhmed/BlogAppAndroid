package com.example.android.simpleblogapp.userDetail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.simpleblogapp.MainActivity;
import com.example.android.simpleblogapp.R;
import com.example.android.simpleblogapp.databinding.bindprofsetting;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ProfileSetting extends AppCompatActivity {

    bindprofsetting bind;

    private static final int GALLERY_REQUEST = 0;

    Uri imageaUri = null;
    private DatabaseReference mDatabase;

    private StorageReference mStroaoge;

    private ProgressDialog mProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this,R.layout.activity_profile_setting);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mStroaoge = FirebaseStorage.getInstance().getReference();

        mProgress = new ProgressDialog(this);

        bind.displayImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplication(), "isdadafifif", Toast.LENGTH_SHORT).show();

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/jpeg");
 //               galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(getApplication(), "superup", Toast.LENGTH_SHORT).show();

        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(getApplication(), "superdown", Toast.LENGTH_SHORT).show();

        if (resultCode == GALLERY_REQUEST && requestCode == RESULT_OK){
            Toast.makeText(getApplication(), "superin", Toast.LENGTH_SHORT).show();
            imageaUri = data.getData();
            bind.displayImg.setImageURI(imageaUri);

        }
    }
}

