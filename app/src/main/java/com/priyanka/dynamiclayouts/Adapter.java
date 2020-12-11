package com.priyanka.dynamiclayouts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Adapter extends RecyclerView.Adapter<View_Holder> {
    Context context;
    ArrayList<Data> arrayList;
    DatabaseHelper db;
    Gson gson=new Gson();
    Intent i;


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
        holder.Name.setText("Name: "+data.getName());
        holder.Name.setLayoutParams(params);
        holder.Name.setTextSize(20);
        holder.linearLayout1.addView(holder.Name);

        //FAV CHAR
        holder.dropdown=new TextView(context);
        holder.dropdown.setLayoutParams(params);
        holder.dropdown.setText("Fav character: "+data.getDropdown());
        holder.dropdown.setTextSize(20);
        holder.linearLayout1.addView(holder.dropdown);

        //RADIO
        holder.radio=new TextView(context);
        Log.e(TAG, "onBindViewHolder: "+data.getRadio() );
        holder.radio.setText("Fav radio station: "+data.getRadio());
        holder.radio.setLayoutParams(params);
        holder.radio.setTextSize(20);
        holder.linearLayout1.addView(holder.radio);

        //fav cars
        holder.checkbox=new TextView(context);
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
        holder.linearLayout1.addView(holder.checkbox);


        //Images
        //call
        holder.Call=new ImageView(context);
        holder.Call.setLayoutParams(params);
//        int id = context.getResources().getIdentifier("@android:drawable/ic_menu_call", null, null);
        holder.Call.setImageResource(R.drawable.ic_baseline_call_24);
        holder.Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = "tel:" + data.getNumber();
                final Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(num));
                context.startActivity(intent);
            }
        });
        holder.linearLayout2.addView(holder.Call);

        //sms
        holder.Sms=new ImageView(context);
        holder.Sms.setLayoutParams(params);
//        int smsid = context.getResources().getIdentifier("@android:drawabl", null, null);
//        Log.e(TAG, "onBindViewHolder: smsid-->"+smsid );
        holder.Sms.setImageResource(R.drawable.ic_baseline_sms_24);
        holder.Sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = "sms:" + data.getNumber();
                final Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(num));
                context.startActivity(intent);
            }
        });
        holder.linearLayout2.addView(holder.Sms);

        //Wp
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(60, 60);
        params.setMargins(0,20,30,20);
        holder.Wp=new ImageView(context);
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
        holder.linearLayout2.addView(holder.Wp);

        //website
        holder.Webic=new ImageView(context);
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
        holder.linearLayout2.addView(holder.Webic);

        //edit
        holder.Edit=new ImageView(context);
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
                i.addFlags(0);
//                mDatabase.Update(data);
                context.startActivity(i);

            }
        });
        holder.linearLayout2.addView(holder.Edit);

        //Email
        holder.Emailic=new ImageView(context);
        holder.Emailic.setLayoutParams(params);
        holder.Emailic.setImageResource(R.drawable.ic_baseline_email_24);
        holder.Emailic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = data.getEmail();
                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("`message/rfc822`");
                final PackageManager pm = context.getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : matches)
                    if (info.activityInfo.packageName.endsWith(".gm") ||
                            info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
                if (best != null)
                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                intent.putExtra(intent.EXTRA_EMAIL, new String[]{to});
                context.startActivity(intent);
            }
        });
        holder.linearLayout2.addView(holder.Emailic);







    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public String userAt(int position){
        return arrayList.get(position).getId();
    }
}
