//package com.example.tcf_gyro_acc_2;
//
//import androidx.annotation.NonNull;
//import io.flutter.embedding.android.FlutterActivity;
//import io.flutter.embedding.engine.FlutterEngine;
//import io.flutter.plugin.common.EventChannel;
////import io.flutter.plugins.generated.TcfGyroAcc2Plugin; // Generated plugin class
//
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.os.Bundle;
//import android.util.Log;
//
//import java.util.Map;
//import java.util.HashMap;
//
//public class MainActivity extends FlutterActivity implements SensorEventListener {
//
//    private static final String STREAM_CHANNEL = "com.example.stream_sensor_data";
//    private EventChannel.EventSink eventSink;
//    private SensorManager sensorManager;
//    private Sensor accelerometer;
//    private Sensor gyroscope;
//
//    @Override
//    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
//        super.configureFlutterEngine(flutterEngine);
//
//        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
//
//        EventChannel eventChannel = new EventChannel(flutterEngine.getDartExecutor(), STREAM_CHANNEL);
//        eventChannel.setStreamHandler((EventChannel.StreamHandler) this);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
//        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        sensorManager.unregisterListener(this);
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        if (eventSink != null) {
//            // Combine sensor data into a single object or map
//            Map<String, Float> sensorData = new HashMap<>();
//            sensorData.put("acc_x", event.values[0]);
//            sensorData.put("acc_y", event.values[1]);
//            sensorData.put("acc_z", event.values[2]);
//            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
//                sensorData.put("gyr_x", event.values[0]);
//                sensorData.put("gyr_y", event.values[1]);
//                sensorData.put("gyr_z", event.values[2]);
//            }
//            eventSink.success(sensorData); // send data to flutter
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        // Handle sensor accuracy changes if necessary
//    }
//
//    @Override
//    public void onListen(EventChannel.EventSink sink, Object arguments) {
//        eventSink = sink;
//    }
//
//    @Override
//    public void onCancel(Object arguments) {
//        eventSink = null;
//    }
//}
