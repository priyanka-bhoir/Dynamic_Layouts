package com.priyanka.dynamiclayouts;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class Recycler extends AppCompatActivity {

    RecyclerView recycler;
    LinearLayout linearLayout;
    ArrayList<Data> dataList;
    DatabaseHelper databaseHelper;
    Adapter adapter;
    View recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        databaseHelper=new DatabaseHelper(this);
        dataList=databaseHelper.listData();

        recycler=findViewById(R.id.recyclerview);
        recycler.setHasFixedSize(true);

        adapter=new Adapter(this,dataList);
        Log.e("parthi","datalist--->"+dataList);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);

    }
}