package com.example.sensor_event_listener;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.loader.FlutterLoader;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
//import io.flutter.plugins.generated.TcfGyroAcc2Plugin; // Generated plugin class

import java.util.HashMap;
import java.util.Map;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends FlutterActivity implements SensorEventListener, EventChannel.StreamHandler {

    private static final String STREAM_CHANNEL = "com.example.stream_sensor_data";

    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), STREAM_CHANNEL).setMethodCallHandler(
                ((call, result) -> {
                    if (call.method.equals(STREAM_CHANNEL)) {

                    } else {
                        result.notImplemented();
                    }
                })
        );
    }
}