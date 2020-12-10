package com.priyanka.dynamiclayouts;

import android.view.View;
import android.webkit.WebView;
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
    public View_Holder(@NonNull View itemView) {
        super(itemView);
        linearLayout1=itemView.findViewById(R.id.cardlin);
        linearLayout2=itemView.findViewById(R.id.linear_left);
    }
}
