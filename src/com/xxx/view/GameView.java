package com.xxx.view;

import com.xxx.activity.BaseActivity;
import com.xxx.activity.GameActivity;
import com.xxx.sprite.Bird;
import com.xxx.sprite.Column;
import com.xxx.sprite.Ground;
import com.xxx.tools.Gua;
import com.xxx.tools.Music;
import com.xxx.tools.Score;
import com.xxx.tools.VibratorHelper;

import xxx.activity.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable, Callback {

	// ���ڿ���SurfaceView
	private SurfaceHolder sfh;
	// ����һ������
	private Paint paint;
	// ����һ���߳�
	private Thread th;
	// �߳������ı�ʶλ
	private boolean flag;
	// ����һ������
	private Canvas canvas;
	// ������Ļ�Ŀ��
	public static int screenW, screenH;
	// ������Ϸ״̬����
	public static final int GAME_MENU = 0; // ��Ϸ�˵�
	public static final int GAMEING = 1; // ��Ϸ��
	public static final int GAME_LOST = 2; // ��Ϸʧ��z
	// ��ǰ��Ϸ״̬(Ĭ�ϳ�ʼ����Ϸ�˵�����)
	public static int gameState = GAME_MENU;
	// ����һ��Resourcesʵ�����ڼ���ͼƬ
	private Resources res = this.getResources();
	// ����Ҫ���ص�ͼƬ
	private Bitmap background;
	private Bitmap gameoverImga;
	private Bitmap restartImg;
	private Bitmap scoreboardImg;
	private Bitmap startImg;
	private Bitmap bmpBird1s[];
	private Bitmap bmpBird2s[];
	private Bitmap bmpGround;
	private Bitmap bmpColumn;
	private Bitmap bmpn1;
	private Bitmap bmpn2;
	private Bitmap bmpn3;
	private Bitmap bmpn4;
	/** ���ͼƬ */
	private Bitmap bmpTap;

	private Column column1;
	private Column column2;
	private Ground ground;
	private Bird bird, bird1;
	public static int score;
	private RectF rectF;

	private boolean round = false;

	/**
	 * SurfaceView��ʼ������
	 */
	public GameView(Context context) {
		super(context);
		// ʵ��SurfaceHolder
		sfh = this.getHolder();
		// ΪSurfaceView���״̬����
		sfh.addCallback(this);
		// ʵ��һ������
		paint = new Paint();
		// ���û�����ɫΪ��ɫ
		paint.setColor(Color.WHITE);
		// ���ý���
		setFocusable(true);
	}

	/**
	 * SurfaceView��ͼ��������Ӧ�˺���
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		Log.e("gaoliang", "��һ��initGame������");
		initGame();
		rectF = new RectF(0, 0, screenW, screenH); // �ֱ�����Ļ�Ŀ�͸ߣ�Ҳ����������ͼƬ��ʾ�Ŀ�͸�
		flag = true;
		// ʵ���߳�
		th = new Thread(this);
		// �����߳�
		th.start();
	}

	/*
	 * �Զ������Ϸ��ʼ������
	 */
	private void initGame() {

		Log.e("gaoliang", "initGame������");
		// ������Ϸ�����̨���½�����Ϸʱ����Ϸ������!
		// ������Ϸ��Դ
		background = BitmapFactory.decodeResource(res, R.drawable.bg);
		gameoverImga = BitmapFactory.decodeResource(res, R.drawable.gameovera);
		restartImg = BitmapFactory.decodeResource(res, R.drawable.restart);
		scoreboardImg = BitmapFactory
				.decodeResource(res, R.drawable.scoreboard);
		startImg = BitmapFactory.decodeResource(res, R.drawable.start);
		bmpBird1s = new Bitmap[3];
		bmpBird2s = new Bitmap[3];
		bmpBird1s[0] = BitmapFactory.decodeResource(res, R.drawable.bird1);
		bmpBird1s[1] = BitmapFactory.decodeResource(res, R.drawable.bird2);
		bmpBird1s[2] = BitmapFactory.decodeResource(res, R.drawable.bird3);
		bmpBird2s[0] = BitmapFactory.decodeResource(res, R.drawable.bird4);
		bmpBird2s[1] = BitmapFactory.decodeResource(res, R.drawable.bird5);
		bmpBird2s[2] = BitmapFactory.decodeResource(res, R.drawable.bird6);
		bmpGround = BitmapFactory.decodeResource(res, R.drawable.ground);
		bmpColumn = BitmapFactory.decodeResource(res, R.drawable.column);

		bmpn1 = BitmapFactory.decodeResource(res, R.drawable.n1);
		bmpn2 = BitmapFactory.decodeResource(res, R.drawable.n2);
		bmpn3 = BitmapFactory.decodeResource(res, R.drawable.n3);
		bmpn4 = BitmapFactory.decodeResource(res, R.drawable.n4);

		bmpTap = BitmapFactory.decodeResource(res, R.drawable.tap);
		// ��ʼ��״̬
		// ʵ����
		bird = new Bird(screenW, screenH, bmpBird1s);
		bird1 = new Bird(screenW, screenH, bmpBird2s);
		// ʵ������
		ground = new Ground(screenH, bmpGround);
		// ʵ����������
		column1 = new Column(screenW, bmpColumn);
		column2 = new Column(screenW + screenW / 4 * 3, bmpColumn);
		// ��ʼ������
		score = 0;
	}

	/**
	 * ��Ϸ��ͼ
	 */
	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawRGB(0, 0, 0);
				// ���Ʊ���(�����뿴����http://blog.csdn.net/centralperk/article/details/7662130)
				canvas.drawBitmap(background, null, rectF, null);
				// ��������
				column1.draw(canvas, paint);
				column2.draw(canvas, paint);
				// ���Ƶ���
				ground.draw(canvas, paint);
				// ���û�������(���壬��ɫ)
				paint.setTextSize(40);
				paint.setColor(Color.BLACK);
				// canvas.drawText("���: " + (column2.x - column1.x), 300, 50,
				// paint);

				// ��ͼ����������Ϸ״̬��ͬ���в�ͬ����
				switch (gameState) {
				case GAME_MENU:
					// �˵��Ļ�ͼ����
					canvas.drawBitmap(startImg, null, rectF, null);

					bird.draw(canvas, paint);
					bird1.draw(canvas, paint);
					break;
				case GAMEING:
					// ���ǻ�ͼ����
					bird.draw(canvas, paint);
					bird1.draw(canvas, paint);
					// ���û�������(���壬��ɫ)
					paint.setTextSize(80);
					paint.setColor(Color.BLACK);
					// �������
					paint.setFlags(Paint.ANTI_ALIAS_FLAG);
					// ���Ʒ���
					canvas.drawText("" + score, screenW / 2
							- (((Integer) score).toString().length()) / 2
							* paint.getTextSize() + 2, screenH / 4 + 2, paint);
					paint.setColor(Color.GRAY);
					canvas.drawText("" + score, screenW / 2
							- (((Integer) score).toString().length()) / 2
							* paint.getTextSize(), screenH / 4, paint);
					if (Gua.tapOn) {
						canvas.drawBitmap(bmpTap, BaseActivity.ScreenW / 2
								- bmpTap.getWidth() / 2, BaseActivity.ScreenH
								- bmpTap.getHeight(), paint);
						Gua.tapOn = false;
					}
					break;
				case GAME_LOST:
					// ���û�������(���壬��ɫ)
					paint.setTextSize(30);
					paint.setColor(Color.BLACK);
					// ���ǻ�ͼ����
					bird1.draw(canvas, paint);
					bird.draw(canvas, paint);
					canvas.drawBitmap(gameoverImga,
							screenW / 2 - gameoverImga.getWidth() / 2, 0, paint);
					canvas.drawBitmap(scoreboardImg, screenW / 2
							- scoreboardImg.getWidth() / 2, screenH / 2
							- scoreboardImg.getHeight() / 2, paint);
					canvas.drawBitmap(restartImg, screenW / 3, screenH / 3 * 2,
							paint);

					// ���Ʊ��η���
					canvas.drawText("" + score, screenW / 10 * 7, screenH / 2,
							paint);
					// ������߷���
					canvas.drawText("" + Score.score, screenW / 10 * 7,
							screenH / 2 + 50, paint);

					paint.setColor(Color.GRAY);
					canvas.drawText("" + score, screenW / 10 * 7 - 1,
							screenH / 2 + 1, paint);
					canvas.drawText("" + Score.score, screenW / 10 * 7 - 1,
							screenH / 2 + 51, paint);
					if (score <= 10) {
						canvas.drawBitmap(
								bmpn1,
								screenW / 2 - scoreboardImg.getWidth() / 11 * 4,
								screenH / 2 - 20, paint);
					} else if (score > 10 && score <= 20) {
						canvas.drawBitmap(
								bmpn2,
								screenW / 2 - scoreboardImg.getWidth() / 11 * 4,
								screenH / 2 - 20, paint);
					} else if (score > 20 && score <= 30) {
						canvas.drawBitmap(
								bmpn3,
								screenW / 2 - scoreboardImg.getWidth() / 11 * 4,
								screenH / 2 - 20, paint);
					} else if (score > 30) {
						canvas.drawBitmap(
								bmpn4,
								screenW / 2 - scoreboardImg.getWidth() / 11 * 4,
								screenH / 2 - 20, paint);
					}
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	/**
	 * �����¼�����
	 */

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Log.e("gaoliang", "��״̬��"+ gameState);
		// ��ͼ����������Ϸ״̬��ͬ���в�ͬ����
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (event.getX() < screenW / 2) {
				bird.flappy();
			}
			if (event.getX() >= screenW / 2) {
				bird1.flappy();
			}
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MASK) {
			// Log.e("gaoliang", "��״̬��"+ gameState);
			switch (gameState) {
			case GAME_MENU: // �˵�״̬
				initGame();
				gameState = GAMEING;
				break;
			case GAMEING: // ��Ϸ״̬
				if (event.getX() < screenW / 2) {
					bird.flappy();
				}
				if (event.getX() >= screenW / 2) {
					bird1.flappy();
				}
				return true;
			case GAME_LOST: // ��Ϸʧ��
				bird.x = screenW / 3; // ��ĳ�ʼλ��
				bird.y = screenH / 2; // ��ĳ�ʼλ��
				bird.x = screenW / 3 - 30; // ��ĳ�ʼλ��
				bird.y = screenH / 2 - 30; // ��ĳ�ʼλ��
				bird.angle = 0;
				bird1.angle = 0;
				gameState = GAME_MENU;
				return true;
			}
		}

		return true;
	}

	/**
	 * ��Ϸ�߼�
	 */
	private void logic() {
		// ��ͼ����������Ϸ״̬��ͬ���в�ͬ�߼�
		switch (gameState) {
		case GAME_MENU:
			ground.step();
			// y�����Ƶ�����һ�����ϡ������ķ�֮һ����
			if (round) {
				bird.y--;
				if (bird.y <= (int) (screenH / 2 - 5)) {
					round = false;
				}
			} else {
				bird.y++;
				if (bird.y >= (int) (screenH / 2 + 5)) {
					round = true;
				}
			}
			break;
		case GAMEING:
			bird.step();
			bird1.step();
			column1.step();
			column2.step();
			// ����Ƿ�ͨ��������
			if (bird.pass(column1, column2)) {
				Music.playSound(2);
				score++;
				if (score > Score.score) {
					Score.saveScore(score);
				}
			}
			if (bird1.pass(column1, column2)) {
				Music.playSound(2);
				score++;
				if (score > Score.score) {
					Score.saveScore(score);
				}
			}
			if (Gua.guaOn) {
				if (score == 0) {
					Gua.gua(bird, column1);
				}
				Gua.gua(bird, column1, column2);
			}

			if (bird.hit(column1, column2, ground)||bird1.hit(column1, column2, ground)) {

				Music.playSound(3);
				//��
				VibratorHelper.Vibrate(GameActivity.me, 500l);
				gameState = GAME_LOST;
			}

			ground.step();

			column1.setSpeed(score / 10 + 1);
			column2.setSpeed(score / 10 + 1);

			break;
		case GAME_LOST:
			Score.loadScore();
			break;
		}
	}

	@Override
	public void run() {
		while (flag) {
			long start = System.currentTimeMillis();
			myDraw();
			logic();

			long end = System.currentTimeMillis();
			try {
				if (end - start < 50) {
					Thread.sleep(50 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���������¼�����
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ����back���ذ���
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// ��Ϸʤ����ʧ�ܡ�����ʱ��Ĭ�Ϸ��ز˵�
			if (gameState == GAMEING || gameState == GAME_LOST) {
				gameState = GAME_MENU;
				// ������Ϸ
				// initGame();
			}
			// ��ʾ�˰����Ѵ������ٽ���ϵͳ����
			// �Ӷ�������Ϸ�������̨
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * SurfaceView��ͼ״̬�����ı䣬��Ӧ�˺���
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	/**
	 * SurfaceView��ͼ����ʱ����Ӧ�˺���
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
		releaseBitmap();
	}

	/**
	 * �ͷ�Bitmap��Դ
	 */
	private void releaseBitmap() {
		// TODO Auto-generated method stub
		background.recycle();
		gameoverImga.recycle();
		restartImg.recycle();
		scoreboardImg.recycle();
		startImg.recycle();
		bmpBird1s = null;
		bmpBird2s = null;
		bmpGround.recycle();
		bmpColumn.recycle();

		bmpn1.recycle();
		bmpn2.recycle();
		bmpn3.recycle();
		bmpn4.recycle();

		bmpTap.recycle();
	}
}