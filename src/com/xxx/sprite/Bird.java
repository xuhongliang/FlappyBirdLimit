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
	
	/** 鸟飞行位置, 这个位置是鸟的中心位置 */
	public int x, y;
	/** 飞行角度 */
	public float angle;
	/** 动画帧 */
	private Bitmap[] bmpBirds;
	/** 当前图片 */
	private Bitmap bmpBird;
	/** 当前图片序号 */
	int index = 0;
	/** 重力加速度 */
	final double g; 
	/** 时间间隔秒 */
	final double t;
	/** 初始上抛速度 */
	final double v0;
	/** 当前上抛速度 */
	double speed;
	/** 移动距离 */
	double s;
	/** 鸟的范围, 鸟的范围是一个正方形区域, 中心点是x,y */
	public int size;
	private RectF collsion;
	
	public Bird(int screenW, int screenH, Bitmap bmpBirds[]) {
		this.g = 4; //重力加速度
		this.t = 0.8; //每次计算的间隔时间
		this.v0 = 20; //初始上抛时间
		this.x = screenW/3; //鸟的初始位置
		this.y = screenH/2; //鸟的初始位置
		size = 26;
//		size = screenW/10 * screenH/10;
		//加载动画帧
		this.bmpBirds = bmpBirds;
		bmpBird = bmpBirds[0];
	}
	
	/** 飞行一步  
	 * 竖直上抛位移计算
	 *  (1) 上抛速度计算 V=Vo-gt  
		(2) 上抛距离计算 S=Vot-1/2gt^2
	 * */
	public void step(){
		//V0 是本次
		double v0 = speed;
		//计算垂直上抛运动, 经过时间t以后的速度, 
		double v = v0 - g*t;
		//作为下次计算的初始速度
		speed = v;
		//计算垂直上抛运动的运行距离
		s = v0*t - 0.5 * g * t * t;
		//将计算的距离 换算为 y坐标的变化
		if(y - (int)s>=0)
			y = y - (int)s;
		//计算小鸟的仰角, 
		angle = -(float)Math.atan(s/8);
		//更换小鸟的动画帧图片, 其中/4 是为了调整动画更新的频率
	}
	/** 小鸟向上飞扬 */
	public void flappy(){
		//每次小鸟上抛跳跃, 就是将小鸟在当前点重新以初始速度 V0 向上抛
		speed = v0;
		Music.playSound(1);
	}
	//绘制时并发执行的, 要同步避免 旋转坐标系对其他绘制方法的影响synchronized 
	public synchronized void draw(Canvas canvas, Paint paint){
		index++;
		bmpBird = bmpBirds[(index/8)%3];
		canvas.save();
		//旋转绘图坐标系, 绘制
		canvas.rotate(angle*15, this.x, this.y);
		canvas.drawBitmap(bmpBird, x - bmpBird.getWidth()/2, y - bmpBird.getHeight()/2, paint);
		
		//以x,y 为中心绘制图片
		int x = this.x - bmpBird.getWidth()/2;
		int y = this.y - bmpBird.getHeight()/2;
		collsion = new RectF(x, y, x + bmpBird.getWidth(), y + bmpBird.getHeight());	//碰撞矩形
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
	/** 判断鸟是否通过柱子 */
	public boolean pass(Column column1, Column column2) {
		return column1.x/5 == x/5 || column2.x/5 == x/5;
	}
	/** 判断鸟是否与柱子和地发生碰撞 */
	public boolean hit(Column column1, Column column2, Ground ground) {
		//碰到地面
		if(y + size >= ground.y){
			Log.e("gaoliang", "碰到地面！！！");
			return true;
		}
		//碰到柱子
		return hit(column1) || hit(column2);
	}
	/** 检查当前鸟是否碰到柱子 */
	public boolean hit(Column column){
		//如果鸟碰到柱子: 鸟的中心点x坐标在 柱子宽度 + 鸟的一半
		if((x > column.x - column.width/2 - size/2) && (x < column.x + column.width/2 + size/2)){
			if((y > column.y - column.gap/2 + size/2) && (y < column.y + column.gap/2 - size/2)) {
				return false;
			}
			return true;
		}
		return false;
	}
}