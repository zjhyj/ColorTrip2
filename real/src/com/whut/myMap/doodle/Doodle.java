package com.whut.myMap.doodle;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class Doodle extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder mSurfaceHolder = null;

	//��ǰ��ѡ���ʵ���״
	private Action curAction = null;
	//Ĭ�ϻ���Ϊ��ɫ
	private int currentColor = Color.BLACK;
	//���ʵĴ�ϸ
	private int currentSize = 5;

	private Paint mPaint;
	//��¼���ʵ��б�
	private List<Action> mActions;

	private Bitmap bmp;
	
	private ActionType type = ActionType.Path;
	
	public Doodle(Context context) {
		super(context);
		init();
	}

	public Doodle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public Doodle(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);
		this.setFocusable(true);

		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		mPaint.setStrokeWidth(currentSize);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Canvas canvas = mSurfaceHolder.lockCanvas();
		canvas.drawColor(Color.WHITE);
		mSurfaceHolder.unlockCanvasAndPost(canvas);
		mActions = new ArrayList<Action>();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_CANCEL) {
			return false;
		}

		float touchX = event.getRawX();
		float touchY = event.getRawY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			setCurAction(touchX, touchY);
			break;
		case MotionEvent.ACTION_MOVE:
			Canvas canvas = mSurfaceHolder.lockCanvas();
			canvas.drawColor(Color.WHITE);
			for (Action a : mActions) {
				a.draw(canvas);
			}
			curAction.move(touchX, touchY);
			curAction.draw(canvas);
			mSurfaceHolder.unlockCanvasAndPost(canvas);
			break;
		case MotionEvent.ACTION_UP:
			mActions.add(curAction);
			curAction = null;
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	// �õ���ǰ���ʵ����ͣ�������ʵ��
	public void setCurAction(float x, float y) {
		switch (type) {
		case Point:
			curAction = new MyPoint(x, y, currentColor);
			break;
		case Path:
			curAction = new MyPath(x, y, currentSize, currentColor);
			break;
		case Line:
			curAction = new MyLine(x, y, currentSize, currentColor);
			break;
		case Rect:
			curAction = new MyRect(x, y, currentSize, currentColor);
			break;
		case Circle:
			curAction = new MyCircle(x, y, currentSize, currentColor);
			break;
		case FillecRect:
			curAction = new MyFillRect(x, y, currentSize, currentColor);
			break;
		case FilledCircle:
			curAction = new MyFillCircle(x, y, currentSize, currentColor);
			break;
		}
	}

	/**
	 * ���û��ʵ���ɫ
	 * @param color
	 */
	public void setColor(String color) {
		currentColor = Color.parseColor(color);
	}

	/**
	 * ���û��ʵĴ�ϸ
	 * @param size
	 */
	public void setSize(int size) {
		currentSize = size;
	}
	
	/**
	 * ���õ�ǰ���ʵ���״
	 * @param type
	 */
	public void setType(ActionType type) {
		this.type = type;
	}

	/**
	 * ��ȡ�����Ľ�ͼ
	 * @return
	 */
	public Bitmap getBitmap() {
		bmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		doDraw(canvas);
		return bmp;
	}

	public void doDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		for (Action a : mActions) {
			a.draw(canvas);
		}
		canvas.drawBitmap(bmp, 0, 0, mPaint);
	}
	
	/**
	 * ����
	 * @return
	 */
	public boolean back() {
		if (mActions != null && mActions.size() > 0) {
			mActions.remove(mActions.size() - 1);
			Canvas canvas = mSurfaceHolder.lockCanvas();
			canvas.drawColor(Color.WHITE);
			for (Action a : mActions) {
				a.draw(canvas);
			}
			mSurfaceHolder.unlockCanvasAndPost(canvas);
			return true;
		}
		return false;
	}
	
	public enum ActionType {
		Point, Path, Line, Rect, Circle, FillecRect, FilledCircle, Eraser
	}

}
