package iiitd.nishant.todolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

interface OnTapListener {
    void onTap(View view, int pos);
    void onLongTap(View view, int pos);
}

public class OnTap implements RecyclerView.OnItemTouchListener{

    private GestureDetector gestureDetector;
    private OnTapListener onTapListener;

    public OnTap (Context context, final RecyclerView recyclerView, final OnTapListener onTapListener) {
        this.onTapListener = onTapListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                View v = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (v != null && onTapListener != null) {
                    onTapListener.onLongTap(v, recyclerView.getChildLayoutPosition(v));
                }
            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View v = rv.findChildViewUnder(e.getX(), e.getY());
        if (v != null && onTapListener != null && gestureDetector.onTouchEvent(e))
            onTapListener.onTap(v, rv.getChildLayoutPosition(v));
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}



