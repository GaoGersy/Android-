package com.jojo.sns.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.jojo.sns.R;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/1/14.
 */
public class SoundUtils {
    private static final int MAX_STREAM = 1;
    private static final int SRC_QULITY = 0;
    private static final int PRIORITY=1;
    private static SoundUtils mInstance;
    private static Context mContext;
    /**
     *  添加的声音资源参数
     */
    private HashMap<Integer, Integer> soundPoolMap;
    /**
     *  声音音量类型，默认为多媒体
     */
    private int soundVolType = 3;
    /**
     *  无限循环播放
     */
    public static final int INFINITE_PLAY = -1;
    /**
     *  单次播放
     */
    public static final int SINGLE_PLAY = 0;
    /**
     *  铃声音量
     */
    public static final int RING_SOUND = 2;
    /**
     *  媒体音量
     */
    public static final int MEDIA_SOUND = 3;
    private SoundPool mSoundPool;
    private int mSoundId;

    public static SoundUtils with(Context context){
        return getInstance(context);
    }

    public SoundUtils(Context context) {
    }

    public static SoundUtils getInstance(Context context){
        mContext = context;
        if (mInstance==null){
            synchronized(SoundUtils.class){
                if (mInstance==null){
                    mInstance = new SoundUtils(context);
                }
            }
        }
        return mInstance;
    }

    /**
     *  添加声音文件进声音池
     * @param order 所添加声音的编号，播放的时候指定
     * @param soundRes 添加声音资源的id
     * @see
     */
    public SoundUtils putSound(int order, int soundRes) {
        // 上下文，声音资源id，优先级
        soundPoolMap.put(order, mSoundPool.load(mContext, soundRes, PRIORITY));
        return mInstance;
    }

    /**
     *设置 soundVolType 的值
     */
    public void setSoundVolType(int soundVolType) {
        this.soundVolType = soundVolType;
    }

    public SoundUtils create() {
        if (mSoundPool==null){
            synchronized(SoundUtils.class){
                if (mSoundPool==null){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        createNewSoundPool();
                    } else {
                        createOldSoundPool();
                    }
                }
            }
        }
        return mInstance;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool() {
        mSoundPool = new SoundPool(MAX_STREAM, AudioManager.STREAM_MUSIC,SRC_QULITY);
    }

    public SoundUtils play(){
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

                soundPool.play(mSoundId, 1f, 1f, 1, INFINITE_PLAY, 1f);
            }
        });

        return mInstance;
    }

    public SoundUtils load(){
        return load(R.raw.avchat_ring);
    }

    public SoundUtils load(int resourceId){
        mSoundId = mSoundPool.load(mContext, resourceId, PRIORITY);
        return mInstance;
    }

    public void destory(){
        if (mSoundPool!=null) {
            mSoundPool.release();
            mSoundPool =null;
        }
    }
}
