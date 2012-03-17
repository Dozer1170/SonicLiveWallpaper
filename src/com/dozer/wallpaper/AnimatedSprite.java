package com.dozer.wallpaper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimatedSprite {
	private Bitmap mAnimation;
    public int mXPos;
    public int mYPos;
    private Rect mSRectangle;
    private int mFPS;
    private int mNoOfFrames;
    private int mCurrentFrame;
    private long mFrameTimer;
    public int mSpriteHeight;
    public int mSpriteWidth;
    
    public AnimatedSprite() {
        mSRectangle = new Rect(0,0,0,0);
        mFrameTimer = 0;
        mCurrentFrame = 0;
        mXPos = 80;
        mYPos = 200;
    }
    
    public void init(Bitmap theBitmap, int Height, int Width, int theFPS, int theFrameCount) {
        mAnimation = theBitmap;
        mSpriteHeight = Height;
        mSpriteWidth = Width;
        mSRectangle.top = 0;
        mSRectangle.bottom = mSpriteHeight;
        mSRectangle.left = 0;
        mSRectangle.right = mSpriteWidth;
        mFPS = 1000 /theFPS;
        mNoOfFrames = theFrameCount;
    }
    
    public void update(long gameTime) {
        if(gameTime > mFrameTimer + mFPS ) {
            mFrameTimer = gameTime;
            mCurrentFrame +=1;
     
            if(mCurrentFrame >= mNoOfFrames) {
                mCurrentFrame = 0;
            }
        }
     
        mSRectangle.left = mCurrentFrame * mSpriteWidth;
        mSRectangle.right = mSRectangle.left + mSpriteWidth;
    }
    
    public void draw(Canvas canvas) {
        Rect dest = new Rect(mXPos, mYPos, mXPos + mSpriteWidth,
        		mYPos + mSpriteHeight);
     
        canvas.drawBitmap(mAnimation, mSRectangle, dest, null);
    }
}
