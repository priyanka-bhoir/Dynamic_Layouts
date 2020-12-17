package com.priyanka.dynamiclayouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        ConstraintLayout constraintLayout=findViewById(R.layout.activity_main2);

        // Set a key to a string.
        FirebaseCrashlytics.getInstance().setCustomKey("str_key", "hello");

// Set a key to a boolean.
        FirebaseCrashlytics.getInstance().setCustomKey("bool_key", true);

// Set a key to an int.
        FirebaseCrashlytics.getInstance().setCustomKey("int_key", 1);

// Set a key to a long.
        FirebaseCrashlytics.getInstance().setCustomKey("int_key", 1L);

// Set a key to a float.
        FirebaseCrashlytics.getInstance().setCustomKey("float_key", 1.0f);

// Set a key to a double.
        FirebaseCrashlytics.getInstance().setCustomKey("double_key", 1.0);

        FirebaseCrashlytics.getInstance().setCustomKey("int_key", 50);
        FirebaseCrashlytics.getInstance().setUserId("12345");
        Button b= findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new RuntimeException("abc");
            }
        });
    }
}