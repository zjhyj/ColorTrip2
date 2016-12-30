package com.whut.myMap.utils;

import com.whut.myMap.adapter.SpeakGridView;
import com.whut.myMap.adapter.SpeakGridViewAdapter;

import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

public final class GridViewUtils {
	 /**
     * �洢���
     */
    static SparseIntArray mGvWidth = new SparseIntArray();
 
    /**
     * ����GridView�ĸ߶�
     * 
     * @param gridView Ҫ�����GridView
     */
    public static void updateGridViewLayoutParams(SpeakGridView gridView, int maxColumn) {
        int childs = gridView.getAdapter().getCount();
 
        if (childs > 0) {
            int columns = childs < maxColumn ? childs % maxColumn : maxColumn;
            gridView.setNumColumns(columns);
            int width = 0;
            int cacheWidth = mGvWidth.get(columns);
            if (cacheWidth != 0) {
                width = cacheWidth;
            } else { // ����gridviewÿ�еĿ��, ���itemС��3���������item�Ŀ��;
                     // ����ֻ����3��child��ȣ����һ�����3��child�� (����������3Ϊ��)
                int rowCounts = childs < maxColumn ? childs : maxColumn;
                for (int i = 0; i < rowCounts; i++) {
                    View childView = gridView.getAdapter().getView(i, null, gridView);
                    childView.measure(0, 0);
                    width += childView.getMeasuredWidth();
                }
            }
 
            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            params.width = width;
            gridView.setLayoutParams(params);
            if (mGvWidth.get(columns) == 0) {
                mGvWidth.append(columns, width);
            }
        }
    }
}