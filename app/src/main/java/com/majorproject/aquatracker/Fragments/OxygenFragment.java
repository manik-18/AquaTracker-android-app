package com.majorproject.aquatracker.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OxygenFragment extends Fragment {

    private DatabaseReference mDatabase;
    private static final String TAG = "OxygenFragment";
    private LineChart mChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oxygen, container, false);

        mChart = view.findViewById(R.id.ochart);
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);
        mChart.setViewPortOffsets(50f, 50f, 50f, 350f);
        mChart.getDescription().setText("Oxygen Trend");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference OxygenDataRef = mDatabase.child("oxygen_levels");

        OxygenDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Entry> entriesInRange = new ArrayList<>();
                ArrayList<Entry> entriesOutOfRange = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<>();
                int index = 0;

                FirebaseData OxygenData = dataSnapshot.getValue(FirebaseData.class);
                if (OxygenData != null && OxygenData.getValues() != null) {
                    for (DataValue dataValue : OxygenData.getValues()) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                            Date date = sdf.parse(dataValue.getTime());
                            long timeMillis = date.getTime();
                            labels.add(dataValue.getTime()); // Assuming dataValue.getTime() gives the label

                            if (dataValue.getValue() >= OxygenData.getMin() && dataValue.getValue() <= OxygenData.getMax()) {
                                entriesInRange.add(new Entry(index, (float) dataValue.getValue()));
                            } else {
                                entriesOutOfRange.add(new Entry(index, (float) dataValue.getValue()));
                            }

                            index++;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                LineDataSet dataSetInRange = new LineDataSet(entriesInRange, "DO. Values (In Range)");
                dataSetInRange.setColor(Color.GREEN);
                dataSetInRange.setCircleColor(Color.GREEN);
                dataSetInRange.setCircleRadius(4f);

                LineDataSet dataSetOutOfRange = new LineDataSet(entriesOutOfRange, "DO. Values (Out of Range)");
                dataSetOutOfRange.setColor(Color.RED);
                dataSetOutOfRange.setCircleColor(Color.RED);
                dataSetOutOfRange.setCircleRadius(4f);

                LineData lineData = new LineData(dataSetInRange, dataSetOutOfRange);

                mChart.setData(lineData);
                XAxis xAxis = mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                xAxis.setLabelRotationAngle(90f);

                YAxis yAxisRight = mChart.getAxisRight();
                yAxisRight.setEnabled(false); // Disable right axis

                mChart.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Failed to read Oxygen data.", error.toException());
            }
        });

        return view;
    }
}
