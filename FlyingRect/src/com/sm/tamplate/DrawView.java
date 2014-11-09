package com.sm.tamplate;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

	private DrawThread drawThread;

	public DrawView(Context context) {
		super(context);
		getHolder().addCallback(this);

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
}
