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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Recycler extends AppCompatActivity {

    RecyclerView recycler;
    LinearLayout linearLayout;
    ArrayList<Data> dataList;
    DatabaseHelper databaseHelper;
    Adapter adapter;
    View recyclerView;
    Data data;
    View_Holder holder;
    AlertDialog dialog;
    SharedPreference sharedPreference;

//    Context context=getApplicationContext();

    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        databaseHelper = new DatabaseHelper(this);
        dataList = databaseHelper.listData();
        sharedPreference= new SharedPreference(getApplicationContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(Recycler.this);


        recycler = findViewById(R.id.recyclerview);
        recycler.setHasFixedSize(true);

        adapter = new Adapter(this, dataList);
        Log.e("parthi", "datalist--->" + dataList);
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

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
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
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recycler);
        if (dataList.size() == 0) {
            final Intent intent = new Intent(Recycler.this, Login.class);
            startActivity(intent);
        }

        //call
        adapter.setOnItemClickListener(new Adapter.OnItemClickLister() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClicked(String tel) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "call permission: not granted ");
                    ActivityCompat.requestPermissions(Recycler.this, new String[]{Manifest.permission.CALL_PHONE}, 10);
                }
//                else if()
//                else if(EasyPermissions.hasPermissions((Activity)context, Manifest.permission.CALL_PHONE)){
//                    requestPermissions((Activity)context,new String[]{Manifest.permission.CALL_PHONE},10);
//
//                }
                else if (ActivityCompat.shouldShowRequestPermissionRationale(Recycler.this, Manifest.permission.CALL_PHONE)) {
                    ActivityCompat.requestPermissions(Recycler.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            9);
                } else {
                    String num = "tel:" + tel;
                    final Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(num));
                    startActivity(intent);
                }
            }
        });
        //For SMS
        adapter.setSmsClickListener(new Adapter.OnSmsClickLister() {
            @Override
            public void onSmsClicked(String position) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "Sms Permission: not granted ");
                    ActivityCompat.requestPermissions(Recycler.this, new String[]{Manifest.permission.SEND_SMS}, 10);
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(Recycler.this, Manifest.permission.SEND_SMS)) {
                    new AlertDialog.Builder(Recycler.this)
                            .setMessage("permission required to send messages")
                            .setPositiveButton("OK", null).show();
//                    ActivityCompat.requestPermissions(Recycler.this,
//                            new String[]{Manifest.permission.SEND_SMS},
//                            9);
                } else {
//                    String num = "sms:" + position;
//                    final Intent intent = new Intent(Intent.ACTION_VIEW);
//                    PendingIntent pi=PendingIntent.getActivities(getApplicationContext(),0, new Intent[]{intent},0);
//                    SmsManager sms=SmsManager.getDefault();
//                    sms.sendTextMessage("num", null, "hello javatpoint", pi,null);
//                    LayoutInf
//                    intent.putExtra("sms_body","Priyanka is the best");
//                    intent.setType("vnd.android-dir/mms-sms");
                    OpenDialog(position, 1, getApplicationContext());
//                    intent.setData(Uri.parse(num));
//                    startActivity(intent);
                }
            }
        });
        //For Email
        adapter.onEmailClickListener(new Adapter.OnEmailClickListener() {
            @Override
            public void onEmailClickListener(String postion) {
//                String to = data.getEmail();
                OpenDialog(postion, 2, getApplicationContext());
                Log.e(TAG, "onEmailClickListener: " + postion);

                //AlertDialog.Builder builder1 = new AlertDialog.Builder(Recycler.this);


            }
        });

        //for Wp
        adapter.setOnWpClickListener(new Adapter.OnWpClickListener() {
            @Override
            public void onWpClickListener(String position) {
                OpenDialog(position, 3, getApplicationContext());
            }
        });
    }

    public void OpenDialog(String position, int flag, Context context) {
        // Dialog dialog=new Dialog(position,flag,context);
        // dialog.show(getSupportFragmentManager(),"Dialog");
        TextInputEditText editText, subject;
        TextInputLayout subject_layout;
        Button button;
        String title = "";
        String title2 = "Send";


        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.send_message_dialog, null);
        editText = view.findViewById(R.id.Messageedit);
        subject = view.findViewById(R.id.subjecttext);
        subject_layout = view.findViewById(R.id.emaildialogmessage);
        button = view.findViewById(R.id.dialog_button);
        Log.e(TAG, "onCreateDialog: >>> flag" + flag);

        builder.setView(view);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0){
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    button.setEnabled(false);
                }else {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                button.setEnabled(true);
            }}

            @Override
            public void afterTextChanged(Editable s) {
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
//                button.setEnabled(true);

            }
        };
        editText.addTextChangedListener(textWatcher);


        if (flag == 1) {
            subject_layout.setVisibility(View.GONE);
            title = "Message";
            title2 = "Send via Manager";
        } else if (flag == 2) {
            button.setVisibility(View.GONE);
            title = "Email";
        } else if (flag == 3) {
            subject_layout.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            title = "Whatsapp";
        }
        builder.setTitle(title);


        button.setEnabled(false);
        button.setOnClickListener(v -> {
            String num = "sms:" + position;
            String s = editText.getText().toString();
            final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.putExtra("sms_body", s);
            intent.setType("vnd.android-dir/mms-sms");
//                SmsManager sms=SmsManager.getDefault();
//                sms.sendTextMessage(position, null, s, null,null);
            intent.setData(Uri.parse(num));
            startActivity(intent);
        });

