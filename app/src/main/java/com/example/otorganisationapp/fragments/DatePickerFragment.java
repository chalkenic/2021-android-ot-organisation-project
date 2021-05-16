package com.example.otorganisationapp.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog.OnDateSetListener callbackListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

//        try {
//            callbackListener = (DatePickerDialog.OnDateSetListener) context;
//        } catch (ClassCastException c) {
//            throw new ClassCastException(context.toString() + " requires a OnDateSetListener");
//        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle SavedInstanceState) {

        final Calendar tempCalendar = Calendar.getInstance();
        int year = tempCalendar.get(Calendar.YEAR);
        int month = tempCalendar.get(Calendar.MONTH);
        int day = tempCalendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this,  year,  month,  day);
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if (callbackListener != null) {
            callbackListener.onDateSet(view, year, month, dayOfMonth);
        }


    }




}
