package com.qinshou.viewmovedemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/**
 * Created by tubro on 2018/4/27.
 */

public class MoveView extends AppCompatButton {

    private float lastX, lastY; //最后一次触摸事件的坐标

    public MoveView(Context context) {
        super(context);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录触摸时的坐标,这里为什么要用getRawX()和getRawY()相信理解getX(),getY()和getRawX(),getRawY()的区别就知道为什么了
                lastX = motionEvent.getRawX();
                lastY = motionEvent.getRawY();
                //return true对事件进行拦截,不继续下发,防止继续响应onClick事件.
                return true;
            case MotionEvent.ACTION_MOVE:
                //每次移动的距离
                float distanceX = motionEvent.getRawX() - lastX;
                float distanceY = motionEvent.getRawY() - lastY;

                //控件将要移动到的位置,先计算一下,不在ofFloat()方法中再计算是因为要防止控件移动到容器之外.
                float nextX = getX() + distanceX;
                float nextY = getY() + distanceY;
                //如果将要移动到的 x 轴坐标小于0,则等于0,防止移出容器左边
                if (nextX < 0)
                    nextX = 0;
                //防止移出容器右边
//                if (nextX > containerWidth - getWidth())
//                    nextX = containerWidth - getWidth();
                //防止移出容器顶边
                if (nextY < 0)
                    nextY = 0;
                //防止移出容器底边
//                if (nextY > containerHeight - getHeight())
//                    nextY = containerHeight - getHeight();
                //利用属性动画改变控件的x,y坐标
                TextView textView = new TextView(getContext());
                textView.setText("啊啊啊啊啊啊");

//                Animation translateAnimation = new TranslateAnimation(getX(), nextX, getY(), nextY);
//                translateAnimation.setDuration(0);
//                 固定属性的设置都是在其属性前加“set”，如setDuration（）
//                startAnimation(translateAnimation);

                ObjectAnimator mObjectAnimatorX = ObjectAnimator.ofFloat(this, "x", getX(), nextX);
                ObjectAnimator mObjectAnimatorY = ObjectAnimator.ofFloat(this, "y", getY(), nextY);
                AnimatorSet mAnimatorSet = new AnimatorSet();
                mAnimatorSet.playTogether(mObjectAnimatorX, mObjectAnimatorY);
                mAnimatorSet.setDuration(0);
                mAnimatorSet.start();
                //移动完之后记录当前坐标
                lastX = motionEvent.getRawX();
                lastY = motionEvent.getRawY();
                System.out.println("getLeft" + getLeft() + ",getTop" + getTop());
                System.out.println("X" + getX() + ",Y" + getY());
                System.out.println("getTranslationX" + getTranslationX() + ",getTranslationY" + getTranslationY());
                break;
        }
        return false;
    }
}
