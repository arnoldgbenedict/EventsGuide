package com.example.anonymous.tezt101;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by arnold.g.benedict on 24/02/18.
 */

public class FileHandler {

    ArrayList<LinkedHashMap<String, Object>> displayContent;

    ArrayList<LinkedHashMap<Integer, String>> content;
    File pathContent, pathDisplayContent;
    static int TIMESTAMP = 0, EVENT_NAME = 1, START_DATE = 2, END_DATE = 3, START_TIME = 4, END_TIME = 5,
            VENUE = 6, TYPE = 7, DESCRIPTION = 8, FACULTY_INCHARGE = 9, TAGS = 10;
    final static int attrLength = 11, attrLengthDisp = 5;
    public FileHandler(String filename, Context c){
        pathContent = new File(c.getFilesDir(), filename);
        //pathContent.delete();//--------------------------------------------------//remove//
        if(!pathContent.exists()){
            try {
                pathContent.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        pathDisplayContent = new File(c.getFilesDir(), "listdisplaysortedfile.txt");
        //pathDisplayContent.delete();//-------------------------------------------------//remove//
        if(!pathDisplayContent.exists()){
            try {
                pathDisplayContent.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        updateHandler();
    }
    public static boolean valid(String s){
        int count = 0;
        for(int i = 0; i<s.length();++i) if(s.charAt(i) =='`') count++;
        if(count == attrLength) return true;
        return false;
    }
    public void appendOnFile(String s){
        try {
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(pathContent, true));
            String temp = ""; LinkedHashMap<Integer, String> tempM = new LinkedHashMap<>();
            for(int i = 0, j = 0; i < s.length();++i) {
                if (s.charAt(i) == '`') {
                    tempM.put(j++, temp);
                    bw.write(""+temp+ "\n");
                    temp = "";
                } else {
                    temp += s.charAt(i);
                }
            }
            content.add(tempM);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateHandler(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathContent));
            content = new ArrayList<LinkedHashMap<Integer, String>>();
            String inputLine; int i = 0; LinkedHashMap<Integer, String> tempM = new LinkedHashMap<>();
            while ((inputLine = br.readLine()) != null) {
                if(inputLine == "") continue;
                tempM.put(i, inputLine);
                i = (i+1) % attrLength;
                if(i==0){ content.add(tempM); tempM = new LinkedHashMap<>();}
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathDisplayContent));
            displayContent = new ArrayList<LinkedHashMap<String, Object>>();
            String inputLine; int i = 0; LinkedHashMap<String, Object> tempM = new LinkedHashMap<>();
            while ((inputLine = br.readLine()) != null) {
                if(inputLine == "") continue;
                switch (i){
                    case 0 : tempM.put("I", Integer.parseInt(inputLine));
                        break;
                    case 1 : Calendar c = Calendar.getInstance();
                        c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(inputLine));
                        tempM.put("D", c);
                        break;
                    case 2 : tempM.put("B", (inputLine.equals("true"))?true:false);
                        break;
                    case 3 : tempM.put("TT", inputLine);
                        break;
                    case 4 : tempM.put("TD", inputLine);
                        break;
                }
                i = (i+1) % attrLengthDisp;
                if(i==0){ Log.e("DC", tempM.toString());displayContent.add(tempM); tempM = new LinkedHashMap<>();}
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        updateAndSortDisplayContent();
    }

    public void updateAndSortDisplayContent(){
        Log.e("uASDC" , "RAN");
        if(content.isEmpty()) return;
        try {
            BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(pathDisplayContent, true));

            displayContent = new ArrayList<LinkedHashMap<String, Object>>();
            CalendarDay leastDate = CalendarDay.from(new SimpleDateFormat("yyyy-MM-dd").parse(content.get(0).get(FileHandler.START_DATE)))
                    , highestDate = CalendarDay.from(new SimpleDateFormat("yyyy-MM-dd").parse(content.get(0).get(FileHandler.END_DATE)));

            for(LinkedHashMap<Integer, String> e : content){
                Date sd = new SimpleDateFormat("yyyy-MM-dd").parse(e.get(FileHandler.START_DATE));
                Date ed = new SimpleDateFormat("yyyy-MM-dd").parse(e.get(FileHandler.END_DATE));

                CalendarDay start = CalendarDay.from(sd);
                CalendarDay end = CalendarDay.from(ed);

                if(start.isBefore(leastDate)) leastDate = start;
                if(end.isAfter(highestDate)) highestDate = end;
            }

            for(CalendarDay i = leastDate; i.isBefore(highestDate) || i.equals(highestDate); ){
                boolean checked = false;
                for(LinkedHashMap<Integer,String> e : content){

                    Date sd = new SimpleDateFormat("yyyy-MM-dd").parse(e.get(FileHandler.START_DATE));
                    Date ed = new SimpleDateFormat("yyyy-MM-dd").parse(e.get(FileHandler.END_DATE));

                    CalendarDay start = CalendarDay.from(sd);
                    CalendarDay end = CalendarDay.from(ed);

                    if(i.isInRange(start, end)){
                        LinkedHashMap<String, Object> tmp = new LinkedHashMap<>();
                        tmp.put("I", content.indexOf(e));
                        Calendar c = i.getCalendar();
                        c.add(Calendar.DATE, -1);
                        tmp.put("D", c);
                        c.add(Calendar.DATE, 1);
                        i = CalendarDay.from(c);
                        if(!checked) { tmp.put("B", true); checked=true;} else { tmp.put("B", false); }
                        tmp.put("TT", e.get(FileHandler.EVENT_NAME));
                        tmp.put("TD", e.get(FileHandler.DESCRIPTION));
                        displayContent.add(tmp);
                    }
                }

                Calendar tc = i.getCalendar();
                tc.add(Calendar.DATE,1);
                i = CalendarDay.from(tc);
            }

            for(LinkedHashMap<String, Object> e : displayContent){
                bw.write("" + e.get("I").toString() + "\n");
                Calendar c = (Calendar) e.get("D");
                bw.write("" + new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()) + "\n");
                bw.write("" + e.get("B").toString() + "\n");
                bw.write("" + e.get("TT").toString() + "\n");
                bw.write("" + e.get("TD").toString() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public String getLastUpdated(){
        if(content.isEmpty()) return "2017-02-24%2014:52:45";
        else if(content.get(content.size() - 1).get(TIMESTAMP) == null) return "2017-02-24%2014:52:45";
        return content.get(content.size() - 1).get(TIMESTAMP).replace(" ", "%20");
    }
}
