package in.programmeraki.hbt;

import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.morphingbutton.MorphingButton;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import in.programmeraki.hbt.adapter.BLEFeedAdapter;
import in.programmeraki.hbt.profile.BleProfileActivity;
import in.programmeraki.hbt.utils.DayAxisValueFormatter;
import in.programmeraki.hbt.utils.HRSManager;
import in.programmeraki.hbt.utils.HRSManagerCallbacks;
import no.nordicsemi.android.ble.BleManager;
import no.nordicsemi.android.ble.BleManagerCallbacks;

public class LiveActivity extends BleProfileActivity implements HRSManagerCallbacks, OnChartValueSelectedListener {

    private final static String HR_VALUE = "hr_value";
    private final static String HR_POS = "hr_pos";

    private final static int MAX_HR_VALUE = 65535;
    private final static int MIN_POSITIVE_VALUE = 0;
    private final static int REFRESH_INTERVAL = 1000; // 1 second interval

    private final String TAG = "Main";
    BLEFeedAdapter bleFeedAdapter;
    private TextView title_tv;
    private Button action_connect, back_btn;
    private ViewGroup content_vg, topbar_ll, graph_fl;
    private ProgressBar progressBar;
    private TextView current_pulse_tv, today_pulse_peak_tv, current_temp_tv, today_temp_peak_tv;
    private int mHrmValue = 0;
    private int mCounter = 0;

    private LineChart mChart;
    ArrayList<ILineDataSet> dataSets;

    ArrayList<Entry> pulseValues = new ArrayList<>();
    ArrayList<Entry> tempValues = new ArrayList<>();

