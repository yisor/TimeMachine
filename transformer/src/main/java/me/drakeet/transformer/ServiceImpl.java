package me.drakeet.transformer;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import me.drakeet.timemachine.CoreContract;
import me.drakeet.timemachine.Message;
import me.drakeet.timemachine.Now;
import me.drakeet.timemachine.TimeKey;

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
    }


    @Override public void onNewOut(Message message) {
        if (message.content.equals("滚")) {
            view.onNewIn(new Message("但是...但是...", "Service", TimeKey.userId, new Now()));
            return;
        }
        // echo
        Message _message = message.clone();
        _message.fromUserId = "Service";
        _message.toUserId = TimeKey.userId;
        _message.createdAt = new Now();
        view.onNewIn(_message);
    }


    @Override public void destroy() {
        handler.removeCallbacks(null);
    }
}
