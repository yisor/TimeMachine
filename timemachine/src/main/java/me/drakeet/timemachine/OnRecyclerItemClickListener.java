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

    private View mChildView;
    private RecyclerView mRecyclerView;
    private GestureDetector mGestureDetector;


    abstract void onItemClick(View view, int position);


    void onItemLongClick(View view, int position) {

    }


    public OnRecyclerItemClickListener(Context context) {
        mGestureDetector = new GestureDetector(context.getApplicationContext(), this);
    }


    @Override public boolean onSingleTapUp(MotionEvent ev) {
        if (mChildView != null) {
            onItemClick(mChildView, mRecyclerView.getChildAdapterPosition(mChildView));
        }
        return true;
    }


    @Override public void onLongPress(MotionEvent ev) {
        if (mChildView != null) {
            onItemLongClick(mChildView, mRecyclerView.getChildAdapterPosition(mChildView));
        }
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        mGestureDetector.onTouchEvent(motionEvent);
        mChildView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        mRecyclerView = recyclerView;
        return false;
    }


    @Override public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

    }


    @Override public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
