package com.cnxxp.cabbagenet.xrecyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class GridSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    boolean top = false, left = true, right = true, bottom = true;

    public GridSpacesItemDecoration(int space) {
        this.space = space;
    }

    public GridSpacesItemDecoration(int space, boolean top, boolean left, boolean right, boolean bottom) {
        this(space);
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        if (top)
            outRect.top = space;
        if (left)
            outRect.left = space;
        if (right)
            outRect.right = space;
        if (bottom)
            outRect.bottom = space;

    }
}