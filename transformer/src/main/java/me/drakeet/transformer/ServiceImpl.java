package me.drakeet.transformer;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import me.drakeet.timemachine.CoreContract;
import me.drakeet.timemachine.Message;
import me.drakeet.timemachine.Now;

/**
 * @author drakeet
 */
public class ServiceImpl implements CoreContract.Service {

    private CoreContract.View view;
    private Handler handler;


    public ServiceImpl(CoreContract.View view) {
        if (view instanceof Fragment && !((Fragment) view).isAdded()) {
            // throw new IllegalAccessException("You must add your fragment to ")
            // TODO: 16/5/22  
        }
        this.view = view;
        view.setService(this);
    }


    public void mock() {
        handler.post(() -> view.onNewIn(
                new Message("A message from Service.", "Service", "drakeet", new Now())));
    }


    @Override public void start() {
        handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            for (; ; ) {
                mock();
                SystemClock.sleep(3000);
            }
        }).start();
    }


    @Override public void onNewOut(Message message) {
        // echo
        Message _message = message.clone();
        _message.fromUserId = "Service";
        _message.toUserId = "drakeet";
        _message.createdAt = new Now();
        view.onNewIn(_message);
    }


    @Override public void destroy() {
        handler.removeCallbacks(null);
    }
}
