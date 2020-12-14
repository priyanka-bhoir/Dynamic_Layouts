package com.priyanka.dynamiclayouts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
    Intent i;
    View_Holder holder;
    private OnItemClickLister mlistener;
    private OnSmsClickLister lister;
    Recycler recycler;

//    holder.Name=new TextView(context);
//    holder.dropdown=new TextView(context);
//    holder.radio=new TextView(context);
//    holder.checkbox=new TextView(context);
//    holder.Call=new ImageView(context);
//    holder.Sms=new ImageView(context);
//    holder.Wp=new ImageView(context);
//    holder.Webic=new ImageView(context);
//    holder.Edit=new ImageView(context);
//    holder.Emailic=new ImageView(context);

    public interface OnItemClickLister{
        void onItemClicked(String position);
    }
    public interface OnSmsClickLister{
        void onSmsClicked(String position);
    }
    public void setOnItemClickListener(OnItemClickLister listener){

        mlistener=listener;
    }
    public void setSmsClickListener(OnSmsClickLister listener){
        lister=listener;
    }


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
        View_Holder view_holder = new View_Holder(list,mlistener);
        Log.e(TAG, "onCreateViewHolder: ==> Layout created" );

        return view_holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {

        Log.e("parthi","position------->"+position );

        final Data data=arrayList.get(position);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        params.setMargins(30,20,30,20);
       // Log.e(TAG, "onBindViewHolder: "+data.getName());

//        holder.linearLayout1=findViewById(R.id.cardlin);
//        linearLayout2=findViewById(R.id.linear_left);
        //name
//        holder.Name=new TextView(context);
        holder.Name.setText("Name: "+data.getName());
        holder.Name.setLayoutParams(params);
        holder.Name.setTextSize(20);
        if (holder.Name.getParent()==null){
        holder.linearLayout1.addView(holder.Name);}

        //FAV CHAR
//        holder.dropdown=new TextView(context);
        holder.dropdown.setLayoutParams(params);
        holder.dropdown.setText("Fav character: "+data.getDropdown());
        holder.dropdown.setTextSize(20);
        if (holder.dropdown.getParent()==null){
        holder.linearLayout1.addView(holder.dropdown);}

        //RADIO
//        holder.radio=new TextView(context);
        Log.e(TAG, "onBindViewHolder: "+data.getRadio() );
        holder.radio.setText("Fav radio station: "+data.getRadio());
        holder.radio.setLayoutParams(params);
        holder.radio.setTextSize(20);
        if (holder.radio.getParent()==null){
        holder.linearLayout1.addView(holder.radio);}

        //fav cars
//        holder.checkbox=new TextView(context);
        String output=data.getCheckbox();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        ArrayList<String> finalOutputString = gson.fromJson(output, type);
        holder.checkbox.setLayoutParams(params);
        Log.e(TAG, "onBindViewHolder: finalOutputString==>"+finalOutputString);
        String a="";
        for(String s:finalOutputString){
            a=s+","+a;
        }
        holder.checkbox.setText("Fav cars: "+a);
        holder.checkbox.setTextSize(20);
        if (holder.checkbox.getParent()==null){
        holder.linearLayout1.addView(holder.checkbox);}


        //View
//        LinearLayout.LayoutParams paramv = new LinearLayout.LayoutParams((int) 0.5, 150);
////        paramv.setMargins(30,20,30,20);
//        View view=new View(context);
//        view.setLayoutParams(paramv);
//        view.setBackgroundColor(R.color.black);
//        holder.linearLayout2.addView(view);




        //Images
        //call
//        holder.Call=new ImageView(context);
        holder.Call.setLayoutParams(params);
//        int id = context.getResources().getIdentifier("@android:drawable/ic_menu_call", null, null);
        holder.Call.setImageResource(R.drawable.ic_baseline_call_24);
        holder.Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mlistener != null){
                    String num=data.getNumber();
                    if(!num.equals(RecyclerView.NO_POSITION)){
                        mlistener.onItemClicked(num);
                    }
                }
            }
        });
//        holder.Call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if(ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//                    Log.e(TAG, "call permission: not granted ");
//                    requestPermissions((Activity)context,new String[]{Manifest.permission.CALL_PHONE},10);
//                }
////                else if()
////                else if(EasyPermissions.hasPermissions((Activity)context, Manifest.permission.CALL_PHONE)){
////                    requestPermissions((Activity)context,new String[]{Manifest.permission.CALL_PHONE},10);
////
////                }
//                else if (shouldShowRequestPermissionRationale((Activity)context,Manifest.permission.CALL_PHONE)){
//                    ActivityCompat.requestPermissions((Activity)context,
//                            new String[]{Manifest.permission.CALL_PHONE},
//                            9);
//                }
//                else {
//                    String num = "tel:" + data.getNumber();
//                    final Intent intent = new Intent(Intent.ACTION_DIAL);
//                    intent.setData(Uri.parse(num));
//                    context.startActivity(intent);
//                }
//            }
//        });
        if (holder.Call.getParent()==null){
        holder.linearLayout2.addView(holder.Call);}

        //sms
