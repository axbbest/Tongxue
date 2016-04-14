package com.tx.zq.tongxue.utils;

import android.net.Uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: CheckMobileAndEmail
 * @Description: TextView容错处理
 * @author zhouqiang
 * @date 2016年1月20日 下午1:53:41
 *
 */
public class CheckMobileAndEmail {

	// 金额验证
	public static boolean isScore(String str) {
		Pattern pattern = Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		Matcher match = pattern.matcher(str);
		return match.matches() != false;
	}

	public static boolean checkScore(String email) {
		boolean flag = false;
		try {
			Pattern pattern = Pattern.compile("^[0-9]+([.]\\d{1,2})?$");
			Matcher matcher = pattern.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			Pattern pattern = Pattern.compile(
					"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
			Matcher matcher = pattern.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	// (!/^[A-Za-z][A-Za-z0-9\\-_]{3,15}$/.test(post_data.txt_account)) {
	public static boolean username(String name) {
		boolean flag = false;
		try {
			Pattern pattern = Pattern.compile("^[A-Za-z][A-Za-z0-9\\-_]{3,15}$");
			Matcher matcher = pattern.matcher(name);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean isMobileNO(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^1[0-9]{10}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean isMoney1(String number) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
			Matcher m = p.matcher(number);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean isMoney2(String number) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^[1-9][0-9]*$");
			Matcher m = p.matcher(number);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean isMoney(String number) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^(0|[1-9][0-9]{0,9})(//.[0-9]{1,2})?$");
			Matcher m = p.matcher(number);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean isPasswd(String pwd) {
		boolean flag = false;
		try {
			// Pattern p = Pattern.compile("^/w{6,20}$");
			Pattern p = Pattern.compile("^[a-zA-Z0-9]{6,18}$");
			Matcher m = p.matcher(pwd);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean isPayPasswd(String pwd) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^[a-zA-Z0-9]{6}$");
			Matcher m = p.matcher(pwd);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean isBankCard(String card) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^[0-9]{16,19}$");
			Matcher m = p.matcher(card);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean isValidURI(String uri) {
		if (uri == null || uri.indexOf(' ') >= 0 || uri.indexOf('\n') >= 0) {
			return false;
		}
		String scheme = Uri.parse(uri).getScheme();
		if (scheme == null) {
			return false;
		}

		// Look for period in a domain but followed by at least a two-char TLD
		// Forget strings that don't have a valid-looking protocol
		int period = uri.indexOf('.');
		if (period >= uri.length() - 2) {
			return false;
		}
		int colon = uri.indexOf(':');
		if (period < 0 && colon < 0) {
			return false;
		}
		if (colon >= 0) {
			if (period < 0 || period > colon) {
				// colon ends the protocol
				for (int i = 0; i < colon; i++) {
					char c = uri.charAt(i);
					if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
						return false;
					}
				}
			} else {
				// colon starts the port; crudely look for at least two numbers
				if (colon >= uri.length() - 2) {
					return false;
				}
				for (int i = colon + 1; i < colon + 3; i++) {
					char c = uri.charAt(i);
					if (c < '0' || c > '9') {
						return false;
					}
				}
			}
		}
		return true;
	}
}
