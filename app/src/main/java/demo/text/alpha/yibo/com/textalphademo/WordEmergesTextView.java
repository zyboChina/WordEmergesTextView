package demo.text.alpha.yibo.com.textalphademo;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;
import android.util.AttributeSet;
import android.view.ViewDebug;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import java.util.ArrayList;

class WordEmergesTextView extends TextView {
    private SpannableString mFadeyText;
    private CharSequence mText;
    ArrayList<Float> alphaList; // the list for recording the original transparency
    float value = 0;
    FadeyLetterSpan[] letters;
    boolean isFirst;

    Handler handler = new Handler() { //for change the text alpha together
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0 && value <= 1) {
                value += 0.01;
                refreshText(letters, value);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendEmptyMessage(0);
                    }
                }, 30);
            }
            super.handleMessage(msg);
        }
    };

    public WordEmergesTextView(Context context) {
        super(context);
        initView();
    }

    public WordEmergesTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public WordEmergesTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        isFirst = true;
    }

    /*
    * for initialization the alpha for each text in textview
    *
    * */
    @Override
    public void setText(CharSequence text, BufferType type) {
        mText = text;

        mFadeyText = new SpannableString(text);

        FadeyLetterSpan[] letters = mFadeyText.getSpans(0, mFadeyText.length(), FadeyLetterSpan.class);
        for (FadeyLetterSpan letter : letters) {
            mFadeyText.removeSpan(letter);
        }

        final int length = mFadeyText.length();
        for (int i = 0; i < length; i++) {
            mFadeyText.setSpan(new FadeyLetterSpan(), i, i + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        super.setText(mFadeyText, BufferType.SPANNABLE);
        if (alphaList != null) {
            alphaList.clear();
        }
        alphaList = new ArrayList<>();
        isFirst = true;
        value = 0;
        alphaList.clear();
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    @ViewDebug.CapturedViewProperty
    public CharSequence getText() {
        return mText;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        letters = mFadeyText.getSpans(0, mFadeyText.length(), FadeyLetterSpan.class);
        if (isFirst) {
            handler.sendEmptyMessage(0);
            isFirst = false;
        }
    }

    private void refreshText(FadeyLetterSpan[] letters, float value) {
        for (int i = 0; i < letters.length; i++) {
            if (alphaList.size() > 0 && i < alphaList.size()) {
                letters[i].setAlpha(alphaList.get(i) + value);
            }
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public class FadeyLetterSpan extends CharacterStyle implements UpdateAppearance {
        private float mAlpha = (float) (Math.random() * 0.4);// initialize alpha for each text in textview,you may change here to init your alpha

        public void setAlpha(float alpha) {
            mAlpha = Math.max(Math.min(alpha, 1.0f), 0.0f);
        }

        @Override
        public void updateDrawState(TextPaint tp) {
            alphaList.add(mAlpha);
            int color = ((int) (0xFF * mAlpha) << 24) | (tp.getColor() & 0x00FFFFFF);
            tp.setColor(color);
        }
    }
}