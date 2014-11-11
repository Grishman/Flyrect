package com.sm.flyrect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

	private DrawThread drawThread;
	Paint p;
	Rect rect;
	Rect canvasRect;
	
	/**
     * Current height of the surface/canvas.
     * 
     * @see #setSurfaceSize
     */
    private int mCanvasHeight = 1;

    /**
     * Current width of the surface/canvas.
     * 
     * @see #setSurfaceSize
     */
    private int mCanvasWidth = 1;

	public DrawView(Context context) {
		super(context);
		getHolder().addCallback(this);
		p = new Paint();
		rect = new Rect();
		canvasRect = new Rect();

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		drawThread = new DrawThread(getHolder());
		drawThread.setRunning(true);
		drawThread.start();

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		//drawThread.setSurfaceSize(width, height);

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		drawThread.setRunning(false);
		while (retry) {
			try {
				drawThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}


	}

	class DrawThread extends Thread {

		private boolean running = false;
		private SurfaceHolder surfaceHolder;
		long prevTime;
		public DrawThread(SurfaceHolder surfaceHolder) {
			this.surfaceHolder = surfaceHolder;
			 // сохран€ем текущее врем€
	        prevTime = System.currentTimeMillis();
			rect.set(250, 300, 350, 400);
			Log.i("Surf", "Surf Change "+mCanvasWidth+" high "+mCanvasHeight);
			canvasRect.set(0, 0, mCanvasWidth, mCanvasHeight);
			
		}

		public void setSurfaceSize(int width, int height) {
            // synchronized to make sure these all change atomically
            synchronized (surfaceHolder) {
            	mCanvasWidth = width;
                mCanvasHeight = height;
                Log.i("SurfSize", "Surf Change "+mCanvasWidth+" high "+mCanvasHeight);
                
                // don't forget to resize the background image
                //mBackgroundImage = mBackgroundImage.createScaledBitmap(
                //        mBackgroundImage, width, height, true);
            }
        }

		public void setRunning(boolean running) {
			this.running = running;
		}

		// –исовачь
		// TODO Implement
		private void doDraw(Canvas canvas) {
			canvas.drawColor(Color.CYAN);
			// настройка кисти
			// красный цвет
			p.setColor(Color.RED);
			// толщина линии = 10
			p.setStrokeWidth(10);
			p.setStyle(Paint.Style.STROKE);
			// настройка объекта Rect;
			// лева€ верхн€€ точка (250,300), нижн€€ права€ (350,500)
			Paint test = new Paint();
			test.setColor(Color.WHITE);
			test.setStrokeWidth(15);
			test.setStyle(Paint.Style.STROKE);
			canvas.drawRect(rect, p);
			canvas.drawRect(canvasRect, test);

		}

		// ќбновл€ет позиции
		// TODO Implement method
		private void updatePhysics() {
			int dx = 15;
			int dy = 15;
			
			rect.offset(dx, dy); 
//			if (rect.intersects(rect, canvasRect)) {
//				Log.v("coll", "its workiing");
//				
//			}
			//canvas.drawRect(rect, p);
		}

		@Override
		public void run() {
			Canvas canvas;

			while (running) {
				
				long now = System.currentTimeMillis();
	            long elapsedTime = now - prevTime;
	            if (elapsedTime > 1000){
	            	 prevTime = now;
	            	//updatePhysics();
	            	 rect.offset(-10, -10);
	            	 if (rect.intersect(canvasRect)){
	            		 rect.offset(15, 15);
	            	 }
	            	 
	            }
				canvas = null;
				try {
					canvas = surfaceHolder.lockCanvas(null);
					if (canvas == null)
						continue;
					// TODO implement update() and draw()
					//canvas.drawRect(canvasRect, p);
					doDraw(canvas);

					//updatePhysics(canvas);
					
				} finally {
					if (canvas != null) {
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}
	}

	public Object getThread() {
		// TODO Auto-generated method stub
		return drawThread;
	}

	public void pause() {
		// TODO Implement
		// synchronized (mSurfaceHolder) {
		// if (mMode == STATE_RUNNING) setState(STATE_PAUSE);
		// }
	}

	public int getmCanvasHeight() {
		return mCanvasHeight;
	}

	public void setmCanvasHeight(int mCanvasHeight) {
		this.mCanvasHeight = mCanvasHeight;
	}

	public int getmCanvasWidth() {
		return mCanvasWidth;
	}

	public void setmCanvasWidth(int mCanvasWidth) {
		this.mCanvasWidth = mCanvasWidth;
	}

}
