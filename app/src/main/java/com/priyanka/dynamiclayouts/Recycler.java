package com.priyanka.dynamiclayouts;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Recycler extends AppCompatActivity {

    RecyclerView recycler;
    LinearLayout linearLayout;
    ArrayList<Data> dataList;
    DatabaseHelper databaseHelper;
    Adapter adapter;
    View recyclerView;
    Data data;
    View_Holder holder;

//    Context context=getApplicationContext();

    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        databaseHelper=new DatabaseHelper(this);
        dataList=databaseHelper.listData();
        AlertDialog.Builder builder= new AlertDialog.Builder(Recycler.this);


        recycler=findViewById(R.id.recyclerview);
        recycler.setHasFixedSize(true);

        adapter=new Adapter(this,dataList);
        Log.e("parthi","datalist--->"+dataList);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);

//        holder.Name=new TextView(this);
//        holder.dropdown=new TextView(this);
//        holder.radio=new TextView(this);
//        holder.checkbox=new TextView(this);
//        holder.Call=new ImageView(this);
//        holder.Sms=new ImageView(this);
//        holder.Wp=new ImageView(this);
//        holder.Webic=new ImageView(this);
//        holder.Edit=new ImageView(this);
//        holder.Emailic=new ImageView(this);

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
        if (dataList.size()==0){
            final Intent intent=new Intent(Recycler.this,Login.class);
            startActivity(intent);
        }

        adapter.setOnItemClickListener(new Adapter.OnItemClickLister() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClicked(String tel) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    Log.e(TAG, "call permission: not granted ");
                    ActivityCompat.requestPermissions(Recycler.this,new String[]{Manifest.permission.CALL_PHONE},10);
                }
//                else if()
//                else if(EasyPermissions.hasPermissions((Activity)context, Manifest.permission.CALL_PHONE)){
//                    requestPermissions((Activity)context,new String[]{Manifest.permission.CALL_PHONE},10);
//
//                }
                else if (ActivityCompat.shouldShowRequestPermissionRationale(Recycler.this,Manifest.permission.CALL_PHONE)){
                    ActivityCompat.requestPermissions(Recycler.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            9);
                }
                else {
                    String num = "tel:" + tel;
                    final Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(num));
                    startActivity(intent);
                }
            }
        });
        adapter.setSmsClickListener(new Adapter.OnSmsClickLister() {
            @Override
            public void onSmsClicked(String position) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                    Log.e(TAG, "Sms Permission: not granted ");
                    ActivityCompat.requestPermissions(Recycler.this,new String[]{Manifest.permission.SEND_SMS},10);
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(Recycler.this,Manifest.permission.SEND_SMS)){
                    ActivityCompat.requestPermissions(Recycler.this,
                            new String[]{Manifest.permission.SEND_SMS},
                            9);
                }
                else {
                    String num = "sms:" + position;
                    final Intent intent = new Intent(Intent.ACTION_VIEW);
//                    PendingIntent pi=PendingIntent.getActivities(getApplicationContext(),0, new Intent[]{intent},0);
//                    SmsManager sms=SmsManager.getDefault();
//                    sms.sendTextMessage("num", null, "hello javatpoint", pi,null);
//                    LayoutInf
                    intent.putExtra("sms_body","Priyanka is the best");
                    intent.setType("vnd.android-dir/mms-sms");
                    OpenDialog(position,0,getApplicationContext());
                    intent.setData(Uri.parse(num));
//                    startActivity(intent);
            }
            }
        });
    }

    public void OpenDialog(String position, int flag, Context context) {
        Dialog dialog=new Dialog(position,flag,context);
        dialog.show(getSupportFragmentManager(),"Dialog");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permissions.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED) && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            new AlertDialog.Builder(Recycler.this)
                    .setMessage("permission required to send messages")
                    .setPositiveButton("OK", null).show();
            Log.e(TAG, "onRequestPermissionsResult: permission granted");
        }
        else {

            Log.e(TAG, "onRequestPermissionsResult: in else block");

            if (ActivityCompat.shouldShowRequestPermissionRationale(Recycler.this,Manifest.permission.CALL_PHONE)){
                new AlertDialog.Builder(Recycler.this)
                        .setMessage("permission required to send messages")
                        .setPositiveButton("OK", null).show();
            }else if (ActivityCompat.shouldShowRequestPermissionRationale(Recycler.this,Manifest.permission.SEND_SMS)){
                new AlertDialog.Builder(Recycler.this).setMessage("permission require to send messages").setPositiveButton("OK",null).show();
            }
            else{

                Log.e(TAG, "onRequestPermissionsResult: if denied is checked");

// ActivityCompat.requestPermissions(UserList.this,new String[]{Manifest.permission.SEND_SMS},10);

// to redirect to permission page
/* Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", this.getPackageName(), null));
//intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
startActivity(intent);*/

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Intent intent=new Intent(Settings.Per);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                Log.e(TAG, "onRequestPermissionsResult: "+ getPackageName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(uri);
                startActivity(intent);
            }
        }

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