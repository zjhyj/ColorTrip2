package com.whut.myMap.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class SpeakGridView extends GridView {
	 public boolean hasScrollBar = true;
	 public OnTouchInvalidPositionListener mTouchInvalidPosListener; 
	    /**
	     * @param context
	     */
	    public SpeakGridView(Context context) {
	        this(context, null);
	    }	 
	    public SpeakGridView(Context context, AttributeSet attrs) {
	        super(context, attrs, 0);
	    }	 
	    public SpeakGridView(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	    }
	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {	 
	    	int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);
	    }
	    public interface OnTouchInvalidPositionListener {
	        /**
	         * motionEvent ��ʹ�� MotionEvent.ACTION_DOWN ���� MotionEvent.ACTION_UP��������Ҫ�����ж�
	         * @return �Ƿ�Ҫ��ֹ�¼���·��
	         */
	        boolean onTouchInvalidPosition(int motionEvent);
	      }
	      /**
	       * ����հ�����ʱ����Ӧ�ʹ���ӿ�
	       */
	      public void setOnTouchInvalidPositionListener(OnTouchInvalidPositionListener listener) {
	        mTouchInvalidPosListener = listener;
	      }
	      @Override
	      public boolean onTouchEvent(MotionEvent event) {
	        if(mTouchInvalidPosListener == null) {
	          return super.onTouchEvent(event);
	        }
	        if (!isEnabled()) {
	          // A disabled view that is clickable still consumes the touch
	          // events, it just doesn't respond to them.
	          return isClickable() || isLongClickable();
	        }
	        final int motionPosition = pointToPosition((int)event.getX(), (int)event.getY());
	        if( motionPosition == INVALID_POSITION ) {
	          super.onTouchEvent(event);
	          return mTouchInvalidPosListener.onTouchInvalidPosition(event.getActionMasked());
	        }
	        return super.onTouchEvent(event);
	      }
	}
