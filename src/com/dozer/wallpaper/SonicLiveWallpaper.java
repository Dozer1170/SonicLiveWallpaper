package com.dozer.wallpaper;

import java.io.IOException;
import java.io.InputStream;

import android.app.WallpaperManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

public class SonicLiveWallpaper extends WallpaperService {

	private final Handler mHandler = new Handler();
	private final String TAG = "com.dozer.wallpaper";

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public Engine onCreateEngine() {
		return new SonicEngine();
	}

	class SonicEngine extends Engine {

		private float mOffset;
		private long mTimer;
		private Bitmap bg, bg2, sonicBitmap, tailsBitmap, knucklesBitmap;
		private float screenHeight, screenWidth;
		private float bitmapWidth, bitmapHeight;
		private float canvasWidth, canvasHeight;
		private float scaleX,scaleY;
		private int screenLoc;
		private AnimatedSprite sonic;
		private AnimatedSprite knuckles;
		private AnimatedSprite tails;
		private int sonicWorldX;
		private int sonicWorldY;
		private int tailsWorldX;
		private int tailsWorldY;
		private int knucklesWorldX;
		private int knucklesWorldY;
		private int sonicPathIndex;
		private int tailsPathIndex;
		private short bgPath[] = { 705, 707, 707, 707, 706, 706, 706, 706, 706,
				706, 705, 705, 705, 705, 705, 705, 704, 704, 704, 704, 704,
				704, 703, 703, 703, 703, 703, 703, 703, 703, 703, 703, 703,
				703, 703, 703, 703, 703, 703, 703, 703, 703, 703, 703, 703,
				703, 703, 703, 703, 703, 703, 703, 703, 703, 703, 703, 703,
				703, 703, 703, 703, 703, 703, 703, 703, 703, 703, 703, 703,
				703, 703, 703, 703, 703, 685, 703, 703, 702, 702, 702, 702,
				702, 702, 702, 702, 702, 702, 702, 702, 702, 702, 702, 702,
				702, 702, 701, 701, 701, 701, 701, 701, 701, 701, 701, 701,
				701, 701, 701, 701, 701, 701, 701, 701, 700, 700, 700, 700,
				700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700,
				700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700,
				700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700,
				700, 700, 700, 699, 699, 699, 699, 699, 699, 699, 699, 699,
				699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699,
				699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699,
				699, 699, 699, 699, 699, 697, 699, 699, 699, 699, 699, 699,
				699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699,
				699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699,
				699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699,
				699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699,
				699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699,
				699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699,
				699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699,
				699, 699, 699, 699, 699, 699, 699, 700, 700, 700, 700, 700,
				700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700,
				700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 701,
				701, 701, 701, 701, 701, 701, 701, 702, 702, 702, 702, 702,
				702, 702, 702, 703, 703, 703, 703, 703, 703, 703, 703, 703,
				704, 704, 704, 704, 704, 704, 704, 704, 705, 705, 705, 705,
				705, 705, 705, 705, 706, 706, 706, 706, 706, 706, 706, 706,
				707, 707, 707, 707, 707, 707, 707, 708, 708, 708, 708, 708,
				708, 708, 709, 709, 709, 709, 709, 709, 709, 709, 710, 710,
				710, 710, 710, 710, 710, 711, 711, 711, 711, 711, 711, 711,
				711, 712, 712, 712, 712, 712, 712, 712, 712, 712, 712, 712,
				712, 712, 712, 712, 713, 713, 713, 713, 713, 713, 713, 713,
				713, 713, 713, 713, 713, 713, 713, 713, 713, 713, 713, 713,
				713, 713, 713, 713, 714, 714, 714, 714, 714, 714, 714, 714,
				714, 714, 714, 714, 714, 714, 714, 714, 714, 713, 713, 713,
				713, 713, 713, 713, 713, 712, 712, 712, 712, 712, 712, 712,
				712, 711, 711, 711, 711, 711, 711, 711, 711, 711, 710, 710,
				710, 710, 710, 710, 710, 710, 709, 709, 709, 709, 709, 709,
				709, 709, 708, 708, 708, 708, 708, 708, 708, 708, 708, 707,
				707, 707, 707, 707, 707, 707, 707, 706, 706, 706, 706, 706,
				706, 706, 706, 705, 705, 705, 705, 705, 705, 705, 705, 705,
				704, 704, 704, 704, 704, 704, 704, 704, 703, 703, 703, 703,
				703, 703, 703, 703, 702, 702, 702, 702, 702, 702, 702, 702,
				702, 701, 701, 701, 701, 701, 701, 701, 701, 700, 700, 700,
				700, 700, 700, 700, 700, 699, 699, 699, 699, 699, 699, 699,
				699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699,
				699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699, 699,
				699, 699, 699, 699, 700, 700, 700, 700, 700, 700, 700, 700,
				700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700,
				700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700,
				700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700,
				700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700, 700,
				701, 701, 701, 701, 702, 702, 702, 702, 703, 703, 703, 703,
				704, 704, 704, 705, 705, 705, 705, 706, 706, 706, 706, 707,
				707, 707, 708, 708, 708, 708, 709, 709, 709, 709, 710, 710,
				710, 710, 711, 711, 711, 711, 711, 711, 711, 711, 711, 711,
				711, 711, 711, 711, 711, 589, 711, 711, 711, 711, 711, 711,
				711, 711, 711, 711, 711, 711, 711, 711, 711, 711, 711, 710,
				710, 710, 710, 709, 709, 709, 709, 708, 708, 708, 708, 707,
				707, 707, 707, 706, 706, 706, 706, 705, 705, 705, 704, 704,
				704, 704, 703, 703, 703, 703, 702, 702, 702, 701, 701, 701,
				701, 700, 700, 699, 699, 698, 698, 697, 697, 696, 696, 695,
				695, 694, 694, 693, 693, 692, 692, 691, 691, 690, 690, 689,
				689, 688, 688, 687, 687, 686, 686, 685, 685, 684, 684, 683,
				683, 682, 682, 681, 681, 680, 680, 679, 679, 678, 678, 677,
				677, 676, 676, 675, 675, 674, 674, 673, 673, 672, 672, 671,
				671, 670, 669, 669, 668, 668, 667, 667, 666, 665, 665, 664,
				664, 663, 662, 662, 661, 661, 660, 660, 659, 659, 658, 658,
				657, 657, 657, 656, 656, 655, 655, 654, 654, 654, 653, 653,
				652, 652, 652, 651, 651, 650, 650, 649, 649, 649, 648, 648,
				647, 647, 646, 646, 645, 645, 644, 644, 643, 643, 642, 642,
				641, 641, 640, 640, 639, 639, 638, 638, 637, 637, 636, 635,
				635, 634, 634, 633, 633, 632, 632, 631, 631, 630, 630, 629,
				629, 628, 628, 627, 627, 626, 626, 625, 625, 624, 624, 623,
				623, 623, 622, 622, 621, 621, 620, 620, 620, 619, 619, 618,
				618, 617, 617, 616, 616, 616, 615, 615, 614, 614, 613, 613,
				612, 612, 612, 611, 611, 611, 611, 610, 610, 610, 609, 609,
				609, 608, 608, 608, 608, 607, 607, 607, 606, 606, 606, 606,
				606, 606, 606, 606, 606, 606, 606, 606, 606, 606, 606, 606,
				606, 606, 606, 606, 606, 606, 606, 606, 606, 606, 606, 606,
				606, 606, 606, 606, 606, 606, 606, 606, 606, 606, 606, 606,
				606, 606, 605, 605, 605, 605, 605 };
		WallpaperManager manager = WallpaperManager
				.getInstance(getApplicationContext());
		Resources res = getResources();

