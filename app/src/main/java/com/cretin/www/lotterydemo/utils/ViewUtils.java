package com.cretin.www.lotterydemo.utils;

import android.content.Context;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by cretin on 16/8/8.
 */
public class ViewUtils {
    //状态栏的高度
    public static int mStatusBarHeight;
    //屏幕高度
    public static int mWindowHeight;
    //屏幕宽度
    public static int mWindowWidth;

    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeights(Context context) {
        if ( mStatusBarHeight == 0 ) {
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                mStatusBarHeight = context.getResources().getDimensionPixelSize(x);
            } catch ( Exception e1 ) {
                e1.printStackTrace();
            }
        }
        return mStatusBarHeight;
    }

    private static TextView tv;

    /**
     * 获取屏幕的缩放级别
     *
     * @param context
     * @return
     */
    public static float getScreenScale(Context context) {
        if ( tv == null ) {
            tv = new TextView(context);
            tv.setTextSize(1);
        }
        return tv.getTextSize();
    }

}
