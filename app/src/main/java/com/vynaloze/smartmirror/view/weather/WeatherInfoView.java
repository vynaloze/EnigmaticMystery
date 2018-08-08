package com.vynaloze.smartmirror.view.weather;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.vynaloze.smartmirror.R;
import com.vynaloze.smartmirror.util.ApplicationContextProvider;

public class WeatherInfoView extends ConstraintLayout {
    private WebView weatherIcon;
    private TextView temperature;
    private TextSwitcher textComment;

    public WeatherInfoView(Context context) {
        super(context);
        initializeViews(context);
    }

    public WeatherInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public WeatherInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.weather_info, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        weatherIcon = findViewById(R.id.weatherInfoImage);
        temperature = findViewById(R.id.currentTemperature);
        textComment = findViewById(R.id.weatherComment);
        initializeTextSwitcher();
    }

    private void initializeTextSwitcher() {
        Context context = ApplicationContextProvider.getContext();
        textComment.setInAnimation(context, R.anim.slide_in_bottom);
        textComment.setOutAnimation(context, R.anim.slide_out_bottom);
        textComment.addView(prepareTextView(context));
        textComment.addView(prepareTextView(context));
    }

    private TextView prepareTextView(Context context) {
        TextView textView = new TextView(context);
        textView.setTextAppearance(R.style.TextViewStyle);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setMarqueeRepeatLimit(-1);
        textView.setHorizontalFadingEdgeEnabled(true);
        return textView;
    }

    public WebView getWeatherIcon() {
        return weatherIcon;
    }

    public TextView getTemperature() {
        return temperature;
    }

    public TextSwitcher getTextComment() {
        return textComment;
    }
}
