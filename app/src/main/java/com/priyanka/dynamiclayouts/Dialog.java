package com.priyanka.dynamiclayouts;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

    public Dialog(String position, int flag, Context context) {
        this.position=position;
        this.flag=flag;
        this.context=context;

    }


    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.send_message_dialog,null);
        editText=view.findViewById(R.id.Messageedit);
        subject=view.findViewById(R.id.subjecttext);
        subject_layout=view.findViewById(R.id.emaildialogmessage);

        builder.setView(view).setTitle("Message").setPositiveButton("Send",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                subject_layout.setVisibility(View.GONE);
                String s=editText.getText().toString();

                if (flag==1){
//                String string=
                String num = "sms:" + position;
                    Log.e(TAG, "onClick: sms"+position);
                final Intent intent = new Intent(Intent.ACTION_VIEW);
//                PendingIntent pi=PendingIntent.getActivities(getContext(),0, new Intent[]{intent},0);
                    SmsManager sms=SmsManager.getDefault();
                    ArrayList<String> string=sms.divideMessage(s);
//                    sms.sendTextMessage(position, null, s, null,null);
                sms.sendMultipartTextMessage(position,null,string,null,null);

            }else {
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
            }
        });
        return builder.create();
    }
}
