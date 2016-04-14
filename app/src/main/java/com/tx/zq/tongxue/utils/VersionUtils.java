package com.tx.zq.tongxue.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 
 * @ClassName: VersionUtils
 * @Description: 版本获取类，本系统采用的是Name作为更新，code辅助
 * @author zhouqiang
 * @date 2015年9月16日 下午4:22:24
 *
 */
public class VersionUtils {

	public static float getVersionCode(Context context) {
		float code = 0;
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			code = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return code;
	}

	public static String getVersionName(Context context) {
		String versionName = "0";
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			versionName = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

}
