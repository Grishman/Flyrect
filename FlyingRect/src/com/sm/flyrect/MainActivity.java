package com.sm.flyrect;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

public class MainActivity extends Activity {
	private DrawView rectView;
	int width =1;
    int height=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSize();
		Log.i("test", "WWW  "+width+"HHH "+height);
		rectView=new DrawView(this);
		rectView.setmCanvasHeight(height);
		rectView.setmCanvasWidth(width);
		setContentView(rectView);
		
	}
	public void getSize() {

		Display display = getWindowManager().getDefaultDisplay();
		    Point size = new Point();
		    display.getSize(size);
		    width = size.x;
		    height = size.y;
		}
	@Override
	protected void onPause() {
		// TODO Implement
		super.onPause();
		//rectView.getThread().pause(); // pause game when Activity pauses
	}
}
