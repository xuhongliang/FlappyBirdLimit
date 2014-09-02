package com.xxx.activity;

import android.app.Activity;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class BaseActivity extends Activity {

protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setFullScreen();
	initSize();
}

public static float ScreenW,ScreenH;//ÆÁÄ»¿í£¬¸ß
public static RectF ScreenRect;
public void initSize() {
	DisplayMetrics metrics = 
			new DisplayMetrics();
 	getWindowManager().getDefaultDisplay().
 	getMetrics(metrics);
		ScreenW = metrics.widthPixels;
		ScreenH = metrics.heightPixels;
		ScreenRect = new RectF(0, 0, ScreenW, ScreenH);
}

public void setFullScreen() {
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	getWindow().setFlags(
			WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}
