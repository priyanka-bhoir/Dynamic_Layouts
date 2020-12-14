package com.priyanka.dynamiclayouts;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class View_Holder extends RecyclerView.ViewHolder {
    TextView Name ;
    TextView Mobile;
    TextView Web;
    TextView Email;
    TextView radio;
    TextView dropdown;
    TextView checkbox;
    TextView date;
    TextView time;
    ImageView Edit;
    ImageView Call;
    ImageView Sms;
    ImageView Wp;
    ImageView Emailic;
    ImageView Webic;
    ImageView Delete;
    WebView mywebview;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    Recycler r;
    Context context;
    Adapter.OnItemClickLister lister;
    public View_Holder(@NonNull View itemView, Adapter.OnItemClickLister listener) {
        super(itemView);
        linearLayout1=itemView.findViewById(R.id.cardlin);
        linearLayout2=itemView.findViewById(R.id.linear_left);
//        context=r.getApplicationContext();
//        if (Name.getParent() != null){
        Name=new TextView(itemView.getContext());
        dropdown=new TextView(itemView.getContext());
        radio=new TextView(itemView.getContext());
        checkbox=new TextView(itemView.getContext());
        Call=new ImageView(itemView.getContext());
        Sms=new ImageView(itemView.getContext());
        Wp=new ImageView(itemView.getContext());
        Webic=new ImageView(itemView.getContext());
        Edit=new ImageView(itemView.getContext());
        Emailic=new ImageView(itemView.getContext());



    }
}
