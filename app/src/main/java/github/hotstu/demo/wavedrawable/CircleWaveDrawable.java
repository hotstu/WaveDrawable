package github.hotstu.demo.wavedrawable;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

import androidx.annotation.NonNull;
import github.hotstu.lib.wavedrawable.WaveDrawable;

/**
 * @author hglf
 * @since 2018/12/28
 * 说明
 */
public class CircleWaveDrawable extends WaveDrawable {

    private final Path mClipPath;

    public CircleWaveDrawable() {
        mClipPath = new Path();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mClipPath.rewind();
        mClipPath.addCircle(bounds.centerX(), bounds.centerY(),
                Math.min(bounds.width(), bounds.height()) * .5f, Path.Direction.CW);
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.clipPath(mClipPath);
        super.draw(canvas);
    }
}
