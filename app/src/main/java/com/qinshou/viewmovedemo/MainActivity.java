package com.qinshou.viewmovedemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
                //记录触摸时的坐标了
                lastX = motionEvent.getRawX();
                lastY = motionEvent.getRawY();
                float downX = motionEvent.getRawX();
                float downY = motionEvent.getRawY() - rv1.getY();
                //寻找按下的题目答案item
                View selectItemView = rv1.findChildViewUnder(downX, downY);
                if (selectItemView == null) {
                    selectItemView = rv1.findChildViewUnder(downX + textView.getWidth(), downY);
                }
                if (selectItemView == null) {
                    selectItemView = rv1.findChildViewUnder(downX, downY + textView.getHeight());
                }
                if (selectItemView == null) {
                    selectItemView = rv1.findChildViewUnder(downX + textView.getWidth(), downY + textView.getHeight());
                }
                if (selectItemView != null) {
                    Button button = (Button) selectItemView.findViewById(R.id.tv_answer);
                    ObjectAnimator mObjectAnimatorX = ObjectAnimator.ofFloat(textView, "x", textView.getX(), downX);
                    ObjectAnimator mObjectAnimatorY = ObjectAnimator.ofFloat(textView, "y", textView.getY(), downY);
                    AnimatorSet mAnimatorSet = new AnimatorSet();
                    mAnimatorSet.playTogether(mObjectAnimatorX, mObjectAnimatorY);
                    mAnimatorSet.setDuration(0);
                    mAnimatorSet.start();
                    textView.setText("我在这儿" + button.getText());
                    textView.setVisibility(View.VISIBLE);
                }
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
                //通过属性动画移动TextView
                ObjectAnimator mObjectAnimatorX = ObjectAnimator.ofFloat(textView, "x", textView.getX(), nextX);
                ObjectAnimator mObjectAnimatorY = ObjectAnimator.ofFloat(textView, "y", textView.getY(), nextY);
                AnimatorSet mAnimatorSet = new AnimatorSet();
                mAnimatorSet.playTogether(mObjectAnimatorX, mObjectAnimatorY);
                mAnimatorSet.setDuration(0);
                mAnimatorSet.start();
                //移动完之后记录当前坐标
                lastX = motionEvent.getRawX();
                lastY = motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                //textview必须在显示的时候才会给问题RV的item设置答案
                if (textView.getVisibility() == View.VISIBLE) {
                    float x = textView.getX();
                    float y = textView.getY() - rv2.getY();
                    View itemView = rv2.findChildViewUnder(x, y);
                    //寻找手指按起时所在的问题RV的item
                    if (itemView == null) {
                        itemView = rv2.findChildViewUnder(x + textView.getWidth(), y);
                    }
                    if (itemView == null) {
                        itemView = rv2.findChildViewUnder(x, y + textView.getHeight());
                    }
                    if (itemView == null) {
                        itemView = rv2.findChildViewUnder(x + textView.getWidth(), y + textView.getHeight());
                    }
                    if (itemView != null) {
                        Button button = (Button) itemView.findViewById(R.id.tv_answer);
                        button.setText(textView.getText());
                    }
                    //TextView销毁掉
                    textView.setVisibility(View.INVISIBLE);
                }
                break;
        }
        return false;

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
