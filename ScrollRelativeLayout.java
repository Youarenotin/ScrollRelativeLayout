package com.tianyue.lovelinkurobot.view.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AnticipateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/** * @author  作者:Lubo E-mail:lubo_wen@126.com 
 * @date 创建时间：2016年6月24日 下午5:02:00 
 * @version 1.0
 * @parameter  
 * @since  
 * @return  
 */
public class ScrollRelativeLayout extends RelativeLayout {
    private Context ct;
    private boolean isMove;

    public ScrollRelativeLayout(Context context) {
        super(context);
        ct = context;
        if (ct != null)
            System.out.println("ct not null");
        initView();
    }

    public ScrollRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ct = context;
        initView();
    }

    public ScrollRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ct = context;
        initView();
    }

    private void initView() {
        AnticipateInterpolator  interpolator = new AnticipateInterpolator();
        mScroller = new Scroller(ct, interpolator);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
            isMove = true;
        } else {
            isMove = false;
        }
        super.computeScroll();
    }


    /**
     * 点击时候Y的坐标
     */
    private int downY = 0;
    /**
     * 拖动时候Y的坐标
     */
    private int moveY = 0;
    /**
     * 松开时候Y的坐标
     */
    private int upY = 0;
    /**
     * 拖动时候Y的方向距离
     */
    private int scrollY = 0;

    private Scroller mScroller;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int offViewY = 0;//屏幕顶部和该布局顶部的距离

        if (!isMove) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                     System.out.println("down");
                    downY = (int) event.getRawY();
                    return true;
                case MotionEvent.ACTION_MOVE:
                     System.out.println("move");
                    moveY = (int) event.getRawY();
                    scrollY = moveY - downY;
//                    System.out.println(scrollY);
                    if (scrollY > 0) {/**向下拖动*/
                        scrollTo(0, -scrollY / 2);
                    } else {/**向上拖动*/
                        scrollTo(0, -scrollY / 2);
                    }
                    break;
                case MotionEvent.ACTION_UP:
//                    System.out.println("up");
                    upY = (int) event.getRawY();
                    if (downY-upY > 0) {/**向上滑动*/
                        startAnimation(-scrollY/2, scrollY/2, 500);
                    } else/**向下滑动*/ {
                        startAnimation(-scrollY/2, scrollY/2, 500);
                    }
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    int down1Y ;
    int move1Y;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down1Y = (int) ev.getRawY();
                System.out.println("down1Y");
                break;
            case MotionEvent.ACTION_MOVE:
                move1Y = (int) ev.getRawY();
                System.out.println("move1");
                if (Math.abs(move1Y - down1Y) > 10) {
                    downY=down1Y;
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void startAnimation(int startY, int dy, int duration) {
        isMove = true;
        mScroller.startScroll(0, startY, 0, dy, duration);
        invalidate();
    }
}
