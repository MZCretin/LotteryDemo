package com.cretin.www.lotterydemo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cretin.www.lotterydemo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cretin on 2018/4/18.
 */

public class LoopView extends RelativeLayout {
    private ImageView lineImageView;

    public LoopView(Context context) {
        super(context);
        init(context);
    }

    public LoopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public LoopView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    //存储最下面一层的ImageView
    private ImageView[] imageViews = new ImageView[8];
    //存储第二层的ImageView
    private ImageView[] imageViews1 = new ImageView[8];
    //存储第最上面层的ImageView
    private ImageView[] imageViews11 = new ImageView[8];

    //初始化操作
    private void init(Context context) {
        //我们一次性将所需要的18个ImageView都添加进去 通过VISIABLE和INVISIABLE来显示可隐藏视图
        // 防止频繁的设置资源降低性能
        View view = View.inflate(context, R.layout.layout_loopview, LoopView.this);

        //最下面一层的ImageView
        imageViews[0] = view.findViewById(R.id.iv_01);
        imageViews[1] = view.findViewById(R.id.iv_02);
        imageViews[2] = view.findViewById(R.id.iv_03);
        imageViews[3] = view.findViewById(R.id.iv_04);
        imageViews[4] = view.findViewById(R.id.iv_05);
        imageViews[5] = view.findViewById(R.id.iv_06);
        imageViews[6] = view.findViewById(R.id.iv_07);
        imageViews[7] = view.findViewById(R.id.iv_08);
        //存储第二层的ImageView
        imageViews1[0] = view.findViewById(R.id.iv_011);
        imageViews1[1] = view.findViewById(R.id.iv_022);
        imageViews1[2] = view.findViewById(R.id.iv_033);
        imageViews1[3] = view.findViewById(R.id.iv_044);
        imageViews1[4] = view.findViewById(R.id.iv_055);
        imageViews1[5] = view.findViewById(R.id.iv_066);
        imageViews1[6] = view.findViewById(R.id.iv_077);
        imageViews1[7] = view.findViewById(R.id.iv_088);
        //存储第最上面层的ImageView
        imageViews11[0] = view.findViewById(R.id.iv_0111);
        imageViews11[1] = view.findViewById(R.id.iv_0222);
        imageViews11[2] = view.findViewById(R.id.iv_0333);
        imageViews11[3] = view.findViewById(R.id.iv_0444);
        imageViews11[4] = view.findViewById(R.id.iv_0555);
        imageViews11[5] = view.findViewById(R.id.iv_0666);
        imageViews11[6] = view.findViewById(R.id.iv_0777);
        imageViews11[7] = view.findViewById(R.id.iv_0888);

        lineImageView = view.findViewById(R.id.iv_line);
    }

