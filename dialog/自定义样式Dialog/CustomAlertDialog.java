package com.jojo.sns.views.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jojo.sns.R;
import com.jojo.sns.base.BaseAlertDialog;
import com.jojo.sns.listener.OnDialogClickListener;

/**
 * Created by Administrator on 2017/2/10.
 */
public class CustomAlertDialog extends BaseAlertDialog {

    private TextView mPositive;
    private TextView mNegative;
    private TextView mTitle;

    public CustomAlertDialog(Context context, OnDialogClickListener listener) {
        super(context, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_custom;
    }

    @Override
    protected void initView() {
        mPositive = (TextView) findViewById(R.id.tv_positive);
        mNegative = (TextView) findViewById(R.id.tv_negative);
        mTitle = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    protected void initListener() {
        mPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogClickListener!=null) {
                    mDialogClickListener.OnConfirmClick();
                }
                dismiss();
            }
        });
        mNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogClickListener!=null) {
                    mDialogClickListener.OnCancelClick();
                }
                dismiss();
            }
        });
    }

    public void setNegativeText(String text){
        if (mNegative!=null) {
            mNegative.setText(text);
        }
    }

    public void setPositiveText(String text){
        if (mPositive!=null) {
            mPositive.setText(text);
        }
    }

    public void setTitleText(String text){
        if (mTitle!=null) {
            mTitle.setText(text);
        }
    }
}
