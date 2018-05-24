package com.example.rado.forecast;

import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by Rado on 8.10.2017 г..
 */

public class WeatherData {

    static ArrayList<String> list = new ArrayList<>();
    static String[] min = new String[10];
    static String[] max = new String[10];
    static String[] wind = new String[10];
    static String[] cond = new String[10];
    static int ix;
    static Integer[] symbolArray = {R.drawable.snowy, R.drawable.partly_sunny, R.drawable.sunny, R.drawable.stormy, R.drawable.cloudy};
    static String[] outlookArray = {"Developing snow storms", "Partly sunny and breezy", "Mostly sunny", "Afternoon storms", "Increasing cloudiness"};


    static void thr() {
        dayFormat[0] = new SimpleDateFormat("EEEE", new Locale("BG"));
        weekDay[0] = dayFormat[0].format(calendar.getTime());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL link = new URL("http://m.sinoptik.bg/shumen-bulgaria-100727233/10-days");
                    BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream()));
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        list.add(inputLine);
                    }
                    in.close();
                } catch (MalformedURLException me) {
                    System.out.println(me);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
        thread.start();
        //getData();
    }

    static void getData() {

        if (min[ix] == null) {
            ix = 0;
            int j = 0;
            //for (int j = 0; j < list.size() - 150; j++) {
            while (j < list.size()) {

                if (list.get(j).toLowerCase().contains(weekDay[0].toLowerCase())) {
                    min[ix] = (getMin(list.get(j + 5)));
                    max[ix] = (getMax(list.get(j + 5)));
                    wind[ix] = (getWind(list.get(j + 12)));
                    cond[ix] = getCond(list.get(j + 3));
                    if (ix < 9) ix++;
                    calendar.add(Calendar.DAY_OF_WEEK, 1);
                    weekDay[0] = dayFormat[0].format(calendar.getTime());
                }
                j++;
            }
        }
    }

    static String getCond(String arr) {
        String[] spl;
        String spl2;
        spl = arr.split("</td>");
        spl2 = spl[0].replace("   ", "").replace("  ", "");
        return spl2;
    }

    static String getWind(String arr) {
        String[] spl;
        String[] spl2 = new String [1];
        spl = arr.split("small\">");
        spl2 = spl[1].split(" m/s");
        return spl2[0];
    }

    static String getMin(String arr) {
        String[] spl;
        String[] spl2 = new String [1];
        spl = arr.split("<strong>");
        spl2 = spl[1].split("&deg");
        return spl2[0];
    }

    static String getMax(String arr) {
        String[] spl;
        String[] spl2 = new String [1];
        spl = arr.split("max-temp\">");
        spl2 = spl[1].split("&deg");
        return spl2[0];
    }

        static Calendar calendar = Calendar.getInstance();
        static String[] weekDay = new String[1];
        static SimpleDateFormat[] dayFormat = new SimpleDateFormat[1];
        //dayFormat[0] = new SimpleDateFormat("E");
        //weekDay[0] = dayFormat[0].format(calendar.getTime());
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);


}
