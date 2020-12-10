package com.priyanka.dynamiclayouts;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Adapter extends RecyclerView.Adapter<View_Holder> {
    Context context;
    ArrayList<Data> arrayList;
    DatabaseHelper db;
    Gson gson=new Gson();


    public Adapter(Context context, ArrayList<Data> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
        Log.e("parthi","arrayList.size--->"+arrayList.size());
        db=new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View list = layoutInflater.inflate(R.layout.recycler_holder, parent, false);
        View_Holder view_holder = new View_Holder(list);
        Log.e(TAG, "onCreateViewHolder: ==> Layout created" );
        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {

        final Data data=arrayList.get(position);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        params.setMargins(30,20,30,20);
       // Log.e(TAG, "onBindViewHolder: "+data.getName());
        //name
        holder.Name=new TextView(context);
        holder.Name.setText(data.getName());
        holder.Name.setLayoutParams(params);
        holder.linearLayout1.addView(holder.Name);

        //FAV CHAR
        holder.dropdown=new TextView(context);
        holder.dropdown.setLayoutParams(params);
        holder.dropdown.setText(data.getDropdown());
        holder.linearLayout1.addView(holder.dropdown);

        //RADIO
        holder.radio=new TextView(context);
        Log.e(TAG, "onBindViewHolder: "+data.getRadio() );
        holder.radio.setText("Fav radio station: "+data.getRadio());
        holder.radio.setLayoutParams(params);
        holder.linearLayout1.addView(holder.radio);

        //fav cars
        holder.checkbox=new TextView(context);
        String output=data.getCheckbox();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        ArrayList<String> finalOutputString = gson.fromJson(output, type);
        holder.checkbox.setLayoutParams(params);
        Log.e(TAG, "onBindViewHolder: finalOutputString==>"+finalOutputString);
        holder.checkbox.setText("Fav cars:"+data.getCheckbox());
        holder.linearLayout1.addView(holder.checkbox);





    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
