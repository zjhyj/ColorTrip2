package com.whut.myMap.layout;

import android.R.integer;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ResizableImageView extends ImageView {  
	  
    public ResizableImageView(Context context) {  
        super(context);  
    }  
  
    public ResizableImageView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){  
        Drawable d = getDrawable();  
  
        if(d!=null){  
            // ceil not round - avoid thin vertical gaps along the left/right edges 
        	int maxheight=1200;
            int width = MeasureSpec.getSize(widthMeasureSpec);  
            //高度根据使得图片的宽度充满屏幕计算而得  
            int height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());  
            if (height<maxheight) {
            	setMeasuredDimension(width, height);  
			} else {
				setMeasuredDimension(width, maxheight);  
			}
        }else{  
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
        }  
    }  
  
}  