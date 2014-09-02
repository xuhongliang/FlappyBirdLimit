package com.xxx.tools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import xxx.activity.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

public class Music {
	static Context cont;

	static MediaPlayer player;
	static int MusicId = -1;
	static AudioManager audio;

	public static int myVolume = 10;

	public static boolean MusicOn = true;

	private static SoundPool soundPool;
	private static HashMap<Integer, Integer> soundPoolMap;

	public static void start(Context context, int musicId, boolean loop) {
		Log.i("aaa", "aaaaa");
		if (cont == null) {
			cont = context;
			loadMusic();
			audio = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
			audio.setStreamVolume(AudioManager.STREAM_MUSIC, myVolume, 0);
		}
		if (!MusicOn)
			return;
		if (MusicId == musicId && player.isPlaying())
			return;
		MusicId = musicId;
		if (player != null) {
			if (player.isPlaying()) {
				player.stop();
			}
			player.reset();
			player = null;
		}
		player = MediaPlayer.create(context, musicId);

		player.setLooping(loop);
		player.start();
		initSounds();
	}

	@SuppressLint("UseSparseArrays")
	private static void initSounds() {
		soundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 1);
		soundPoolMap = new HashMap<Integer, Integer>();
		soundPoolMap.put(1, soundPool.load(cont, R.raw.sfx_swooshing, 0)); // 飞
		soundPoolMap.put(2, soundPool.load(cont, R.raw.sfx_point, 0)); // 分
		soundPoolMap.put(3, soundPool.load(cont, R.raw.sfx_hit, 0)); // 撞
		soundPoolMap.put(4, soundPool.load(cont, R.raw.sfx_wing, 0)); // 落
		soundPoolMap.put(5, soundPool.load(cont, R.raw.sfx_die, 0)); // 摔
	}

	public static void playSound(final int sound) {
		if (soundPoolMap == null) {
			initSounds();
		}
		if (MusicOn) {
			soundPool.play(soundPoolMap.get(sound), myVolume, myVolume, 1, 0,
					2f);
		}
	}

	public static void stop() {
		if (player != null)
			player.stop();
	}

	public static boolean playing() {
		if (player != null && player.isPlaying()) {
			return true;
		}
		return false;
	}

	public static void saveMusic(int volume) {
		if (cont == null) {
			return;
		}
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		try {
			fos = cont.openFileOutput("music.and1", Context.MODE_PRIVATE);// 备注2
			dos = new DataOutputStream(fos);
			dos.writeBoolean(MusicOn);
			dos.writeInt(volume);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 在finally中关闭流 这样即使try中有异常我们也能对其进行关闭操作 ;
			try {
				dos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void loadMusic() {
		FileInputStream fis = null;
		DataInputStream dis = null;
		try {
			// 这里找不到数据文件就会报异常,所以finally里关闭流尤为重要!!!
			if (cont.openFileInput("music.and1") != null) {

				fis = cont.openFileInput("music.and1");
				dis = new DataInputStream(fis);
				MusicOn = dis.readBoolean();
				myVolume = dis.readInt();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 在finally中关闭流!因为如果找不到数据就会异常我们也能对其进行关闭操作 ;
			try {
				if (cont.openFileInput("music.and1") != null) {
					// 这里也要判断，因为找不到的情况下，两种流也不会实例化。
					// 既然没有实例化，还去调用close关闭它,肯定"空指针"异常！！！
					fis.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
