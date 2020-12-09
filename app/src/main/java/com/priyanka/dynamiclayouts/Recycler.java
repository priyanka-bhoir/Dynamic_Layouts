package com.priyanka.dynamiclayouts;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.List;

public class Recycler extends AppCompatActivity {

    RecyclerView recycler;
    LinearLayout linearLayout;
    List<Data> dataList;
    DatabaseHelper databaseHelper;
    Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
//        linearLayout=findViewById(R.id.relinear);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        recycler=findViewById(R.id.recyclerview);
        databaseHelper=new DatabaseHelper(this);
        adapter=new Adapter();

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);


//        linearLayout.addView(recyclerView);
//        recyclerView.setHasTransientState();
//        recyclerView=findViewById(R.id.recyclerview);

    }
}