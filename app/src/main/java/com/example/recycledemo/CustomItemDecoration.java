package com.example.recycledemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewManager;

import java.util.List;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;
    private List<Hero> heroList;
    private String tagsStr;

    private Paint mPaint;
    private final Rect mBounds = new Rect();
    private static final int dividerHeight = 80;

    public CustomItemDecoration(Context context) {
        mContext = context;

        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 抗锯齿
        mPaint.setDither(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setDatas(List<Hero> list, String tagStr) {
        this.heroList = list;
        this.tagsStr = tagStr;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }

        c.save();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (heroList == null || heroList.size() == 0 || heroList.size() <= position || position < 0) {
                continue;
            }
            if (position == 0) {
                // 第一条数据有bar
                drawTitleBar(c, parent, child, heroList.get(position), tagsStr.indexOf(heroList.get(position).getIndexTag()));
            } else if (position > 0) {
                if (TextUtils.isEmpty(heroList.get(position).getIndexTag())) {
                    return;
                }

                // 与上一条数据中的tag不同时，该显示bar了
                if (!heroList.get(position).getIndexTag().equals(heroList.get(position-1).getIndexTag())) {
                    drawTitleBar(c, parent, child, heroList.get(position), tagsStr.indexOf(heroList.get(position).getIndexTag()));
                }
            }
        }
        c.restore();
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // 用来绘制悬浮窗
        int position = ((LinearLayoutManager)(parent.getLayoutManager())).findFirstVisibleItemPosition();
        if (heroList == null || heroList.size() == 0 || heroList.size() <= position || position < 0) {
            return;
        }

        final int bottom = parent.getPaddingTop() + dividerHeight;
        mPaint.setColor(0xff67bf74);
        c.drawRect(parent.getLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + dividerHeight, mPaint);
        mPaint.setTextSize(40);
        mPaint.setColor(Color.WHITE);
        c.drawText(heroList.get(position).getIndexTag(), 100, bottom - dividerHeight/3, mPaint);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        if (heroList == null || heroList.size() == 0 || heroList.size() <= position || position < 0) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }

        if (position == 0) {
            // 第一条数据有bar
            outRect.set(0, dividerHeight, 0, 0);
        } else if (position > 0) {
            if (TextUtils.isEmpty(heroList.get(position).getIndexTag())) return;

            // 与上一条数据的tag不同，该显示bar了
            if (!heroList.get(position).getIndexTag().equals(heroList.get(position-1).getIndexTag())) {
                outRect.set(0, dividerHeight, 0, 0);
            }
        }

    }

    /**
     * 绘制bar
     *
     * @param canvas
     * @param parent
     * @param child
     * @param hero
     * @param position
     */
    private void drawTitleBar(Canvas canvas, RecyclerView parent, View child, Hero hero, int position) {
        // 获取每个Item的位置

        // 设置矩形(分割线)的宽度为10px

        mPaint.setColor(Color.BLACK);
        // 矩形左上顶点 = (ItemView的左边界,ItemView的下边界)
        final int left = child.getLeft();
        final int top = child.getTop() - dividerHeight;
        // 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
        final int right = child.getRight();
        final int bottom = top + dividerHeight;
        // 通过Canvas绘制矩形（分割线）
        canvas.drawRect(left, top, right, bottom, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawRect(left, top, right, bottom, mPaint);
        mPaint.setTextSize(40);
        mPaint.setColor(Color.BLACK);
        canvas.drawText(heroList.get(position).getIndexTag(), 100, bottom - dividerHeight/3, mPaint);

    }
}