//        builder.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);


        builder.setPositiveButton(title2, (dialog, which) -> {
            Boolean wantToCloseDialog = false;



            String s = editText.getText().toString();
            Log.e(TAG, "onClick: s==>" + s);
            if (s.isEmpty()) {
                Log.e(TAG, "onClick: s==>" + s);
                editText.setError("Empty");
                editText.setFocusable(true);
                ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                 Toast.makeText(context, "Give the input", Toast.LENGTH_LONG).show();

            } else {
                wantToCloseDialog = true;
//                ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

                if (flag == 1) {
                    //sms
                    String num = "sms:" + position;
                    Log.e(TAG, "onClick: sms" + position);
//                    final Intent intent = new Intent(Intent.ACTION_VIEW);
//                PendingIntent pi=PendingIntent.getActivities(getContext(),0, new Intent[]{intent},0);
//                    SmsManager sms = SmsManager.getDefault();
//                    ArrayList<String> string = sms.divideMessage(s);
////                    sms.sendTextMessage(position, null, s, null,null);
//                    sms.sendMultipartTextMessage(position, null, string, null, null);
//                    Toast.makeText(context,"message sent to "+position,Toast.LENGTH_LONG).show();
                    smsBySmsManager(position,s);


                } else if (flag == 2) {
                    //Email
                    String subjectm = subject.getText().toString();
                    String message = editText.getText().toString();
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setType("`message/rfc822`");
                    final PackageManager pm = context.getPackageManager();
                    final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                    ResolveInfo best = null;
                    for (final ResolveInfo info : matches)
                        if (info.activityInfo.packageName.endsWith(".gm") ||
                                info.activityInfo.name.toLowerCase().contains("gmail"))
                            best = info;
                    if (best != null)
                        intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                    intent.putExtra(intent.EXTRA_EMAIL, new String[]{position});
                    intent.putExtra(intent.EXTRA_SUBJECT, subjectm);
                    intent.putExtra(intent.EXTRA_TEXT, message);
                    startActivity(intent);
                } else if (flag == 3) {
                    String text = editText.getText().toString();
//                    String url = "https://api.whatsapp.com/send?phone=+91"+ position;
                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.putExtra(Intent.EXTRA_TEXT,"text");
//                    i.setType("text/plain");
//                    i.setPackage("com.whatsapp");
//                    i.setData(Uri.parse(url));
//                    context.startActivity(i);
                    PackageManager pm = context.getPackageManager();
                    try {
                        String url = "https://api.whatsapp.com/send?phone=+91" + position + "&text=" + URLEncoder.encode(text, "UTF-8");
                        i.setPackage("com.whatsapp");
                        i.setData(Uri.parse(url));
                        if (i.resolveActivity(pm) != null) {
                            startActivity(i);
                        }

                    } catch (Exception e) {
                        Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                                .show();
                    }

                }
            }

            Log.e("parthi", "--->" + wantToCloseDialog);
            if (wantToCloseDialog) {
                dialog.dismiss();
            }

        });


       /* if (dialog != null) {
            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean wantToCloseDialog = false;

                    String s = editText.getText().toString();
                    Log.e(TAG, "onClick: s==>" + s);
                    if (s.isEmpty()) {
                        Log.e(TAG, "onClick: s==>" + s);
                        editText.setError("Empty");
                        editText.setFocusable(true);
                        Toast t = Toast.makeText(context, "Give the input", Toast.LENGTH_LONG);
                        t.show();

                    } else {
                        wantToCloseDialog = true;
                        if (flag == 1) {
                            //sms
                            String num = "sms:" + position;
                            Log.e(TAG, "onClick: sms" + position);
                            final Intent intent = new Intent(Intent.ACTION_VIEW);
//                PendingIntent pi=PendingIntent.getActivities(getContext(),0, new Intent[]{intent},0);
                            SmsManager sms = SmsManager.getDefault();
                            ArrayList<String> string = sms.divideMessage(s);
//                    sms.sendTextMessage(position, null, s, null,null);
                            sms.sendMultipartTextMessage(position, null, string, null, null);

                        } else if (flag == 2) {
                            //Email
                            String subjectm = subject.getText().toString();
                            String message = editText.getText().toString();
                            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                            intent.setType("`message/rfc822`");
                            final PackageManager pm = context.getPackageManager();
                            final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                            ResolveInfo best = null;
                            for (final ResolveInfo info : matches)
                                if (info.activityInfo.packageName.endsWith(".gm") ||
                                        info.activityInfo.name.toLowerCase().contains("gmail"))
                                    best = info;
                            if (best != null)
                                intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                            intent.putExtra(intent.EXTRA_EMAIL, new String[]{position});
                            intent.putExtra(intent.EXTRA_SUBJECT, subjectm);
                            intent.putExtra(intent.EXTRA_TEXT, message);
                            startActivity(intent);
                        } else if (flag == 3) {
                            String text = editText.getText().toString();
//                    String url = "https://api.whatsapp.com/send?phone=+91"+ position;
                            Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.putExtra(Intent.EXTRA_TEXT,"text");
//                    i.setType("text/plain");
//                    i.setPackage("com.whatsapp");
//                    i.setData(Uri.parse(url));
//                    context.startActivity(i);
                            PackageManager pm = context.getPackageManager();
                            try {
                                String url = "https://api.whatsapp.com/send?phone=+91" + position + "&text=" + URLEncoder.encode(text, "UTF-8");
                                i.setPackage("com.whatsapp");
                                i.setData(Uri.parse(url));
                                if (i.resolveActivity(pm) != null) {
                                    startActivity(i);
                                }

                            } catch (Exception e) {
                                Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                                        .show();
                            }

                        }
                    }

                    Log.e("parthi", "--->" + wantToCloseDialog);
                    if (wantToCloseDialog) {
                        dialog.dismiss();
                    }
                }
            });
        }*/


        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);


    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permissions.length > 0) {

            for (int i = 0; i < permissions.length; i++) {

                Log.e("parthi", "permissions===>" + permissions[i]);
                Log.e("parthi", "grantResults===>" + grantResults[i]);
                switch (permissions[i]) {

                    case Manifest.permission.SEND_SMS:
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                            showSmsOptionDialog(phoneNo);
                        } else if (ActivityCompat.shouldShowRequestPermissionRationale(Recycler.this, Manifest.permission.SEND_SMS)) {
//                            new AlertDialog.Builder(Recycler.this)
//                                    .setMessage("permission required to send messages")
//                                    .setPositiveButton("OK", null).show();
                        } else {

                            toRedirectToPermissionPage();
                        }
                        break;


                    case Manifest.permission.CALL_PHONE:
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                            doCall(phoneNo);
                        } else if (ActivityCompat.shouldShowRequestPermissionRationale(Recycler.this, Manifest.permission.CALL_PHONE)) {
//                            new AlertDialog.Builder(Recycler.this)
//                                    .setMessage("permission required to call")
//                                    .setPositiveButton("OK", null).show();
                        } else {

                            toRedirectToPermissionPage();
                        }
                        break;
                    default:
                        break;

                }
            }

        }
