package com.majorproject.aquatracker;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.majorproject.aquatracker.Model.DataValue;
import com.majorproject.aquatracker.Model.FirebaseData;
import com.majorproject.aquatracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BackgroundService extends Service {

    private static final String TAG = "BackgroundService";
    private static final int NOTIFICATION_ID = 123;
    private static final String CHANNEL_ID = "BackgroundServiceChannel";
    private static final long DELAY_INTERVAL = 5000; // 5 seconds

    private int notificationIdCounter = 0;

    private NotificationManager notificationManager;
    private DatabaseReference mDatabase;
    private Handler handler;
    private Map<String, FirebaseData> dataMap;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, createNotification());

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchData();
                handler.postDelayed(this, DELAY_INTERVAL);
            }
        }, DELAY_INTERVAL);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Background Service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification createNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Background Service")
                .setContentText("Running...")
                .setSmallIcon(R.drawable.logo)
                .build();
    }

    private void fetchData() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataMap = new HashMap<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String dataType = snapshot.getKey();
                    FirebaseData data = snapshot.getValue(FirebaseData.class);
                    if (data != null) {
                        dataMap.put(dataType, data);
                    }
                }
                checkData();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Failed to read data.", error.toException());
            }
        });
    }

    private void checkData() {
        for (Map.Entry<String, FirebaseData> entry : dataMap.entrySet()) {
            String dataType = entry.getKey();
            FirebaseData data = entry.getValue();
            if (data != null && data.getValues() != null) {
                for (DataValue dataValue : data.getValues()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                        Date date = sdf.parse(dataValue.getTime());
                        long timeMillis = date.getTime();

                        if (!isInRange(dataValue.getValue(), data.getMin(), data.getMax())) {
                            sendNotification("Current " + dataType + " is out of range: " + dataValue.getValue(),dataType);
                        }
                    } catch (ParseException e) {
                        Log.e(TAG, "Error parsing date", e);
                    }
                }
            }
        }
    }

    private boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    private void sendNotification(String message,String type) {
        notificationIdCounter++;
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(type+" Out of Range")
                .setContentText(message)
                .setSmallIcon(R.drawable.logo)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        notificationManager.notify(notificationIdCounter, notification);
    }
}
