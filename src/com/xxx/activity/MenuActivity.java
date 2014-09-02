package com.xxx.activity;

import xxx.activity.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.xxx.tools.Music;
import com.xxx.tools.Score;
import com.xxx.view.GameView;

public class MenuActivity extends BaseActivity {

	ImageView startButton;
	ImageView optionButton;
	ImageView helpButton;
	ImageView shareButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Music.start(this, R.raw.heros, true, R.raw.shoot);
		setContentView(R.layout.layout_menu);
		shareButton = (ImageView) findViewById(R.id.button_share);
		shareButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				StartShareApp(MenuActivity.this, "分享到", "分享到", "我发现了一款叫TwoBird的游戏,挺好玩的!赶快来挑战我吧！" + "我最高分是"
						+ Score.score + "分，上局得分" + GameView.score);
			}
		});
		startButton = (ImageView) findViewById(R.id.button_start);
		startButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {

				// VibratorHelper.Vibrate(getParent(), 500l);
				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
					// startButton.setImageResource(R.drawable.menu01);
					return true;
				}
				if (event.getActionMasked() == MotionEvent.ACTION_UP) {
					// startButton.setImageResource(R.drawable.menu00);
					Intent start = new Intent(MenuActivity.this,
							GameActivity.class);
					startActivity(start);
					finish();
					return true;
				}
				return false;
			}
		});

		optionButton = (ImageView) findViewById(R.id.button_option);
		optionButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {

				// VibratorHelper.Vibrate(getParent(), 500l);
				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
					// optionButton.setImageResource(R.drawable.menu11);
					return true;
				}
				if (event.getActionMasked() == MotionEvent.ACTION_UP) {
					// optionButton.setImageResource(R.drawable.menu10);
					Intent option = new Intent(MenuActivity.this,
							SettingActivity.class);
					startActivity(option);
					return true;
				}
				return false;
			}
		});

		helpButton = (ImageView) findViewById(R.id.button_help);
		helpButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {

				// VibratorHelper.Vibrate(getParent(), 500l);
				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
					// helpButton.setImageResource(R.drawable.menu21);
					return true;
				}
				if (event.getActionMasked() == MotionEvent.ACTION_UP) {
					// helpButton.setImageResource(R.drawable.menu20);
					Intent help = new Intent(MenuActivity.this,
							HelpActivity.class);
					startActivity(help);
					return true;
				}
				return false;
			}
		});

	}

	public void exitGame() {
		new AlertDialog.Builder(MenuActivity.this).setTitle("确认?")
				.setIcon(R.drawable.ic).setMessage("确定要退出游戏吗亲?")
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				})
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Music.stop();
						finish();
					}
				}).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME
		// ||keyCode == KeyEvent.KEYCODE_POWER
		// ||keyCode == KeyEvent.KEYCODE_VOLUME_UP
		// ||keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
		// ||keyCode == KeyEvent.KEYCODE_MENU
		) {
			Music.stop();
			exitGame();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void StartShareApp(Context context, final String szChooserTitle,
			final String title, final String msg) {

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, title);
		intent.putExtra(Intent.EXTRA_TEXT, msg);
		context.startActivity(Intent.createChooser(intent, szChooserTitle));
	}
}
