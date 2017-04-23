package com.example.android.simpleblogapp.accountSetup;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.android.simpleblogapp.R;
import com.example.android.simpleblogapp.databinding.DataBinding_activity_foregotpsw;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by android on 2/28/2017.
 */

public class ForgotActivity extends AppCompatActivity {
    DataBinding_activity_foregotpsw dataBinding_foregetpsw;

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding_foregetpsw = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        mFirebaseAuth = FirebaseAuth.getInstance();

        dataBinding_foregetpsw.forgotPasswordEmailSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBinding_foregetpsw.forgotProgressbar.setVisibility(View.VISIBLE);
                String email = dataBinding_foregetpsw.forgotEmailEdit.getText().toString();
               if(TextUtils.isEmpty(email)){
                   Toast.makeText(ForgotActivity.this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                   return;
               }

                mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(ForgotActivity.this, getString(R.string.check_email), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgotActivity.this,LoginActivity.class));
                        }
                        else
                        {
                            dataBinding_foregetpsw.forgotEmailEdit.setText("");
                            Toast.makeText(ForgotActivity.this, getString(R.string.failed_to_send_eamil), Toast.LENGTH_SHORT).show();
                        }
                        dataBinding_foregetpsw.forgotProgressbar.setVisibility(View.GONE);
                    }
                });



            }
        });

    }
}
