package com.sm.tamplate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

	private boolean running = false;
	private SurfaceHolder surfaceHolder;
	Paint p;
	Rect rect;

	public DrawThread(SurfaceHolder surfaceHolder) {
		this.surfaceHolder = surfaceHolder;
		p = new Paint();
		rect = new Rect();
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void run() {
		Canvas canvas;
		// ��������� �����
		// ������� ����
		p.setColor(Color.RED);
		// ������� ����� = 10
		p.setStrokeWidth(10);
		p.setStyle(Paint.Style.STROKE);
		// ��������� ������� Rect;
		// ����� ������� ����� (250,300), ������ ������ (350,500)
		rect.set(250, 300, 350, 400);

		while (running) {
			canvas = null;
			try {
				canvas = surfaceHolder.lockCanvas(null);
				if (canvas == null)
					continue;
				canvas.drawColor(Color.CYAN);
				canvas.drawRect(rect, p);
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
