package com.example.rado.forecast;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.example.rado.forecast.WeatherData.getData;
import static com.example.rado.forecast.WeatherData.thr;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private ActionBar actionBar;
    private static int notificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.pager);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Notification:
        if (notificationId == 0) { postAlert(0); }

        Calendar calendar = Calendar.getInstance();
        String weekDay;
        SimpleDateFormat dayFormat, dayFormat2;
        dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        dayFormat2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("Днес").setTabListener(this));
        thr();
        getData();
        for (int i = 0; i < 9; i++) {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            weekDay = dayFormat.format(calendar.getTime());
            actionBar.addTab(actionBar.newTab().setText(weekDay + "\n" + dayFormat2.format(calendar.getTime())).setTabListener(this));

        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i2) {}

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    //  Notification
    private void postAlert(int i) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Weather Alert!").setContentText(WeatherData.outlookArray[i])
                .setSmallIcon(R.drawable.small_icon).setAutoCancel(true).setTicker("Wrap up warm!")
        .setLargeIcon(BitmapFactory.decodeResource(getResources(), WeatherData.symbolArray[i]))
        .setPriority(Notification.PRIORITY_HIGH).setVisibility(Notification.VISIBILITY_PUBLIC)
        .setVibrate(new long[]{100, 50, 100, 50, 100}).setCategory(Notification.CATEGORY_ALARM);

        NotificationCompat.BigPictureStyle bigStyle = new NotificationCompat.BigPictureStyle();
        bigStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.snow_scene));
        builder.setStyle(bigStyle);

        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class).addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());

        notificationId++;
    }
}
