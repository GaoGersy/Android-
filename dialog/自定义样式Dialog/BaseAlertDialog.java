package com.jojo.sns.base;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import com.jojo.sns.R;
import com.jojo.sns.listener.OnDialogClickListener;

/**
 * Created by Administrator on 2017/2/10.
 */
public abstract class BaseAlertDialog {
    private AlertDialog dialog;
    private Window mWindow;
    protected OnDialogClickListener mDialogClickListener;

    public BaseAlertDialog(Context context,OnDialogClickListener listener) {
        mDialogClickListener = listener;
        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setCancelable(true);
        mWindow = dialog.getWindow();
        mWindow.setBackgroundDrawableResource(R.mipmap.reward_transparent_icon);
        mWindow.setContentView(getLayoutId());
        initView();
        initListener();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    protected View findViewById(int id){
       return mWindow.findViewById(id);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initListener();
}
