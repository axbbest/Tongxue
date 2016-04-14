package com.tx.zq.tongxue.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 
 * @ClassName: ToastUtils
 * @Description: 单例Toast
 * @author zhouqiang
 * @date 2016年2月16日 下午6:28:57
 *
 */
public class ToastUtils {

	static Toast toast;

	public static void show(Context context, String text) {
		if (context == null) {
			return;
		}

		if (TextUtils.isEmpty(text)) {
			text = "";
		}

		if (toast != null) {
			toast.setText(text);
		} else {
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		}
		toast.show();
	}

	public static void show(Context context, int resId) {
		if (context == null) {
			return;
		}
		if (toast != null) {
			toast.cancel();
		}
		toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		toast.show();
	}
}
