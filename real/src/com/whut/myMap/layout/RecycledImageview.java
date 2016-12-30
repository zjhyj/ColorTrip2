package com.whut.myMap.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RecycledImageview extends ImageView{

	 public RecycledImageview (Context context, AttributeSet attrs) {  
		          super(context, attrs);  
		      }  
		  
	  
	    @Override  
	    protected void onDraw(Canvas canvas) {  
	        try {  
	            super.onDraw(canvas);  
	        } catch (Exception e) {  
	            System.out.println("trying to use a recycled bitmap");  
	        }  
	    }

}
