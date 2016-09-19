package com.example.a25908.partybuild.Dialogs;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;


public class TipDialog extends PopupWindow {
	public static AlertDialog.Builder builder;
	public static AlertDialog dialog;
	private static TipDialog td;
	private TipDialog() {}

	public static TipDialog ShowTipDialog(Context context, String txt , DialogInterface.OnClickListener itemsOnClick) {
		if (td == null) {
			td = new TipDialog();
		}
		builder = new AlertDialog.Builder(context);
		builder.setTitle("提示");
		builder.setMessage(txt);
		builder.setPositiveButton("确认",itemsOnClick);
		dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
		return td;
	}
}
