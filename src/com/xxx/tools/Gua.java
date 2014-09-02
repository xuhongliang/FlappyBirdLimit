package com.xxx.tools;

import com.xxx.activity.BaseActivity;
import com.xxx.sprite.Bird;
import com.xxx.sprite.Column;


public class Gua {

	public static boolean guaOn = false;
	public static boolean tapOn = false;
	
	public static void gua(Bird bird, Column column1, Column column2){

		//如果鸟的x坐标还没有超过第一个管子的x坐标
		if(bird.x <= column1.x + column1.width/2 + bird.size
				&& column1.x < BaseActivity.ScreenW-bird.size
				&& column1.x > -bird.size/2){

			gua(bird,column1);

		//如果鸟的x坐标还没有超过第二个管子的x坐标
		}else if(bird.x <= column2.x + column2.width/2 + bird.size
				&& column2.x < BaseActivity.ScreenW-bird.size
				&& column2.x > -bird.size/2){
			gua(bird,column2);
			
		}
	}
	public static void gua(Bird bird, Column column){
		//如果鸟的y坐标低于管子的y坐标
		if((bird.y >= column.y + column.gap/2 - bird.size*2)){
			bird.flappy();
			tapOn = true;
		}
	}
}
