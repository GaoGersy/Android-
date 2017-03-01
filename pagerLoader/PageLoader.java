package com.jojo.sns.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.jojo.sns.R;
import com.jojo.sns.utils.LoggerUtils;
import com.jojo.sns.views.dialog.LoadingDialog;

/**
 * Created by arieridwan on 20/11/2016.
 */

public class PageLoader extends RelativeLayout {

    private RelativeLayout progressView;
    private LinearLayout progressViewStart;
    private LinearLayout progressViewFailed;
    private LinearLayout progressPageNoData;
    private ImageView imageLoading;
    private TextView textLoading;
    private ImageView imageError;
    private TextView textError;
    private TypedArray array;
    private Context mContext;
    //    private Typeface typeface;
    private View mContentView;
    private Animation myRotation;
    private Animation myFlip;
    private Animation myVibrate;
    private Animation myShake;
    private Animation myBounce;
    private ImageView mImageNoData;
    private TextView mTextNoData;
    private OnRetryClickListener mListener;
    private LoadingDialog mLoadingDialog;
    private int mPageSize = 20;

    public PageLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.pageloader, this, true);
        mContext = context;
        array = context.obtainStyledAttributes(attrs, R.styleable.PageLoader, 0, 0);
        initView();
    }

    private void initView() {
        progressView = (RelativeLayout) findViewById(R.id.progressPage);
        progressViewStart = (LinearLayout) findViewById(R.id.progressPageStart);
        progressViewFailed = (LinearLayout) findViewById(R.id.progressPageFailed);
        progressPageNoData = (LinearLayout) findViewById(R.id.progressPageNoData);
        progressViewFailed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startProgress();
                if (mListener != null) {
                    mListener.onRetryClick();
                }
            }
        });
        imageLoading = (ImageView) findViewById(R.id.mImageLoading);
        textLoading = (TextView) findViewById(R.id.mTextLoading);
        imageError = (ImageView) findViewById(R.id.mImageError);
        textError = (TextView) findViewById(R.id.mTextError);
        mImageNoData = (ImageView) findViewById(R.id.mImageNoData);
        mTextNoData = (TextView) findViewById(R.id.mTextNoData);

        imageLoading.setImageDrawable(WaveLoadingUtils.getDrawable(mContext));
    }

    public void setTextSize(int i) {
        if (i != 0) {
            textError.setTextSize(TypedValue.COMPLEX_UNIT_SP, i);
            textLoading.setTextSize(TypedValue.COMPLEX_UNIT_SP, i);
            //Toast.makeText(mContext,i+"",Toast.LENGTH_LONG).show();
        } else {
            textError.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
            textLoading.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
        }
    }

    public void setTextColor(ColorStateList c) {
        if (c != null) {
            textError.setTextColor(c);
            textLoading.setTextColor(c);
        } else {
            textError.setTextColor(getResources().getColor(R.color.gray_80));
            textLoading.setTextColor(getResources().getColor(R.color.gray_80));
        }
    }

    public void setTextLoading(String s) {
        if (!TextUtils.isEmpty(s)) {
            textLoading.setText(s);
        } else {
            textLoading.setText(getResources().getString(R.string.loading_text));
        }
    }


    public void setTextError(String s) {
        if (!TextUtils.isEmpty(s)) {
            textError.setText(s);
        } else {
            textError.setText(getResources().getString(R.string.error_text));
        }
    }

    public void setOnRetryClickListener(OnRetryClickListener listener) {
        mListener = listener;
    }

    public interface OnRetryClickListener {
        void onRetryClick();
    }

    public void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }

    public void setContentView(View view) {
        mContentView = view;
    }

    public void startLoading() {
        mLoadingDialog = new LoadingDialog(mContext);
    }

    public void stopLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    public void startProgress() {
        progressView.setVisibility(View.VISIBLE);
        progressViewStart.setVisibility(View.VISIBLE);
        progressViewFailed.setVisibility(View.GONE);
        progressPageNoData.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);
        LoggerUtils.d("startProgress");
    }

    public void startProgress(boolean isRefresh) {
        if (isRefresh) {
            startProgress();
        } else {
            startLoading();
        }
    }

    public void stopProgress() {
        progressView.setVisibility(View.GONE);
        progressViewStart.setVisibility(View.GONE);
        progressViewFailed.setVisibility(View.GONE);
        progressPageNoData.setVisibility(View.GONE);
        mContentView.setVisibility(View.VISIBLE);
        LoggerUtils.d("stopProgress");
    }

    public void stopProgress(int size) {
        stopProgress();
        stopLoading();
        XRefreshView contentView = null;
        if (mContentView instanceof XRefreshView) {
            contentView = (XRefreshView) mContentView;
        }

        if (contentView != null) {
            if (size < mPageSize) {
                contentView.setPullLoadEnable(false);
            } else {
                contentView.setPullLoadEnable(true);
            }
        }
    }

    public void stopProgressAndNoData() {
        progressView.setVisibility(View.VISIBLE);
        progressViewStart.setVisibility(View.GONE);
        progressViewFailed.setVisibility(View.GONE);
        progressPageNoData.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.GONE);
        LoggerUtils.d("stopProgressAndNoData");
    }

    public void stopProgressAndFailed() {
        progressView.setVisibility(View.VISIBLE);
        progressViewStart.setVisibility(View.GONE);
        progressViewFailed.setVisibility(View.VISIBLE);
        progressPageNoData.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);
        LoggerUtils.d("stopProgressAndFailed");
    }
}
