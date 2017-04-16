package com.example.android.simpleblogapp.userDetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.android.simpleblogapp.R;

public class ProfileSetting extends AppCompatActivity {

    ImageButton imageButton;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        imageButton = (ImageButton) findViewById(R.id.displayImg);
        editText = (EditText) findViewById(R.id.displayName);
        button = (Button) findViewById(R.id.updatechanges);


    }
}