//        if (permissions.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED) && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//
//            new AlertDialog.Builder(Recycler.this)
//                    .setMessage("permission required to send messages")
//                    .setPositiveButton("OK", null).show();
//            Log.e(TAG, "onRequestPermissionsResult: permission granted");
//        }
//        else {
//
//            Log.e(TAG, "onRequestPermissionsResult: in else block");
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(Recycler.this,Manifest.permission.CALL_PHONE)){
//                new AlertDialog.Builder(Recycler.this)
//                        .setMessage("permission required to send messages")
//                        .setPositiveButton("OK", null).show();
//            }else if (ActivityCompat.shouldShowRequestPermissionRationale(Recycler.this,Manifest.permission.SEND_SMS)){
//                new AlertDialog.Builder(Recycler.this).setMessage("permission require to send messages").setPositiveButton("OK",null).show();
//            }
//            else{
//
//                Log.e(TAG, "onRequestPermissionsResult: if denied is checked");
//
//// ActivityCompat.requestPermissions(UserList.this,new String[]{Manifest.permission.SEND_SMS},10);
//
//// to redirect to permission page
///* Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", this.getPackageName(), null));
////intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//startActivity(intent);*/
//
//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////                Intent intent=new Intent(Settings.Per);
//                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                Log.e(TAG, "onRequestPermissionsResult: "+ getPackageName());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setData(uri);
//                startActivity(intent);
//            }
//        }

    }

    private void toRedirectToPermissionPage() {

        new AlertDialog.Builder(Recycler.this)
                .setMessage("permission required to send messages")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Intent intent=new Intent(Settings.Per);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        Log.e(TAG, "onRequestPermissionsResult: " + getPackageName());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }).show();
//        alertDialog.setOnCancelListener();

    }

    private void smsBySmsManager(String phone,String message){


// --sends an SMS message to another device---
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

//---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

//---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phone, null, message, sentPI, deliveredPI);

/***************************************/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent i = new Intent(this, Login.class);
                sharedPreference.setEmail("");
                sharedPreference.setPassword("");
                startActivity(i);
                //add the function to perform here
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Recycler.super.onBackPressed();
                    }
                }).create().show();
    }
}