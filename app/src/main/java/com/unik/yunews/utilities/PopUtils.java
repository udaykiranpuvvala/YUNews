package com.unik.yunews.utilities;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.unik.yunews.R;

import java.util.ArrayList;


public class PopUtils {
    private ArrayList<String> areaList;
    TextView mTxtPhoneNumber;
    static Context context;

    Dialog dialog = null;
    public static void alertDialog(final Context mContext, String message, final View.OnClickListener okClick) {
        TextView mTxtOk, mTxtMessage;
        final Dialog dialog = new Dialog(mContext, R.style.AlertDialogCustom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = LayoutInflater.from(mContext).inflate(R.layout.alert_dialog, null);
        mTxtOk = (TextView) v.findViewById(R.id.txtNo);
        mTxtMessage = (TextView) v.findViewById(R.id.txtMessage);

        dialog.getWindow().getAttributes().windowAnimations = R.style.AlertDialogCustom;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mTxtMessage.setText(message);
        mTxtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (okClick != null) {
                    okClick.onClick(v);
                }
            }
        });

        dialog.setContentView(v);
        dialog.setCancelable(false);

        int width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.30);
        dialog.getWindow().setLayout(width, lp.height);

        dialog.show();
    }

    public static void alertReportDialog(final Context mContext, String message, final View.OnClickListener okClick) {
        TextView mTxtOk, mTxtMessage;
        final Dialog dialog = new Dialog(mContext, R.style.AlertDialogCustom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = LayoutInflater.from(mContext).inflate(R.layout.alert_report_dialog, null);
//        mTxtOk = (TextView) v.findViewById(R.id.txtNo);
//        mTxtMessage = (TextView) v.findViewById(R.id.txtMessage);

        dialog.getWindow().getAttributes().windowAnimations = R.style.AlertDialogCustom;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

       /* mTxtMessage.setText(message);
        mTxtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (okClick != null) {
                    okClick.onClick(v);
                }
            }
        });*/

        dialog.setContentView(v);
        dialog.setCancelable(true);

        int width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.30);
        dialog.getWindow().setLayout(width, lp.height);

        dialog.show();
    }


    public static Dialog exitDialog(final Context mContext, String message, final View.OnClickListener yesClick) {
        TextView mTxtMessage;
        TextView mBtnYes, mBtnNo;

        Dialog dialog = new Dialog(mContext, R.style.AlertDialogCustom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = LayoutInflater.from(mContext).inflate(R.layout.alert_two_button, null);
        mBtnYes = (TextView) v.findViewById(R.id.btnYes);
        mBtnNo = (TextView) v.findViewById(R.id.btnNo);
        mTxtMessage = (TextView) v.findViewById(R.id.txtMessage);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AlertDialogCustom;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mTxtMessage.setText(message);
        mBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (yesClick != null) {
                    yesClick.onClick(v);
                }
            }
        });

        dialog.setContentView(v);
        dialog.setCancelable(false);
        int width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.30);
        dialog.getWindow().setLayout(width, lp.height);
        dialog.show();
        return dialog;
    }

   /* public static Dialog loginPopUpDialog(final Context mContext, String message, final View.OnClickListener registerClick,
                                          final View.OnClickListener loginClick) {
        TextView mTxtMessage;
        ImageView imgCancel;
        TextView txtRegister, txtSignIn;
        Dialog dialog = new Dialog(mContext, R.style.AlertDialogCustom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = LayoutInflater.from(mContext).inflate(R.layout.alert_login_popup, null);
        txtRegister = (TextView) v.findViewById(R.id.txtRegister);
        imgCancel = (ImageView) v.findViewById(R.id.imgCancel);
        txtSignIn = (TextView) v.findViewById(R.id.txtSignIn);
        mTxtMessage = (TextView) v.findViewById(R.id.txtMessage);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AlertDialogCustom;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mTxtMessage.setText(message);
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginClick != null) {
                    loginClick.onClick(v);
                }
                dialog.dismiss();
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerClick != null) {
                    registerClick.onClick(v);
                }
                dialog.dismiss();
            }
        });
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(v);
        dialog.setCancelable(false);
        int width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.30);
        dialog.getWindow().setLayout(width, lp.height);
        dialog.show();
        return dialog;
    }
*/

}
