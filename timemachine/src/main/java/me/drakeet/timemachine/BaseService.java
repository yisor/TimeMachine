package me.drakeet.timemachine;

import android.support.v4.app.Fragment;

/**
 * @author drakeet
 */
public abstract class BaseService implements CoreContract.Service {

    private CoreContract.View view;


    public BaseService(CoreContract.View view) {
        if (view instanceof Fragment && !((Fragment) view).isAdded()) {
            // TODO: 16/5/22: 16/6/11: I forgot what to do, laugh cry
        }
        view.setService(this);
        this.view = view;
    }


    protected void addNewIn(Message message) {
        view.onNewIn(message);
    }
}
