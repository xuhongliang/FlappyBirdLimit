package com.xxx.activity;

import com.xxx.view.LogoView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.content.Intent;
import android.graphics.RectF;

public class FlappyBirdActivity extends BaseActivity {

	public static final boolean DEBUG = false;
	LogoView lv;
	public static FlappyBirdActivity me;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		me = this;
		lv = new LogoView(this);
		setContentView(lv);
	}

	public void gotoMenu() {
		Intent menu = new Intent(FlappyBirdActivity.this, MenuActivity.class);
		startActivity(menu);
		finish();
	}

	public static RectF ScreenRect;

	public void initSize() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		ScreenW = metrics.widthPixels;
		ScreenH = metrics.heightPixels;
		ScreenRect = new RectF(0, 0, ScreenW, ScreenH);
	}
}