//        holder.Sms=new ImageView(context);
        holder.Sms.setLayoutParams(params);
//        int smsid = context.getResources().getIdentifier("@android:drawabl", null, null);
//        Log.e(TAG, "onBindViewHolder: smsid-->"+smsid );
        holder.Sms.setImageResource(R.drawable.ic_baseline_sms_24);
        holder.Sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mlistener!=null){
                    String num=data.getNumber();
                    if (!num.equals(RecyclerView.NO_POSITION)){
                        lister.onSmsClicked(num);
                    }
                }
            }
        });
//        holder.Sms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
//                    Log.e(TAG, "Sms Permission: not granted ");
//                    requestPermissions((Activity)context,new String[]{Manifest.permission.SEND_SMS},10);
//                } else if (shouldShowRequestPermissionRationale((Activity)context,Manifest.permission.SEND_SMS)){
//                    ActivityCompat.requestPermissions((Activity)context,
//                            new String[]{Manifest.permission.SEND_SMS},
//                            9);
//                }
//                else {
//
//                String num = "sms:" + data.getNumber();
//                final Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(num));
//                context.startActivity(intent);
//            }}
//        });
        if (holder.Sms.getParent()==null){
        holder.linearLayout2.addView(holder.Sms);}

        //Wp
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(60, 60);
        params.setMargins(0,20,30,20);
//        holder.Wp=new ImageView(context);
        holder.Wp.setLayoutParams(param);
        int wpid = context.getResources().getIdentifier("@android:draw(able/ic_dialog_email", null, null);
        holder.Wp.setImageResource(R.drawable.whatsapp);
//        holder.Wp.setLayoutParams();
//        holder.Wp.setMaxWidth(10);
        Log.e(TAG, "onBindViewHolder: wpid==>"+wpid );
//        holder.Wp.setImageResource(wpid);
        holder.Wp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = data.getNumber();
                String url = "https://api.whatsapp.com/send?phone=+91" + num;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });
        if (holder.Wp.getParent()==null){
        holder.linearLayout2.addView(holder.Wp);}

        //website
//        holder.Webic=new ImageView(context);
        holder.Webic.setLayoutParams(params);
//        int web = context.getResources().getIdentifier("@drawable/ic_baseline_web_24",null,null);
        holder.Webic.setImageResource(R.drawable.ic_baseline_web_24);
//        holder.Webic.setImageResource(web);
        holder.Webic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www." + data.getWeb();
                Log.e("web", "onClick: " + url);
                Intent i = new Intent(context, Web_Page.class);
                i.putExtra("site", url);
                Log.e("web", "onClick: " + url);
                context.startActivity(i);
            }
        });
        if (holder.Webic.getParent()==null){
        holder.linearLayout2.addView(holder.Webic);}

        //edit
//        holder.Edit=new ImageView(context);
        holder.Edit.setLayoutParams(params);
        holder.Edit.setImageResource(R.drawable.ic_baseline_edit_24);
        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: Data ret:"+" id:"+data.getId()+" name:"+data.getName()+" Email:"+data.getEmail()+" Phone:"+data.getNumber()+
                        " Dropdon:"+data.getDropdown()+" Radio:"+data.getRadio()+" Checkbox:"+data.getCheckbox()
                +" Date:"+data.getDate()+" Time:"+data.getTime()+" web"+data.getWeb());
                i = new Intent(context, MainActivity.class);
                i.putExtra("ID", data.getId());
                i.putExtra("Name", data.getName());
                i.putExtra("Email", data.getEmail());
                i.putExtra("Phone", data.getNumber());
                i.putExtra("Dropdown",data.getDropdown());
                i.putExtra("Radio",data.getRadio());
                i.putExtra("Check",data.getCheckbox());
                i.putExtra("Date",data.getDate());
                i.putExtra("Time",data.getTime());
                i.putExtra("Website", data.getWeb());
                i.putExtra("Password",data.getPass());
                i.addFlags(0);
//                mDatabase.Update(data);
                context.startActivity(i);

            }
        });
        if (holder.Edit.getParent()==null){
        holder.linearLayout2.addView(holder.Edit);}

        //Email
//        holder.Emailic=new ImageView(context);
        holder.Emailic.setLayoutParams(params);
        holder.Emailic.setImageResource(R.drawable.ic_baseline_email_24);
        holder.Emailic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = data.getEmail();
                recycler.OpenDialog(to,2, context);

            }
        });
        if (holder.Emailic.getParent()==null){
        holder.linearLayout2.addView(holder.Emailic);}

    }

    @Override
    public int getItemCount() {
       // Log.e("parthi","arraysize------->"+arrayList.size());
        return arrayList.size();

    }

    public String userAt(int position){
        Log.e("parthi","position----->"+position);
        return arrayList.get(position).getId();
    }

}
