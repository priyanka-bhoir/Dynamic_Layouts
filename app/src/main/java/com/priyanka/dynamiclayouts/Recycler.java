package com.priyanka.dynamiclayouts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Recycler extends AppCompatActivity {

    RecyclerView recycler;
    LinearLayout linearLayout;
    ArrayList<Data> dataList;
    DatabaseHelper databaseHelper;
    Adapter adapter;
    View recyclerView;
    Data data;

    private static final String TAG = "Main";

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

        ItemTouchHelper.SimpleCallback simpleCallback= new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Toast.makeText(Recycler.this, "Swiped on " + position, Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
//                Log.e("pos", "onSwiped:------ " + position + "||" + "||" + viewHolder+"||"+d.Email);
                String id = adapter.userAt(position);

                Log.e(TAG, "onSwiped: id " + id);

                databaseHelper.Delete(id);
                //madapter.notifyItemRemoved(position);
                //madapter.notifyItemRangeChanged(position, data.size());

                dataList.remove(position);
                adapter = new Adapter(Recycler.this, dataList);
                Log.e("pos", "onSwiped:1------ " + dataList.size());
                recycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
//                position=0;
            }
        };
        ItemTouchHelper itemTouchHelper= new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recycler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
            //add the function to perform here
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

}