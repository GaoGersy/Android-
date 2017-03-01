package com.jojo.sns.views;

import android.content.Context;

import com.jojo.sns.R;

/**
 * Created by Administrator on 2017/2/24.
 */
public class WaveLoadingUtils {
    public static WaveDrawable getDrawable(Context context){
        WaveDrawable drawable = new WaveDrawable(context, R.drawable.loading_bg);
        drawable.setIndeterminate(true);
        drawable.setLevel(50);
        drawable.setWaveLength(273);
        drawable.setWaveAmplitude(13);
        drawable.setWaveSpeed(8);
        return drawable;
    }
}
