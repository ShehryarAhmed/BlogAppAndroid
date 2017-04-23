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

import com.example.android.simpleblogapp.MainActivity;
import com.example.android.simpleblogapp.R;
import com.example.android.simpleblogapp.databinding.DataBinding_activity_login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by android on 2/28/2017.
 */

public class LoginActivity extends AppCompatActivity {
    DataBinding_activity_login mDataBinding_activity_login;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding_activity_login = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        if(mFirebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
        mDataBinding_activity_login.loginLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mDataBinding_activity_login.loginEmail.getText().toString();
                final String password = mDataBinding_activity_login.loginPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mDataBinding_activity_login.loginProgressbar.setVisibility(View.VISIBLE);
                mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mDataBinding_activity_login.loginProgressbar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                mDataBinding_activity_login.loginPassword.setError(getString(R.string.minimum_password));
                            } else {

                                Toast.makeText(LoginActivity.this, "" + getString(R.string.authentication_failed), Toast.LENGTH_SHORT).show();
                                mDataBinding_activity_login.loginForgetPassword.setVisibility(View.VISIBLE);
                                mDataBinding_activity_login.loginForgetPassword.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }
        );


        mDataBinding_activity_login.loginSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
