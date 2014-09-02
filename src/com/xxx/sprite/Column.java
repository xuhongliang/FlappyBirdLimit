package com.xxx.sprite;

import java.util.Random;

import com.xxx.activity.BaseActivity;
import com.xxx.activity.FlappyBirdActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
/**
 * x, y 是空隙中间位置. 
 */
public class Column {
	private Bitmap bmpColumn;
	//以柱子的中间作为柱子的位置
	public int x;	//柱子的X坐标
	public int y;	//柱子的Y坐标
	public int width;	//柱子的宽度
	float height;	//柱子的高度
	float screenW;	//屏幕的宽度
	private float screenH;
	public int gap;	//柱子之间的间隔
	Random r = new Random();
	private RectF collsion;
	private RectF collsionNO;
	private boolean round = false;
	private int speed = 1;
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public Column(int x, Bitmap bmpColumn){ 
		this.bmpColumn = bmpColumn;
		width = bmpColumn.getWidth();
		height = bmpColumn.getHeight();
		screenW = BaseActivity.ScreenW;
		screenH = BaseActivity.ScreenH;
		gap = (int) (screenW/3);
		this.x = (int) (x + screenW/2);
		this.y = (int) (r.nextInt((int) (screenH/4)) + screenH/4);
	}
	//移动
	public void step(){
		if(x < - width/2){
			x = (int) (screenW + screenW/2 - width/2-10);
		}else x -= 5;
		
		//y往下移到多于一半向上、少于四分之一再下
		if(round){
			y -= speed;
			if(y <= (int)(screenH/4)){
				round = false;
			}
		} else{
			y += speed;
			if(y >= (int)(screenH/2)){
				round = true;
			}
		}
	}
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bmpColumn, x - width/2, y - height/2, paint);
		
		collsion = new RectF(x - width/2, 0, x + width/2, y + height/2);	//碰撞矩形
		collsionNO = new RectF(x - width/2, y - gap/2, x + width/2, y + gap/2);	//碰撞矩形
		if(FlappyBirdActivity.DEBUG){
			paint.setColor(Color.RED);
			canvas.drawRect(collsion, paint);
			paint.setColor(Color.BLUE);
			canvas.drawRect(collsionNO, paint);
		}
	}
}
