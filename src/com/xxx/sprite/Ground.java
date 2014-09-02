package com.xxx.sprite;

import com.xxx.activity.BaseActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Ground {
	private Bitmap bmpGround;
	private RectF rectL;
	int x;
	int y;
	int width;
	int height;
	public Ground(int y, Bitmap bmpGround) {
		this.bmpGround = bmpGround;
		width = bmpGround.getWidth();
		height = bmpGround.getHeight();
		this.y = y/5*4;
		x = 0;
	}

	public void step(){
		//地面图片不断移动
		x -= 5;
		//超出屏幕之前重置图片x坐标（暂时还有点问题）
		if(x<= BaseActivity.ScreenW - width){
			x=0;
		}
	}
	//绘制
	public void draw(Canvas canvas, Paint paint) {
//		rectL = new RectF(x, y, MySurfaceView.screenW, MySurfaceView.screenH);   //分别是屏幕的宽和高，也就是你想让图片显示的宽和高  
		rectL = new RectF(x, y, x + BaseActivity.ScreenW + width, BaseActivity.ScreenH);   //分别是屏幕的宽和高，也就是你想让图片显示的宽和高  
		
		//绘制图片
//		canvas.drawBitmap(bmpGround, x, y, paint);
		canvas.drawBitmap(bmpGround, null, rectL, paint);  
	}
}
