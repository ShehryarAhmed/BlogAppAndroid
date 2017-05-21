package com.example.android.simpleblogapp.accountSetup;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.simpleblogapp.MainActivity;
import com.example.android.simpleblogapp.R;
import com.example.android.simpleblogapp.databinding.DataBinding_activity_signup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

/**
 * Created by android on 2/28/2017.
 */

public class SignUpActivity extends AppCompatActivity {
    UserDetail mUserDetail;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDBReference;

    private int GALLERY_REQUEST = 1;

    private Uri imageUri = null;
    DataBinding_activity_signup mDataBinding_activity_signup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();

        mDBReference = FirebaseDatabase.getInstance().getReference().child("Users");


        mDataBinding_activity_signup = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        mDataBinding_activity_signup.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/jpeg");
                galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

                startActivityForResult(Intent.createChooser(galleryIntent, "Complet Action "), GALLERY_REQUEST);
            }
        });
        mDataBinding_activity_signup.signupAlreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mDataBinding_activity_signup.signupCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.enter_first_name), Toast.LENGTH_SHORT).show();
                    //fname
                    String fname = mDataBinding_activity_signup.signupFirstName.getText().toString().trim();
                    if (TextUtils.isEmpty(fname)) {
                    return;
                }

                String lname = mDataBinding_activity_signup.signupLastName.getText().toString().trim();
                if (TextUtils.isEmpty(lname)) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.enter_last_name), Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = mDataBinding_activity_signup.signupEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                    return;
                }

                String password = mDataBinding_activity_signup.signupPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.minimum_password), Toast.LENGTH_SHORT).show();
                }

                mUserDetail = new UserDetail(fname, lname, email, password);
                mDataBinding_activity_signup.signupProgressbar.setVisibility(View.VISIBLE);
                mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final DatabaseReference currentUserDb = mDBReference.child(mFirebaseAuth.getCurrentUser().getUid().toString());

                        mDataBinding_activity_signup.signupProgressbar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, getString(R.string.authentication_failed), Toast.LENGTH_SHORT).show();
                        } else {

                            currentUserDb.setValue(mUserDetail);

                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
        mDataBinding_activity_signup.signupAlreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(3, 3)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                mDataBinding_activity_signup.profileImage.setImageURI(resultUri);

            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
