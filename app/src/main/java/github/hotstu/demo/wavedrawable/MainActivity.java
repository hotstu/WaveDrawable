package github.hotstu.demo.wavedrawable;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import github.hotstu.lib.wavedrawable.WaveDrawable;

import static android.animation.ValueAnimator.INFINITE;

public class MainActivity extends AppCompatActivity {

    private ValueAnimator valueAnimator;
    private WaveDrawable background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background = new WaveDrawable();
        background.setColor(Color.parseColor("#90CAF9"));
        findViewById(R.id.view).setBackground(background);
        SeekBar seekbar = findViewById(R.id.seekBar);
        SeekBar seekbar2 = findViewById(R.id.seekBar2);
        SeekBar seekbar3 = findViewById(R.id.seekBar3);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                background.setProgress(progress * 0.01f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                background.setWaveSpeed(0.1f * progress * 0.01f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                background.setWaveStrength(0.02f * progress * 0.01f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setDuration(2000);
        //valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                background.setProgress(animation.getAnimatedFraction());

            }
        });
        valueAnimator.setRepeatCount(INFINITE);

    }


    public void start(View v) {
        valueAnimator.start();
    }

    public void end(View v) {
        valueAnimator.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        valueAnimator.cancel();
    }

    public void animate(View view) {
        if (background.isRunning()) {
            background.stop();
        } else {
            background.start();
        }
    }
}
