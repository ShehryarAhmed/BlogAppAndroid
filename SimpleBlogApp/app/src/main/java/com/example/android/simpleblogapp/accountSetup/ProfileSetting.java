package com.example.android.simpleblogapp.accountSetup;

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

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/jpeg");
                galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(galleryIntent,"Complet Action "),GALLERY_REQUEST);
            }
        }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == GALLERY_REQUEST && resultCode== RESULT_OK) {

          imageaUri = data.getData();
          CropImage.activity(imageaUri)
                  .setGuidelines(CropImageView.Guidelines.ON)
                  .setAspectRatio(3,3)
                  .start(this);
      }

      if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
          CropImage.ActivityResult result = CropImage.getActivityResult(data);
          if(resultCode == RESULT_OK){
              Uri resultUri = result.getUri();
              bind.displayImg.setImageURI(resultUri);

          }
          else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
              Exception error = result.getError();
          }
      }
    }
}

