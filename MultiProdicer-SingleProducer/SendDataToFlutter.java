package com.example.tcf_gyro_acc_2;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.BlockingQueue;

import io.flutter.plugin.common.EventChannel;

public class SendDataToFlutter implements EventChannel.StreamHandler {
    private EventChannel.EventSink events;
    private BlockingQueue<String> accQ;
    private BlockingQueue<String> gyrQ;
    private String accData;
    private String gyrData;

    SendDataToFlutter(BlockingQueue<String> accQ, BlockingQueue<String> gyrQ) {
        this.accQ = accQ;
        this.gyrQ = gyrQ;
    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        this.events = events;
        startSendingData();
    }

    @Override
    public void onCancel(Object arguments) {
        this.events = null;
    }

    private void startSendingData() {
        new Thread(() -> {
            while (true) {
                try {
                    accData = this.accQ.take();
                    gyrData = this.gyrQ.take();

                    Bundle data = new Bundle();
                    data.putString("acc", accData);
                    data.putString("gyr", gyrData);

                    if (events != null) {
                        events.success(data);
                    }

                    Log.d("blocking queue", "success");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
