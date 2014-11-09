package com.sm.tamplate;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	private DrawView rectView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new DrawView(this));
	}
	
	@Override
	protected void onPause() {
		// TODO Implement
		super.onPause();
		//rectView.getThread().pause(); // pause game when Activity pauses
	}
}
