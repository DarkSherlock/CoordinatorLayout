package com.liang.tind.coordinatorlayoutdemo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    /**
     * 该字段用于 设置图标先滑动80PX 再进行旋转动画。
     */
    public static final int TOP_OFF_SET = 80;

    /**
     * 存放图标的LinearLayout的高度
     */
    private int mHeight;
    /**
     * 存放图标的LinearLayout的宽度
     */
    private int mWidth;

    /**
     * 图标起始相对于父控件的Y轴坐标
     */
    private float mStartYPosition1;
    private float mStartYPosition2;
    private float mStartYPosition3;

    /**
     * 图标相对于父控件的起始X轴坐标
     */
    private float mStartXPosition1;
    private float mStartXPosition3;

    /**
     * 总的X轴滚动值和Y轴滚动值
     */
    private float mScrollX1;
    private float mScrollY1;
    private float mScrollY3;
    private float mScrollX3;
    private int mMaxScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        final LinearLayout ll1 = (LinearLayout) findViewById(R.id.ll1);
        final LinearLayout ll2 = (LinearLayout) findViewById(R.id.ll2);
        final LinearLayout ll3 = (LinearLayout) findViewById(R.id.ll3);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        final CollapsingToolbarLayout collapsing = (CollapsingToolbarLayout) findViewById(R.id.collapsing);

        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        ll3.setOnClickListener(this);


//         AppBar的监听
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //测量一次就够了
                if (mStartYPosition1 == 0) {
                    //Y
                    mStartYPosition1 = (ll1.getY());
                    mStartYPosition2 = (ll2.getY());
                    mStartYPosition3 = (ll3.getY());
                    //X
                    mStartXPosition1 = (ll1.getX());
                    mStartXPosition3 = (ll3.getX());

                    mHeight = (ll.getHeight());
                    mWidth = (ll.getWidth());

                    //第一个图标是从纵轴顶部 横轴中间的位置 最终滚动到 纵轴中间，横轴最左边的位置
                    //分开计算XY轴的位移量
                    mScrollX1 = mStartXPosition1 - 0;
                    mScrollY1 = mHeight / 2 - ll1.getHeight() / 2 - mStartYPosition1;

                    //第二个图标这里保持默认位置不动

                    //第三个图标是从纵轴底部 横轴中间的位置 最终滚动到 纵轴中间，横轴最右边的位置
                    //分开计算XY轴的位移量
                    mScrollX3 = mWidth - mStartXPosition3 - ll3.getWidth();
                    mScrollY3 = mStartYPosition3 - (mHeight / 2 - ll3.getHeight() / 2);
                    //这里设置 滑动TOP_OFF_SET=80的距离后再做位移动画的
                    mMaxScroll = appBarLayout.getTotalScrollRange() - TOP_OFF_SET;
                    Log.e(TAG, "onOffsetChanged: mMaxScroll ==" + mMaxScroll);
                }


                //向下滑动到最底部的时候 verticalOffset = 0，
                //向上滑动到顶部的时候 verticalOffset = -appBarLayout.getTotalScrollRange()；
                //如果mMaxScroll 没有-TOP_OFF_SET话不需要判断verticalOffset >= -mMaxScroll
                if (verticalOffset >= -mMaxScroll) {
                    float percentage = (float) (verticalOffset) / mMaxScroll;
                    ll1.setX(mStartXPosition1 + (percentage * (mScrollX1)));
                    ll1.setY(mStartYPosition1 - (percentage * (mScrollY1)));

                    ll3.setX(mStartXPosition3 - (percentage * (mScrollX3)));
                    ll3.setY(mStartYPosition3 + (percentage * (mScrollY3)));

                    Log.e(TAG, "onOffsetChanged: percentage ==" + percentage);
                }

                //如果mMaxScroll 没有-TOP_OFF_SET话不需要以下判断，此为解决防止快速滑动的时候percentage计算不准确的问题
                if (verticalOffset < -mMaxScroll) {
                    float percentage = -1;
                    ll1.setX(mStartXPosition1 + (percentage * (mScrollX1)));
                    ll1.setY(mStartYPosition1 - (percentage * (mScrollY1)));

                    ll3.setX(mStartXPosition3 - (percentage * (mScrollX3)));
                    ll3.setY(mStartYPosition3 + (percentage * (mScrollY3)));

                    Log.e(TAG, "on verticalOffset == mTotalScrollRange: " );
                }
                //如果mMaxScroll 没有-TOP_OFF_SET的话不需要以下判断，此为解决防止快速滑动的时候percentage计算不准确的问题
                if (verticalOffset == 0) {
                    float percentage = 0;
                    ll1.setX(mStartXPosition1 + (percentage * (mScrollX1)));
                    ll1.setY(mStartYPosition1 - (percentage * (mScrollY1)));

                    ll3.setX(mStartXPosition3 - (percentage * (mScrollX3)));
                    ll3.setY(mStartYPosition3 + (percentage * (mScrollY3)));

                    Log.e(TAG, "on verticalOffset == 0: " );
                }

                Log.e(TAG, "onOffsetChanged: verticalOffset ==" + verticalOffset);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll1:
                Toast.makeText(this, "点击了第1个图标", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll2:
                Toast.makeText(this, "点击了第2个图标", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll3:
                Toast.makeText(this, "点击了第3个图标", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
