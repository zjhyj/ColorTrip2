package com.whut.myMap.utils;
import android.R.integer;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
public class MyOrientationListener implements SensorEventListener {
	 private SensorManager mSensorManager;
	     private Context mContext;
	     //传感器
	     private Sensor mSensor;
	    private float lastX;
	    private OnOrientationListener mOnOrientationListener;
	     //构造方法
       public MyOrientationListener(Context context)
	    {
	        this.mContext = context;
	    }
       @SuppressWarnings("deprecation")
           //开始监听
            public void start()
           {
            //拿到系统服务
              mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
              if (mSensorManager != null)
             {
                 // 获得方向传感器
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
             }
    
            if (mSensor != null)
            {
                 mSensorManager.registerListener(this, mSensor,
                          SensorManager.SENSOR_DELAY_FASTEST);
             }
         }
     //结束监听
         public void stop()
      {
            mSensorManager.unregisterListener(this);
       }
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		int sensortype=event.sensor.getType();
		switch(sensortype){
		case  Sensor.TYPE_ORIENTATION:
		            float x = event.values[0]-180;		            
			         if (Math.abs(x - lastX) > 0.5)
		            {
		                if (mOnOrientationListener != null)
		                 {
		                   mOnOrientationListener.onOrientationChanged(x,lastX);
		                }
		            }
			             lastX = x;
			             break;
		        }
	}
	 public void setOnOrientationListener( OnOrientationListener mOnOrientationListener)
		{
			    this.mOnOrientationListener =  mOnOrientationListener;
	}			 
   public interface OnOrientationListener
		    {
		         void onOrientationChanged(float x, float lastX);
		    }
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

}