    /*
     * BleActivity Abstract Methods
     * */
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_live);

        action_connect = findViewById(R.id.action_connect);
        title_tv = findViewById(R.id.title_tv);
        back_btn = findViewById(R.id.back_btn);
        progressBar = findViewById(R.id.progressBar);
        content_vg = findViewById(R.id.content_vg);
        topbar_ll = findViewById(R.id.topbar_ll);
        graph_fl = findViewById(R.id.graph_fl);
        content_vg = findViewById(R.id.content_vg);
        current_pulse_tv = findViewById(R.id.current_pulse_tv);
        today_pulse_peak_tv = findViewById(R.id.today_pulse_peak_tv);
        current_temp_tv = findViewById(R.id.current_temp_tv);
        today_temp_peak_tv = findViewById(R.id.today_temp_peak_tv);

        title_tv.setOnClickListener(view -> {
            content_vg.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            action_connect.setVisibility(View.GONE);
            setHRSValueOnView(60);
        });

        back_btn.setOnClickListener(view -> {
            finish();
        });

        setUpChart();

        content_vg.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected BleManager<? extends BleManagerCallbacks> initializeManager() {
        final HRSManager manager = HRSManager.getInstance(getApplicationContext());
        manager.setGattCallbacks(this);
        return manager;
    }

    @Override
    protected void setDefaultUI() {
        Log.d(TAG, "setDefaultUI: ");
        current_pulse_tv.setText(R.string.not_available_value);
    }

    @Override
    protected int getDefaultDeviceName() {
        Log.d(TAG, "getDefaultDeviceName: ");
        return R.string.hrs_default_name;
    }

    @Override
    protected int getAboutTextId() {
        Log.d(TAG, "getAboutTextId: ");
        return R.string.hrs_about_text;
    }

    @Override
    protected UUID getFilterUUID() {
        Log.d(TAG, "getFilterUUID: ");
        return HRSManager.HR_SERVICE_UUID;
    }


    /*
     * Custom Methods
     * */
    private void setHRSValueOnView(final int value) {
        Log.d(TAG, "setHRSValueOnView: ");
        runOnUiThread(() -> {
            if (value >= MIN_POSITIVE_VALUE && value <= MAX_HR_VALUE) {
                current_pulse_tv.setText(Integer.toString(value));
            } else {
                current_pulse_tv.setText(R.string.not_available_value);
            }
            appendLineChartData(value, 25);
        });
    }


    /*
     * HRS Manager Callbacks
     * */
    @Override
    public void onHRSensorPositionFound(BluetoothDevice device, String position) {
        Log.d(TAG, "onHRSensorPositionFound: " + device.getAddress() + ", pos: " + position);
    }

    @Override
    public void onHRValueReceived(BluetoothDevice device, int value) {
        Log.d(TAG, "onHRValueReceived: " + device.getAddress() + ", val: " + value);
        mHrmValue = value;
        setHRSValueOnView(mHrmValue);
    }


    /*
     * BleActivity Override Methods
     * */
    @Override
    public void onServicesDiscovered(BluetoothDevice device, boolean optionalServicesFound) {
        super.onServicesDiscovered(device, optionalServicesFound);
        Log.d(TAG, "onServicesDiscovered: ");
    }

    @Override
    public void onDeviceConnected(BluetoothDevice device) {
        super.onDeviceConnected(device);
        runOnUiThread(() ->{
            progressBar.setVisibility(View.GONE);

            Toast.makeText(this, "Device Connected", Toast.LENGTH_SHORT).show();
            content_vg.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onDeviceReady(BluetoothDevice device) {
        super.onDeviceReady(device);
        Log.d(TAG, "onDeviceReady: ");
    }

    @Override
    public void onDeviceDisconnected(final BluetoothDevice device) {
        super.onDeviceDisconnected(device);
        Log.d(TAG, "onDeviceDisconnected: ");
        runOnUiThread(() -> {
            current_pulse_tv.setText(R.string.not_available_value);
            current_pulse_tv.setText(R.string.not_available);
            Toast.makeText(this, "Device Disconnected", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDeviceConnecting(BluetoothDevice device) {
        super.onDeviceConnecting(device);
        runOnUiThread(() -> {
            progressBar.setVisibility(View.VISIBLE);
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(HR_VALUE, mHrmValue);
    }


    /*
     * Chart Methods
     */
    private void setUpChart(){
        mChart = findViewById(R.id.mChart);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(getResources().getColor(R.color.colorWhite));

        XAxis xAxis = mChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
//        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);
//        xAxis.setValueFormatter(xAxisFormatter);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setAxisMaximum(150f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(true);

        mChart.getAxisRight().setEnabled(false);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
    }

    private void appendLineChartData(int pulse, int temp){
//        Entry pulseEntry = new Entry(Calendar.getInstance().getTime().getTime(), pulse);
//        Entry tempEntry = new Entry(Calendar.getInstance().getTime().getTime(), temp);

        Entry pulseEntry = new Entry(pulseValues.size(), pulse);
        Entry tempEntry = new Entry(tempValues.size(), temp);

        pulseValues.add(pulseEntry);
        tempValues.add(tempEntry);

        if( mChart.getData() != null && mChart.getData().getDataSetCount() > 1){
            dataSets.get(0).addEntry(pulseEntry);
            dataSets.get(1).addEntry(tempEntry);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
            mChart.invalidate();
            return;
        }

        // create a dataset and give it a type
        LineDataSet pulseSet = new LineDataSet(pulseValues, "Pulse (bpm)");
        LineDataSet tempSet = new LineDataSet(tempValues, "Temp (˚C)");

        // set the line to be drawn like this "- - - - - -"
        pulseSet.setColor(getResources().getColor(R.color.redPrimaryDark));
        tempSet.setColor(getResources().getColor(R.color.orangePrimaryDark));

        dataSets = new ArrayList<>();
        dataSets.add(pulseSet); // add the datasets
        dataSets.add(tempSet); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        pulseSet.setDrawIcons(false);
        tempSet.setDrawIcons(false);

        pulseSet.setCircleColor(getResources().getColor(R.color.redPrimaryDark));
        tempSet.setCircleColor(getResources().getColor(R.color.orangePrimaryDark));

        pulseSet.setLineWidth(1f);
        tempSet.setLineWidth(1f);

        pulseSet.setCircleRadius(3f);
        tempSet.setCircleRadius(3f);

        pulseSet.setDrawCircleHole(false);
        tempSet.setDrawCircleHole(false);

        pulseSet.setValueTextSize(9f);
        tempSet.setValueTextSize(9f);

        pulseSet.setDrawFilled(true);
        tempSet.setDrawFilled(true);

        pulseSet.setFormLineWidth(1f);
        tempSet.setFormLineWidth(1f);

        pulseSet.setFormSize(15.f);
        tempSet.setFormSize(15.f);

        pulseSet.setFillColor(Color.BLACK);
        tempSet.setFillColor(Color.BLACK);

        // set data
        mChart.setData(data);
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mHrmValue = savedInstanceState.getInt(HR_VALUE);
    }

    /*
     * Chart implemented methods
     */
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        String eVal = new SimpleDateFormat("HH:mm:ss").format(new Date((long) e.getX()));
        Toast.makeText(this, "time: " + eVal + ", value: " +e.getY(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
}
