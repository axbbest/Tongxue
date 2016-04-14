package com.tx.zq.tongxue.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.tx.zq.tongxue.App;
import com.tx.zq.tongxue.R;
import com.tx.zq.tongxue.base.BaseActity;
import com.tx.zq.tongxue.entity.BaseModel;
import com.tx.zq.tongxue.entity.Student;
import com.tx.zq.tongxue.entity.User;
import com.tx.zq.tongxue.entity.UserModel;
import com.tx.zq.tongxue.utils.CheckMobileAndEmail;
import com.tx.zq.tongxue.utils.Logutils;
import com.tx.zq.tongxue.utils.ToastUtils;

import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

@ContentView(R.layout.activity_login_activitu)
public class LoginActivity extends BaseActity implements View.OnClickListener {
    @ViewInject(R.id.username)
    private EditText username;
    @ViewInject(R.id.password)
    private EditText password;
    @ViewInject(R.id.go)
    private Button go;
    @ViewInject(R.id.register)
    private TextView register;
    @ViewInject(R.id.qq)
    private TextView qq;
    @ViewInject(R.id.root)
    private RelativeLayout root;
    @ViewInject(R.id.head)
    private ImageView head;
    DbManager db;
    private String name;
    private Activity context;
    //---------------dialog
    private EditText rname_et;
    private EditText rpwd_et1;
    private EditText rpwd_et2;
    private Tencent mTencent;
    private boolean isServerSideLogin;
    private String APPID = "222222";
    private Display display;
    private View view;
    private ImageOptions imageOptions;

    @Override
    protected void initView() {
        context = this;
        //------------------

        //-----------------------

        x.view().inject(this);
//        go.setOnClickListener(this);
//        register.setOnClickListener(this);
//        qq.setOnClickListener(this);
        mTencent = Tencent.createInstance(APPID, this.getApplicationContext());
        //  EventBus.getDefault().register(this);
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        //-----------------
        Glide.with(this).
                load(R.drawable.a).asGif().crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(head);
        imageOptions =
                new ImageOptions.Builder()
                        .setIgnoreGif(false)
                        .setCrop(false)
                        // .setImageScaleType(ImageView.ScaleType.FIT_XY)
                        //.setLoadingDrawableId(R.mipmap.ic_launcher)
                        //.setFailureDrawableId(R.drawable.fails)
                        .build();
        //  x.image().bind(head,"http://img.gaoxiaogif.cn/GaoxiaoGiffiles/images/2015/08/31/yaziweiyu.gif",imageOptions);
    }

    public void onEventMainThread(String event) {

    }

    @Event(value = {R.id.go})
    private void gos(View view) {
        //  ToastUtils.show(LoginActivity.this, ":::::::::::::::::");

    }

    @Override
    protected void initDate() {
        try {
            db = x.getDb(((App) App.context).getDaoConfig());
        /*    List<Student> all = db.findAll(Student.class);
            for (Student s : all) {
                Logutils.a(s.getUsername() + "::" + s.getPassword());
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go://登陆




     /*           view = LayoutInflater.from(context).inflate(
                        R.layout.include_chat_add, null);
                Dialog dialog = new Dialog(LoginActivity.this, R.style.ActionSheetDialogStyle);
                view.setMinimumWidth(display.getWidth());
                dialog.setContentView(view);
                Window dialogWindow = dialog.getWindow();
                dialogWindow.setGravity(Gravity.BOTTOM);
                dialog.show();*/


                //   EventBus.getDefault().post("sadaaaaaaa");

                //    root.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

//                String s = username.getText().toString();
//                String str = String.format(s, new Date());
//                password.setText(str);
                //    checkNameAndPwd();
                break;
            case R.id.qq://登陆
                mTencent = Tencent.createInstance(APPID, this.getApplicationContext());
                if (!mTencent.isSessionValid()) {
                    mTencent.login(this, "all", loginListener);
                }

                break;
            case R.id.register://注册
                registerStudent();
                break;
        }
    }

