package com.example.tcf_gyro_acc_2;

import android.util.Log;

import java.util.concurrent.BlockingQueue;

public class BackgroundSensorCollector extends Thread {

    private BlockingQueue<String> queue;
    public String data;
    public BackgroundSensorCollector(BlockingQueue<String> queue){
        this.queue = queue;
    }
    public void run() {
        while (true) {
            try {
                data = queue.take();
                Log.d("get sensor data", data);
            } catch (InterruptedException e) {
                data = e.toString();
            }
        }
    }
}
