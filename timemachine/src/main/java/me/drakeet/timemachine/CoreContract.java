package me.drakeet.timemachine;

import java.util.List;

/**
 * @author drakeet
 */
public interface CoreContract {

    interface View {
        void setDelegate(Delegate delegate);
        void setService(Service service);
        void onNewIn(Message message);
        void onNewOut(Message message);
    }


    interface Delegate {
        List<Message> provideInitialMessages();
        void onNewOut(Message message);
        void onMessageClick(Message message);
        void onMessageLongClick(Message message);
        boolean onLeftActionClick();
        boolean onRightActionClick();
    }


    interface Service {
        // pass
        void start();
        void destroy();
        void onNewOut(Message message);
    }
}
