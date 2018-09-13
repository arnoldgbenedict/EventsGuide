package com.example.anonymous.tezt101;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by arnold.g.benedict on 04/06/18.
 */

public class NotificationReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        FileHandler fControl = new FileHandler("datafile.txt", context);
        Calendar tc = Calendar.getInstance();
        tc.add(Calendar.DATE,1);
        CalendarDay selectedDate = CalendarDay.from(tc);

        for (LinkedHashMap<String, Object> e : fControl.displayContent) {
            Calendar date = (Calendar) e.get("D");
            if (date.get(Calendar.YEAR) == selectedDate.getCalendar().get(Calendar.YEAR) && date.get(Calendar.DAY_OF_YEAR) == selectedDate.getCalendar().get(Calendar.DAY_OF_YEAR)) {

                int index = (int) fControl.displayContent.get(fControl.displayContent.indexOf(e)).get("I");
                LinkedHashMap<Integer, String> disp = fControl.content.get(index);
                Intent disp_intent = new Intent(context, eventDetails.class);
                disp_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                disp_intent.putExtra("Name", disp.get(FileHandler.EVENT_NAME));
                disp_intent.putExtra("Type", disp.get(FileHandler.TYPE));
                disp_intent.putExtra("Description", disp.get(FileHandler.DESCRIPTION));
                disp_intent.putExtra("StartDate", disp.get(FileHandler.START_DATE));
                disp_intent.putExtra("StartTime", disp.get(FileHandler.START_TIME));
                disp_intent.putExtra("EndDate", disp.get(FileHandler.END_DATE));
                disp_intent.putExtra("EndTime", disp.get(FileHandler.END_TIME));
                disp_intent.putExtra("Faculty", disp.get(FileHandler.FACULTY_INCHARGE));
                disp_intent.putExtra("Venue", disp.get(FileHandler.VENUE));
                disp_intent.putExtra("Tags", disp.get(FileHandler.TAGS));

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 1024 + index, disp_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.today)
                        .setContentTitle("Upcoming Event")
                        .setContentText(disp.get(FileHandler.EVENT_NAME))
                        .setSubText(disp.get(FileHandler.TYPE))
                        .setColor(Color.argb(1, 0, 145, 234))
                        .setAutoCancel(true);

                notificationManager.notify(1024 + index, builder.build());

            }
        }
    }
}
