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
 * x, y �ǿ�϶�м�λ��. 
 */
public class Column {
	private Bitmap bmpColumn;
	//�����ӵ��м���Ϊ���ӵ�λ��
	public int x;	//���ӵ�X����
	public int y;	//���ӵ�Y����
	public int width;	//���ӵĿ��
	float height;	//���ӵĸ߶�
	float screenW;	//��Ļ�Ŀ��
	private float screenH;
	public int gap;	//����֮��ļ��
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
	//�ƶ�
	public void step(){
		if(x < - width/2){
			x = (int) (screenW + screenW/2 - width/2-10);
		}else x -= 5;
		
		//y�����Ƶ�����һ�����ϡ������ķ�֮һ����
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
		
		collsion = new RectF(x - width/2, 0, x + width/2, y + height/2);	//��ײ����
		collsionNO = new RectF(x - width/2, y - gap/2, x + width/2, y + gap/2);	//��ײ����
		if(FlappyBirdActivity.DEBUG){
			paint.setColor(Color.RED);
			canvas.drawRect(collsion, paint);
			paint.setColor(Color.BLUE);
			canvas.drawRect(collsionNO, paint);
		}
	}
}
