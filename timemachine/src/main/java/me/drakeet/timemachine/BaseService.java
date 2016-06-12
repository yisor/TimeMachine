package me.drakeet.timemachine;

/**
 * @author drakeet
 */
public abstract class BaseService implements CoreContract.Service {

    private CoreContract.View view;


    public BaseService(CoreContract.View view) {
        view.setService(this);
        this.view = view;
    }


    protected void addNewIn(Message message) {
        view.onNewIn(message);
    }
}
