package com.example.tcf_gyro_acc_2;

import android.os.Looper;
import android.util.Log;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import java.util.ArrayList;

import es.uji.geotec.backgroundsensors.collection.CollectionConfiguration;
import es.uji.geotec.backgroundsensors.sensor.Sensor;
import es.uji.geotec.backgroundsensors.service.manager.ServiceManager;


public class BackgroundListeningSensor extends Thread {
    private static final String TAG = "Sensor listening";
    private final Sensor sensor;
    private final ServiceManager serviceManager;
//    private final MethodChannel methodChannel; // Inject MethodChannel
    private int dataCount = 0;
    private final ArrayList<String> sensorDataBuffer = new ArrayList<>(); // Buffer to store data
    private final Handler handler;


    private final String sensorType;

    public BackgroundListeningSensor(Sensor sensor, ServiceManager serviceManager, String sensorType) {
        this.serviceManager = serviceManager;
        this.sensor = sensor;
        this.sensorType = sensorType;
        handler = new Handler(Looper.getMainLooper());
    }

    public void run() {
        // the main task run in multithreading
        CollectionConfiguration config2 = new CollectionConfiguration(sensor, android.hardware.SensorManager.SENSOR_DELAY_GAME, 50);
        serviceManager.startCollection(config2, records -> {
            Log.d(TAG, "onRecordsCollected : " + records.size() + " records");
            Log.d(TAG, "a sample: " + records.get(0)); // data stored
            sensorDataBuffer.add(records.get(0).toString());
            if (dataCount == 4) {
                // Send all collected data to Flutter
                dataCount = 0;
                sendDataToMainThread(dataToJSON(sensorDataBuffer));
                sensorDataBuffer.clear();
                Log.d(TAG, "clear");
            } else {
                dataCount++;
            }
        });
    }

    private void sendDataToMainThread(String jsonData) {
        Message msg = handler.obtainMessage();
        // Create a bundle to hold both data and sensor type
        Bundle dataBundle = new Bundle();
        dataBundle.putString("jsonData", jsonData);
        dataBundle.putString("sensorType", sensorType);
        msg.setData(dataBundle);
        handler.sendMessage(msg);
    }

    private String dataToJSON(ArrayList<String> sensorDataBuffer) {
        StringBuilder sb = new StringBuilder("[\n");
        for (String data:sensorDataBuffer){
            sb.append(data.substring(data.indexOf("{"), data.lastIndexOf("}")).replace('=', ':'));
            sb.append("}, \n");
        }
        sb.append("]");
        return sb.toString();
    }

    /*
    {
        "sensor": "GYROSCOPE",
        "timestamp": 1714616463597,
        "x": -0.0030543262,
        "y": 0.002443461,
        "z": -0.0018325958
      },`
     */
}

