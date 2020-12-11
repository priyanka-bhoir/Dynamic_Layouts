package com.priyanka.dynamiclayouts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Login extends AppCompatActivity {

    LinearLayout linearLayout;
    TextInputLayout email,password;
    MainActivity main;
    TextInputEditText emailtext,passwordtext;
    TextView textView,textView1;
    List<TextInputEditText> listData;
    Button button;
    DatabaseHelper databaseHelper;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linearLayout=findViewById(R.id.linear_login);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        params.setMargins(30,20,30,20);

        listData=new List<TextInputEditText>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(@Nullable Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<TextInputEditText> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] a) {
                return null;
            }

            @Override
            public boolean add(TextInputEditText textInputEditText) {
                return false;
            }

            @Override
            public boolean remove(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends TextInputEditText> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NonNull Collection<? extends TextInputEditText> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public TextInputEditText get(int index) {
                return null;
            }

            @Override
            public TextInputEditText set(int index, TextInputEditText element) {
                return null;
            }

            @Override
            public void add(int index, TextInputEditText element) {

            }

            @Override
            public TextInputEditText remove(int index) {
                return null;
            }

            @Override
            public int indexOf(@Nullable Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(@Nullable Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<TextInputEditText> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<TextInputEditText> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<TextInputEditText> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        databaseHelper=new DatabaseHelper(this);


        textView=new TextView(this);
        textView.setText("Welcome back user....!");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(30);
        linearLayout.addView(textView);

        emailtext=text(linearLayout,email,emailtext,"Enter Your Email",0,0);
        emailtext.setFocusable(true);
        emailtext.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        passwordtext=text(linearLayout,password,passwordtext,"Enter your password",0,9);
        passwordtext.setTransformationMethod(new PasswordTransformationMethod());

        button=new Button(this);
        button.setLayoutParams(params);
        button.setText("Submit");
        button.setBackgroundColor(R.color.purple_700);

        button.setGravity(Gravity.CENTER);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean val;
                Log.e("TAG", "email: "+emailtext.getText().toString() );
                Log.e("TAG", "pass: "+passwordtext.getText().toString());
                val=databaseHelper.search(emailtext.getText().toString(),passwordtext.getText().toString());
                if (val==true){
                    Intent intent=new Intent(Login.this,Recycler.class);
                    startActivity(intent);
                }
                else {
                    Toast t=Toast.makeText(getApplicationContext(),"Inavlid ID or password",Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
        linearLayout.addView(button);

        textView1=new TextView(this);
        textView1.setText("Not an existing user");
        textView1.setTextColor(R.color.purple_200);
        textView1.setGravity(Gravity.CENTER);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        linearLayout.addView(textView1);



    }
    TextInputEditText text(LinearLayout ll,TextInputLayout numbertextLayout, TextInputEditText numbertext, String hint, int flag, int i){

//        String text;
//        i--;
        TextInputLayout.LayoutParams textinputparams = new TextInputLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        textinputparams.setMargins(10,5,10,5);
        numbertextLayout=new TextInputLayout(this);
        numbertextLayout.setLayoutParams(textinputparams);
        numbertext=new TextInputEditText(numbertextLayout.getContext());
        numbertextLayout.setHint(hint);
        numbertextLayout.setPadding(10,0,10,0);
        numbertext.setLayoutParams(textinputparams);
        numbertextLayout.addView(numbertext,0);
        listData.add(numbertext);
        if(flag==1){
            numbertextLayout.setVisibility(View.GONE);
        }
        if (i==9){
            numbertextLayout.setPasswordVisibilityToggleEnabled(true);
        }
        ll.addView(numbertextLayout);
//        text=numbertext.getText().toString();
        return numbertext;
    }
}