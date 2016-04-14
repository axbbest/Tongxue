package com.tx.zq.tongxue.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tx.zq.tongxue.App;

import java.util.ArrayList;

public class UiUtils {
    public static final String KEY_APP_KEY = "JPUSH_APPKEY";
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        synchronized (UiUtils.class) {
            long time = System.currentTimeMillis();
            long timeD = time - lastClickTime;
            if (0 < timeD && timeD < 300) {
                return true;
            }
            lastClickTime = time;
            return false;
        }
    }

    //uri转file
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 浏览者模式和登录模式判定
     *
     */
//    public static void checkUserID(Context context, Class clazz) {
//        if (!SharedPreferencesDB.getInstance(context).getBoolean("login", false)) {
//            Intent i = new Intent(context, LoginActivity.class);
//            context.startActivity(i);
//            return;
//        } else {
//            if (clazz != null)
//                context.startActivity(new Intent(context, clazz));
//
//        }
//    }
    @SuppressLint("NewApi")
    public static void removeOnGlobalLayoutListener(View
                                                            v, OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    // 设备唯一识别号
    public static String getImei(Context context, String imei) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
        }
        return imei;
    }

    // 取得AppKey
    public static String getAppKey(Context context) {
        Bundle metaData = null;
        String appKey = null;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            if (null != ai)
                metaData = ai.metaData;
            if (null != metaData) {
                appKey = metaData.getString(KEY_APP_KEY);
                if ((null == appKey) || appKey.length() != 24) {
                    appKey = null;
                }
            }
        } catch (NameNotFoundException e) {

        }
        return appKey;
    }

    // 小数点后2位
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });
    }

    /**
     * @param context
     * @param id
     * @return
     */
    public static <T> T findView(Activity context, int id) {
        return (T) context.findViewById(id);

    }

    /**
     * @param edText
     * @return
     */
    public static String GetEditViewContent(TextView edText) {
        return edText.getText().toString().trim();
    }

    public static void StartActivity(Context context, Class target) {
        if (context != null && context instanceof Activity) {
            context.startActivity(new Intent(context, target));

        } else {
            Intent intent = new Intent(context, target);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }

    /**
     * @Title: setListViewHeight @Description: 获取ListView的高度 @param @param
     * listView 设定文件 @return void 返回类型 @throws
     */
    public static void setGrideViewHeight(GridView gridView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        if (listAdapter.getCount() % 2 == 0) {
            for (int i = 0, len = listAdapter.getCount() / 2; i < len; i++) { // listAdapter.getCount()返回数据项的数目

                View listItem = (listAdapter).getView(i, null, gridView);

                listItem.measure(0, 0); // 计算子项View 的宽高

                totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
            }
        } else {
            for (int i = 0, len = (listAdapter.getCount() + 1) / 2; i < len; i++) { // listAdapter.getCount()返回数据项的数目

                View listItem = (listAdapter).getView(i, null, gridView);

                listItem.measure(0, 0); // 计算子项View 的宽高

                totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
            }
        }

        LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gridView.setLayoutParams(params);
    }

    /**
     * @Title: setListViewHeight @Description: 获取ListView的高度 @param @param
     * listView 设定文件 @return void 返回类型 @throws
     */
    public static void setEXGrideViewHeight(Context context, GridView gridView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        if (listAdapter.getCount() % 2 == 0) {
            for (int i = 0, len = listAdapter.getCount() / 2; i < len; i++) { // listAdapter.getCount()返回数据项的数目

                View listItem = (listAdapter).getView(i, null, gridView);

                listItem.measure(0, 0); // 计算子项View 的宽高

                totalHeight += listItem.getMeasuredHeight() + DensityUtil.dip2px(context, 5); // 统计所有子项的总高度
            }
        } else {
            for (int i = 0, len = (listAdapter.getCount() + 1) / 2; i < len; i++) { // listAdapter.getCount()返回数据项的数目

                View listItem = (listAdapter).getView(i, null, gridView);

                listItem.measure(0, 0); // 计算子项View 的宽高

                totalHeight += listItem.getMeasuredHeight() + DensityUtil.dip2px(context, 5); // 统计所有子项的总高度
            }
        }

        LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gridView.setLayoutParams(params);
    }

    /**
     * @Title: setListViewHeight @Description: 获取ListView的高度 @param @param
     * listView 设定文件 @return void 返回类型 @throws
     */
    public static void setListViewHeight(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;

        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目

            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0); // 计算子项View 的宽高

            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度

        }

        LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));

        // listView.getDividerHeight()获取子项间分隔符占用的高度

        // params.height最后得到整个ListView完整显示需要的高度

        listView.setLayoutParams(params);
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * // tag:1隐藏，2：显示
     *
     * @param context
     * @param view
     * @param tag
     */
    public static void hideSoftInputByView(Context context, View view, int tag) {
        InputMethodManager inputManager1 =

                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (tag == 1) {
            inputManager1.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            inputManager1.showSoftInput(view, 0);
        }
    }

    /**
     * 设置edittext为空时按钮不可以用
     */
    Button bt;
    EditText[] et;

    public void setBtnEnableByEditText(Button bt, EditText... et) {
        this.bt = bt;
        this.et = et;
        for (int i = 0; i < et.length; i++) {
            et[i].addTextChangedListener(new MyWatcher());
        }
    }

    class MyWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            for (int i = 0; i < et.length; i++) {
                if (et[i].getText().toString().isEmpty()) {
                    bt.setEnabled(false);
                    bt.setPressed(false);
                    return;
                }
            }
            bt.setEnabled(true);
            bt.setPressed(true);
        }
    }

    //动态设置图片的长宽
    public static void setImage(Context context, final View view, final int draw) {
        final LayoutParams params = view.getLayoutParams();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), draw);
        params.height = bitmap.getHeight() * App.screenWidth / bitmap.getWidth();
        final OnGlobalLayoutListener listener = new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                removeOnGlobalLayoutListener(view, this);
                view.setLayoutParams(params);
                view.requestLayout();
                view.setBackgroundResource(draw);
            }
        };
        view.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }


    public static ArrayList<String> getStringArray(String[] s) {
        ArrayList<String> al = new ArrayList<String>();
        for (String str : s) {
            al.add(str);
        }
        return al;
    }

    public static void SartActivity(Context c, Class clazz) {
        c.startActivity(new Intent(c, clazz));
    }


}
