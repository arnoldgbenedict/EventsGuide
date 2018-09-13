package com.example.anonymous.tezt101;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;

/**
 * Created by arnold.g.benedict on 25/05/18.
 */

public class listAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<LinkedHashMap<String, Object>> list;

    public listAdapter(Activity context, ArrayList<LinkedHashMap<String, Object>> list, String[] names) {
        /*ArrayList<String> tmp = new ArrayList<>();
        for(LinkedHashMap<String,Object> ele : list){
            tmp.add((String) ele.get("TT"));
        }
        for(LinkedHashMap)*/
        super(context, R.layout.listviewstyle, names);
        this.context = context;
        this.list = list;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listviewstyle, null,true);
        if(list.isEmpty()) return rowView;

        TextView dateText = (TextView) rowView.findViewById(R.id.dispdate);
        TextView dayText = (TextView) rowView.findViewById(R.id.day);
        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        TextView descText = (TextView) rowView.findViewById(R.id.desc);

        Calendar date = (Calendar) list.get(position ).get("D");

        if(date.compareTo(Calendar.getInstance())<0) { dateText.setTextColor(Color.LTGRAY); dayText.setTextColor(Color.LTGRAY);
            rowView.findViewById(R.id.ll).setBackgroundTintList(parent.getResources().getColorStateList(R.color.colorListDone));
        }
        else { dateText.setTextColor(Color.BLACK); dayText.setTextColor(Color.BLACK);
            rowView.findViewById(R.id.ll).setBackgroundTintList(parent.getResources().getColorStateList(R.color.colorListUpcoming));}

        if(date.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) && date.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)){
            dateText.setTextColor(Color.parseColor("#0091ea"));
            dayText.setTextColor(Color.parseColor("#0091ea"));
            context.findViewById(R.id.gotoTodayButton).setVisibility(View.VISIBLE);
            rowView.findViewById(R.id.ll).setBackgroundTintList(parent.getResources().getColorStateList(R.color.colorListToday));
        }
        dateText.setText(new DecimalFormat("00").format(date.get(Calendar.DAY_OF_MONTH)));
        String day = "Err";
        switch (date.get(Calendar.DAY_OF_WEEK)){
            case Calendar.MONDAY:   day="Mon";  break;
            case Calendar.TUESDAY:  day="Tue";  break;
            case Calendar.WEDNESDAY:day="Wed";  break;
            case Calendar.THURSDAY: day="Thu";  break;
            case Calendar.FRIDAY:   day="Fri";  break;
            case Calendar.SATURDAY: day="Sat";  break;
            case Calendar.SUNDAY:   day="Sun";  break;
        }

        dayText.setText(day);
        if((Boolean) list.get(position).get("B")) rowView.findViewById(R.id.dateSet).setVisibility(View.VISIBLE);
        else rowView.findViewById(R.id.dateSet).setVisibility(View.INVISIBLE);
        titleText.setText((String) list.get(position).get("TT"));
        descText.setText((String) list.get(position).get("TD"));

        if((Boolean) list.get(position).get("B")) rowView.setPadding(0, 40, 0, 0);
        return rowView;

    }
}
