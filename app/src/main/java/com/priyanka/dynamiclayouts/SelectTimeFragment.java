package com.priyanka.dynamiclayouts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SelectTimeFragment extends DialogFragment implements TimePicker.OnTimeChangedListener {
    private EditText editText;
//    public int AMPM;
    String am_pm="";
    SimpleDateFormat sdf;
    public SelectTimeFragment(EditText editText) {
        this.editText=editText;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("hh:mm");
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
//        AMPM=calendar.get(Calendar.AM_PM);
//        int dd = calendar.get(Calendar.DAY_OF_MONTH);
//        TimePickerDialog tpd=new TimePickerDialog()
        if (calendar.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (calendar.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";
//        String strHrsToShow = Calendar.get(Calendar.HOUR) == 0) ? "12" : Calendar.get(Calendar.HOUR) + "";
        return new TimePickerDialog(getActivity(), this::onTimeChanged ,hourOfDay, min,false);
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        populateSetTime(editText, hourOfDay, minute);
    }
    public void populateSetTime(EditText edit,  int hourOfDay, int minute)
    {
//        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        String s=String.format("%02d:%02d", hourOfDay , minute);
        edit.setText(s+" "+am_pm);
    }

}
