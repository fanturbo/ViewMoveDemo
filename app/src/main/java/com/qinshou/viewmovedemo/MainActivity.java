package com.qinshou.viewmovedemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private float lastX, lastY; //最后一次触摸事件的坐标
    TextView textView;
    private RecyclerView rv1;
    private RecyclerView rv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv1 = (RecyclerView) findViewById(R.id.rv1);
        rv2 = (RecyclerView) findViewById(R.id.rv2);
        rv1.setLayoutManager(new GridLayoutManager(this, 4));
        rv2.setLayoutManager(new GridLayoutManager(this, 4));

        textView = (TextView) findViewById(R.id.tv_temp);
        List<String> list = new ArrayList<>();
        list.add("你好1");
        list.add("你好2");
        list.add("你好3");
        list.add("你好4");
        list.add("你好5");
        list.add("你好6");
        list.add("你好7");
        list.add("你好8");
        list.add("你好9");
        list.add("你好10");
        list.add("你好11");
        list.add("你好12");

        rv1.setAdapter(new AnswerAdapter(list));
        rv2.setAdapter(new QuestionAdapter(list));
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
                float nextX = textView.getX() + distanceX;
                float nextY = textView.getY() + distanceY;
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

//                Animation translateAnimation = new TranslateAnimation(getX(), nextX, getY(), nextY);
//                translateAnimation.setDuration(0);
//                 固定属性的设置都是在其属性前加“set”，如setDuration（）
//                startAnimation(translateAnimation);

                ObjectAnimator mObjectAnimatorX = ObjectAnimator.ofFloat(textView, "x", textView.getX(), nextX);
                ObjectAnimator mObjectAnimatorY = ObjectAnimator.ofFloat(textView, "y", textView.getY(), nextY);
                AnimatorSet mAnimatorSet = new AnimatorSet();
                mAnimatorSet.playTogether(mObjectAnimatorX, mObjectAnimatorY);
                mAnimatorSet.setDuration(0);
                mAnimatorSet.start();
                //移动完之后记录当前坐标
                lastX = motionEvent.getRawX();
                lastY = motionEvent.getRawY();
                System.out.println("=========getLeft" + textView.getLeft() + ",getTop" + textView.getTop());
                System.out.println("========X" + textView.getX() + ",Y" + textView.getY());
                System.out.println("=========getTranslationX" + textView.getTranslationX() + ",getTranslationY" + textView.getTranslationY());
                break;
            case MotionEvent.ACTION_UP:
                Button button = (Button) rv2.findChildViewUnder(lastX, lastY).findViewById(R.id.tv_answer);
                button.setText("我是小兵");
                break;
        }
        return false;

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
