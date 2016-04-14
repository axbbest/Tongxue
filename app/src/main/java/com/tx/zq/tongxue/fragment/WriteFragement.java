package com.tx.zq.tongxue.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tx.zq.tongxue.App;
import com.tx.zq.tongxue.R;
import com.tx.zq.tongxue.entity.People;
import com.tx.zq.tongxue.event.FirstEvent;
import com.tx.zq.tongxue.utils.ToastUtils;
import com.tx.zq.tongxue.utils.UiUtils;
import com.tx.zq.tongxue.view.PickerView;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/2/1.
 */
@ContentView(R.layout.writefragement)
public class WriteFragement extends Fragment implements View.OnClickListener, View.OnTouchListener {
    @ViewInject(R.id.save)
    private Button save;
    @ViewInject(R.id.sex)
    private EditText sex;
    @ViewInject(R.id.addr)
    private EditText addr;
    @ViewInject(R.id.age)
    private EditText age;
    @ViewInject(R.id.birth)
    private EditText birth;
    @ViewInject(R.id.hob)
    private EditText hob;
    @ViewInject(R.id.path)
    private EditText path;
    @ViewInject(R.id.xz)
    private EditText xz;
    @ViewInject(R.id.skill)
    private EditText skill;
    @ViewInject(R.id.content)
    private EditText content;
    @ViewInject(R.id.tel)
    private EditText tel;
    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.pic)
    private ImageView pic;
    @ViewInject(R.id.minute_pv)
    private PickerView minute_pv;
    @ViewInject(R.id.ok)
    private TextView ok;
    @ViewInject(R.id.rl)
    private RelativeLayout rl;

    @ViewInject(R.id.scroll)
    private ScrollView scroll;
    private DbManager db;
    private String realFilePath1;
    private ArrayList<String> a, b, s, xl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    private String sage;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListData();

        initData();
    }

    private void initListData() {
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);


        a = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            a.add(i + "");
        }
        s = new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            s.add(i == 0 ? "男" : "女");
        }

    }


    private void setPicView(ArrayList<String> a, final EditText age, int i) {
        minute_pv.setData(a);
        minute_pv.setSelected(i);
        age.setText(a.get(i));
        minute_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                age.setText(text);
            }
        });
    }

    private void initData() {
        save.setOnClickListener(this);
        db = x.getDb(((App) App.context).getDaoConfig());
        pic.setOnClickListener(this);
        ok.setOnClickListener(this);
        age.setOnClickListener(this);
        sex.setOnClickListener(this);
        birth.setOnClickListener(this);
        scroll.setOnTouchListener(this);
    }

    //当前系统的年月日
    private int mYear;
    private int mMonth;
    private int mDay;
    //回调函数，int year, int monthOfYear,int dayOfMonth三个参数为日期对话框设置的日期
    private DatePickerDialog.OnDateSetListener setDateCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            birth.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.birth:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), setDateCallBack, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;

            case R.id.sex:
                setPicView(s, sex, 1);

                vorg();
                break;
            case R.id.age:
                setPicView(a, age, 18);

                vorg();
                break;
            case R.id.ok:
                rl.setVisibility(View.GONE);
                break;
            case R.id.save:
                save();
                break;
            case R.id.pic:
                choicephotos();
                break;
        }
    }

    private void vorg() {
        rl.setVisibility(rl.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        if (rl.getVisibility() == View.VISIBLE)
            rl.requestFocus();
    }

    private void save() {
        boolean isok = false;
        String name = getString(this.name);
        String sex = getString(this.sex);
        String content = getString(this.content);
        String path = getString(this.path);
        String tel = getString(this.tel);
        if (TextUtils.isEmpty(name)) {
            ToastUtils.show(getActivity(), "尊姓大名是什么");
            return;
        }
        if (TextUtils.isEmpty(sex)) {
            ToastUtils.show(getActivity(), "大声告诉你的性别");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtils.show(getActivity(), "你就不想给我说什么吗？");
            return;
        }
        if (TextUtils.isEmpty(path)) {
            ToastUtils.show(getActivity(), "留下你最美的时刻让我思念");
            return;
        }
        if (TextUtils.isEmpty(tel)) {
            ToastUtils.show(getActivity(), "留下联系方式，我们聊聊好吗？");
            return;
        }
        try {
            List<People> peoples = db.selector(People.class).where("name", "=", name).findAll();
            if (null != peoples) {
                for (People p : peoples) {
                    if (!isok) {
                        if (p.getSex().equals(sex) && p.getTel().equals(tel)) {
                            isok = true;
                            ToastUtils.show(getActivity(), "你的好友已存在");
                            return;
                        }
                    }
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        People people = new People(name, sex, path, getString(age), getString(birth), getString(addr), getString(xz), tel, getString(hob), getString(skill), content);
        try {
            if (!isok) {
                db.save(people);
                EventBus.getDefault().post(new FirstEvent("保存成功"));
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final int P_ZOOM = 12;

    public void choicephotos() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        startActivityForResult(intent, P_ZOOM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            if (requestCode == P_ZOOM) {
                String paths = UiUtils.getRealFilePath(getActivity(), data.getData());
                if (paths != null) {
                    path.setText(paths);
                    pic.setImageBitmap(BitmapFactory.decodeFile(paths));
                }
            }
        }
    }

    public String getString(EditText s) {
        return s.getText().toString().trim();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!(v instanceof RelativeLayout)) {
                    rl.setVisibility(View.GONE);
                }
        }
        return false;
    }
}
