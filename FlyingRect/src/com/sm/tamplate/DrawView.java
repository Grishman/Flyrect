package com.sm.tamplate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

	private DrawThread drawThread;
	Paint p;
	Rect rect;

	public DrawView(Context context) {
		super(context);
		getHolder().addCallback(this);
		p = new Paint();
		rect = new Rect();

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
		drawThread = new DrawThread(getHolder());
		drawThread.setRunning(true);
		drawThread.start();

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

		public DrawThread(SurfaceHolder surfaceHolder) {
			this.surfaceHolder = surfaceHolder;

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
			rect.set(250, 300, 350, 400);
			canvas.drawRect(rect, p);

		}

		// ќбновл€ет позиции
		// TODO Implement method
		private void updatePhysics(Canvas canvas) {
			int dx = 15;
			int dy = 15;
			
			rect.offset(dx, dy);
			canvas.drawRect(rect, p);
		}

		@Override
		public void run() {
			Canvas canvas;

			while (running) {
				canvas = null;
				try {
					canvas = surfaceHolder.lockCanvas(null);
					if (canvas == null)
						continue;
					// TODO implement update() and draw()

					doDraw(canvas);
					updatePhysics(canvas);
					
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

}
