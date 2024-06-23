package com.example.tcf_gyro_acc_2;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import es.uji.geotec.backgroundsensors.collection.CollectionConfiguration;
import es.uji.geotec.backgroundsensors.sensor.Sensor;
import es.uji.geotec.backgroundsensors.service.manager.ServiceManager;

public class BackgroundSensorListener implements Runnable {
    private static final String TAG = "Sensor listening";
    private final Sensor sensor;
    private final ServiceManager serviceManager;

    BlockingQueue<String> blockingQueue = null;
    private String sensorType;
    private final ArrayList<String> sensorDataBuffer = new ArrayList<>(); // Buffer to store data

    public BackgroundSensorListener(Sensor sensor, ServiceManager serviceManager, String sensorType, BlockingQueue<String> blockingQueue) {
        this.serviceManager = serviceManager;
        this.sensor = sensor;
        this.sensorType = sensorType;
        this.blockingQueue = blockingQueue;
    }
    @Override
    public void run() {
        CollectionConfiguration config2 = new CollectionConfiguration(sensor, android.hardware.SensorManager.SENSOR_DELAY_GAME, 50);
        serviceManager.startCollection(config2, records -> {
            sensorDataBuffer.add(records.get(0).toString());
            if (sensorDataBuffer.size() == 10) {
                try {
                    this.blockingQueue.put(dataToJSON());
//                    Log.d(TAG, "successfully enqueue data");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sensorDataBuffer.clear();
            }
        });
    }

    private String dataToJSON() {
        StringBuilder sb = new StringBuilder("[");
        for (String data:sensorDataBuffer){
            sb.append(data.substring(data.indexOf("{"), data.lastIndexOf("}")).replace('=', ':'));
            sb.append("}, \n");
        }
        sb.append("]");
        return sb.toString();
    }
}
