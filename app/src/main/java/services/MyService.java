package services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;


public class MyService extends ResultReceiver {
    private Receiver mReceiver;

    public MyService(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        try {
            if (mReceiver != null) {
                mReceiver.onReceiveResult(resultCode, resultData);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);

    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }
}