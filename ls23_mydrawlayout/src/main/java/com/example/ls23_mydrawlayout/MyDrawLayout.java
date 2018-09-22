package com.example.ls23_mydrawlayout;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/6/30 0030.
 *
 *
 * 摆放
 *
 * 绘制
 *
 * 事件
 */

public class MyDrawLayout extends DrawerLayout  implements DrawerLayout.DrawerListener {
    /**
     * 侧滑控件
     */
    private MyDrawSlideBar myDrawSlideBar;
    private  View contenView;
    /*
     */
    private  float slideOffset;


    private  float y;

    /**
     *
     * @param context
     */
    /**
     *   即装载了  myDrawSlideBar;  背景View
     */
    private  MyDrawBgRealativeLayout myDrawBgRealativeLayout;
    public MyDrawLayout(Context context) {
        super(context);
    }

    public MyDrawLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        for (int i=0;i<getChildCount();i++) {
            //找到 侧滑控件  和内容控件
            View view = getChildAt(i);
            if (view instanceof MyDrawSlideBar) {
                myDrawSlideBar = (MyDrawSlideBar) view;
            } else {
                contenView=view;
            }
        }
        //先移除 myDrawSlideBar      再来添加  RelativeLayout
        removeView(myDrawSlideBar);

        //实例化  背景RealativeLayout
        myDrawBgRealativeLayout=new MyDrawBgRealativeLayout(myDrawSlideBar);
        //偷梁换柱
        addView(myDrawBgRealativeLayout);
        addDrawerListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        y=ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            closeDrawers();
            myDrawSlideBar.onMotionUp();
            return super.dispatchTouchEvent(ev);
        }

        //没有打开之前 不拦截     打开之后拦不拦截  大于1  后  内容区域不再进行偏移
        if (slideOffset < 1) {
            return super.dispatchTouchEvent(ev);
        }else {
            //等于  1
            myDrawBgRealativeLayout.setTouchY(y,slideOffset);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     *
     * @param drawerView
     * @param slideOffset  滑动的百分比   Move
     */
    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        this.slideOffset=slideOffset;
        myDrawBgRealativeLayout.setTouchY(y,slideOffset);
        //针对内容区域进行破偏移
        float contentViewoffset=drawerView.getWidth()*slideOffset/2;
        contenView.setTranslationX(contentViewoffset);

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
