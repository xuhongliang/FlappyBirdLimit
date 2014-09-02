package com.xxx.tools;

import com.xxx.activity.BaseActivity;
import com.xxx.sprite.Bird;
import com.xxx.sprite.Column;


public class Gua {

	public static boolean guaOn = false;
	public static boolean tapOn = false;
	
	public static void gua(Bird bird, Column column1, Column column2){

		//������x���껹û�г�����һ�����ӵ�x����
		if(bird.x <= column1.x + column1.width/2 + bird.size
				&& column1.x < BaseActivity.ScreenW-bird.size
				&& column1.x > -bird.size/2){

			gua(bird,column1);

		//������x���껹û�г����ڶ������ӵ�x����
		}else if(bird.x <= column2.x + column2.width/2 + bird.size
				&& column2.x < BaseActivity.ScreenW-bird.size
				&& column2.x > -bird.size/2){
			gua(bird,column2);
			
		}
	}
	public static void gua(Bird bird, Column column){
		//������y������ڹ��ӵ�y����
		if((bird.y >= column.y + column.gap/2 - bird.size*2)){
			bird.flappy();
			tapOn = true;
		}
	}
}
