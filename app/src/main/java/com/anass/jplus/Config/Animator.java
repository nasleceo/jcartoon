package com.anass.jplus.Config;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Animator {

    private AnimationListener animationListener;

    public void setAnimationListener(AnimationListener animationListener) {
        this.animationListener = animationListener;
    }

    public void AnimateWidth(View view, int toWidth, int duration){
        WidthAnimate anim = new WidthAnimate(view, toWidth);
        anim.setDuration(duration);
        view.clearAnimation();
        view.startAnimation(anim);
    }

    public void AnimateOpacity(View view, float toOpacity, int duration){
        OpacityAnimate anim = new OpacityAnimate(view, toOpacity);
        anim.setDuration(duration);
        view.clearAnimation();
        view.startAnimation(anim);
    }

    private class WidthAnimate extends Animation {
        private int mWidth;
        private int mStartWidth;
        private View mView;

        public WidthAnimate(View view, int toWidth) {
            mView = view;
            mStartWidth = Utils.PxToDp(view.getWidth(), view.getContext());
            mWidth = toWidth;
        }

        boolean oneTime = true;
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newWidth = mStartWidth + (int) ((mWidth - mStartWidth) * interpolatedTime);
            mView.getLayoutParams().width = Utils.DpToPx(newWidth, mView.getContext());
            mView.requestLayout();

            if(interpolatedTime == 1 && oneTime){
                animationListener.onAnimationFinish();
                oneTime = false;
            }

        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    private class OpacityAnimate extends Animation {
        private float mOpacity;
        private float mStartOpacity;
        private View mView;

        public OpacityAnimate(View view, float toOpacity) {
            mView = view;
            mStartOpacity = view.getAlpha();
            mOpacity = toOpacity;
        }

        boolean oneTime = true;
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            float newOpacity = mStartOpacity + (mOpacity - mStartOpacity) * interpolatedTime;
            mView.setAlpha(newOpacity);
            mView.requestLayout();

            if(interpolatedTime == 1 && oneTime){
                animationListener.onAnimationFinish();
                oneTime = false;
            }

        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    public void AnimateSize(View view, int toSize, int duration){
        SizeAnimate anim = new SizeAnimate(view, toSize);
        anim.setDuration(duration);
        view.clearAnimation();
        view.startAnimation(anim);
    }

    private class SizeAnimate extends Animation {
        private int mSize;
        private int mStartSize;
        private View mView;

        public SizeAnimate(View view, int toSize) {
            mView = view;
            mStartSize= Utils.PxToDp(view.getWidth(), view.getContext());
            mSize = toSize;
        }

        boolean oneTime = true;
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newSize = mStartSize + (int) ((mSize - mStartSize) * interpolatedTime);
            mView.getLayoutParams().width = Utils.DpToPx(newSize, mView.getContext());
            mView.getLayoutParams().height = Utils.DpToPx(newSize, mView.getContext());
            mView.requestLayout();

            if(interpolatedTime == 1 && oneTime){
                animationListener.onAnimationFinish();
                oneTime = false;
            }

        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }


    public interface AnimationListener{
        public void onAnimationFinish();
    }
}
