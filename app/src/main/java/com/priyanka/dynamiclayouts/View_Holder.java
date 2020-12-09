package com.priyanka.dynamiclayouts;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class View_Holder extends RecyclerView.ViewHolder {
    TextView Fname;
    TextView Lname;
    TextView Mobile;
    TextView Web;
    TextView Email;
    ImageView Edit;
    ImageView Call;
    ImageView Sms;
    ImageView Wp;
    ImageView Emailic;
    ImageView Webic;
    ImageView Delete;
    WebView mywebview;
    public View_Holder(@NonNull View itemView) {
        super(itemView);
    }
}