		private final Runnable drawWallpaper = new Runnable() {
			public void run() {
				drawFrame();
			}
		};
		private boolean mVisible;

		SonicEngine() {
			android.os.Debug.waitForDebugger();
			Log.i(TAG, "Initializing Sonic wallpaper!");
			screenHeight = manager.getDesiredMinimumHeight();
			screenWidth = manager.getDesiredMinimumWidth();
			bg = getBitmap(R.drawable.sonicclippedbg);
			bg2 = getBitmap(R.drawable.bggreenhill);
			bitmapHeight = bg.getHeight();
			bitmapWidth = bg.getWidth();
			scaleY = screenHeight / bitmapHeight;
			scaleX = screenWidth / bitmapWidth;
			sonic = new AnimatedSprite();
			sonicBitmap = getBitmap(R.drawable.sonicrunspritesheet);
			sonic.init(sonicBitmap, sonicBitmap.getHeight(),
					sonicBitmap.getWidth() / 4, 60, 4);
			tails = new AnimatedSprite();
			tailsBitmap = getBitmap(R.drawable.tailspritesheet);
			tails.init(tailsBitmap, tailsBitmap.getHeight(),
					tailsBitmap.getWidth() / 4, 30, 4);
			sonic.mYPos = 610;
			sonicWorldX = 0;
			sonicWorldY = 705;
			tailsWorldX = -128;
			tailsWorldY = 705;
			tails.mYPos = 610;
			knuckles = new AnimatedSprite();
			knucklesBitmap = getBitmap(R.drawable.knuckles);
			knuckles.init(knucklesBitmap, knucklesBitmap.getHeight(),
					knucklesBitmap.getWidth(), 60, 1);
			knuckles.mYPos = 200;
			knucklesWorldX = 512;
			knucklesWorldY = 200;
		}
		
