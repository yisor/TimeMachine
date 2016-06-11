package me.drakeet.transformer;

import android.os.Handler;
import android.os.Looper;
import me.drakeet.timemachine.BaseService;
import me.drakeet.timemachine.CoreContract;
import me.drakeet.timemachine.Message;
import me.drakeet.timemachine.Now;
import me.drakeet.timemachine.TimeKey;

/**
 * @author drakeet
 */
public class ServiceImpl extends BaseService {

    private Handler handler;


    public ServiceImpl(CoreContract.View view) {
        super(view);
    }


    @Override public void start() {
        handler = new Handler(Looper.getMainLooper());
    }


    @Override public void onNewOut(Message message) {
        if (message.content.equals("滚")) {
            addNewIn(new Message.Builder()
                .setContent("但是...但是...")
                .setFromUserId("Service")
                .setToUserId(TimeKey.userId)
                .thenCreateAtNow());
            return;
        }
        // echo
        Message _message = message.clone();
        _message.fromUserId = "Service";
        _message.toUserId = TimeKey.userId;
        _message.createdAt = new Now();
        addNewIn(_message);
    }


    @Override public void destroy() {
        handler.removeCallbacks(null);
    }
}
