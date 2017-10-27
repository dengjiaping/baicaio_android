package com.cnxxp.cabbagenet.xrecyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class ListSpacesItemDecoration extends RecyclerView.ItemDecoration {
	private int space;

	public ListSpacesItemDecoration(int space) {
		this.space = space;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		outRect.bottom = space;

	}
}