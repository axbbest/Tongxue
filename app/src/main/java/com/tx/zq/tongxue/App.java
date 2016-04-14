package com.tx.zq.tongxue;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tx.zq.tongxue.Services.DemoMessageHandler;
import com.tx.zq.tongxue.dao.StudentDaoHelper;

import org.xutils.DbManager;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;

import cn.bmob.newim.BmobIM;


/**
 * @author zhouqiang
 * @ClassName: App
 * @Description: 3.10入口
 * @date 2016年3月11日 上午11:37:35
 */

public class App extends Application {
    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * DPI密度120/160/240/320
     */
    public static int densityDPI;
    /**
     * 密度 0.75/1.0/1.5/2.0
     */
    public static float density;
    /**
     * 设备标识
     */
    public static String deviceId = "";
    public static String model;
    public static ImageOptions imageOptions;
    private DbManager.DaoConfig daoConfig;

    public void setContext(Context context) {
        this.context = context;
    }

    public static Context context;

    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initXutis();
        daoConfig = new StudentDaoHelper().StudentDaoHelper();
        getDeviceInfo();
        initImageLoader(getContext());

//        AnalyticsConfig.enableEncrypt(true);
        // 极光推送
//        JPushInterface.init(getContext());
//        JPushInterface.setDebugMode(true);
        setStyleBasic();
//---------
        //NewIM初始化
        BmobIM.init(this);
        //注册消息接收器
        BmobIM.registerDefaultMessageHandler(new DemoMessageHandler(this));

        // ----------------
    }

    private void initXutis() {
        x.Ext.init(this);

        imageOptions =
                new ImageOptions.Builder()
                        .setIgnoreGif(false)
                        .setCrop(false)
                       // .setImageScaleType(ImageView.ScaleType.FIT_XY)
                        //.setLoadingDrawableId(R.mipmap.ic_launcher)
                        //.setFailureDrawableId(R.drawable.fails)
                        .build();


    }


    private void setStyleBasic() {
//        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(getContext());
//        builder.statusBarDrawable = R.drawable.ic_launcher;
//        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL; // 设置为点击后自动消失
//        builder.notificationDefaults = Notification.DEFAULT_SOUND; // 设置为铃声（
//        // Notification.DEFAULT_SOUND）或者震动（
//        // Notification.DEFAULT_VIBRATE）
//        JPushInterface.setPushNotificationBuilder(1, builder);
    }

    public Context getContext() {
        context = getApplicationContext();
        return getApplicationContext();
    }

    /**
     * 设定文件
     *
     * @return void 返回类型
     * @throws
     * @Title: getDeviceInfo
     * @Description: 获得终端的基本信息 设备 MEID
     */
    private void getDeviceInfo() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        density = dm.density;
        densityDPI = dm.densityDpi;
        model = Build.MODEL;
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        deviceId = tm.getDeviceId();
    }

    // 初始化Imageloader
    public static void initImageLoader(Context context) {

        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache");

        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                // .showStubImage(R.drawable.nomal2)
                // // 在ImageView加载过程中显示图片
                //.showImageForEmptyUri(R.drawable.morenheader_c) // image连接地址为空时
                //.showImageOnFail(R.drawable.morenheader_c) // image加载失败
                .cacheInMemory(true) // 加载图片时会在内存中加载缓存
                .cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).bitmapConfig(Bitmap.Config.RGB_565)
                // .displayer(new RoundedBitmapDisplayer(20)) //
                // 设置用户加载图片task(这里是圆角图片显示)
                .build();
        // 初始化ImageLoad
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPoolSize(5)
                .defaultDisplayImageOptions(options).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).discCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
                .memoryCache(new WeakMemoryCache())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 10 * 1000)) // connectTimeout
                .build();
        ImageLoader.getInstance().init(config);

    }

}
