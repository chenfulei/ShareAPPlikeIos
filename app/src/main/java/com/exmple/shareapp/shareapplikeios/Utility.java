package com.exmple.shareapp.shareapplikeios;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.Window;


/**
 * Created by test on 2016/3/15.
 */
public class Utility {

    public static Dialog getDialog(Context context, int viewId, boolean canceledOnTouchOutside, boolean cancelable) {
        final Dialog d = new Dialog(context, R.style.customDialog);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCanceledOnTouchOutside(canceledOnTouchOutside);
        d.setCancelable(cancelable);
        d.setContentView(viewId);
        return d;
    }

    public static Dialog getDialog(Context context, int viewId) {
        return getDialog(context, viewId, true, true);
    }
    public static BottomSheetDialog getBottomDialog(Context context, int viewId, boolean canceledOnTouchOutside, boolean cancelable) {
        final BottomSheetDialog d = new BottomSheetDialog(context);
//        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        d.setCanceledOnTouchOutside(canceledOnTouchOutside);
//        d.setCancelable(cancelable);
        d.setContentView(viewId);
        return d;
    }

    public static BottomSheetDialog getBottomDialog(Context context, int viewId) {
        return getBottomDialog(context, viewId, true, true);
    }

}
