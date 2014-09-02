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
		//����ͼƬ�����ƶ�
		x -= 5;
		//������Ļ֮ǰ����ͼƬx���꣨��ʱ���е����⣩
		if(x<= BaseActivity.ScreenW - width){
			x=0;
		}
	}
	//����
	public void draw(Canvas canvas, Paint paint) {
//		rectL = new RectF(x, y, MySurfaceView.screenW, MySurfaceView.screenH);   //�ֱ�����Ļ�Ŀ�͸ߣ�Ҳ����������ͼƬ��ʾ�Ŀ�͸�  
		rectL = new RectF(x, y, x + BaseActivity.ScreenW + width, BaseActivity.ScreenH);   //�ֱ�����Ļ�Ŀ�͸ߣ�Ҳ����������ͼƬ��ʾ�Ŀ�͸�  
		
		//����ͼƬ
//		canvas.drawBitmap(bmpGround, x, y, paint);
		canvas.drawBitmap(bmpGround, null, rectL, paint);  
	}
}
