package com.example.rado.forecast;
import static android.content.ContentValues.TAG;
import static com.example.rado.forecast.WeatherData.*;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


/**
 * Created by Rado on 8.10.2017 г..
 */

public class WeatherFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);
        Bundle args = getArguments();
        int i = args.getInt("day");
        getData();


        TextView textOutlook = ((TextView) rootView.findViewById(R.id.text_outlook));
        ImageView symbolView = ((ImageView) rootView.findViewById(R.id.image_symbol));
        TextView tempsView = ((TextView) rootView.findViewById(R.id.text_temp));
        TextView windView = ((TextView) rootView.findViewById(R.id.text_min));
        TextView realFeelView = ((TextView) rootView.findViewById(R.id.text_real_feel));
        //if (cond[i] != null && cond[i].contains("дъжд")) symbolView.setImageResource(R.drawable.rain);
        if (cond[i] != null && cond[i].toLowerCase().contains("облачно")) symbolView.setImageResource(R.drawable.cloudy);
        if (cond[i] != null && cond[i].toLowerCase().contains("дъжд")) symbolView.setImageResource(R.drawable.rain);
        //if (cond[i] != null && cond[i].contains("Предимно облачно")) symbolView.setImageResource(R.drawable.cloudy);
        if (cond[i] != null && cond[i].contains("Слънчево")) symbolView.setImageResource(R.drawable.sunny);
        if (cond[i] != null && cond[i].contains("Предимно слънчево")) symbolView.setImageResource(R.drawable.partly_sunny);
        if (cond[i] != null && cond[i].contains("Променлива облачност")) symbolView.setImageResource(R.drawable.partly_sunny);
        textOutlook.setText(cond[i]);
        tempsView.setText("Макс. " + max[i] + ":°c");
        windView.setText("Вятър " + wind[i] +  " m/s");
        realFeelView.setText("Мин. " + min[i] +  "°c");

        return rootView;
    }
}
