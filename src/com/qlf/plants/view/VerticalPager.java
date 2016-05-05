package com.qlf.plants.view;

import android.content.Context;

import android.util.AttributeSet;

import android.util.Log;

import android.view.MotionEvent;

import android.view.VelocityTracker;

import android.view.View;

import android.view.ViewGroup;

import android.view.animation.Interpolator;

import android.widget.Scroller;

import android.widget.Toast;

/**
 * 
 * ʵ��������»�����Ч������Ҫ��������Ļ����ϵ�͹�����
 * 
 * @author Administrator
 * 
 * 
 */

public class VerticalPager extends ViewGroup {

	private Scroller mScroller; // ������

	private Context mContext;

	private final static int RATE = 5; // ���ʱ�׼

	private final static int DISTANCE = 300;// ��Ҫ�����ľ���

	private VelocityTracker mVelocityTracker;// ͨ��������Լ����ٶ�

	public VerticalPager(Context context, AttributeSet attrs) {

		super(context, attrs);

		this.mContext = context;

		mScroller = new Scroller(context);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		int totalHeight = 0;

		int count = getChildCount();

		for (int i = 0; i < count; i++) {

			View childView = getChildAt(i);

			childView.layout(l, totalHeight, r, totalHeight + b);

			totalHeight += b;

		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int width = MeasureSpec.getSize(widthMeasureSpec);

		int height = MeasureSpec.getSize(heightMeasureSpec);

		int count = getChildCount();

		for (int i = 0; i < count; i++) {

			getChildAt(i).measure(width, height);

		}

		setMeasuredDimension(width, height);

	}

	private int mLastMotionY;

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (mVelocityTracker == null) {

			mVelocityTracker = VelocityTracker.obtain();

		}

		mVelocityTracker.addMovement(event);

		int action = event.getAction();

		float y = event.getY();

		switch (action) {

		case MotionEvent.ACTION_DOWN:

			if (!mScroller.isFinished()) {

				mScroller.abortAnimation();

			}

			mLastMotionY = (int) y;

			Log.d("montion", "" + getScrollY());

			break;

		case MotionEvent.ACTION_MOVE:

			int deltaY = (int) (mLastMotionY - y);

			scrollBy(0, deltaY);

			invalidate();

			mLastMotionY = (int) y;

			break;

		case MotionEvent.ACTION_UP:

			// if(mVelocityTracker!=null){

			// mVelocityTracker.recycle();

			// mVelocityTracker=null;

			// }

			mVelocityTracker.computeCurrentVelocity(1, 1000); // ��λΪ1˵����һ��һ�����أ����ֵΪ1000

			float vy = mVelocityTracker.getYVelocity(); // vy����Y�᷽�������

			Log.i("test", "velocityTraker : " + mVelocityTracker.getYVelocity());

			if (getScrollY() < 0) {

				mScroller.startScroll(0, -DISTANCE, 0, DISTANCE);

			} else if (getScrollY() > (getHeight() * (getChildCount() - 1))) {

				View lastView = getChildAt(getChildCount() - 1);

				mScroller.startScroll(0, lastView.getTop() + DISTANCE, 0,
						-DISTANCE);

			} else {

				int position = getScrollY() / getHeight();

				View positionView = null;

				if (vy < -RATE) { // �»�

					positionView = getChildAt(position + 1);

					mScroller.startScroll(0, positionView.getTop() - DISTANCE,
							0, +DISTANCE);

				} else if (vy > RATE) {// �ϻ�

					positionView = getChildAt(position);

					mScroller.startScroll(0, positionView.getTop() - DISTANCE,
							0, +DISTANCE);

				} else {

					int mod = getScrollY() % getHeight();

					if (mod > getHeight() / 2) {

						positionView = getChildAt(position + 1);

						mScroller.startScroll(0, positionView.getTop()
								- DISTANCE, 0, +DISTANCE);

					} else {

						positionView = getChildAt(position);

//						mScroller.startScroll(0, positionView.getTop()
//								+ DISTANCE, 0, -DISTANCE);
						mScroller.startScroll(0, 0, 0, 0);

					}

				}

			}

			invalidate();

			break;

		}

		return true; // ����true��ʾ�¼��ɱ�View���ѵ�

	}

	@Override
	public void computeScroll() {

		super.computeScroll();

		if (mScroller.computeScrollOffset()) {

			scrollTo(0, mScroller.getCurrY());

		}

	}

}