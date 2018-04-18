package com.cretin.www.lotterydemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cretin.www.lotterydemo.adapter.CommonAdapter;
import com.cretin.www.lotterydemo.adapter.ViewHolder;
import com.cretin.www.lotterydemo.utils.ViewUtils;
import com.cretin.www.lotterydemo.view.LoopView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    /**
     * 如果说你的数据源是固定的
     * 比如数据如下
     */
    private String[] source1 = new String[]{"83", "34", "21", "43", "23", "53", "30", "32", "14", "98", "25"};

    /**
     * 如果说你的数据源不是固定的
     * 初始数据如下
     */
    private String[] source2 = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"};

    private ListView listView;
    //适配器
    private Adapter adapter;
    //数据源
    private List<String> list;

    //listview的高度
    private int listViewHeight;

    //listview item 项的高度
    private int itemViewHeight;

    //自定义view
    private LoopView loopview;

    //开始按钮
    private ImageView ivGo;

    //是否是第一次进来
    private boolean firstIn = true;

    private int currIndex;

    //此类型测试数据是固定的
    private int CURRTYPE = 0;
    //此类型测试数据是不固定的
//    private int CURRTYPE = 1;


    //产生随机数
    private Random mRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mRandom = new Random();

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            findViewById(R.id.tv_cj).setPadding(0, ViewUtils.getStatusBarHeights(this), 0, 0);
        }

        listView = findViewById(R.id.listview);
        loopview = findViewById(R.id.loopview);
        ivGo = findViewById(R.id.iv_go);

        list = new ArrayList<>();

        if ( CURRTYPE == 0 ) {
            for ( int i = 0; i < source1.length; i++ ) {
                list.add(source1[i]);
            }
        } else {
            for ( int i = 0; i < source2.length; i++ ) {
                list.add(source2[i]);
            }
        }

        adapter = new Adapter(this, list, R.layout.item_test);
        listView.setAdapter(adapter);

        ViewTreeObserver vto = listView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi( Build.VERSION_CODES.KITKAT )
            @Override
            public void onGlobalLayout() {
                listView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                listViewHeight = listView.getMeasuredHeight();
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 200);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if ( scrollState == SCROLL_STATE_IDLE ) {
                    if ( firstIn ) {
                        firstIn = false;
                        return;
                    }
                    Toast.makeText(MainActivity.this, "结果为：" + list.get(currIndex), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        //点击GO 这个是随机的
        ivGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这个随机数指的是下次到的位置
                int random = 0;

                if ( currIndex == 0 )
                    currIndex = 2;
                list = list.subList(0, currIndex + 1);

                if ( CURRTYPE == 0 ) {
                    random = mRandom.nextInt(source1.length - 1) + source1.length;
                    //如果是固定数据 在这里添加这些数据
                    for ( int i = 0; i < source1.length; i++ ) {
                        list.add(source1[i]);
                    }
                    for ( int i = 0; i < source1.length; i++ ) {
                        list.add(source1[i]);
                    }
                } else {
                    //如果是非固定数据 在这里添加这些数据
                    //本次添加的数据条数
                    int num = mRandom.nextInt(9) + 20;
                    random = mRandom.nextInt(num - 1) + 1;
                    for ( int i = 0; i < num; i++ ) {
                        list.add((mRandom.nextInt(90) + 10) + "");
                    }
                }

                currIndex = currIndex + random + 1;
                adapter.notifyDataSetChanged();
                doit(true, currIndex, 5);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            doit(false, 1, 1);
        }
    };

    //执行
    public void doit(boolean loop, int aimPosition, float time) {
        int firstPosition = listView.getFirstVisiblePosition();
        int top = 0;
        if ( listView.getChildAt(0) != null )
            top = listView.getChildAt(0).getTop();

        int needScrollPostion = aimPosition - firstPosition;
        float f = itemViewHeight / 2 + listViewHeight / 2;
        int distance = ( int ) (needScrollPostion * itemViewHeight + (itemViewHeight - Math.abs(top)) - f);
        if ( loop ) {
            listView.smoothScrollBy(distance, ( int ) (time * 1000));
            loopview.start(time);
        } else {
            listView.smoothScrollBy(distance, 10);
        }
    }

    class Adapter extends CommonAdapter<String> {

        public Adapter(Context context, List<String> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, String item, int position) {
            holder.setText(R.id.tv_01, item);

            if ( itemViewHeight == 0 ) {
                final View convertView = holder.getConvertView();
                ViewTreeObserver vto = convertView.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @TargetApi( Build.VERSION_CODES.KITKAT )
                    @Override
                    public void onGlobalLayout() {
                        convertView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        itemViewHeight = convertView.getMeasuredHeight();
                    }
                });
            }
        }
    }
}
