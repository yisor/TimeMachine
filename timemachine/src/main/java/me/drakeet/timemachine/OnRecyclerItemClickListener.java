package me.drakeet.timemachine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author drakeet
 */
public abstract class OnRecyclerItemClickListener extends GestureDetector.SimpleOnGestureListener
        implements RecyclerView.OnItemTouchListener {

    private View childView;
    private RecyclerView touchView;
    private GestureDetector mGestureDetector;


    abstract void onItemClick(View view, int position);


    void onItemLongClick(View view, int position) {

    }


    public OnRecyclerItemClickListener(Context context) {
        mGestureDetector = new GestureDetector(context.getApplicationContext(), this);
    }


    @Override public boolean onSingleTapUp(MotionEvent ev) {
        if (childView != null) {
            onItemClick(childView, touchView.getChildAdapterPosition(childView));
        }
        return true;
    }


    @Override public void onLongPress(MotionEvent ev) {
        if (childView != null) {
            onItemLongClick(childView, touchView.getChildAdapterPosition(childView));
        }
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        mGestureDetector.onTouchEvent(motionEvent);
        childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        touchView = recyclerView;
        return false;
    }


    @Override public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

    }


    @Override public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
