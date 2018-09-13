package com.example.anonymous.tezt101;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class eventDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        TextView eventNameTV = findViewById(R.id.eventNameTV);
        Bundle extras = getIntent().getExtras();
        eventNameTV.setText(extras.getString("Name"));

        TextView eventTV = findViewById(R.id.description);
        eventTV.setText(extras.getString("Description"));

        eventTV = findViewById(R.id.dates);
        Date ed = Calendar.getInstance().getTime(), sd = Calendar.getInstance().getTime();
        try {
            sd = new SimpleDateFormat("yyyy-MM-dd").parse(extras.getString("StartDate"));
            ed = new SimpleDateFormat("yyyy-MM-dd").parse(extras.getString("EndDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(ed.getMonth() != sd.getMonth())
            eventTV.setText(sd.getDate() + " " + getMonth(sd.getMonth()) + " - " + ed.getDate() + " " + getMonth(ed.getMonth()) + ", " + (ed.getYear() + 1900));
        if(ed.getDate() != sd.getDate() && ed.getMonth() == sd.getMonth())
            eventTV.setText(sd.getDate() + " - " + ed.getDate() + " " + getMonth(ed.getMonth()) + ", " + (ed.getYear() + 1900));
        if(ed.getDate() == sd.getDate())
            eventTV.setText(sd.getDate() + " " + getMonth(ed.getMonth()) + ", " + (ed.getYear() + 1900));

        eventTV = findViewById(R.id.venue);
        eventTV.setText(extras.getString("Venue"));

        eventTV = findViewById(R.id.faculty);
        eventTV.setText(extras.getString("Faculty"));

        eventTV = findViewById(R.id.audiance);
        eventTV.setText(extras.getString("Tags"));

        eventTV = findViewById(R.id.type);
        eventTV.setText(extras.getString("Type"));

        ImageButton back = findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    String getMonth(int i){
        String[] l = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return l[i];
    }
}
