package com.capstone.newmytripplanner.activity.addmytrip;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.activity.addcontents.AddContentsActivity;
import com.capstone.newmytripplanner.activity.main.MainActivity;
import com.capstone.newmytripplanner.util.RecyclerViewDecoration;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.List;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    View v;
    private ImageButton buttonPre_calendar;
    private MaterialCalendarView calendarView;
    private Button nextBtn;
    private RecyclerView select_locationRecycler;
    private List<CalendarDay> dates;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_date_picker, container, false);
        buttonPre_calendar = v.findViewById(R.id.buttonPre_calendar);
        nextBtn = v.findViewById(R.id.buttonDone_calendar);
        buttonPre_calendar.setOnClickListener(v ->{
            getActivity().finish();
            Intent preActivity = new Intent(getActivity(),MainActivity.class);
            startActivity(preActivity);
        });
        calendarView = v.findViewById(R.id.calendar_view);
        calendarView.setOnRangeSelectedListener((widget, dates) -> {
            Toast.makeText(getActivity(),"날짜 : " +calendarView.getSelectedDates(),Toast.LENGTH_LONG).show();
        });
        nextBtn.setOnClickListener(v -> {
            ((addMyTripActivity)getActivity()).dates = calendarView.getSelectedDates();
            ((addMyTripActivity)getActivity()).changeFragment(1);
        });
        return v;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