    private void registerStudent() {
        final View view = View.inflate(context, R.layout.register, null);
        x.view().inject(view);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context).setTitle("注册")
                .setView(view).setCancelable(false).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rname_et = (EditText) view.findViewById(R.id.name);
                        rpwd_et1 = (EditText) view.findViewById(R.id.pwd1);
                        rpwd_et2 = (EditText) view.findViewById(R.id.pwd2);
                        String name = rname_et.getText().toString().trim();
                        String pwd1 = rpwd_et1.getText().toString().trim();
                        String pwd2 = rpwd_et2.getText().toString().trim();
                        try {
                            Student student = db.selector(Student.class).where("username", "=", name).findFirst();
//                            if (null != student) {
//                                if (student.getUsername().equals(name)) {
//                                    ToastUtils.show(context, "用户已存在");
//                                    return;
//                                }
//                            }
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd1) || TextUtils.isEmpty(pwd2)) {
                            ToastUtils.show(context, "请把注册资料填完整");
                            return;
                        }
                        if (!pwd1.equals(pwd2)) {
                            ToastUtils.show(context, "两次密码输入不一致");
                            return;
                        }
                        if (CheckMobileAndEmail.isPasswd(pwd1)) {
                            try {
                                db.saveOrUpdate(new Student(name, pwd1));
                                dialog.cancel();

                                UserModel.getInstance().register(name, pwd1, pwd2, new LogInListener() {
                                    @Override
                                    public void done(Object o, BmobException e) {
                                        if (e == null) {
                                            ToastUtils.show(context, "注册成功");
                                            //   EventBus.getDefault().post(new FinishEvent());
                                            GoMain();
                                        } else {
                                            if (e.getErrorCode() == BaseModel.CODE_NOT_EQUAL) {
                                                ToastUtils.show(context, "注册失败");
                                            }
                                            ToastUtils.show(LoginActivity.this, e.getMessage() + "(" + e.getErrorCode() + ")");
                                        }
                                    }
                                });
                            } catch (DbException e) {
                                e.printStackTrace();
                                ToastUtils.show(context, "注册失败");
                                return;
                            }
                        } else {
                            ToastUtils.show(context, "请输入6-18位密码");
                            return;
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialog.show();
    }

    private void checkNameAndPwd() {
        String pwd = password.getText().toString().trim();
        name = username.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(LoginActivity.this, "用户名为空", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Student where = db.selector(Student.class).where("username", "=", name).findFirst();
            if (null == where) {
                ToastUtils.show(context, "无此用户");
                return;
            } else if (!name.equals(where.getUsername()) || !pwd.equals(where.getPassword())) {
                ToastUtils.show(context, "用户名或密码错误");
                return;
            }
            UserModel.getInstance().login(name, pwd, new LogInListener() {

                @Override
                public void done(Object o, BmobException e) {
                    if (e == null) {
                        User user = (User) o;
                        Logutils.e("user:???" + user.toString());
                        GoMain();
                        //更新当前用户资料
                        BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getAvatar()));
                        //  startActivity(MainActivity.class);
                    } else {
                        Logutils.e(e.getMessage() + "(" + e.getErrorCode() + ")");
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GoMain() {
        Intent i = new Intent(context, MainActivity.class);
        startActivity(i);
        context.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }
    }

    public void getUserInfoInThread() {
        new Thread() {
            @Override
            public void run() {

            }
        }.start();
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());

        }
    };

    private UserInfo mInfo;

    private class BaseUiListener implements IUiListener {


        @Override//QQ登陆成功
        public void onComplete(Object o) {
            ToastUtils.show(context, "登陆成功");
            Logutils.e("信息：" + o.toString());
            doComplete((JSONObject) o);

            GoMain();
        }

        protected void doComplete(JSONObject values) {

        }

        @Override//失败
        public void onError(UiError e) {
            ToastUtils.show(context, "code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
        }

        @Override
        public void onCancel() {
            ToastUtils.show(context, "取消");
        }
    }
}
