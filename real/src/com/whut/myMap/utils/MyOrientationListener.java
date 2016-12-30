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
	     //������
	     private Sensor mSensor;
	    private float lastX;
	    private OnOrientationListener mOnOrientationListener;
	     //���췽��
       public MyOrientationListener(Context context)
	    {
	        this.mContext = context;
	    }
       @SuppressWarnings("deprecation")
           //��ʼ����
            public void start()
           {
            //�õ�ϵͳ����
              mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
              if (mSensorManager != null)
             {
                 // ��÷��򴫸���
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
             }
    
            if (mSensor != null)
            {
                 mSensorManager.registerListener(this, mSensor,
                          SensorManager.SENSOR_DELAY_FASTEST);
             }
         }
     //��������
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