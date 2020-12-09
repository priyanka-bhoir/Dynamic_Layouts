package com.priyanka.dynamiclayouts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.view.Gravity.CENTER;

public class MainActivity extends AppCompatActivity {

    ScrollView scrollView;
    LinearLayout linearLayout;
    List<TextInputEditText> listData;
    TextInputLayout nametextInputLayout,numbertextLayout,emaillayout,websiteLayout,passwordLayout,confPassLayout;
    TextInputEditText nametext,numbertext,emailtext,websitetext,passwordtext,confpasstext;
    String name,number,email,website,password,confpass;
    Button submit;
    int i=0;
    String TAG="Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout=findViewById(R.id.linear);
        scrollView =findViewById(R.id.scrollView);
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

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        params.setMargins(30,20,30,20);
//        txt_gender.setLayoutParams(params);

        //Welcome text view
        TextView textView=new TextView(MainActivity.this);
        textView.setText("WELCOME");
        textView.setGravity(CENTER);
        textView.setTextSize((float) 50);
        textView.setPadding(10,0,10,0);
        linearLayout.addView(textView,0);



        //Text inputlayot
        //name
        text(nametextInputLayout,nametext,"Name",0,i);

        //number
        text(numbertextLayout,numbertext,"Number",0,i);
//        linearLayout.addView(numbertextLayout);

        //Email
        text(emaillayout,emailtext,"Email",0,i);
//        linearLayout.addView(emaillayout);

        //website
        text(websiteLayout,websitetext,"Website",0,i);
        //Password

        text(passwordLayout,passwordtext,"Password",1,i);

        text(confPassLayout,confpasstext,"ReEnter password",1,i);

        submit=new Button(this);
        submit.setText("Submit");
        submit.setLayoutParams(params);
        submit.setGravity(CENTER);
        linearLayout.addView(submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                name=nametext.getText().toString();
                //                    linearLayout.getChildAt(1);
                //                    Log.e(TAG, "onClick:==> " +linearLayout.getChildAt(1));
                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (nametext.getText().length()>0)
                        nametext.setError("Invalid");
                    }
                };
                nametext.addTextChangedListener(textWatcher);
//                else if(number.isEmpty() && number.length()<13){
//                    numbertext.setError("Invalid");
//                }
//                else if(email.isEmpty()){
//                    emailtext.setError("Empty");
//                }else if(website.isEmpty()){
//                    websitetext.setError("Empty");
//                }else if(confpass.equals(password)){
//                    passwordtext.setError("Invalid");
//                }
            }
        });


    }

    void text(TextInputLayout numbertextLayout, TextInputEditText numbertext,String hint,int flag,int i){

        String text;
        i--;
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
        linearLayout.addView(numbertextLayout,i);
        text=numbertext.getText().toString();
        return ;
    }
}