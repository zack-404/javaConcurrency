package com.example.sensor_event_listener;

import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import io.flutter.plugin.common.EventChannel;

import es.uji.geotec.backgroundsensors.sensor.BaseSensor;
import es.uji.geotec.backgroundsensors.sensor.Sensor;
import es.uji.geotec.backgroundsensors.sensor.SensorManager;
import es.uji.geotec.backgroundsensors.service.BaseSensorRecordingService;
import es.uji.geotec.backgroundsensors.service.manager.ServiceManager;

public class SensorListener implements  EventChannel.StreamHandler, SensorEventListener {

    private SensorManager sensorManager;
    private ServiceManager serviceManager;
    private Sensor acc;
    private Sensor gyr;

    public void init() {
        // Initialize SensorManager and ServiceManager
        sensorManager = new SensorManager(this);
        serviceManager = new ServiceManager(this, BaseSensorRecordingService.class);

    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink eventSink) {

    }

    @Override
    public void onCancel(Object arguments) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }
}
