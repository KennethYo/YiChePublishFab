package me.kenneth.yichepublishfab;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by kenneth on 2016/11/22.
 */

public class FloatingActionButton extends FrameLayout {

  private View closeIcon;
  private int closeIconSize;

  public FloatingActionButton(Context context) {
    super(context);
    init();
  }

  private void init() {
    setRotation(0);

    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
    int fabSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, displayMetrics);
    int fabBottomMargin =
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, displayMetrics);
    int fabRightMargin =
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, displayMetrics);

    FrameLayout.LayoutParams fabParams = new LayoutParams(fabSize, fabSize);
    fabParams.bottomMargin = fabBottomMargin;
    fabParams.rightMargin = fabRightMargin;
    fabParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
    setLayoutParams(fabParams);

    setBackgroundResource(R.drawable.bg_circle);

    closeIconSize =
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, displayMetrics);

    closeIcon = new View(getContext());
    closeIcon.setBackgroundResource(R.drawable.icon_add);

    FrameLayout.LayoutParams closeIconParams = new LayoutParams(closeIconSize, closeIconSize);
    closeIconParams.gravity = Gravity.CENTER;
    addView(closeIcon, closeIconParams);
  }

  public void openAnimator() {
    setRotation(0);
    PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
    PropertyValuesHolder scaleXHolder = PropertyValuesHolder.ofFloat(View.SCALE_X, 0);
    PropertyValuesHolder scaleYHolder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0);
    ObjectAnimator fabAnimator =
        ObjectAnimator.ofPropertyValuesHolder(this, rotationHolder, scaleXHolder, scaleYHolder);
    fabAnimator.addListener(new MyAnimatorListener() {
      @Override public void onAnimationEnd(Animator animation) {
        setVisibility(View.GONE);
      }
    });
    fabAnimator.start();
  }

  public void closeAnimator() {
    setRotation(45);
    setVisibility(View.VISIBLE);
    PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
    PropertyValuesHolder scaleXHolder = PropertyValuesHolder.ofFloat(View.SCALE_X, 1);
    PropertyValuesHolder scaleYHolder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1);
    ObjectAnimator.ofPropertyValuesHolder(this, rotationHolder, scaleXHolder, scaleYHolder).start();
  }
}
