package com.xxx.activity;

import xxx.activity.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.xxx.tools.Music;
import com.xxx.tools.Score;
import com.xxx.view.GameView;

public class GameActivity extends BaseActivity {

	public static GameActivity me ;
	GameView gv;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    me = this;
	    Music.start(this, R.raw.play, true);
	    Score.start(this);
	    gv = new GameView(this);
	    setContentView(gv);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent(GameActivity.this, MenuActivity.class);
			startActivity(intent);
			finish();
			return true;
		}
		if(keyCode == KeyEvent.KEYCODE_HOME){
			Intent intent = new Intent(GameActivity.this, MenuActivity.class);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		gv.f = false;	//如果没有这句，即使退出，run()函数也不停止运行，既游戏界面退出，到后台后仍在运行着。
		Music.stop();
	}
	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		gv.running = true;	//如果没有这句，即使退出，run()函数也不停止运行，既游戏界面退出，到后台后仍在运行着。
//		Music.playing();
//	}
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		gv.running = false;
//	}
//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
//		super.onStop();
//		gv.running = false;
//	}
}
