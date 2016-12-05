package me.kenneth.yichepublishfab;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kenneth on 2016/11/22.
 */

public class YiCheFab implements View.OnClickListener {

  private Activity activity;

  private FloatingActionButton fab;
  private View backgroundView;
  private AnimatorView animatorView;
  private String subjectTitle = "发主贴", askTitle = "问大家", pickTitle = "晒新车";
  private TextView tvSubject;
  private TextView tvAsk;
  private TextView tvPick;
  private ImageView ivSubject;
  private ImageView ivAsk;
  private ImageView ivPick;
  private View viewMenus;

  public YiCheFab() {
  }

  public void init(Activity activity) {
    this.activity = activity;
    attachToActivity();
  }

  private void attachToActivity() {
    attachBackground();
    attachFab();
    attachAnimation();
    attachMenus();
  }

  private void attachBackground() {
    backgroundView = new View(activity);
    backgroundView.setBackgroundColor(Color.parseColor("#8C000000"));
    backgroundView.setVisibility(View.GONE);
    backgroundView.setOnClickListener(this);
    FrameLayout.LayoutParams backgroundViewParams =
        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    getActivityContentView().addView(backgroundView, backgroundViewParams);
  }

  private void attachFab() {
    fab = new FloatingActionButton(activity);
    fab.setId(R.id.publish_btn);
    fab.setOnClickListener(this);
    getActivityContentView().addView(fab);
  }

  private void attachAnimation() {
    animatorView = new AnimatorView(activity);
    getActivityContentView().addView(animatorView);
  }

  private void attachMenus() {
    viewMenus = LayoutInflater.from(activity).inflate(R.layout.view_publish_menus, null, false);
    viewMenus.setVisibility(View.GONE);

    tvSubject = (TextView) viewMenus.findViewById(R.id.tv_subject);
    tvAsk = (TextView) viewMenus.findViewById(R.id.tv_ask);
    tvPick = (TextView) viewMenus.findViewById(R.id.tv_pick);

    tvSubject.setText(subjectTitle);
    tvAsk.setText(askTitle);
    tvPick.setText(pickTitle);

    tvSubject.setOnClickListener(this);
    tvAsk.setOnClickListener(this);
    tvPick.setOnClickListener(this);

    ivSubject = (ImageView) viewMenus.findViewById(R.id.iv_subject);
    ivAsk = (ImageView) viewMenus.findViewById(R.id.iv_ask);
    ivPick = (ImageView) viewMenus.findViewById(R.id.iv_pick);

    ivSubject.setOnClickListener(this);
    ivAsk.setOnClickListener(this);
    ivPick.setOnClickListener(this);

    int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 140,
        activity.getResources().getDisplayMetrics());
    FrameLayout.LayoutParams params =
        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
    params.gravity = Gravity.BOTTOM;

    getActivityContentView().addView(viewMenus, params);
  }

  private FrameLayout getActivityContentView() {
    return (FrameLayout) activity.getWindow().getDecorView().findViewById(android.R.id.content);
  }

  @Override public void onClick(View v) {
    if (v == backgroundView) {
      closeMenu();
    }
    if (v == fab) {
      openMenu();
    }
    if (v == tvSubject || v == ivSubject) {
      closeMenu();
    }
    if (v == tvAsk || v == ivAsk) {
      closeMenu();
    }
    if (v == tvPick || v == ivPick) {
      closeMenu();
    }
  }

  private void openMenu() {
    backgroundView.setVisibility(View.VISIBLE);
    fab.openAnimator();
    animatorView.openAnimator();
    ValueAnimator animator = ValueAnimator.ofInt(0, 100);
    animator.addListener(new MyAnimatorListener() {
      @Override public void onAnimationStart(Animator animation) {
        setMenusEnable(false);
      }

      @Override public void onAnimationEnd(Animator animation) {
        setMenusVisibility(View.VISIBLE);
        setMenusEnable(true);
      }
    });
    animator.start();
  }

  private void closeMenu() {
    backgroundView.setVisibility(View.GONE);
    fab.closeAnimator();
    animatorView.closeAnimator();
    setMenusVisibility(View.GONE);

    ValueAnimator animator = ValueAnimator.ofInt(0, 100);
    animator.addListener(new MyAnimatorListener() {
      @Override public void onAnimationStart(Animator animation) {
        setMenusEnable(false);
      }

      @Override public void onAnimationEnd(Animator animation) {
        setMenusEnable(true);
      }
    });
    animator.start();
  }

  private void setMenusVisibility(int visibility) {
    viewMenus.setVisibility(visibility);
  }

  private void setMenusEnable(boolean enabled) {
    backgroundView.setEnabled(enabled);
    fab.setEnabled(enabled);
  }
}
