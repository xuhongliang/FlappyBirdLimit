package com.xxx.sprite;

import com.xxx.activity.FlappyBirdActivity;
import com.xxx.tools.Music;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;


public class Bird {
	
	/** �����λ��, ���λ�����������λ�� */
	public int x, y;
	/** ���нǶ� */
	public float angle;
	/** ����֡ */
	private Bitmap[] bmpBirds;
	/** ��ǰͼƬ */
	private Bitmap bmpBird;
	/** ��ǰͼƬ��� */
	int index = 0;
	/** �������ٶ� */
	final double g; 
	/** ʱ������ */
	final double t;
	/** ��ʼ�����ٶ� */
	final double v0;
	/** ��ǰ�����ٶ� */
	double speed;
	/** �ƶ����� */
	double s;
	/** ��ķ�Χ, ��ķ�Χ��һ������������, ���ĵ���x,y */
	public int size;
	private RectF collsion;
	
	public Bird(int screenW, int screenH, Bitmap bmpBirds[]) {
		this.g = 4; //�������ٶ�
		this.t = 0.8; //ÿ�μ���ļ��ʱ��
		this.v0 = 20; //��ʼ����ʱ��
		this.x = screenW/3; //��ĳ�ʼλ��
		this.y = screenH/2; //��ĳ�ʼλ��
		size = 26;
//		size = screenW/10 * screenH/10;
		//���ض���֡
		this.bmpBirds = bmpBirds;
		bmpBird = bmpBirds[0];
	}
	
	/** ����һ��  
	 * ��ֱ����λ�Ƽ���
	 *  (1) �����ٶȼ��� V=Vo-gt  
		(2) ���׾������ S=Vot-1/2gt^2
	 * */
	public void step(){
		//V0 �Ǳ���
		double v0 = speed;
		//���㴹ֱ�����˶�, ����ʱ��t�Ժ���ٶ�, 
		double v = v0 - g*t;
		//��Ϊ�´μ���ĳ�ʼ�ٶ�
		speed = v;
		//���㴹ֱ�����˶������о���
		s = v0*t - 0.5 * g * t * t;
		//������ľ��� ����Ϊ y����ı仯
		if(y - (int)s>=0)
			y = y - (int)s;
		//����С�������, 
		angle = -(float)Math.atan(s/8);
		//����С��Ķ���֡ͼƬ, ����/4 ��Ϊ�˵����������µ�Ƶ��
	}
	/** С�����Ϸ��� */
	public void flappy(){
		//ÿ��С��������Ծ, ���ǽ�С���ڵ�ǰ�������Գ�ʼ�ٶ� V0 ������
		speed = v0;
		Music.playSound(1);
	}
	//����ʱ����ִ�е�, Ҫͬ������ ��ת����ϵ���������Ʒ�����Ӱ��synchronized 
	public synchronized void draw(Canvas canvas, Paint paint){
		index++;
		bmpBird = bmpBirds[(index/8)%3];
		canvas.save();
		//��ת��ͼ����ϵ, ����
		canvas.rotate(angle*15, this.x, this.y);
		canvas.drawBitmap(bmpBird, x - bmpBird.getWidth()/2, y - bmpBird.getHeight()/2, paint);
		
		//��x,y Ϊ���Ļ���ͼƬ
		int x = this.x - bmpBird.getWidth()/2;
		int y = this.y - bmpBird.getHeight()/2;
		collsion = new RectF(x, y, x + bmpBird.getWidth(), y + bmpBird.getHeight());	//��ײ����
		if(FlappyBirdActivity.DEBUG){
			paint.setColor(Color.RED);
			canvas.drawRect(collsion, paint);
		}
		canvas.restore();
	}

	@Override
	public String toString() {
		return "Bird [x=" + x + ", y=" + y + ", g=" + g + ", t=" + t + ", v0="
				+ v0 + ", speed=" + speed + ",s="+s+"]";
	}
	/** �ж����Ƿ�ͨ������ */
	public boolean pass(Column column1, Column column2) {
		return column1.x/5 == x/5 || column2.x/5 == x/5;
	}
	/** �ж����Ƿ������Ӻ͵ط�����ײ */
	public boolean hit(Column column1, Column column2, Ground ground) {
		//��������
		if(y + size >= ground.y){
			Log.e("gaoliang", "�������棡����");
			return true;
		}
		//��������
		return hit(column1) || hit(column2);
	}
	/** ��鵱ǰ���Ƿ��������� */
	public boolean hit(Column column){
		//�������������: ������ĵ�x������ ���ӿ�� + ���һ��
		if((x > column.x - column.width/2 - size/2) && (x < column.x + column.width/2 + size/2)){
			if((y > column.y - column.gap/2 + size/2) && (y < column.y + column.gap/2 - size/2)) {
				return false;
			}
			return true;
		}
		return false;
	}
}