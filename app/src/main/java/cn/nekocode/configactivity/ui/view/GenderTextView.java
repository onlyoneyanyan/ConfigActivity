package cn.nekocode.configactivity.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


/**
 * Created by nekocode on 15/6/4.
 */
public class GenderTextView extends View {
    private final static int TEXT_SIZE_SP = 14;
    private TextPaint textPaint;
    private int textSizePx;
    private ValueAnimator animationOffest;
    private int centerX = 0;
    private int margin = 0;
    private int selected = 0;
    private int marginLeft = 0;
    private int threshold = 0;
    private String title[] = new String[]{"♂", "♀"};
    private String gender[] = new String[]{"male", "female"};

    public GenderTextView(Context context) {
        super(context);
        init();
    }

    public GenderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GenderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textSizePx = sp2px(getContext(), TEXT_SIZE_SP);
        margin = (int) (textSizePx / 2.0f) + dip2px(getContext(), 10);
        marginLeft = dip2px(getContext(), 10);
        threshold = dip2px(getContext(), 10);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);
        textPaint.setTextSize(textSizePx);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }
    
    public String getSelected() {
        return gender[selected];
    }

    public void toggle() {

        if(animationOffest != null && animationOffest.isRunning()) {
            animationOffest.removeAllUpdateListeners();
            animationOffest.cancel();
        }

        int viewCenterX = (int) (getWidth() / 2.0f);
        if(selected == 0) {
            animationOffest = ValueAnimator.ofInt(centerX, viewCenterX - margin);
            selected = 1;
        } else {
            animationOffest = ValueAnimator.ofInt(centerX, viewCenterX + margin);
            selected = 0;
        }

        animationOffest.setInterpolator(new DecelerateInterpolator());
        animationOffest.setDuration(200);
        animationOffest.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                centerX = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animationOffest.start();
    }

    public void setSelected(String gender) {
        int viewCenterX = (int) (getWidth() / 2.0f);
        if(gender.equals("male")) {
            selected = 0;
            centerX = viewCenterX + margin;
        } else {
            selected =1;
            centerX = viewCenterX - margin;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int viewCenterX = (int) (getMeasuredWidth() / 2.0f);
        if(selected == 0) {
            centerX = viewCenterX + margin;
        } else {
            centerX = viewCenterX - margin;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerY = (int) (getHeight() / 2.0f) + (int) (textSizePx / 2.0f);
        int viewCenterX = (int) (getWidth() / 2.0f);
        int offsetX = centerX - viewCenterX;
        if(offsetX < -threshold) {
            offsetX = -threshold;
        } else if(offsetX > threshold) {
            offsetX = threshold;
        }
        int alpha = (int) ((offsetX * 1.0f / threshold) * 125);
        textPaint.setColor(0xff818181);
        textPaint.setAlpha(127 + alpha);
        canvas.drawText(title[0], centerX - margin + marginLeft, centerY, textPaint);

        textPaint.setColor(0xff818181);
        textPaint.setAlpha(127 - alpha);
        canvas.drawText(title[1], centerX + margin + marginLeft, centerY, textPaint);
    }

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
