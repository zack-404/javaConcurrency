package com.example.tcf_gyro_acc_2;

import android.os.Build;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import es.uji.geotec.backgroundsensors.sensor.BaseSensor;
import es.uji.geotec.backgroundsensors.sensor.Sensor;
import es.uji.geotec.backgroundsensors.sensor.SensorManager;
import es.uji.geotec.backgroundsensors.service.BaseSensorRecordingService;
import es.uji.geotec.backgroundsensors.service.manager.ServiceManager;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "com.flutter.java/gyroAndAcc";
    private SensorManager sensorManager;
    private ServiceManager serviceManager;
    private FlutterEngine flutterEngine;

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        this.flutterEngine = flutterEngine;
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL).setMethodCallHandler(this::onMethodCall);
    }

    private void onMethodCall(MethodCall call, MethodChannel.Result result) {
        if (call.method.equals("startSensorCollecting")) {
            // Initialize SensorManager and ServiceManager
            sensorManager = new SensorManager(this);
            serviceManager = new ServiceManager(this, BaseSensorRecordingService.class);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                serviceManager.enableServiceNotification();
            }

            // Get available sensors
            List<Sensor> sensors = sensorManager.availableSensors(BaseSensor.values());

            // Start background collection of available sensors
            if (sensors.size() > 1) {
                BlockingQueue<String> accQ = new LinkedBlockingQueue<>();
                BlockingQueue<String> gyrQ = new LinkedBlockingQueue<>();

                Runnable accRunner = new BackgroundSensorListener(sensors.get(0), serviceManager, "acc", accQ);
                Runnable gyrRunner = new BackgroundSensorListener(sensors.get(1), serviceManager, "gyr", gyrQ);

                Thread acc = new Thread(accRunner, "acc");
                Thread gyr = new Thread(gyrRunner, "gyr");

                acc.start();
                gyr.start();

                new EventChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                        .setStreamHandler(new SendDataToFlutter(accQ, gyrQ));

                result.success("true");
            } else {
                result.error("UNAVAILABLE", "Sensors not available.", null);
            }
        } else {
            result.notImplemented();
        }
    }
}
 
