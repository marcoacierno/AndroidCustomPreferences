package com.comuf.revonline.custompreferences.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.TwoStatePreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class SliderPreference extends Preference
{
    private final static String TAG = "SliderPreference";

    private int sliderValue;
    private int maxValue;

    public SliderPreference(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        maxValue = attrs.getAttributeIntValue("android", "max", 100);
    }

    public SliderPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        maxValue = attrs.getAttributeIntValue("android", "max", 100);
    }

    public SliderPreference(Context context)
    {
        super(context);
    }

    @Override
    protected View onCreateView(ViewGroup parent)
    {
        View view = super.onCreateView(parent);

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(view);

        SeekBar seekBar = new SeekBar(getContext());
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBar.setProgress(sliderValue);
        seekBar.setMax(maxValue);

        linearLayout.addView(seekBar);
        return linearLayout;
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue)
    {
        if (restorePersistedValue)
        {
            sliderValue = getPersistedInt(0);
        }
        else
        {
            if (defaultValue == null)
            {
                sliderValue = 0;
            }
            else
            {
                sliderValue = Integer.parseInt(defaultValue.toString());
            }
        }

        Log.d(TAG, "Restore value to: " + sliderValue);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index)
    {
        return a.getInt(index, 0);
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {

        }

        //// i used onStopTrackingTouch but you could need to save it manually if your application-target is TV too.
        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            int progress = seekBar.getProgress();

            callChangeListener(progress);

            persistInt(progress);
            sliderValue = progress;

            Log.d(TAG, "Persist progress value: " + progress);
        }
    };
}
