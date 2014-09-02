package com.xxx.tools;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

public class VibratorHelper {
	public static boolean isZd = false;

	public static void Vibrate(final Activity activity, long milliseconds) {
		if (isZd) {
			Vibrator vib = (Vibrator) activity
					.getSystemService(Service.VIBRATOR_SERVICE);
			vib.vibrate(milliseconds);
		}
	}

	public static void Vibrate(final Activity activity, long[] pattern,
			boolean isRepeat) {
		if (isZd) {
			Vibrator vib = (Vibrator) activity
					.getSystemService(Service.VIBRATOR_SERVICE);
			vib.vibrate(pattern, isRepeat ? 1 : -1);
		}
	}
}
