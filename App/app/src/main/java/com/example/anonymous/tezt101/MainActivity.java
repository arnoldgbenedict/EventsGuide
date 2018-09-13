package com.example.anonymous.tezt101;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class MainActivity extends Activity {

    //@SuppressLint("ClickableViewAccessibility")

    //private GestureDetectorCompat mDetector;
    private FileHandler fControl;
    MaterialCalendarView cal;
    CalendarDay selectedDate;
    Collection<CalendarDay> markers;

    ListView lv;
    Context context;

    long updatedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        //collapse(findViewById(R.id.textView));
        fControl = new FileHandler("datafile.txt", this);
        markers = new ArrayList<CalendarDay>();
        lv = findViewById(R.id.list);

        cal = findViewById(R.id.calendarView);
        cal.state().edit()
                .setFirstDayOfWeek(Calendar.SATURDAY)
                .setMinimumDate(CalendarDay.from(2018, 1, 0))
                .setMaximumDate(CalendarDay.from(2100, 9, 20))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        cal.setDateSelected(Calendar.getInstance(), true);
        selectedDate = CalendarDay.from(Calendar.getInstance());

        cal.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectedDate = date;
                updateList();
            }
        });
        fControl.updateAndSortDisplayContent();
        updateList();

        Button todayButton = findViewById(R.id.gotoTodayButton);
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = CalendarDay.from(Calendar.getInstance());
                updateList();
            }
        });

        Button refreshButton = findViewById(R.id.refresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedTime = 0;
            }
        });

        Button aboutButton = findViewById(R.id.about);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, About.class);
                startActivity(intent);
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 5);
        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1024, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        new fetch(fetch.POST, (TextView)findViewById(R.id.textView3)).execute();
    }

    public static void expand(final View v) {
        v.measure(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ConstraintLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    public class EventDecorator implements DayViewDecorator {

        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(10, color));
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public class fetch  extends AsyncTask {
        public static final int POST = 1, GET = 0;
        private int byGetOrPost = 0;
        private TextView netMsg;

        //flag 0 means get and 1 means post.(By default it is get.)
        public fetch(int flag, TextView v) {
            byGetOrPost = flag;
            netMsg = v;
            Log.d("Lopp","");
        }

        @Override

        protected void onPreExecute(){
        }

        @Override
        protected Object doInBackground(Object[] arg0) {
            while(true) {
                if(isNetworkAvailable()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            netMsg.setVisibility(View.VISIBLE);
                            netMsg.setText("Internet Connection Available");
                            netMsg.setTextColor(Color.WHITE);
                            netMsg.setBackgroundColor(Color.parseColor("#00c853"));
                            netMsg.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    netMsg.setVisibility(View.INVISIBLE);
                                }
                            }, 4500);

                        }
                    });
                    while(isNetworkAvailable()) {
                        if(updatedTime + 300000 > System.currentTimeMillis()) continue;
                        Log.e("Time", System.currentTimeMillis() + "");
                        String link = "https://evntmgmt.000webhostapp.com/androidConnectPost.php?ts=" + fControl.getLastUpdated();
                        InputStream is = null;
                        BufferedReader br = null;
                        try {
                            URL url = new URL(link);
                            final URLConnection conn = url.openConnection();

                            is =conn.getInputStream();
                            //if(is.available() == 0) continue;
                            br = new BufferedReader(new InputStreamReader(is));

                            String line = null;
                            String content = "";
                            while ((line = br.readLine()) != null) {
                                content += line;
                            }

                            Log.e("content", content);
                            String temp = "";
                            for(int i = 0;i<content.length();++i){
                                if(content.charAt(i) == '^'){
                                    if(!FileHandler.valid(temp)) break;
                                    fControl.appendOnFile(temp);
                                    updateList();
                                    temp = "";
                                }
                                else temp+=content.charAt(i);
                            }
                            if(content.length()>0) fControl.updateAndSortDisplayContent();
                            updatedTime = System.currentTimeMillis();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                        expand(netMsg);
                                        netMsg.setText("Synced");
                                        netMsg.setTextColor(Color.LTGRAY);
                                        netMsg.setBackgroundColor(Color.BLACK);
                                        netMsg.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                collapse(netMsg);
                                            }
                                        }, 3000);

                                }
                            });
                        } catch (Exception e) {
                            /*final Exception f = e;
                            new AlertDialog.Builder(context)
                                    .setTitle("Internal Error Occured")
                                    .setMessage("Help us improve by taking some time to report the bug")
                                    .setNegativeButton("REPORT BUG", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            sendEmail(f.toString(), "BUG : Async().fetch() Error");
                                        }
                                    })
                                    .show();*/
                            e.printStackTrace();
                        } finally {
                            if(is != null) {
                                try {
                                    is.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if(br != null) {
                                try {
                                    br.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            netMsg.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    expand(netMsg);
                                }
                            }, 1000);
                            netMsg.setText("No Internet Connection");
                            netMsg.setTextColor(Color.LTGRAY);
                            netMsg.setBackgroundColor(Color.BLACK);
                        }
                    });
                    while(!isNetworkAvailable());
                    updatedTime = 0;
                }
            }
        }

        @Override
        protected void onPostExecute(Object result){
        }
    }

    /* BEFORE CODING
    ArrayList<Integer> selectedListIndex = new ArrayList<>();
    void updateList(ArrayList<LinkedHashMap<Integer, String>> a){
        lv.setOnItemClickListener(null);
        final ArrayList<LinkedHashMap<Integer, String>> val = a;
        final Activity tmpActivity = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                selectedListIndex = new ArrayList<>();
                final ArrayList<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
                for(LinkedHashMap<Integer, String> e : val){
                    try {
                        Date sd = new SimpleDateFormat("yyyy-MM-dd").parse(e.get(FileHandler.START_DATE));
                        Date ed = new SimpleDateFormat("yyyy-MM-dd").parse(e.get(FileHandler.END_DATE));

                        CalendarDay start = CalendarDay.from(sd);
                        CalendarDay end = CalendarDay.from(ed);

                        while (!sd.after(ed)) {
                            markers.add(CalendarDay.from(sd));
                            GregorianCalendar cal = new GregorianCalendar();
                            cal.setTime(sd);
                            cal.add(java.util.Calendar.DATE, 1);
                            sd = cal.getTime();
                        }

                        if(selectedDate.isInRange(start, end)) {
                            LinkedHashMap<String, Object> tmp = new LinkedHashMap<String, Object>();
                            tmp.put("D", selectedDate.getCalendar());
                            tmp.put("B", true);
                            tmp.put("TT", e.get(FileHandler.EVENT_NAME));
                            tmp.put("TD", e.get(FileHandler.DESCRIPTION));
                            list.add(tmp);
                            selectedListIndex.add(val.indexOf(e));
                        }
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                }
                updateCalender();

                ArrayList<String> n = new ArrayList<>();
                for(LinkedHashMap<String,Object> ele : list){n.add((String) ele.get("TT"));}
                listAdapter arrayAdapter = new listAdapter(tmpActivity, list, n.toArray(new String[0]));
                TextView noContentTextView = findViewById(R.id.textView);
                if(arrayAdapter.isEmpty()) noContentTextView.setText("No Events to display");
                else noContentTextView.setText("");
                lv.setAdapter(arrayAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int index = selectedListIndex.get(i);
                        final LinkedHashMap<Integer, String> disp = fControl.content.get(index);
                        new AlertDialog.Builder(context)
                                .setTitle(disp.get(FileHandler.EVENT_NAME))
                                .setMessage(disp.get(FileHandler.DESCRIPTION))
                                .setNegativeButton("More Info", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(context, eventDetails.class);
                                        intent.putExtra("Name", disp.get(FileHandler.EVENT_NAME));
                                        intent.putExtra("Description", disp.get(FileHandler.DESCRIPTION));
                                        intent.putExtra("StartDate", disp.get(FileHandler.START_DATE));
                                        intent.putExtra("StartTime", disp.get(FileHandler.START_TIME));
                                        intent.putExtra("EndDate", disp.get(FileHandler.END_DATE));
                                        intent.putExtra("EndTime", disp.get(FileHandler.END_TIME));
                                        intent.putExtra("Faculty", disp.get(FileHandler.FACULTY_INCHARGE));
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                });
            }
        });
    }*/

    void updateList(){
        try {
            lv.setOnItemClickListener(null);
            lv.setOnScrollListener(null);
            final ArrayList<LinkedHashMap<Integer, String>> val = fControl.content;
            final Activity tmpActivity = this;
            for (LinkedHashMap<Integer, String> e : val) {
                try {
                    Date sd = new SimpleDateFormat("yyyy-MM-dd").parse(e.get(FileHandler.START_DATE));
                    Date ed = new SimpleDateFormat("yyyy-MM-dd").parse(e.get(FileHandler.END_DATE));

                    while (!sd.after(ed)) {
                        markers.add(CalendarDay.from(sd));
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTime(sd);
                        cal.add(java.util.Calendar.DATE, 1);
                        sd = cal.getTime();
                    }
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateCalender();

                    ArrayList<String> n = new ArrayList<>();
                    for (LinkedHashMap<String, Object> ele : fControl.displayContent) {
                        n.add((String) ele.get("TT"));
                    }
                    listAdapter arrayAdapter = new listAdapter(tmpActivity, fControl.displayContent, n.toArray(new String[0]));
                    TextView noContentTextView = findViewById(R.id.textView);
                    if (arrayAdapter.isEmpty()) noContentTextView.setText("No Events to display");
                    else {
                        noContentTextView.setText("");
                        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView absListView, int i) {

                            }

                            @Override
                            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                                if (fControl.displayContent.size() >= 2)
                                    if (!fControl.displayContent.get(i + 1).get("D").equals(fControl.displayContent.get(i).get("D")) && !(Boolean) fControl.displayContent.get(i).get("B"))
                                        i++;
                                Calendar date = (Calendar) fControl.displayContent.get(i).get("D");

                                cal.setSelectedDate(date);
                                cal.setCurrentDate(date);
                            }
                        });
                    }
                    lv.setAdapter(arrayAdapter);

                    int pos = 0;
                    for (LinkedHashMap<String, Object> e : fControl.displayContent) {
                        Calendar date = (Calendar) e.get("D");
                        if (date.get(Calendar.YEAR) == selectedDate.getCalendar().get(Calendar.YEAR) && date.get(Calendar.DAY_OF_YEAR) == selectedDate.getCalendar().get(Calendar.DAY_OF_YEAR)) {
                            pos = fControl.displayContent.indexOf(e);
                            break;
                        }

                    }
                    lv.setSelection(pos);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            int index = (int) fControl.displayContent.get(i).get("I");
                            final LinkedHashMap<Integer, String> disp = val.get(index);
                            View alertLayout = getLayoutInflater().inflate(R.layout.alertstyle, null);
                            Date ed = Calendar.getInstance().getTime(), sd = Calendar.getInstance().getTime();
                            try {
                                ed = new SimpleDateFormat("yyyy-MM-dd").parse(disp.get(FileHandler.END_DATE));
                                sd = new SimpleDateFormat("yyyy-MM-dd").parse(disp.get(FileHandler.START_DATE));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            TextView dates = alertLayout.findViewById(R.id.dates);
                            if (ed.getDate() != sd.getDate())
                                dates.setText(sd.getDate() + " - " + ed.getDate());
                            else dates.setText("" + sd.getDate());

                            TextView month = alertLayout.findViewById(R.id.month);
                            if (ed.getMonth() != sd.getMonth())
                                month.setText(getMonth(sd.getMonth()) + " - " + getMonth(ed.getMonth()));
                            else month.setText("" + getMonth(sd.getMonth()));

                            TextView en = alertLayout.findViewById(R.id.eventName);
                            en.setText("" + disp.get(FileHandler.EVENT_NAME));

                            TextView time = alertLayout.findViewById(R.id.description);
                            time.setText("" + disp.get(FileHandler.START_TIME).substring(0, 5));

                            TextView type = alertLayout.findViewById(R.id.type);
                            type.setText("" + disp.get(FileHandler.TYPE));

                            new AlertDialog.Builder(context)
                                    .setView(alertLayout)
                                    .setNegativeButton("More Info", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(context, eventDetails.class);
                                            intent.putExtra("Name", disp.get(FileHandler.EVENT_NAME));
                                            intent.putExtra("Type", disp.get(FileHandler.TYPE));
                                            intent.putExtra("Description", disp.get(FileHandler.DESCRIPTION));
                                            intent.putExtra("StartDate", disp.get(FileHandler.START_DATE));
                                            intent.putExtra("StartTime", disp.get(FileHandler.START_TIME));
                                            intent.putExtra("EndDate", disp.get(FileHandler.END_DATE));
                                            intent.putExtra("EndTime", disp.get(FileHandler.END_TIME));
                                            intent.putExtra("Faculty", disp.get(FileHandler.FACULTY_INCHARGE));
                                            intent.putExtra("Venue", disp.get(FileHandler.VENUE));
                                            intent.putExtra("Tags", disp.get(FileHandler.TAGS));
                                            startActivity(intent);
                                        }
                                    })
                                    .show();
                        }
                    });

                }
            });
        }catch (Exception e){
            /*final Exception f = e;
            new AlertDialog.Builder(context)
                    .setTitle("Internal Error Occured")
                    .setMessage("Help us improve by taking some time to report the bug")
                    .setNegativeButton("REPORT BUG", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendEmail(f.toString(), "BUG : UpdateList() Error");
                        }
                    })
                    .show();*/
            e.printStackTrace();
        }
    }

    String getMonth(int i){
        String[] l = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return l[i];
    }

    void updateCalender(){
        DayViewDecorator dec = new EventDecorator(Color.parseColor("#64c1ff"), markers);
        cal.removeDecorators();
        cal.addDecorator(dec);
    }
    protected void sendEmail(String FEEDBACK, String SUBJECT) {
        String[] TO = {"arnold.benedict@gmail.com"};
        String[] CC = {"manav.mehta@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
        emailIntent.putExtra(Intent.EXTRA_TEXT, FEEDBACK);

        try {
            startActivity(Intent.createChooser(emailIntent, "CHOOSE E-MAIl CLIENT.."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}

