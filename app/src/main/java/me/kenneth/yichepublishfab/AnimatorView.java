package me.kenneth.yichepublishfab;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by kenneth on 2016/11/22.
 */

public class AnimatorView extends View {
  private Paint paint;
  private RoundedRectangleDrawable drawable = new RoundedRectangleDrawable();
  private RoundedRectangleParams params = new RoundedRectangleParams();
  private int bottomMargin;
  private int rightMargin;
  private int x, y;
  private int fabRadius;

  public AnimatorView(Context context) {
    super(context);
    init();
  }

  private void init() {
    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
    fabRadius =
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, displayMetrics) / 2;
    bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, displayMetrics)
        + fabRadius;
    rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, displayMetrics)
        + fabRadius;

    int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 140, displayMetrics);
    FrameLayout.LayoutParams params =
        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
    params.gravity = Gravity.BOTTOM;
    setLayoutParams(params);

    drawable.view = this;

    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setColor(getResources().getColor(R.color.blue));
    paint.setStyle(Paint.Style.FILL);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    int width = MeasureSpec.getSize(widthMeasureSpec);
    int height = MeasureSpec.getSize(heightMeasureSpec);

    x = width - rightMargin;
    y = height - bottomMargin;

    params.fromLeft = x - fabRadius;
    params.toLeft = 0;

    params.fromTop = y - fabRadius;
    params.toTop = 0;

    params.fromRight = x + fabRadius;
    params.toRight = width;

    params.fromBottom = y + fabRadius;
    params.toBottom = height;

    params.fromCornerRadius = fabRadius;
    params.toCornerRadius = 0;
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawRoundRect(drawable.getRectF(), drawable.cornerRadius, drawable.cornerRadius, paint);
  }

  public void openAnimator() {
    setVisibility(VISIBLE);
    ObjectAnimator left = ObjectAnimator.ofFloat(drawable, "left", params.fromLeft, params.toLeft);
    ObjectAnimator top = ObjectAnimator.ofFloat(drawable, "top", params.fromTop, params.toTop);
    ObjectAnimator right =
        ObjectAnimator.ofFloat(drawable, "right", params.fromRight, params.toRight);
    ObjectAnimator bottom =
        ObjectAnimator.ofFloat(drawable, "bottom", params.fromBottom, params.toBottom);
    ObjectAnimator cornerRadius =
        ObjectAnimator.ofFloat(drawable, "cornerRadius", params.fromCornerRadius,
            params.toCornerRadius);

    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playTogether(left, top, right, bottom, cornerRadius);
    animatorSet.start();
  }

  public void closeAnimator() {
    ObjectAnimator left = ObjectAnimator.ofFloat(drawable, "left", params.toLeft, params.fromLeft);
    ObjectAnimator top = ObjectAnimator.ofFloat(drawable, "top", params.toTop, params.fromTop);
    ObjectAnimator right =
        ObjectAnimator.ofFloat(drawable, "right", params.toRight, params.fromRight);
    ObjectAnimator bottom =
        ObjectAnimator.ofFloat(drawable, "bottom", params.toBottom, params.fromBottom);
    ObjectAnimator cornerRadius =
        ObjectAnimator.ofFloat(drawable, "cornerRadius", params.toCornerRadius,
            params.fromCornerRadius);

    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playTogether(left, top, right, bottom, cornerRadius);
    animatorSet.addListener(new MyAnimatorListener() {
      @Override public void onAnimationEnd(Animator animation) {
        setVisibility(GONE);
      }
    });
    animatorSet.start();
  }

  static class RoundedRectangleParams {
    public float fromLeft;
    public float toLeft;

    public float fromTop;
    public float toTop;

    public float fromRight;
    public float toRight;

    public float fromBottom;
    public float toBottom;

    public float fromCornerRadius;
    public float toCornerRadius;
  }

  /**
   * Created by kenneth on 2016/11/22.
   */
  static class RoundedRectangleDrawable {
    public float left;
    public float top;
    public float right;
    public float bottom;
    public float cornerRadius;
    public View view;

    public float getLeft() {
      return left;
    }

    public void setLeft(float left) {
      this.left = left;
      view.postInvalidate();
    }

    public float getTop() {
      return top;
    }

    public void setTop(float top) {
      this.top = top;
      view.postInvalidate();
    }

    public float getRight() {
      return right;
    }

    public void setRight(float right) {
      this.right = right;
      view.postInvalidate();
    }

    public float getBottom() {
      return bottom;
    }

    public void setBottom(float bottom) {
      this.bottom = bottom;
      view.postInvalidate();
    }

    public float getCornerRadius() {
      return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
      this.cornerRadius = cornerRadius;
      view.postInvalidate();
    }

    public RectF getRectF() {
      return new RectF(left, top, right, bottom);
    }
  }
}
