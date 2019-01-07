package github.hotstu.lib.wavedrawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

import java.lang.annotation.Retention;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static java.lang.annotation.RetentionPolicy.SOURCE;


/**
 * @author hglf <a href="https://github.com/hotstu">hglf</a>
 * @since 2018/12/26
 */
public class WaveDrawable extends Drawable implements Progressable, Animatable {

    @Retention(SOURCE)
    @IntDef({LEFT, TOP, RIGHT, BOTTOM})
    public @interface Direction {
    }

    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;

    private final Paint mPaint;
    private Path mWavePath;
    private float waveHeight;
    private float waveStrength;
    private float waveSpeed;

    private float dt;
    private float mProgress;
    private boolean running;
    private @Direction int direction;


    public WaveDrawable() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        waveHeight = -1;
        waveStrength = 0.01f;
        mWavePath = new Path();
        waveSpeed = 0.05f;
        dt = 0;
        direction = TOP;
    }


    public void setColor(@ColorInt int color) {
        mPaint.setColor(color);
        invalidateSelf();
    }

    /**
     * the directon the wave expanding, can be onf of TOP LEFT RIGHT BOTTOM
     * 方向，可以是TOP LEFT RIGHT BOTTOM
     * @param direction TOP LEFT RIGHT BOTTOM
     */
    public void setDirection(@Direction int direction) {
        this.direction = direction;
        invalidateSelf();
    }

    /**
     * the expanding progress, in [0, 1]
     * 进度 [0, 1]
     * @param progress [0, 1]
     */
    public void setProgress(@FloatRange(from = 0.0, to = 1.0) float progress) {
        if (mProgress != progress) {
            mProgress = progress;
            invalidateSelf();
        }
    }

    public float getProgress() {
        return mProgress;
    }


    /**
     * the height of the wave slop, default is height * .05f
     * 设置波峰高度，px,默认为height * .05f
     *
     * @param waveHeight default is height * .05f
     */
    public void setWaveHeight(float waveHeight) {
        if (this.waveHeight != waveHeight) {
            this.waveHeight = waveHeight;
            invalidateSelf();
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        if (waveHeight < 0) {
            waveHeight = bounds.height() * .05f;
        }
    }

    /**
     * the strength of the wave, the larger the more slop in area, default is 0.01f
     * 设置波浪强度 数值越大单位区域内峰谷越多,默认0.01f
     *
     * @param waveStrength default is 0.01f
     */
    public void setWaveStrength(float waveStrength) {
        if (this.waveStrength != waveStrength) {
            this.waveStrength = waveStrength;
            invalidateSelf();
        }
    }

    /**
     * the speed of the wave, the larger the wave change faster, default is 0.05f
     * 设置波浪速度 数值越大波浪变化越快,默认0.05f
     * @param speed default is 0.05f
     */
    public void setWaveSpeed(float speed) {
        if (this.waveSpeed != speed) {
            this.waveSpeed = speed;
            invalidateSelf();
        }
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        switch (direction) {
            case LEFT:
                drawLeft(canvas);
                break;
            case TOP:
                drawTop(canvas);
                break;
            case RIGHT:
                drawRight(canvas);
                break;
            case BOTTOM:
                drawBottom(canvas);
                break;
        }
        if (running) {
            invalidateSelf();
        }

    }


    private void drawLeft(Canvas canvas) {
        Rect bounds = getBounds();
        int height = bounds.height();
        int width = bounds.width();
        float xoff = 0f;
        mWavePath.rewind();
        float waveBias = width * (1 - mProgress) + waveHeight * (1 - mProgress) - waveHeight * (mProgress);
        for (int i = 0; i <= 320; i += 1) {
            double noise = SimplexNoise.noise(dt, xoff);
            mWavePath.lineTo(
                    map((float) noise, 0, 1, waveBias, waveBias + waveHeight),
                    map(i, 0, 320, 0, height));
            xoff += waveStrength;
        }
        dt += waveSpeed;
        mWavePath.lineTo(width, height);
        mWavePath.lineTo(width, 0);
        mWavePath.close();
        canvas.drawPath(mWavePath, mPaint);
    }

    private void drawTop(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        int height = bounds.height();
        int width = bounds.width();
        float xoff = 0f;
        mWavePath.rewind();
        float waveBias = height * (1 - mProgress) + waveHeight * (1 - mProgress) - waveHeight * (mProgress);
        for (int i = 0; i <= 320; i += 1) {
            double noise = SimplexNoise.noise(dt, xoff);
            mWavePath.lineTo(map(i, 0, 320, 0, width),
                    map((float) noise, 0, 1, waveBias, waveBias + waveHeight));
            xoff += waveStrength;
        }
        dt += waveSpeed;
        mWavePath.lineTo(width, height);
        mWavePath.lineTo(0, height);
        mWavePath.close();
        canvas.drawPath(mWavePath, mPaint);
    }

    private void drawRight(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        int height = bounds.height();
        int width = bounds.width();
        float xoff = 0f;
        mWavePath.rewind();
        float waveBias = width * (mProgress) + waveHeight * (mProgress) - waveHeight * (1 - mProgress);
        for (int i = 0; i <= 320; i += 1) {
            double noise = SimplexNoise.noise(dt, xoff);
            mWavePath.lineTo(
                    map((float) noise, 0, 1, waveBias, waveBias + waveHeight),
                    map(i, 0, 320, 0, height));
            xoff += waveStrength;
        }
        dt += waveSpeed;
        mWavePath.lineTo(0, height);
        mWavePath.lineTo(0, 0);
        mWavePath.close();
        canvas.drawPath(mWavePath, mPaint);
    }

    private void drawBottom(Canvas canvas) {
        Rect bounds = getBounds();
        int height = bounds.height();
        int width = bounds.width();
        float xoff = 0f;
        mWavePath.rewind();
        float waveBias = height * (mProgress) + waveHeight * (mProgress) - waveHeight * (1 - mProgress);
        for (int i = 0; i <= 320; i += 1) {
            double noise = SimplexNoise.noise(dt, xoff);
            mWavePath.lineTo(map(i, 0, 320, 0, width),
                    map((float) noise, 0, 1, waveBias, waveBias + waveHeight));
            xoff += waveStrength;
        }
        dt += waveSpeed;
        mWavePath.lineTo(width, 0);
        mWavePath.lineTo(0, 0);
        mWavePath.close();
        canvas.drawPath(mWavePath, mPaint);
    }


    private float map(float v, float fromMin, float fromMax, float toMin, float toMax) {

        if (fromMax <= fromMin || toMax <= toMin) {
            throw new IllegalArgumentException();
        }
        return v / (fromMax - fromMin) * (toMax - toMin) + toMin;
    }

    @Override
    public void setAlpha(int alpha) {
        if (alpha != mPaint.getAlpha()) {
            mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }


    @Override
    public void start() {
        running = true;
        invalidateSelf();
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
