package com.xxx.view;

import xxx.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.xxx.activity.FlappyBirdActivity;

public class LogoView extends SurfaceView implements Runnable {
	Paint paint;

	Bitmap splashImg;
	Bitmap yellow, green;
	int logo_state;
	public static final int STATE_LOGO1 = 0;
	public static final int STATE_LOGO2 = 1;

	int logo1Time;
	int logo2Time;

	private Animation startAnim;
	private Animation endAnim;

	public LogoView(Context context) {
		super(context);

		paint = new Paint();
		splashImg = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bird);
		yellow = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.yellow);
		green = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.green);

		startAnim = AnimationUtils.loadAnimation(getContext(),
				R.anim.anim_left_in);
		endAnim = AnimationUtils.loadAnimation(getContext(),
				R.anim.anim_right_out);

		logo_state = STATE_LOGO1;
		logo1Time = 20;
		new Thread(this).start();
	}

	public void render() {
		SurfaceHolder sh = getHolder();
		Canvas canvas = sh.lockCanvas();
		if (canvas == null) {
			return;
		}
		canvas.drawColor(Color.BLACK);

		switch (logo_state) {
		case STATE_LOGO1:

			canvas.drawBitmap(yellow, getWidth() / 2 - yellow.getWidth() / 2,
					getHeight() / 2 - yellow.getHeight() / 2, paint);

			this.setAnimation(startAnim);

			break;
		case STATE_LOGO2:

			canvas.drawBitmap(green, getWidth() / 2 - green.getWidth() / 2,
					getHeight() / 2 - green.getHeight() / 2, paint);
			this.setAnimation(endAnim);
			break;
		}
		sh.unlockCanvasAndPost(canvas);
	}

	@Override
	public void run() {
		while (true) {
			event();// C
			update();// M
			render();// V
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void event() {
		// TODO Auto-generated method stub

	}

	public void update() {
		switch (logo_state) {
		case STATE_LOGO1:
			logo1Time--;
			if (logo1Time < 0) {
				logo_state = STATE_LOGO2;
				logo2Time = 20;
			}
			break;
		case STATE_LOGO2:
			logo2Time--;

			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			switch (logo_state) {
			case STATE_LOGO1:
				logo_state = STATE_LOGO2;
				logo2Time = 20;
				break;
			case STATE_LOGO2:
				FlappyBirdActivity.me.gotoMenu();
				break;
			}
			return true;
		}
		if (event.getActionMasked() == MotionEvent.ACTION_UP) {
			// Ì§ÆðÆÁÄ»
			return true;
		}
		if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
			// »¬¶¯ÆÁÄ»
			return true;
		}
		return super.onTouchEvent(event);
	}
}