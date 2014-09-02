package com.xxx.activity;

import xxx.activity.R;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.xxx.tools.Gua;
import com.xxx.tools.Music;
import com.xxx.tools.VibratorHelper;

public class SettingActivity extends BaseActivity {

	public static SettingActivity me;
	ImageView buttonGua;
	ImageView buttonSound;
	ImageView buttonZd;
	SeekBar volumeBar;
	AudioManager am;

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    me = this;
	    am = (AudioManager) getSystemService(AUDIO_SERVICE);
	    setContentView(R.layout.option_layout);
	    
	    buttonGua = (ImageView) findViewById(R.id.button_g);
	    buttonGua.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				VibratorHelper.Vibrate(me, 500l);
				//Log.i("xxx","点击");
				if(Gua.guaOn){
				//	Log.i("xxx","关闭");
					Gua.guaOn = false;
					buttonGua.setImageResource(R.drawable.audio_off);
				}else{
				//	Log.i("xxx","打开");
					Gua.guaOn = true;
					buttonGua.setImageResource(R.drawable.audio_on);
				}
			}
		});
		if(Gua.guaOn){
			buttonGua.setImageResource(R.drawable.audio_on);
		}else{
			buttonGua.setImageResource(R.drawable.audio_off);
		}
		
		buttonZd = (ImageView) findViewById(R.id.button_zd);
	    buttonZd.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				//Log.i("xxx","点击");
				if(VibratorHelper.isZd){
				//	Log.i("xxx","关闭");
					VibratorHelper.isZd = false;
					buttonZd.setImageResource(R.drawable.audio_off);
				}else{
				//	Log.i("xxx","打开");
					VibratorHelper.isZd = true;
					buttonZd.setImageResource(R.drawable.audio_on);
				}
				VibratorHelper.Vibrate(me, 500l);
			}
		});
		if(VibratorHelper.isZd){
			buttonZd.setImageResource(R.drawable.audio_on);
		}else{
			buttonZd.setImageResource(R.drawable.audio_off);
		}
		
	    buttonSound = (ImageView) findViewById(R.id.button_sound);
	    buttonSound.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				VibratorHelper.Vibrate(me, 500l);
				if(Music.MusicOn){
					Music.MusicOn = false;
					Music.stop();
					buttonSound.setImageResource(R.drawable.audio_off);
					Music.saveMusic(Music.myVolume);
				}else{
					Music.MusicOn = true;
					Music.start(SettingActivity.this, R.raw.play, true);
					buttonSound.setImageResource(R.drawable.audio_on);
					Music.saveMusic(Music.myVolume);
				}
			}
		});

		if(Music.MusicOn){
			buttonSound.setImageResource(R.drawable.audio_on);
		}else{
			buttonSound.setImageResource(R.drawable.audio_off);
		}
	    
	    volumeBar = (SeekBar) findViewById(R.id.seekBar1);
	    volumeBar.setMax(
	    		am.getStreamMaxVolume(
	    				AudioManager.STREAM_MUSIC));
	    volumeBar.setProgress(Music.myVolume);
	    
	    volumeBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
				VibratorHelper.Vibrate(me, 500l);
				am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
				Music.myVolume = progress;
				Music.saveMusic(Music.myVolume);
			}
		});
	}
}