    //记录当前视图的宽度
    private int width;
    //记录当前圆的半径
    private float radius;
    //记录当前imageview的宽度
    private int imageWidth;
    private int imageHeight;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if ( width == 0 || imageHeight == 0 ) {
            //获取宽度
            width = right - left;
            //获取圆的半径
            radius = ( float ) width / 2;

            //计算小imageview的大小
            ViewTreeObserver vto = imageViews[0].getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @TargetApi( Build.VERSION_CODES.KITKAT )
                @Override
                public void onGlobalLayout() {
                    imageViews[0].getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    imageHeight = imageViews[0].getMeasuredHeight();
                    imageWidth = imageViews[0].getMeasuredWidth();

                    //摆放位置
                    draw();
                }
            });
        }
    }

    //设置球的位置
    private void draw() {
        //设置最外面的圈
        lineImageView.setPadding(( int ) (imageWidth / 2), ( int ) (imageHeight / 2), ( int ) (imageWidth / 2), ( int ) (imageHeight / 2));

        //先处理比较简单的上下左右
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imageViews[0].setLayoutParams(layoutParams);
        imageViews1[0].setLayoutParams(layoutParams);
        imageViews11[0].setLayoutParams(layoutParams);

        layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        imageViews[2].setLayoutParams(layoutParams);
        imageViews1[2].setLayoutParams(layoutParams);
        imageViews11[2].setLayoutParams(layoutParams);

        layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imageViews[4].setLayoutParams(layoutParams);
        imageViews1[4].setLayoutParams(layoutParams);
        imageViews11[4].setLayoutParams(layoutParams);

        layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        imageViews[6].setLayoutParams(layoutParams);
        imageViews1[6].setLayoutParams(layoutParams);
        imageViews11[6].setLayoutParams(layoutParams);

        //计算并设置四个角落的球  至于这些是怎么来的 需要要自己算了  我算了一张草稿纸
        double lP = radius * Math.sin(Math.PI / 4);
        float l1 = ( float ) (radius - lP);
        imageViews[7].setX(( float ) (l1 - imageWidth / 2 + (Math.sin(Math.PI / 4)) * imageWidth / 2));
        imageViews[7].setY(( float ) (l1 - imageHeight / 2 + (Math.sin(Math.PI / 4)) * imageHeight / 2));
        imageViews1[7].setX(( float ) (l1 - imageWidth / 2 + (Math.sin(Math.PI / 4)) * imageWidth / 2));
        imageViews1[7].setY(( float ) (l1 - imageHeight / 2 + (Math.sin(Math.PI / 4)) * imageHeight / 2));
        imageViews11[7].setX(( float ) (l1 - imageWidth / 2 + (Math.sin(Math.PI / 4)) * imageWidth / 2));
        imageViews11[7].setY(( float ) (l1 - imageHeight / 2 + (Math.sin(Math.PI / 4)) * imageHeight / 2));

        float l3 = ( float ) (radius + lP);
        imageViews[1].setX(( float ) (l3 - imageWidth / 2 - (Math.sin(Math.PI / 4)) * imageWidth / 2));
        imageViews[1].setY(( float ) (l1 - imageHeight / 2 + (Math.sin(Math.PI / 4)) * imageHeight / 2));
        imageViews1[1].setX(( float ) (l3 - imageWidth / 2 - (Math.sin(Math.PI / 4)) * imageWidth / 2));
        imageViews1[1].setY(( float ) (l1 - imageHeight / 2 + (Math.sin(Math.PI / 4)) * imageHeight / 2));
        imageViews11[1].setX(( float ) (l3 - imageWidth / 2 - (Math.sin(Math.PI / 4)) * imageWidth / 2));
        imageViews11[1].setY(( float ) (l1 - imageHeight / 2 + (Math.sin(Math.PI / 4)) * imageHeight / 2));

        imageViews[3].setX(( float ) (l3 - imageWidth / 2 - (Math.sin(Math.PI / 4)) * imageWidth / 2));
        imageViews[3].setY(( float ) (l3 - imageHeight / 2 - (Math.sin(Math.PI / 4)) * imageHeight / 2));
        imageViews1[3].setX(( float ) (l3 - imageWidth / 2 - (Math.sin(Math.PI / 4)) * imageWidth / 2));
        imageViews1[3].setY(( float ) (l3 - imageHeight / 2 - (Math.sin(Math.PI / 4)) * imageHeight / 2));
        imageViews11[3].setX(( float ) (l3 - imageWidth / 2 - (Math.sin(Math.PI / 4)) * imageWidth / 2));
        imageViews11[3].setY(( float ) (l3 - imageHeight / 2 - (Math.sin(Math.PI / 4)) * imageHeight / 2));

        imageViews[5].setX(( float ) (l1 - imageWidth / 2 + (Math.sin(Math.PI / 4)) * imageWidth / 2));
        imageViews[5].setY(( float ) (l3 - imageHeight / 2 - (Math.sin(Math.PI / 4)) * imageHeight / 2));
        imageViews1[5].setX(( float ) (l1 - imageWidth / 2 + (Math.sin(Math.PI / 4)) * imageWidth / 2));
        imageViews1[5].setY(( float ) (l3 - imageHeight / 2 - (Math.sin(Math.PI / 4)) * imageHeight / 2));
        imageViews11[5].setX(( float ) (l1 - imageWidth / 2 + (Math.sin(Math.PI / 4)) * imageWidth / 2));
        imageViews11[5].setY(( float ) (l3 - imageHeight / 2 - (Math.sin(Math.PI / 4)) * imageHeight / 2));
    }

    int index1 = 1;
    int index = 1;
    private float all = 100;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //这里是写的一个简单的加速 减速  匀速的算法  如果觉得不好 可以自己修改

            if ( index < all ) {
                if ( index % 11 == 0 ) {
                    setImageResource(index1 % 8, (index1 - 1) % 8);
                }
            } else if ( index < all * 2 ) {
                if ( index % 10 == 0 ) {
                    setImageResource(index1 % 8, (index1 - 1) % 8);
                }
            } else if ( index < all * 3 ) {
                if ( index % 8 == 0 ) {
                    setImageResource(index1 % 8, (index1 - 1) % 8);
                }
            } else if ( index < all * 4 ) {
                if ( index % 5 == 0 ) {
                    setImageResource(index1 % 8, (index1 - 1) % 8);
                }
            } else if ( index < all * 5 ) {
                if ( index % 3 == 0 ) {
                    setImageResource(index1 % 8, (index1 - 1) % 8);
                }
            } else if ( index < all * 6 ) {
                if ( index % 2 == 0 ) {
                    setImageResource(index1 % 8, (index1 - 1) % 8);
                }
            } else if ( index < all * 7 ) {
                if ( index % 3 == 0 ) {
                    setImageResource(index1 % 8, (index1 - 1) % 8);
                }
            } else if ( index < all * 8 ) {
                if ( index % 5 == 0 ) {
                    setImageResource(index1 % 8, (index1 - 1) % 8);
                }
            } else if ( index < all * 9 ) {
                if ( index % 8 == 0 ) {
                    setImageResource(index1 % 8, (index1 - 1) % 8);
                }
            } else if ( index < all * 10 ) {
                if ( index % 10 == 0 ) {
                    setImageResource(index1 % 8, (index1 - 1) % 8);
                }
            } else if ( index < all * 11 ) {
                if ( index % 11 == 0 ) {
                    setImageResource(index1 % 8, (index1 - 1) % 8);
                }
            } else if ( index < all * 18 ) {
                if ( index % 14 == 0 ) {
                    setImageResource(index1 % 8, (index1 - 1) % 8);
                }
            } else {
                if ( timer != null ) {
                    timer.cancel();
                    timer = null;
                }
            }
            index++;
        }
    };
    private Timer timer;

    //所有的旋转时间
    private float allTime;

    //闪亮吧骚年
    public void start(float time) {
        allTime = time;
        index = 1;
        index1 = 1;
        //计算每一个区分块的大小
        all = ( float ) ((allTime * 200 * 0.9) / 18);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 0, 5);
    }

    //设置资源
    private void setImageResource(int start, int second) {
        //全部隐藏第二层和第三层的视图
        for ( int i = 0; i < 8; i++ ) {
            imageViews1[i].setVisibility(INVISIBLE);
            imageViews11[i].setVisibility(INVISIBLE);
        }

        //显示需要展示的视图的图层
        imageViews1[start].setVisibility(VISIBLE);
        imageViews11[second].setVisibility(VISIBLE);
        //进度加1
        index1++;
    }
}
