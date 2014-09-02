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

	// 用于控制SurfaceView
	private SurfaceHolder sfh;
	// 声明一个画笔
	private Paint paint;
	// 声明一条线程
	private Thread th;
	// 线程消亡的标识位
	private boolean flag;
	// 声明一个画布
	private Canvas canvas;
	// 声明屏幕的宽高
	public static int screenW, screenH;
	// 定义游戏状态常量
	public static final int GAME_MENU = 0; // 游戏菜单
	public static final int GAMEING = 1; // 游戏中
	public static final int GAME_LOST = 2; // 游戏失败z
	// 当前游戏状态(默认初始在游戏菜单界面)
	public static int gameState = GAME_MENU;
	// 声明一个Resources实例便于加载图片
	private Resources res = this.getResources();
	// 声明要加载的图片
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
	/** 点击图片 */
	private Bitmap bmpTap;

	private Column column1;
	private Column column2;
	private Ground ground;
	private Bird bird, bird1;
	public static int score;
	private RectF rectF;

	private boolean round = false;

	/**
	 * SurfaceView初始化函数
	 */
	public GameView(Context context) {
		super(context);
		// 实例SurfaceHolder
		sfh = this.getHolder();
		// 为SurfaceView添加状态监听
		sfh.addCallback(this);
		// 实例一个画笔
		paint = new Paint();
		// 设置画笔颜色为白色
		paint.setColor(Color.WHITE);
		// 设置焦点
		setFocusable(true);
	}

	/**
	 * SurfaceView视图创建，响应此函数
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		Log.e("gaoliang", "第一次initGame！！！");
		initGame();
		rectF = new RectF(0, 0, screenW, screenH); // 分别是屏幕的宽和高，也就是你想让图片显示的宽和高
		flag = true;
		// 实例线程
		th = new Thread(this);
		// 启动线程
		th.start();
	}

	/*
	 * 自定义的游戏初始化函数
	 */
	private void initGame() {

		Log.e("gaoliang", "initGame！！！");
		// 放置游戏切入后台重新进入游戏时，游戏被重置!
		// 加载游戏资源
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
		// 初始化状态
		// 实例鸟
		bird = new Bird(screenW, screenH, bmpBird1s);
		bird1 = new Bird(screenW, screenH, bmpBird2s);
		// 实例地面
		ground = new Ground(screenH, bmpGround);
		// 实例两个管子
		column1 = new Column(screenW, bmpColumn);
		column2 = new Column(screenW + screenW / 4 * 3, bmpColumn);
		// 初始化分数
		score = 0;
	}

	/**
	 * 游戏绘图
	 */
	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawRGB(0, 0, 0);
				// 绘制背景(不懂请看这里http://blog.csdn.net/centralperk/article/details/7662130)
				canvas.drawBitmap(background, null, rectF, null);
				// 绘制柱子
				column1.draw(canvas, paint);
				column2.draw(canvas, paint);
				// 绘制地面
				ground.draw(canvas, paint);
				// 设置画笔属性(字体，颜色)
				paint.setTextSize(40);
				paint.setColor(Color.BLACK);
				// canvas.drawText("间隔: " + (column2.x - column1.x), 300, 50,
				// paint);

				// 绘图函数根据游戏状态不同进行不同绘制
				switch (gameState) {
				case GAME_MENU:
					// 菜单的绘图函数
					canvas.drawBitmap(startImg, null, rectF, null);

					bird.draw(canvas, paint);
					bird1.draw(canvas, paint);
					break;
				case GAMEING:
					// 主角绘图函数
					bird.draw(canvas, paint);
					bird1.draw(canvas, paint);
					// 设置画笔属性(字体，颜色)
					paint.setTextSize(80);
					paint.setColor(Color.BLACK);
					// 消除锯齿
					paint.setFlags(Paint.ANTI_ALIAS_FLAG);
					// 绘制分数
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
					// 设置画笔属性(字体，颜色)
					paint.setTextSize(30);
					paint.setColor(Color.BLACK);
					// 主角绘图函数
					bird1.draw(canvas, paint);
					bird.draw(canvas, paint);
					canvas.drawBitmap(gameoverImga,
							screenW / 2 - gameoverImga.getWidth() / 2, 0, paint);
					canvas.drawBitmap(scoreboardImg, screenW / 2
							- scoreboardImg.getWidth() / 2, screenH / 2
							- scoreboardImg.getHeight() / 2, paint);
					canvas.drawBitmap(restartImg, screenW / 3, screenH / 3 * 2,
							paint);

					// 绘制本次分数
					canvas.drawText("" + score, screenW / 10 * 7, screenH / 2,
							paint);
					// 绘制最高分数
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
	 * 触屏事件监听
	 */

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Log.e("gaoliang", "外状态："+ gameState);
		// 绘图函数根据游戏状态不同进行不同绘制
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
			// Log.e("gaoliang", "内状态："+ gameState);
			switch (gameState) {
			case GAME_MENU: // 菜单状态
				initGame();
				gameState = GAMEING;
				break;
			case GAMEING: // 游戏状态
				if (event.getX() < screenW / 2) {
					bird.flappy();
				}
				if (event.getX() >= screenW / 2) {
					bird1.flappy();
				}
				return true;
			case GAME_LOST: // 游戏失败
				bird.x = screenW / 3; // 鸟的初始位置
				bird.y = screenH / 2; // 鸟的初始位置
				bird.x = screenW / 3 - 30; // 鸟的初始位置
				bird.y = screenH / 2 - 30; // 鸟的初始位置
				bird.angle = 0;
				bird1.angle = 0;
				gameState = GAME_MENU;
				return true;
			}
		}

		return true;
	}

	/**
	 * 游戏逻辑
	 */
	private void logic() {
		// 绘图函数根据游戏状态不同进行不同逻辑
		switch (gameState) {
		case GAME_MENU:
			ground.step();
			// y往下移到多于一半向上、少于四分之一再下
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
			// 检查是否通过柱子了
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
				//震动
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
	 * 按键按下事件监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 处理back返回按键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 游戏胜利、失败、进行时都默认返回菜单
			if (gameState == GAMEING || gameState == GAME_LOST) {
				gameState = GAME_MENU;
				// 重置游戏
				// initGame();
			}
			// 表示此按键已处理，不再交给系统处理，
			// 从而避免游戏被切入后台
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * SurfaceView视图状态发生改变，响应此函数
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	/**
	 * SurfaceView视图消亡时，响应此函数
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
		releaseBitmap();
	}

	/**
	 * 释放Bitmap资源
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