		public Bitmap getBitmap(int id) {
			return BitmapFactory.decodeResource(res, id);
		}

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
			android.os.Debug.waitForDebugger();
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			mHandler.removeCallbacks(drawWallpaper);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			mVisible = visible;
			if (visible) {
				drawFrame();
			} else {
				mHandler.removeCallbacks(drawWallpaper);
			}
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			this.canvasWidth = width;
			this.canvasHeight = height;
			scaleY = screenHeight / canvasHeight;
			scaleX = screenWidth / canvasWidth;
			drawFrame();
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			mHandler.removeCallbacks(drawWallpaper);
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
				float yStep, int xPixels, int yPixels) {
			mOffset = xOffset;
			drawFrame();
		}

		/*
		 * Draw one frame of the animation. This method gets called repeatedly
		 * by posting a delayed Runnable. You can do any drawing you want in
		 * here.
		 */
		void drawFrame() {
			final SurfaceHolder holder = getSurfaceHolder();
			mTimer = System.currentTimeMillis();

			Canvas c = null;
			try {
				c = holder.lockCanvas();
				if (c != null) {
					screenLoc = (int) ((screenWidth - bitmapWidth) * mOffset);
					c.drawBitmap(bg2, (screenWidth - bitmapWidth)
							* (mOffset / 2), 0, null);
					c.drawBitmap(bg, screenLoc, 0, null);
					sonic.update(mTimer);
					tails.update(mTimer);
					sonicWorldX += 20;
					tailsWorldX += 20;
					knucklesWorldX += 15;
					knucklesWorldY += 1;
					if (sonicWorldX > bitmapWidth) {
						sonicWorldX = -128;
						sonicWorldY = bgPath[0] - sonic.mSpriteHeight;
						sonic.mYPos = sonicWorldY;
					}

					if (tailsWorldX > bitmapWidth) {
						tailsWorldX = -128;
						tailsWorldY = bgPath[0] - tails.mSpriteHeight;
						tails.mYPos = tailsWorldY;
					}

					if (knucklesWorldX > bitmapWidth) {
						knucklesWorldX = -128;
						knucklesWorldY = 200;
						knuckles.mYPos = knucklesWorldY;
					}

					sonic.mXPos = sonicWorldX + screenLoc;
					tails.mXPos = tailsWorldX + screenLoc;
					knuckles.mXPos = knucklesWorldX + screenLoc;
					sonicPathIndex = sonicWorldX + 64;
					tailsPathIndex = tailsWorldX + 36;
					if (sonicPathIndex > 0 && sonicPathIndex < 1024) {
						sonicWorldY = bgPath[sonicPathIndex]
								- sonic.mSpriteHeight;
						sonic.mYPos = sonicWorldY;
					}

					if (tailsPathIndex > 0 && tailsPathIndex < 1024) {
						tailsWorldY = bgPath[tailsPathIndex]
								- tails.mSpriteHeight;
						tails.mYPos = tailsWorldY;
					}

					knuckles.mYPos = knucklesWorldY;

					if (sonic.mXPos + 128 > 0 && sonic.mXPos < screenWidth)
						sonic.draw(c);

					if (tails.mXPos + 128 > 0 && tails.mXPos < screenWidth)
						tails.draw(c);

					if (knuckles.mXPos + 128 > 0
							&& knuckles.mXPos < screenWidth)
						knuckles.draw(c);
					//c.scale(1,scaleY);
				}
			} finally {
				if (c != null)
					holder.unlockCanvasAndPost(c);
			}

			// Reschedule the next redraw
			mHandler.removeCallbacks(drawWallpaper);
			if (mVisible) {
				mHandler.postDelayed(drawWallpaper, 1000 / 60);
			}
		}

	}
}