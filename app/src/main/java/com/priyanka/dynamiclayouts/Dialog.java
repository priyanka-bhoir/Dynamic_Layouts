package com.priyanka.dynamiclayouts;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.priyanka.dynamiclayouts.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class Dialog extends AppCompatDialogFragment {
    private TextInputEditText editText,subject;
    private TextInputLayout subject_layout;
    private Button button;
    String position;
    int flag;
    Context context;
    String title;
    private String title2="Send";
    AlertDialog dialog;

    public Dialog(String position, int flag, Context context) {
        this.position=position;
        this.flag=flag;
        this.context=context;

    }


    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        dialog=builder.create();
        dialog.show();

        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.send_message_dialog,null);
        editText=view.findViewById(R.id.Messageedit);
        subject=view.findViewById(R.id.subjecttext);
        subject_layout=view.findViewById(R.id.emaildialogmessage);
        button=view.findViewById(R.id.dialog_button);
        Log.e(TAG, "onCreateDialog: >>> flag"+flag );

        if (flag==1){
            subject_layout.setVisibility(View.GONE);
            title="Message";
            title2 = "Send via Manager";
        }
        else if (flag==2){
            button.setVisibility(View.GONE);
            title="Email";
        }
        else if (flag==3){
            subject_layout.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            title="Whatsapp";
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = "sms:" + position;
                String s=editText.getText().toString();
                final Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.putExtra("sms_body",s);
                intent.setType("vnd.android-dir/mms-sms");
//                SmsManager sms=SmsManager.getDefault();
//                sms.sendTextMessage(position, null, s, null,null);
                intent.setData(Uri.parse(num));
                    startActivity(intent);
            }
        });

        builder.setView(view).setTitle(title).setPositiveButton(title2,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = false;

                String s=editText.getText().toString();
                Log.e(TAG, "onClick: s==>"+s );
                if (s.isEmpty()){
                    Log.e(TAG, "onClick: s==>"+s );
                    editText.setError("Empty");
                    editText.setFocusable(true);
                    Toast t=Toast.makeText(context,"Give the input",Toast.LENGTH_LONG);
                    t.show();

                }else{
                    wantToCloseDialog=true;
                    if (flag==1){
                        //sms
                        String num = "sms:" + position;
                        Log.e(TAG, "onClick: sms"+position);
                        final Intent intent = new Intent(Intent.ACTION_VIEW);
//                PendingIntent pi=PendingIntent.getActivities(getContext(),0, new Intent[]{intent},0);
                        SmsManager sms=SmsManager.getDefault();
                        ArrayList<String> string=sms.divideMessage(s);
//                    sms.sendTextMessage(position, null, s, null,null);
                        sms.sendMultipartTextMessage(position,null,string,null,null);

                    }else if (flag==2){
                        //Email
                        String subjectm=subject.getText().toString();
                        String message=editText.getText().toString();
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
                        intent.putExtra(intent.EXTRA_EMAIL, new String[]{position});
                        intent.putExtra(intent.EXTRA_SUBJECT,subjectm);
                        intent.putExtra(intent.EXTRA_TEXT,message);
                        startActivity(intent);
                    }
                    else if (flag==3){
                        String text=editText.getText().toString();
//                    String url = "https://api.whatsapp.com/send?phone=+91"+ position;
                        Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.putExtra(Intent.EXTRA_TEXT,"text");
//                    i.setType("text/plain");
//                    i.setPackage("com.whatsapp");
//                    i.setData(Uri.parse(url));
//                    context.startActivity(i);
                        PackageManager pm = context.getPackageManager();
                        try {
                            String url = "https://api.whatsapp.com/send?phone=+91"+ position +"&text=" + URLEncoder.encode(text, "UTF-8");
                            i.setPackage("com.whatsapp");
                            i.setData(Uri.parse(url));
                            if (i.resolveActivity(pm) != null) {
                                startActivity(i);
                            }

                        } catch (Exception e) {
                            Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }}

                if(wantToCloseDialog){
                    dialog.dismiss();}
            }
        });
        return builder.create();
    }
}
