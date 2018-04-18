package com.cretin.www.lotterydemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Spannable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ViewHolder {
    public static int mPosition;
    //类似hashMap
    private SparseArray<View> mViews;
    private View mConvertView;

    private Context context;

    public ViewHolder(Context context, ViewGroup parent, int layoutId,
                      int position) {
        mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
        this.context = context;
    }

    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            mPosition = position;
            return holder;
        }
    }

    /**
     * 通过viewId获取view
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 获取convertView
     *
     * @return
     */
    public View getConvertView() {
        return mConvertView;
    }

    /*
     * 设置字符串设置字符串
     *
     * @args viewId
     *
     * @args text
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(text);
        }
        return this;
    }

    /*
     * 设置字符串设置字符串
     *
     * @args viewId
     *
     * @args text
     */
    public ViewHolder setText(int viewId, Spannable spanable) {
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(spanable);
        }
        return this;
    }

    /**
     * 设置图片资源
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    /**
     * 设置背景资源
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setImageBackgroundResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setBackgroundResource(resId);
        return this;
    }

    /**
     * 设置背景资源
     *
     * @param viewId
     * @return
     */
    @SuppressLint("NewApi")
    public ViewHolder setImageBackground(int viewId, Drawable background) {
        ImageView iv = getView(viewId);
        iv.setBackgroundDrawable(background);
        return this;
    }

    /**
     * 设置图片资源
     *
     * @param viewId
     * @param bm
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bm);
        return this;
    }

    /**
     * @param viewId
     * @param uri
     * @return
     */
    public ViewHolder setImageUri(int viewId, Uri uri) {
        ImageView iv = getView(viewId);
        iv.setImageURI(uri);
        return this;
    }

    /**
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageUrl(int viewId, String url, int w, int h) {
        ImageView iv = getView(viewId);
        // Picasso.with(context).load(url).resize(w,h).centerCrop().into(iv);
        return this;
    }

    /**
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageBigUrl(int viewId, String url) {
        final ImageView iv = getView(viewId);
        // Picasso.with(context).load(url).into(iv);
        return this;
    }

    /**
     * 设置CheckBox
     *
     * @param viewId
     * @param isChecked
     * @return
     */
    public ViewHolder setChecked(int viewId, boolean isChecked) {
        CheckBox cb = getView(viewId);
        cb.setChecked(isChecked);
        return this;
    }
}
